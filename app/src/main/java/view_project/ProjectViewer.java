package view_project;

import android.content.Context;
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

public class ProjectViewer extends AppCompatActivity {

    private ProjectLayoutManager projectManager = new ProjectLayoutManager();
    private DrawerLayout drawerLayout;// = new DrawerLayout();
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

    public void addClass(View view){
        LinearLayout classList = (LinearLayout) findViewById(R.id.classList);
        View aClass = getLayoutInflater().inflate(R.layout.classname,null);
        CheckBox aClassCheckBox = (CheckBox)aClass.findViewById(R.id.checkBox);
//        aClassCheckBox.setId(projectManager.getClassId());
//        aClassCheckBox.setText("Class" + (projectManager.getClassId() + 1));
        aClassCheckBox.setId(projectManager.getClassList().size());
        aClassCheckBox.setText("Class" + (projectManager.getClassList().size() + 1));
        //Dont delete or uncomment the following code before discussing with Aashish
//        aClassCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                //projectManager.getCheckedCheckBoxes() == 1 -> if more than 1
//                // check boxes are checked then we want to display the uml relationship diagram
//                if(isChecked && projectManager.getCheckedCheckBoxes() == 1){
//                    //launch an activity for displaying classes
//                    FragmentTransaction aTransaction = getSupportFragmentManager().beginTransaction();
//                    aTransaction.replace(R.id.frameLayout,
//                            projectManager.getUmlFragment(projectManager.getClassList().indexOf((CheckBox)buttonView)));
//                    aTransaction.addToBackStack(null);
//                    aTransaction.commit();
//                    drawerLayout.closeDrawers();
//                }
//            }
//        });
        classList.addView(aClass);
        projectManager.addCheckBox(aClassCheckBox);

    }

    public void viewClass(View view){
        List<CheckBox> selectedClasses = projectManager.getCheckedCheckBoxes();
        switch(selectedClasses.size()){
            case 0: break;
            case 1:
            {
                //when only one class needs to be viewed
                FragmentTransaction aTransaction = getSupportFragmentManager().beginTransaction();
                int indexOfClass = projectManager.getClassList().indexOf(selectedClasses.get(0));
                UmlLayout umlLayout = projectManager.getUmlFragment(indexOfClass);


                aTransaction.replace(R.id.frameLayout,umlLayout);
                aTransaction.addToBackStack(null);
                aTransaction.commit();
                drawerLayout.closeDrawers();

                umlLayout.setClassPositionInProject(indexOfClass);
                if(projectManager.getProject().getUmlList().size() > indexOfClass){
                    umlLayout.setUML(projectManager.getProject().getUmlList().get(indexOfClass));

                }
                //drawerLayout.closeDrawers();
                //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
            default:
            {
                //this is where multiple classes will be view together
            }
        }
    }

    @Override
    public void onPostCreate(Bundle saveInstanceState){
        super.onPostCreate(saveInstanceState);
        drawerListener.syncState();
    }

    public ProjectLayoutManager getProjectManager(){
        return projectManager;
    }
    @Override
    public void onBackPressed()
    {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        }
        else{
            drawerLayout.openDrawer(Gravity.LEFT);
        }

    }
    public void selectAll(View view){

        for(CheckBox aClass : projectManager.getClassList()){
            aClass.setChecked(true);
        }
    }

    public void deselectAll(View view){

        for(CheckBox aClass : projectManager.getClassList()){
            aClass.setChecked(false);
        }
    }
    public void onSaveProject(View v){

        if(v.getId()==R.id.saveProject){
            IProject currentProject = this.projectManager.getProject();
            currentProject.setProjectName(((EditText)findViewById(R.id.projectName)).toString());
            try {
                FileOutputStream fileOutputStream = openFileOutput(currentProject.getProjectName() + ".ser", Context.MODE_PRIVATE);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(currentProject);
                objectOutputStream.close();
                fileOutputStream.close();
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

    public void convertToJava(View v){
        ConvertToJava javaCode = new ConvertToJava(this.projectManager.getProject());
        List<StringBuilder> projectCode = javaCode.getJavaCode();
        List<IUML> umlList = this.projectManager.getProject().getUmlList();
        for(int i = 0; i < umlList.size(); i++){
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(umlList.get(i).getClassName()+".java", Context.MODE_PRIVATE));
                outputStreamWriter.write(projectCode.get(i).toString());
                outputStreamWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



    }
}
