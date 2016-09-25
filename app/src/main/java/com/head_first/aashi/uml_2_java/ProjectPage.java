package com.head_first.aashi.uml_2_java;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import project.IProject;
import project.Project;

/**
 * This will be the project page where the user can start adding classes and edit/save changes
 * Created by moses on 18/09/2016.
 */
public class ProjectPage extends Activity {
    private IProject p;
    private EditText projectName;
    private Boolean fileSaved;


    public Boolean getFileSaved() {
        return fileSaved;
    }

    public EditText getProjectName() {
        return projectName;
    }



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
    protected void onSaveProjectTest(View v){

        if(v.getId()==R.id.saveProjectTest){

            //Update the project name from the Project Name Text Field
            p.setProjectName(projectName.getText().toString());


            try {
                FileOutputStream fileOut = openFileOutput(p.getProjectName(),MODE_PRIVATE);
                fileOut.write(p.toString().getBytes());  //Store the toString() of the project object.
                Toast.makeText(getApplicationContext(),"Project Saved", Toast.LENGTH_LONG).show();
                fileSaved = true;
            } catch (IOException e) {
                e.printStackTrace();
            }

//            Other Method (To be finalised by the team in next Sprint)
//            SharedPreferences myPreferns = getPreferences(MODE_PRIVATE);
//
//            Editor prefsEditor = myPreferns.edit();
//            Gson gson = new Gson();
//            String json = gson.toJson(myObject); // myObject
//            prefsEditor.putString("MyObject", json);
//            prefsEditor.commit();




        }
    }



    //Code to Test the Share File feature
    protected void onShareProject(View v){

        if(v.getId()==R.id.shareProject){

           // String shareProjectAsJavaFile = p.toString();

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);

            //The File Type to be shared
            sharingIntent.setType("Java File");

            //The Title of the File being Shared (This will be the title of the project)
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Test UML Project");

            //The body of the File being shared (This will be the toString representation of the Project File)
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Sharing the ToString of the Project File here ");
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

        }
    }


    //
    public void saveProjectToRunTest(){
        projectName.setText("Test File");

        try {
            FileOutputStream fileOut = openFileOutput("Test File",MODE_PRIVATE);
            fileOut.write(p.toString().getBytes());  //Store the toString() of the project object.
            Toast.makeText(getApplicationContext(),"Project Saved as 'Test File'", Toast.LENGTH_LONG).show();
            fileSaved = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Code to Handle the items on Menu if needed (Share Feature can also be added here)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
