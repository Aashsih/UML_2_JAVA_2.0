package view_project;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.head_first.aashi.uml_2_java.R;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import project.IProject;
import uml_components.IUML;
import uml_to_java.ConvertToJava;

/**
 * This class contains the Project Activity.
 * This Activity (DrawerLayout) will start of to be empty if a new Project is created or
 * will be populated with the stored work of the user
 *
 * Note: This sprint is only limited to saving the user's work
 */
public class ProjectViewer extends AppCompatActivity {

    //Communicates between the UI components and the Project
    private ProjectLayoutManager projectManager = new ProjectLayoutManager();

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_view);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        DrawerLayout slidingMenu = (DrawerLayout)findViewById(R.id.drawerLayout);
        slidingMenu.openDrawer(Gravity.LEFT);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);

        drawerListener = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerListener);
        drawerListener.syncState();
    }

    @Override
    public void onPostCreate(Bundle saveInstanceState){
        super.onPostCreate(saveInstanceState);
        drawerListener.syncState();
    }
    @Override
    public void onBackPressed()
    {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            finish();
        }
        else{
            drawerLayout.openDrawer(Gravity.LEFT);
        }

    }
    /**
     *
     * This method is called when the Add Class button is cliked from the Sliding Menu.
     * 1. Adds a new CheckBox for the Class, sets its Id and Text
     * 2. Initializes The UmlLayout (Fragment) for the class
     */
    public void addClass(View view){
        LinearLayout classList = (LinearLayout) findViewById(R.id.classList);
        View aClass = getLayoutInflater().inflate(R.layout.classname,null);
        CheckBox aClassCheckBox = (CheckBox)aClass.findViewById(R.id.checkBox);
        aClassCheckBox.setId(projectManager.getClassList().size());
        aClassCheckBox.setText("Class" + (projectManager.getClassList().size() + 1));
        classList.addView(aClass);
        projectManager.addCheckBox(aClassCheckBox);

    }

    /**
     * This method is called when the View button from the Sliding Menu is clicked.
     *
     */
    public void viewClass(View view){
        //Get List of classes that the user has selected in the SlidingMenu
        List<CheckBox> selectedClasses = projectManager.getCheckedCheckBoxes();

        switch(selectedClasses.size()){
            case 0: break; //If no classes are selected
            case 1: //If one class is selected, allow the user to edit the class (UmlLayout Fragment)
            {
                //when only one class needs to be viewed
                FragmentTransaction aTransaction = getSupportFragmentManager().beginTransaction();
                //get index of the selected class
                int indexOfClass = projectManager.getClassList().indexOf(selectedClasses.get(0));
                //Get the fragment that needs to be Viewed
                UmlLayout umlLayout = projectManager.getUmlFragment(indexOfClass);

                //Replace the old Fragment with the selected UmlLayout Fragment
                aTransaction.replace(R.id.frameLayout,umlLayout);
                aTransaction.addToBackStack(null);
                aTransaction.commit();
                drawerLayout.closeDrawers();//Close the Sliding Menu
                //Pass the index of the selected class to the UmlLayout
                umlLayout.setClassPositionInProject(indexOfClass);
                //If the Fragment has a saved state then restore it
                if(projectManager.getProject().getUmlList().size() > indexOfClass){
                    umlLayout.setUML(projectManager.getProject().getUmlList().get(indexOfClass));

                }

            }
            default:
            {
                //this is where multiple classes will be viewed together
            }
        }
    }

    /**
     *
     * This method is executed when the user clicks on the Select All button from the SlidingMenu.
     * This methods sets the isChecked attribute for all the CheckBoxes to true
     *
     */
    public void selectAll(View view){

        for(CheckBox aClass : projectManager.getClassList()){//for all CheckBoxes in the Project
            aClass.setChecked(true);//Set Checked to true
        }
    }

    /**
     *
     * This method is executed when the user clicks on the Deselect All button from the SlidingMenu.
     * This methods sets the isChecked attribute for all the CheckBoxes to false
     *
     */
    public void deselectAll(View view){

        for(CheckBox aClass : projectManager.getClassList()){//for all CheckBoxes in the Project
            aClass.setChecked(false);//Set Checked to false
        }
    }

    /**
     * This method is executed the user click the Save Project button from the Sliding Menu.
     * The methods store the current state of the Project as a ".ser" file in the App directory
     */
    public void onSaveProject(View v){

        if(v.getId()==R.id.saveProject){
            IProject currentProject = this.projectManager.getProject();
            currentProject.setProjectName(((EditText)findViewById(R.id.projectName)).getText().toString());
            try {
                String fileName = currentProject.getProjectName() + ".ser";
                FileOutputStream fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(currentProject);
                objectOutputStream.close();
                fileOutputStream.close();
                Toast.makeText(this,"Project Saved", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }

//            Other Method
//            SharedPreferences myPreferns = getPreferences(MODE_PRIVATE);
//
//            Editor prefsEditor = myPreferns.edit();
//            Gson gson = new Gson();
//            String json = gson.toJson(myObject); // myObject
//            prefsEditor.putString("MyObject", json);
//            prefsEditor.commit();

        }
    }

    /**
     * This method is executed when the Convert To Java button from the Sliding Menu is Clicked.
     * This Methods converts the current state of the Project into Java code which can be shared via different SHaring Applications
     * stored as .java file temporarily and then emailed to the user
     *
     */
    public void convertToJava(View v){
        ConvertToJava javaCode = new ConvertToJava(this.projectManager.getProject());
        List<StringBuilder> projectCode = javaCode.getJavaCode();
        List<IUML> umlList = this.projectManager.getProject().getUmlList();
        String javaFileTOShare = "";
        String buffer = "";
        // sharingIntent used to share multiple Java classes from the Project File
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);

        //The File Type to be shared
        sharingIntent.setType("plain/text");
        //The Title of the File being Shared (This will be the title of the Class)
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,this.projectManager.getProject().getProjectName());

        for(int i = 0; i < umlList.size(); i++){
            try {
                 buffer += projectCode.get(i).toString();
                    buffer+="\n\n";

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        //The body of the File being shared (This will be the toString representation of each Class)
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, buffer);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
        Toast.makeText(getApplicationContext(),"Successfully Stored The File to Share", Toast.LENGTH_LONG).show();


    }
    //get ProjectLayout Manager
    public ProjectLayoutManager getProjectManager(){
        return projectManager;
    }
}
