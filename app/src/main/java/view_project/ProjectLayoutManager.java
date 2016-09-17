package view_project;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aashish Indorewala on 27-Aug-16.
 */
public class ProjectLayoutManager {
    //private static int CLASS_ID = 0;
    //Navigation VIew
    private List<CheckBox> classList = null;
    private List<UmlLayout> umlFragments = null;
    public ProjectLayoutManager(){
        classList = new ArrayList<>();
        umlFragments = new ArrayList<>();
    }

    public final void addCheckBox(CheckBox checkBox){
        //CLASS_ID++;
        this.classList.add(checkBox);
        this.umlFragments.add(new UmlLayout());

    }

    public final List<CheckBox> getClassList(){
        return classList;
    }

//    public final int getClassId(){
//        return CLASS_ID;
//    }

    public final UmlLayout getUmlFragment(int position){
        return this.umlFragments.get(position);
    }

    public List<CheckBox> getCheckedCheckBoxes(){
        List<CheckBox> checkedCheckBoxes = new ArrayList<CheckBox>();
        for(CheckBox checkBox : classList){
            if(checkBox.isChecked()){
                checkedCheckBoxes.add(checkBox);
            }
        }
        return checkedCheckBoxes;
    }


//    //UML layout for a class (Control talking to presentation)
//    private EditText className;
//    private Spinner classType;
//    List<FieldLayout> fields;
//    List<MethodLayout> methods;
//
//    //Field
//    private class FieldLayout{
//        private Spinner fieldAccessModifier;
//        private EditText fieldName;
//        private EditText dataType;
//        private CheckBox isFinal;
//        private CheckBox isStatic;
//
//        public FieldLayout(Spinner fieldAccessModifier, EditText fieldName, EditText dataType, CheckBox isFinal, CheckBox isStatic){
//            this.fieldAccessModifier = fieldAccessModifier;
//            this.fieldName = fieldName;
//            this.dataType = dataType;
//            this.isFinal = isFinal;
//            this.isStatic = isStatic;
//        }
//    }
//
//    //Method
//    private class MethodLayout{
//        private Spinner methodAccessModifier;
//        private EditText methodName;
//        private EditText parameters;
//        private EditText returnType;
//        private CheckBox isFInal;
//        private CheckBox isStatic;
//
//        public MethodLayout(Spinner methodAccessModifier, EditText methodName,EditText paramters, EditText returnType, CheckBox isFInal, CheckBox isStatic){
//            this.methodAccessModifier = methodAccessModifier;
//            this.methodName = methodName;
//            this.parameters = paramters;
//            this.returnType = returnType;
//            this.isFInal = isFInal;
//            this.isStatic = isStatic;
//        }
//    }

}
