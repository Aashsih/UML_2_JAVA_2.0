package com.head_first.aashi.uml_2_java;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import project.Project;

/**
 * This will be the project page where the user can start adding classes and edit/save changes
 * Created by moses on 18/09/2016.
 */
public class ProjectPage extends Activity {
    private Project p;
    private EditText projectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_page);
        p = new Project();
        projectName = (EditText)findViewById(R.id.projectName);
    }



    //Handles the Save Project Button
    //All information from UML classes will be saved onto the internal storage of the device as a String File.
    //Alternatively we can use Gson to store the UML data as a Project data. (This option is to discussed in the next Sprint)
    //I have already added the appropriate library file to the build.gradle in the dependencies section.
    protected void onSaveProject(View v){

        if(v.getId()==R.id.saveProject){

            //Update the project name from the Project Name Text Field
            p.setProjectName(projectName.getText().toString());

            try {
                FileOutputStream fileOut = openFileOutput(p.getProjectName(),MODE_PRIVATE);
                fileOut.write(p.toString().getBytes());  //Store the toString() of the project object.
                Toast.makeText(getApplicationContext(),"Project Saved", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
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
}
