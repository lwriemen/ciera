T_b("package ");
T_b(self->package);
T_b(".impl;");
T_b("\n");
T_b(imports);
T_b("\n");
T_b("public class ");
T_b(self->name);
T_b("Impl extends ");
T_b(self->extends);
T_b(" implements ");
T_b(self->name);
T_b(" {");
T_b("\n");
T_b("    ");
T_b("// attributes");
T_b("\n");
T_b(attributes);
T_b("\n");
T_b("    ");
T_b("// selections");
T_b("\n");
T_b(selectors);
T_b("\n");
T_b("    ");
T_b("@Override");
T_b("\n");
T_b("    ");
T_b("public ");
T_b(self->class_name);
T_b(" nullElement() {");
T_b("\n");
T_b("        ");
T_b("return ");
T_b(self->class_name);
T_b("Impl.EMPTY_");
T_b(T_underscore(T_u(self->class_name)));
T_b(";");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
T_b("    ");
T_b("@Override");
T_b("\n");
T_b("    ");
T_b("public ");
T_b(self->name);
T_b(" emptySet() {");
T_b("\n");
T_b("      ");
T_b("return new ");
T_b(self->name);
T_b("Impl();");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
T_b("    ");
T_b("@Override");
T_b("\n");
T_b("    ");
T_b("public ");
T_b(self->name);
T_b(" value() {");
T_b("\n");
T_b("        ");
T_b("return this;");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
T_b("}");
T_b("\n");
