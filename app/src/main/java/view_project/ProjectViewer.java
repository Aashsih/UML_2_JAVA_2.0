package view_project;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.head_first.aashi.uml_2_java.R;

import java.util.List;

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
                aTransaction.replace(R.id.frameLayout,
                        projectManager.getUmlFragment(projectManager.getClassList().indexOf(selectedClasses.get(0))));
                aTransaction.addToBackStack(null);
                aTransaction.commit();
                drawerLayout.closeDrawers();
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
}
