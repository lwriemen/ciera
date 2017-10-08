.// This archetype performs static analysis on the activities to check for
.// potential issues with referential integrity.
.//
.// For each create statement, it checks that:
.//   1. All identifiers for the instance are initialized before the end of the
.//      body
.//   2. The instance is not used in a relate statement for a formalized
.//      association in which it is the participant before all its identifying
.//      attributes are set (in the formalizing identifier)
.//
.// For each assignement of an identifying attribute (regular assignment or
.// relate statement for referential as identifying attribute), it checks that:
.//   1. There is a create statement earlier in the body
.//   2. It is not a reassigment
.//
.// The algorithm considers all "while" and "for" blocks potentially
.// unreachable. "if", "elif", and "else" blocks are considered potentially
.// unreachable, however, if an idntifing attribute is set in all blocks of an
.// "if" statement, it is considered to be guaranteed initialized.
.//
.// The algorithm considers assigment statements and relate statements for
.// assigning identifier attributes. It does not consider assigments as part
.// of expressions.
.//
.function emit_warning
  .param inst_ref smt
  .param string msg
  .select one act related by smt->ACT_BLK[R602]->ACT_ACT[R601]
  .print "Warning: ${act.Label} line: ${smt.LineNumber} -- ${msg}"
.end function
.//
.function is_fully_initialized
  .param inst_ref var
  .assign attr_ret = true
  .select one obj related by var->S_DT[R848]->S_IRDT[R17]->O_OBJ[R123]
  .if ( empty obj )
    .select one obj related by var->V_INT[R814]->O_OBJ[R818]
  .end if
  .select many oidas related by obj->O_ID[R104]->O_OIDA[R105]
  .select many oidis related by oidas->O_OIDI[R199] where ( selected.Var_ID == var.Var_ID )
  .assign card_oidis = cardinality oidis
  .assign attr_ret = ( ( attr_ret ) and ( ( cardinality oidas ) == ( cardinality oidis ) ) )
  .assign oida_card = cardinality oidas
  .assign oidi_card = cardinality oidis
  .for each oidi in oidis
    .assign attr_ret = ( ( attr_ret ) and ( oidi.initialized ) )
  .end for
.end function
.//
.function get_unique_id_dt_id
  .select any dt from instances of S_DT where ( selected.Name == "unique_id" )
  .assign attr_dt_id = dt.DT_ID
