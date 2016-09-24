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

import project.IProject;
import project.Project;
import uml_components.UML;

/**
 * Created by Aashish Indorewala on 27-Aug-16.
 *
 * This class Controls communication between the ProjectViewer class and the Project class
 *
 * It stores both the UI components and the DataStructure Project and is used to communicate
 * for all creating, storing and editing option
 */
public class ProjectLayoutManager {
    //Abstraction
    private IProject project;

    //Navigation VIew -- Control
    private List<CheckBox> classList = null;//Stores CheckBox references for all the Classes created in the current project
    private List<UmlLayout> umlFragments = null;//Stores all the Fragments of type UmlLayout
    public ProjectLayoutManager(){
        project = new Project();
        classList = new ArrayList<>();
        umlFragments = new ArrayList<>();
    }

    /**
     * This class adds a checkbox for each Class that the user creates.
     *
     */
    public final void addCheckBox(CheckBox checkBox){
        //CLASS_ID++;
        this.classList.add(checkBox);//Store the CheckBox reference
        this.project.getUmlList().add(new UML());//add a new UML Class to the project to be stored
        this.umlFragments.add(new UmlLayout());//Create the UmlLayout Fragment to be displayed

    }

    /**
     * This method returns all the Classes that have been checked (selected by the user)
     * This method can be used to:
     * 1. Delete
     * 2. View ..multiple classes
     *
     */
    public List<CheckBox> getCheckedCheckBoxes(){
        List<CheckBox> checkedCheckBoxes = new ArrayList<CheckBox>();
        for(CheckBox checkBox : classList){
            if(checkBox.isChecked()){
                checkedCheckBoxes.add(checkBox);
            }
        }
        return checkedCheckBoxes;
    }
    //Getters and Setters
    public final IProject getProject(){
        return project;
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


}
