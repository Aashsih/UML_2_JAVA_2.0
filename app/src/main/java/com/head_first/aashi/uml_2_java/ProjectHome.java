package com.head_first.aashi.uml_2_java;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import project.IProject;
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
    private List<String> fileNames;
    private Spinner projectList;

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
            //Toast.makeText(getApplicationContext(),"To be implemented in Sprint 2", Toast.LENGTH_LONG).show();
            String path = getApplicationContext().getFilesDir().getAbsolutePath();
            //AssetManager assetManager = getAssets();

            List<File> file = Arrays.asList((new File(path)).listFiles());//get list of files in the current directory
            //new LinkedList<>((new File(path)).listFiles());
            //File file[] = (new File(path)).listFiles();
            List<String> fileNames = new ArrayList<>(file.size());
            int numberOfFiles = file.size();
            for(int i = 0; i < numberOfFiles; i++){//store all the file names with extension ".ser"
                if(file.get(i).getAbsolutePath().endsWith(".ser")){
                    fileNames.add(file.get(i).getName());
                }
            }
            this.fileNames = fileNames;
            if(fileNames.size() == 0){
                this.fileNames.add("None");
            }
            else{
                this.fileNames.add("Select a Project");
            }
            setupSavedProjectList();

            //We can read the file as Bytes from a String or use Gson and shared preference to read the file as a UML Project object.
            //This feature is not part of Sprint0 hence the methods will be discussed with the Scrum Master & the team before finalising

//            Method to open file using Gson
//            Gson gson = new Gson();
//            String json = mPrefs.getString("MyObjectName", "");
//            Project object = gson.fromJson(json, Project.class);   This Class has already been defined under main->src
        }
    }
    private void setupSavedProjectList(){
        projectList = (Spinner)findViewById(R.id.projectList);
        projectList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, this.fileNames));
        projectList.setSelection(this.fileNames.size() - 1);
        projectList.setVisibility(View.VISIBLE);
        projectList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try{
                    if(position != ProjectHome.this.fileNames.size() - 1){
                        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(
                                new File(getApplicationContext().getFilesDir().getAbsolutePath() + "/" + ProjectHome.this.fileNames.get(position))));//open the first file for testing
                        Intent savedProject = new Intent(ProjectHome.this, ProjectViewer.class);
                        savedProject.putExtra("hello",(Project) objectInputStream.readObject());//read and pass the Project object to the ProjectViewer Activity
                        ProjectHome.this.projectList.setVisibility(View.INVISIBLE);
                        objectInputStream.close();
                        startActivity(savedProject);

                    }

                }
                catch(FileNotFoundException fnfe){
                    fnfe.printStackTrace();
                }
                catch (IOException ioe){
                    ioe.printStackTrace();
                }
                catch (ClassNotFoundException cnfe){
                    cnfe.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}