.end function
.//
.function check_creates
  .select many bodies from instances of ACT_ACT
  .invoke result = get_unique_id_dt_id()
  .assign unique_id = result.dt_id
  .for each body in bodies
    .// get all create no variables. For these statements, they are invalide
    .// unless they have no identifying attributes
    .select many create_nv_smts related by body->ACT_BLK[R601]->ACT_SMT[R602]->ACT_CNV[R603]
    .for each create_nv_smt in create_nv_smts
      .select any id_attr related by create_nv_smt->O_OBJ[R672]->O_ID[R104]->O_OIDA[R105]->O_ATTR[R105] where ( selected.DT_ID != unique_id )
      .if ( not_empty id_attr )
        .select one smt related by create_nv_smt->ACT_SMT[R603]
        .select one obj related by create_nv_smt->O_OBJ[R672]
        .invoke emit_warning( smt, "Create no variable statement used with class that has identifying attributes: ${obj.Key_Lett}" )
      .end if
    .end for
    .// get all create statements
    .select many create_smts related by body->ACT_BLK[R601]->ACT_SMT[R602]->ACT_CR[R603]
    .for each create_smt in create_smts
      .select one smt related by create_smt->ACT_SMT[R603]
      .select one obj related by create_smt->O_OBJ[R671]
      .select one var related by create_smt->V_VAR[R633]
      .select many oidas related by obj->O_ID[R104]->O_OIDA[R105]
      .for each oida in oidas
        .// set each oida uninitialized
        .// unique_id attrs are automatically initialized
        .select one attr related by oida->O_ATTR[R105]
        .create object instance oidi of O_OIDI
        .assign oidi.initialized = ( attr.DT_ID == unique_id )
        .relate var to oidi across R199
        .relate oida to oidi across R199
      .end for
      .// iterate through following statements
      .select one block related by smt->ACT_BLK[R602]
      .select one smt related by smt->ACT_SMT[R661.'precedes']
      .assign done = false
      .assign original_block = block
      .while ( ( not_empty block ) and ( not done ) )
        .while ( ( not_empty smt ) and ( not done ) )
          .// check assignment statement initializations
          .select one assign_smt related by smt->ACT_AI[R603]
          .if ( not_empty assign_smt )
            .select one assigned_to_oattr related by assign_smt->V_VAL[R689]->V_AVL[R801]->O_ATTR[R806]
            .select one assigned_to_obj related by assigned_to_oattr->O_OBJ[R102]
            .if ( assigned_to_obj == obj )
              .select many assigned_to_oidas related by assigned_to_oattr->O_OIDA[R105]
              .for each assigned_to_oida in assigned_to_oidas
                .select any oidi related by assigned_to_oida->O_OIDI[R199] where ( selected.Var_ID == var.Var_ID )
                .if ( oidi.initialized )
                  .invoke emit_warning( smt, "Reassignment of identifying attribute: ${var.Name}.${assigned_to_oattr.Name}" )
                .else
                  .assign oidi.initialized = true
                .end if
              .end for
            .end if
          .end if
          .// check relate statement initializations
          .select many ref_idas related by obj->O_ATTR[R102]->O_RATTR[R106]->O_ATTR[R106]->O_OIDA[R105]
          .if ( not_empty ref_idas )
            .select one relate_smt related by smt->ACT_REL[R603]
            .select one relate_using_smt related by smt->ACT_RU[R603]
            .if ( ( not_empty relate_smt ) or ( not_empty relate_using_smt ) )
              .select one rel related by relate_using_smt->R_REL[R654]
              .if ( empty rel )
                .select one rel related by relate_smt->R_REL[R653]
              .end if
              .select any rgo related by rel->R_OIR[R201]->R_RGO[R203] where ( selected.Obj_ID == obj.Obj_ID )
              .if ( not_empty rgo )
                .select one one_var related by relate_using_smt->V_VAR[R617]
                .select one oth_var related by relate_using_smt->V_VAR[R618]
                .select one use_var related by relate_using_smt->V_VAR[R619]
                .if ( ( empty one_var ) and ( empty oth_var ) )
                  .select one one_var related by relate_smt->V_VAR[R615]
                  .select one oth_var related by relate_smt->V_VAR[R616]
                .end if
                .if ( ( ( var == one_var ) or ( var == oth_var ) ) or ( var == use_var ) )
                  .for each ref_ida in ref_idas
                    .select any oidi related by ref_ida->O_OIDI[R199] where ( selected.Var_ID == var.Var_ID )
                    .if ( oidi.initialized )
                      .invoke emit_warning( smt, "Reassignment of identifying attributes through relate: ${var.Name}" )
                    .else
                      .assign oidi.initialized = true
                    .end if
                  .end for
                .end if
              .end if
            .end if
          .end if
          .// check if all are initialized
          .invoke result = is_fully_initialized( var )
          .assign done = result.ret
          .// select inner block for if statements
          .select one if_smt related by smt->ACT_IF[R603]
          .select one elif_smt related by smt->ACT_EL[R603]
          .select one else_smt related by smt->ACT_E[R603]
          .if ( ( ( not_empty if_smt ) or ( not_empty elif_smt ) ) or ( not_empty else_smt ) )
            .if ( not_empty if_smt )
              .select one block related by if_smt->ACT_BLK[R607]
            .end if
            .if ( not_empty elif_smt )
              .select one block related by elif_smt->ACT_BLK[R658]
            .end if
            .if ( not_empty else_smt )
              .select one block related by else_smt->ACT_BLK[R606]
            .end if
            .// select first statement in inner block
            .select any smt related by block->ACT_SMT[R602]
            .select one prev_smt related by smt->ACT_SMT[R661.'succeeds']
            .while ( not_empty prev_smt )
              .assign smt = prev_smt
              .select one prev_smt related by smt->ACT_SMT[R661.'succeeds']
            .end while
          .// select next statement
          .else
            .select one smt related by smt->ACT_SMT[R661.'precedes']
          .end if
        .end while
        .// select outer block and next statment
        .if ( block != original_block )
          .select one if_smt related by block->ACT_IF[R607]
          .select one elif_smt related by block->ACT_EL[R658]
          .select one else_smt related by block->ACT_E[R606]
          .if ( ( ( not_empty if_smt ) or ( not_empty elif_smt ) ) or ( not_empty else_smt ) )
            .if ( not_empty if_smt )
              .select one smt related by if_smt->ACT_SMT[R603]
            .end if
            .if ( not_empty elif_smt )
              .select one smt related by elif_smt->ACT_SMT[R603]
            .end if
            .if ( not_empty else_smt )
              .select one smt related by else_smt->ACT_SMT[R603]
            .end if
            .select one block related by smt->ACT_BLK[R602]
            .select one smt related by smt->ACT_SMT[R661.'precedes']
          .end if
        .else
          .assign done = true
        .end if
      .end while
      .// check if the identifiers are potentially uninitialized
      .invoke result = is_fully_initialized( var )
      .if ( not result.ret )
        .select one smt related by create_smt->ACT_SMT[R603]
        .invoke emit_warning( smt, "Instance identifiers may not have been initialized after create statement: ${obj.Key_Lett}" )
      .end if
    .end for .// for each create_smt in create_smts
  .end for .// for each body in bodies
.end function
.//
.function check_assignments
.end function
.//
.function analyze
  .invoke check_creates()
  .invoke check_assignments()
.end function
.//
.//------ MAIN CODE -------//
.invoke analyze()
