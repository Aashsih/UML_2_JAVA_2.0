package uml_to_java;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import project.IProject;
import uml_components.IMethod;
import uml_components.IUML;
import uml_components.IVariable;
import utils.AccessModifier;

/**
 * Created by Aashish Indorewala on 23-Sep-16.
 *
 * This class is used to convert a Project Object into Java code.
 * It converts the Project into a String representation of Java code
 */
public class ConvertToJava {

    //Project to be converted into Java code
    private IProject project;


    public ConvertToJava(IProject project){
        if(project == null){
            //throw an exception
        }
        this.project = project;
    }

    /**
     * This methods returns a List of StringBuilder Objects where each
     * object contains the Java code for one class.
     */
    public final List<StringBuilder> getJavaCode(){
        List<StringBuilder> javaCode = new ArrayList<>(this.project.getUmlList().size());
        if(project != null){
            for(IUML uml : this.project.getUmlList()){//for all classes
                javaCode.add(getClassCode(uml));//Add code for one class
            }
        }

        return javaCode;

    }
    /**
     * This Class returns the Java code for one class
     */
    private final StringBuilder getClassCode(IUML uml){
        StringBuilder classCode = new StringBuilder("");
        if(!(uml.getImportStatements() == null || uml.getImportStatements() == "")){
            String[] importStatements = uml.getImportStatements().split("\\n+");
            for(String anImportStatement : importStatements){
                classCode.append("import " + anImportStatement + ";\n");
            }
            classCode.append("\n");
        }
        classCode.append("public");
        classCode.append(getClassTypeInformation(uml)); //Add ClassType

        classCode.append(getFieldsCode(uml.getVariableList()));//Get Java Code for Fields of a class
        classCode.append(getMethodsCode(uml.getMethodList()));//Get Java Code for methods of a class

        classCode.append("}");//Closing bracket for the class
        return classCode;
    }

    /**
     * Gets the type of the class.
     * Currently it only supports the type "class", at a later stage I might add
     * other types like, "enum" or "interface"
     *
     */
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

    /**
     * Gets the code for all the fields in the Class
     *
     */
    private final StringBuilder getFieldsCode(List<IVariable> fields){
        StringBuilder fieldCode = new StringBuilder();

        for(IVariable field : fields){//for all fields
            fieldCode.append("\t" + getAccessModifier(field.getAccessModifier()));//indent and get AccessModifier
            fieldCode.append(isStatic(field.isStatic()));//append "static" if the field is static
            fieldCode.append(isFinal(field.isFinal()));//append "final" if the field is final
            fieldCode.append(field.getVarName() + ";\n");//append the "fieldName"
        }

        return fieldCode;
    }

    /**
     * Gets the code for all the methods in the class
     */
    private final StringBuilder getMethodsCode(List<IMethod> methods){
        StringBuilder methodCode = new StringBuilder();

        for(IMethod method : methods){//for all methods in the class
            methodCode.append("\t" + getAccessModifier(method.getAccessModifier()));
            methodCode.append(isStatic(method.isStatic()));
            methodCode.append(isFinal(method.isFinal()));
            methodCode.append(method.getReturnType() + " ");
            methodCode.append(method.getMethodName() + "(");
            //the following can be made better by making an enum for primitives and
            //then returnig the default value
            methodCode.append(method.getParameters() + "){\n");
            methodCode.append("\t\treturn " + getReturnType(method.getReturnType()) + ";\n");//add the appropriate return statement
            methodCode.append("\t}");

        }

        return methodCode;
    }

    /**
     * Builds the appropriate return statement for the class
     *
     */
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

    /**
     * Gets the correct AccessModifier : public, private, protected
     *
     */
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

    /**
     *
     * Appends "static" if isStaic is true
     */
    private final String isStatic(boolean isStatic){
        if(isStatic){
            return "static ";
        }
        return "";
    }
    /**
     *
     * Appends "final" if isFinalis true
     */
    private final String isFinal(boolean isFinal){
        if(isFinal){
            return "final ";
        }
        return "";
    }

//    public static void main(String[] args){
//
//    }
}
