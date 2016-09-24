package view_project;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Aashish Indorewala on 23-Sep-16.
 */
public class MethodLayout {

    private Spinner methodAccessModifier;
    private EditText methodName;
    private EditText parameters;
    private EditText returnType;
    private CheckBox isFinal;
    private CheckBox isStatic;

    public MethodLayout(Spinner methodAccessModifier, EditText methodName,EditText paramters, EditText returnType, CheckBox isFInal, CheckBox isStatic){
        this.methodAccessModifier = methodAccessModifier;
        this.methodName = methodName;
        this.parameters = paramters;
        this.returnType = returnType;
        this.isFinal = isFInal;
        this.isStatic = isStatic;
    }

    public Spinner getMethodAccessModifier() {
        return methodAccessModifier;
    }

    public void setMethodAccessModifier(Spinner methodAccessModifier) {
        this.methodAccessModifier = methodAccessModifier;
    }

    public EditText getMethodName() {
        return methodName;
    }

    public void setMethodName(EditText methodName) {
        this.methodName = methodName;
    }

    public EditText getParameters() {
        return parameters;
    }

    public void setParameters(EditText parameters) {
        this.parameters = parameters;
    }

    public EditText getReturnType() {
        return returnType;
    }

    public void setReturnType(EditText returnType) {
        this.returnType = returnType;
    }

    public CheckBox getIsFinal() {
        return isFinal;
    }

    public void setIsFinal(CheckBox isFinal) {
        this.isFinal = isFinal;
    }

    public CheckBox getIsStatic() {
        return isStatic;
    }

    public void setIsStatic(CheckBox isStatic) {
        this.isStatic = isStatic;
    }
}
