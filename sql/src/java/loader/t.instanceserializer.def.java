T_b("    ");
T_b("public void serialize_");
T_b(self->class_name);
T_b("( ");
T_b(self->class_name);
T_b(" ");
T_b(T_l(self->class_name));
T_b("_inst, PrintStream out ) throws XtumlException {");
T_b("\n");
T_b("        ");
T_b("out.print( \"INSERT INTO ");
T_b(self->class_key_letters);
T_b(" VALUES(\" );");
T_b("\n");
T_b(attribute_serializers);
T_b("        out.println( \");\" );");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");