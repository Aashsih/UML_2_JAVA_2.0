package view_project;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Aashish Indorewala on 23-Sep-16.
 */
public class FieldLayout {
    //
    private Spinner fieldAccessModifier;
    private EditText fieldName;
    private EditText dataType;
    private CheckBox isFinal;
    private CheckBox isStatic;

    public FieldLayout(Spinner fieldAccessModifier, EditText fieldName, EditText dataType, CheckBox isFinal, CheckBox isStatic){
        this.fieldAccessModifier = fieldAccessModifier;
        this.fieldName = fieldName;
        this.dataType = dataType;
        this.isFinal = isFinal;
        this.isStatic = isStatic;
    }

    public Spinner getFieldAccessModifier() {
        return fieldAccessModifier;
    }

    public void setFieldAccessModifier(Spinner fieldAccessModifier) {
        this.fieldAccessModifier = fieldAccessModifier;
    }

    public EditText getFieldName() {
        return fieldName;
    }

    public void setFieldName(EditText fieldName) {
        this.fieldName = fieldName;
    }

    public EditText getDataType() {
        return dataType;
    }

    public void setDataType(EditText dataType) {
        this.dataType = dataType;
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
