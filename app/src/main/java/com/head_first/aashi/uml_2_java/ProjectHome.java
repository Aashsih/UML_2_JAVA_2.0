package com.head_first.aashi.uml_2_java;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import project.Project;
import view_project.ProjectViewer;


/**
 * This will be the main display page when the user starts the application
 * User will have the option to start a new UML project or load a previously saved project
 * Created by moses on 18/09/2016.
 */
public class ProjectHome extends AppCompatActivity {

    private Button startP;
    private Button openP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

    }

   //Handle the Start Project Button
   protected void onStartProject(View v){
       if(v.getId()==R.id.startProject){
           Intent projectNew = new Intent(this, ProjectViewer.class);
           startActivity(projectNew);
       }
   }

    //Handles the Open Project Button
    protected void onOpenProject(View v){
        if(v.getId()==R.id.openProject){
             Toast.makeText(getApplicationContext(),"To be implemented in Sprint 2", Toast.LENGTH_LONG).show();

            //We can read the file as Bytes from a String or use Gson and shared preference to read the file as a UML Project object.
            //This feature is not part of Sprint0 hence the methods will be discussed with the Scrum Master & the team before finalising

//            Method to open file using Gson
//            Gson gson = new Gson();
//            String json = mPrefs.getString("MyObjectName", "");
//            Project object = gson.fromJson(json, Project.class);   This Class has already been defined under main->src
        }
    }

}


