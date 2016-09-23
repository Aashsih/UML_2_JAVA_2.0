package uml_to_java;

import java.util.ArrayList;
import java.util.List;

import project.IProject;
import uml_components.IMethod;
import uml_components.IUML;
import uml_components.IVariable;
import utils.AccessModifier;

/**
 * Created by Aashish Indorewala on 23-Sep-16.
 */
public class ConvertToJava {

    private IProject project;

    public ConvertToJava(IProject project){
        if(project == null){
            //throw an exception
        }
        this.project = project;
    }

    public final List<StringBuilder> getJavaCode(){
        List<StringBuilder> javaCode = new ArrayList<>(this.project.getUmlList().size());

        for(IUML uml : this.project.getUmlList()){
            javaCode.add(getClassCode(uml));
        }

        return javaCode;

    }

    private final StringBuilder getClassCode(IUML uml){
        StringBuilder classCode = new StringBuilder("public");
        classCode.append(getClassTypeInformation(uml));

        classCode.append(getFieldsCode(uml.getVariableList()));
        classCode.append(getMethodsCode(uml.getMethodList()));

        classCode.append("}");
        return classCode;
    }

    private final StringBuilder getClassTypeInformation(IUML uml){
        StringBuilder classTypeInformation = new StringBuilder();

        switch(uml.getClassType()){
            case CLASS :
            {
                classTypeInformation.append("class" + uml.getClassName() + "{\n");
            }
//            case INTERFACE:
//            {
//                classTypeInformation.append("interface" + uml.getClassName() + "{\n");
//            }
//            case ENUMERATION:
//            {
//                classTypeInformation.append("enum" + uml.getClassName() + "{\n");
//            }
            default:
            {
                classTypeInformation.append("class" + uml.getClassName() + "{\n");
            }

        }

        return classTypeInformation;
    }

    private final StringBuilder getFieldsCode(List<IVariable> fields){
        StringBuilder fieldCode = new StringBuilder();

        for(IVariable field : fields){
            fieldCode.append("\t" + getAccessModifier(field.getAccessModifier()));
            fieldCode.append(isStatic(field.isStatic()));
            fieldCode.append(isFinal(field.isFinal()));
            fieldCode.append(field.getVarName() + ";\n");
        }

        return fieldCode;
    }
    private final StringBuilder getMethodsCode(List<IMethod> methods){
        StringBuilder methodCode = new StringBuilder();

        for(IMethod method : methods){
            methodCode.append("\t" + getAccessModifier(method.getAccessModifier()));
            methodCode.append(isStatic(method.isStatic()));
            methodCode.append(isFinal(method.isFinal()));
            methodCode.append(method.getReturnType() + " ");
            methodCode.append(method.getMethodName() + "(");
            //the following can be made better by making an enum for primitives and
            //then returnig the default value
            methodCode.append(method.getParameters() + "){\n");
            methodCode.append("\t\treturn " + getReturnType(method.getReturnType()) + ";\n");
            methodCode.append("\t}");

        }

        return methodCode;
    }

    private final String getReturnType(String returnType){
        switch (returnType){
            case "int":
            case "float":
            case "double":
            case "char":
            {
                return "0";
            }
            case "boolean":
            {
                return "false";
            }
            default:
            {
                return "null";
            }

        }

    }

    private final StringBuilder getAccessModifier(AccessModifier accessModifier){
        StringBuilder accessModifierString = new StringBuilder();

        switch(accessModifier){
            case PUBLIC:
            {
                accessModifierString.append("public ");
            }
            case PRIVATE:
            {
                accessModifierString.append("private ");
            }
            case PROTECTED:
            {
                accessModifierString.append("protected ");
            }
            case DEFAULT:
            default:
            {
                accessModifierString.append("");
            }

        }

        return accessModifierString;
    }
    private final String isStatic(boolean isStatic){
        if(isStatic){
            return "static ";
        }
        return "";
    }
    private final String isFinal(boolean isStatic){
        if(isStatic){
            return "final ";
        }
        return "";
    }

//    public static void main(String[] args){
//
//    }
}
