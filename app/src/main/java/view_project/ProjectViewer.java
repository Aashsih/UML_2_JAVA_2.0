package view_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.head_first.aashi.uml_2_java.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import project.IProject;
import project.Project;
import uml_components.IUML;
import uml_to_java.ConvertToJava;

import static com.head_first.aashi.uml_2_java.R.id.umlLayout;

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
    private boolean fileSaved;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_view);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        DrawerLayout slidingMenu = (DrawerLayout)findViewById(R.id.drawerLayout);
        //slidingMenu.openDrawer(Gravity.LEFT);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);

        drawerListener = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerListener);
        drawerListener.syncState();

        //Get the project that the user selected to be opened in the ProjectHome activity
        IProject currentProject = (IProject)getIntent().getSerializableExtra(Project.PROJECT_TAG);
        this.projectManager = new ProjectLayoutManager(currentProject,this);
        this.setupProject(this);

    }

    @Override
    public void onPostCreate(Bundle saveInstanceState){
        super.onPostCreate(saveInstanceState);
        drawerListener.syncState();
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        ((LinearLayout)findViewById(R.id.projectPageLayout)).setVisibility(View.VISIBLE);
    }

//    private final Fragment getActiveFragment(){
//        List<Fragment> fragments = getSupportFragmentManager().getFragments();
//        if(fragments != null){
//            for(Fragment fragment : fragments){
//                if(fragment != null && fragment.isVisible())
//                    return fragment;
//            }
//        }
//        return null;
//    }

    /**
     *  Helper Method
     * When a saved Project is opened by a user this method is used to setup the
     * layout of the ProjectViewer class and also setup the data in this class
     */
    public void setupProject(ProjectViewer currentProjectView){
        currentProjectView.setProjectName();
        for(int i = 0; i < this.projectManager.getProject().getUmlList().size(); i++){

            currentProjectView.setupCheckBoxForAClass(false, i);//Create and setup a checkbox with the correct class name, then add it to the SlidingMenu

        }
    }
    /**
     *
     * This method is called when the Add Class button is cliked from the Sliding Menu.
     * 1. Adds a new CheckBox for the Class, sets its Id and Text
     * 2. Initializes The UmlLayout (Fragment) for the class
     */
    public void addClass(View view){
        setupCheckBoxForAClass(true,0);
    }

    /**
     * This method adds a CheckBox to the Sliding Menu of the project.
     * @param isNewProject is used to tell the method if a new CheckBox needs to be setup in a new Project
     *                     or if a new CheckBox needs to be setUp from a saved project
     * @param classPosition specifies the position of the class in the ProjectLayoutManager umlList to get the correct classs name
     */
    public void setupCheckBoxForAClass(boolean isNewProject, int classPosition){
        LinearLayout classList = (LinearLayout) findViewById(R.id.classList);
        View aClass = getLayoutInflater().inflate(R.layout.classname,null);
        CheckBox aClassCheckBox = (CheckBox)aClass.findViewById(R.id.checkBox);
        aClassCheckBox.setId(projectManager.getClassList().size());
        if(isNewProject){//if a new project, setup a default check box with standard Class Name
            aClassCheckBox.setText("Class " + this.projectManager.getClassId());
        }
        else{
            //if opening a saved project, then set the CheckBox text to the className that the user saved
            String className = this.projectManager.getProject().getUmlList().get(classPosition).getClassName();
            aClassCheckBox.setText(className);
        }
        classList.addView(aClass);
        projectManager.addCheckBox(aClassCheckBox, isNewProject);


    }

    /**
     * This method is used when a saved project is being opened to set
     * the project name.
     */
    public final void setProjectName(){
        EditText projectName = (EditText) findViewById(R.id.projectName);
        projectName.setText(this.projectManager.getProject().getProjectName());

    }

    /**
     * This method is called when the View button from the Sliding Menu is clicked.
     *
     */
    public void editClass(View view){
        //Get List of classes that the user has selected in the SlidingMenu
        List<CheckBox> selectedClasses = projectManager.getCheckedCheckBoxes();

        switch(selectedClasses.size()){
            case 0: break; //If no classes are selected
            case 1: //If one class is selected, allow the user to editClass the class (UmlLayout Fragment)
            {
                //when only one class needs to be viewed
                FragmentTransaction aTransaction = getSupportFragmentManager().beginTransaction();
                //get index of the selected class
                int indexOfClass = projectManager.getClassList().indexOf(selectedClasses.get(0));
                //Get the fragment that needs to be Viewed
                UmlLayout umlLayout = projectManager.getUmlFragment(indexOfClass);

                //Replace the old Fragment with the selected UmlLayout Fragment
                aTransaction.replace(R.id.projectPage,umlLayout);
                ((LinearLayout)findViewById(R.id.projectPageLayout)).setVisibility(View.INVISIBLE);
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

    public void viewClass(View view){

        List<CheckBox> selectedClasses = projectManager.getCheckedCheckBoxes();

        switch(selectedClasses.size()){

            case 0: break; //If no classes are selected
            case 1: //If one class is selected, allow the user to editClass the class (UmlLayout Fragment)
            {
                //when only one class needs to be viewed
                FragmentTransaction aTransaction = getSupportFragmentManager().beginTransaction();
                //get index of the selected class
                int indexOfClass = projectManager.getClassList().indexOf(selectedClasses.get(0));
                //Get the fragment that needs to be Viewed
                TemplateLayout templateLayout = projectManager.getTemplateFragment(indexOfClass);
                // UmlLayout umlLayout = projectManager.getUmlFragment(indexOfClass);

                //Replace the old Fragment with the selected UmlLayout Fragment
                aTransaction.replace(R.id.projectPage,templateLayout);
                ((LinearLayout)findViewById(R.id.projectPageLayout)).setVisibility(View.INVISIBLE);
                aTransaction.addToBackStack(null);
                aTransaction.commit();
                drawerLayout.closeDrawers();//Close the Sliding Menu
//                //Pass the index of the selected class to the UmlLayout
//                umlLayout.setClassPositionInProject(indexOfClass);
                //If the Fragment has a saved state then restore it
                if(projectManager.getProject().getUmlList().size() > indexOfClass){
                    templateLayout.setUml(projectManager.getProject().getUmlList().get(indexOfClass));

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
            if(this.projectManager.getProject() == null){
                Toast.makeText(this,"Nothing to save",Toast.LENGTH_SHORT).show();
            }
            else{
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
                    fileSaved = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


        }
    }


    /**
     * This method executes the  Delete Project button from the Sliding Menu.
     * The method first searches for the Project file in the internal storage
     *  and if found it is deleted.
     */
    public void onDeleteProject(View v){

        if(v.getId()==R.id.deleteProject){

                String fileName = ((EditText)findViewById(R.id.projectName)).getText().toString();
                if(!fileName.isEmpty()){

                    try {
                        fileName = fileName + ".ser";
                        File dir = getApplicationContext().getFilesDir();
                        File file = new File(dir, fileName);
                        boolean deleted = file.delete();
                        if(deleted){Toast.makeText(this,"Project Deleted", Toast.LENGTH_SHORT).show();}
                        else{Toast.makeText(this,"Project has not been saved!", Toast.LENGTH_SHORT).show();}

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                else{
                    Toast.makeText(this,"Project does not have a valid name",Toast.LENGTH_SHORT).show();
                }
            }



        }
    /**
     * This method is executed when the Conver To Java button from the Sliding Menu is Clicked.
     * This Methods converts the current state of the Project into Java code which is
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
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(umlList.get(i).getClassName()+".java", Context.MODE_PRIVATE));
                outputStreamWriter.write(projectCode.get(i).toString());
                outputStreamWriter.close();
                 buffer += projectCode.get(i).toString();
                    buffer+="\n\n";

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        //The body of the File being shared (This will be the toString representation of each Class)
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, buffer);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
        Toast.makeText(getApplicationContext(),"File Saved Successfully", Toast.LENGTH_LONG).show();


    }

    public void deleteSelectedClasses(View view){
        List<CheckBox> selectedClasses = this.projectManager.getCheckedCheckBoxes();
        if(selectedClasses.size() == 0){
            Toast.makeText(this, "No Class Selected", Toast.LENGTH_SHORT).show();
        }
        else{
            for(CheckBox aClass : selectedClasses){
                int indexOfClass = projectManager.getClassList().indexOf(aClass);
                aClass.setVisibility(View.GONE);
                ((LinearLayout) findViewById(R.id.classList)).removeView(aClass);
                this.projectManager.getProject().getUmlList().remove(indexOfClass);
                this.projectManager.getClassList().remove(indexOfClass);
                this.projectManager.deleteUMLFragment(indexOfClass);
            }
        }

    }
    //get ProjectLayout Manager
    public ProjectLayoutManager getProjectManager(){
        return projectManager;
    }
}
