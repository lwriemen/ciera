T_b("package ");
T_b(self->package);
T_b(";");
T_b("\n");
T_b(imports);
T_b("\n");
T_b("public class ");
T_b(self->name);
T_b(" extends ");
T_b(extends);
T_b(" {");
T_b("\n");
T_b("    ");
T_b("private static final int relNum = ");
T_b(T_s(num));
T_b(";");
T_b("\n");
T_b("    ");
T_b("// one class");
T_b("\n");
T_b("    ");
T_b("private UniqueId ");
T_b(self->one_class_name);
T_b("");
if ( 0!=strcmp("",self->other_phrase) ) {
T_b("_");
T_b(T_l(T_underscore(self->other_phrase)));
T_b("_");
T_b(self->other_class_name);
T_b("");
}
T_b(";");
T_b("\n");
T_b("    ");
T_b("// other class");
T_b("\n");
T_b("    ");
T_b("private UniqueId ");
T_b(self->other_class_name);
T_b("");
if ( 0!=strcmp("",self->one_phrase) ) {
T_b("_");
T_b(T_l(T_underscore(self->one_phrase)));
T_b("_");
T_b(self->one_class_name);
T_b("");
}
T_b(";");
T_b("\n");
T_b("    ");
T_b("public ");
T_b(self->name);
T_b("( UniqueId ");
T_b(self->one_class_name);
T_b("");
if ( 0!=strcmp("",self->other_phrase) ) {
T_b("_");
T_b(T_l(T_underscore(self->other_phrase)));
T_b("_");
T_b(self->other_class_name);
T_b("");
}
T_b(", UniqueId ");
T_b(self->other_class_name);
T_b("");
if ( 0!=strcmp("",self->one_phrase) ) {
T_b("_");
T_b(T_l(T_underscore(self->one_phrase)));
T_b("_");
T_b(self->one_class_name);
T_b("");
}
T_b(" ");
T_b(") throws XtumlException {");
T_b("\n");
T_b("        ");
T_b("this.");
T_b(self->one_class_name);
T_b("");
if ( 0!=strcmp("",self->other_phrase) ) {
T_b("_");
T_b(T_l(T_underscore(self->other_phrase)));
T_b("_");
T_b(self->other_class_name);
T_b("");
}
T_b(" ");
T_b("= ");
T_b(self->one_class_name);
T_b("");
if ( 0!=strcmp("",self->other_phrase) ) {
T_b("_");
T_b(T_l(T_underscore(self->other_phrase)));
T_b("_");
T_b(self->other_class_name);
T_b("");
}
T_b(";");
T_b("\n");
T_b("        ");
T_b("this.");
T_b(self->other_class_name);
T_b("");
if ( 0!=strcmp("",self->one_phrase) ) {
T_b("_");
T_b(T_l(T_underscore(self->one_phrase)));
T_b("_");
T_b(self->one_class_name);
T_b("");
}
T_b(" ");
T_b("= ");
T_b(self->other_class_name);
T_b("");
if ( 0!=strcmp("",self->one_phrase) ) {
T_b("_");
T_b(T_l(T_underscore(self->one_phrase)));
T_b("_");
T_b(self->one_class_name);
T_b("");
}
T_b(";");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
T_b("    ");
T_b("@Override");
T_b("\n");
T_b("    ");
T_b("public UniqueId getOne() {");
T_b("\n");
T_b("        ");
T_b("return ");
T_b(self->one_class_name);
T_b("");
if ( 0!=strcmp("",self->other_phrase) ) {
T_b("_");
T_b(T_l(T_underscore(self->other_phrase)));
T_b("_");
T_b(self->other_class_name);
T_b("");
}
T_b(";");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
T_b("    ");
T_b("@Override");
T_b("\n");
T_b("    ");
T_b("public UniqueId getOther() {");
T_b("\n");
T_b("        ");
T_b("return ");
T_b(self->other_class_name);
T_b("");
if ( 0!=strcmp("",self->one_phrase) ) {
T_b("_");
T_b(T_l(T_underscore(self->one_phrase)));
T_b("_");
T_b(self->one_class_name);
T_b("");
}
T_b(";");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
T_b("    ");
T_b("@Override");
T_b("\n");
T_b("    ");
T_b("public int getNumber() {");
T_b("\n");
T_b("        ");
T_b("return relNum;");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
T_b("    ");
T_b("@Override");
T_b("\n");
T_b("    ");
T_b("public void delete() {");
T_b("\n");
T_b("    ");
T_b("}");
T_b("\n");
T_b("}");
T_b("\n");
