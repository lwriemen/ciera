T_b("package ");
T_b(self->package);
T_b(".impl;");
T_b("\n");
T_b(imports);
T_b("\n");
T_b("public class ");
T_b(self->name);
T_b("Impl<C extends IComponent<C>> extends Utility<C> implements ");
T_b(self->name);
T_b(" {");
T_b("\n");
T_b("    ");
T_b("public ");
T_b(self->name);
T_b("Impl( C population ) {");
T_b("\n");
T_b("        ");
T_b("super( population );");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
T_b(utility_functions);
T_b("\n");
T_b("}");
T_b("\n");