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



    protected void onSaveProject(View v){

        if(v.getId()==R.id.saveProject){
            p.setProjectName(projectName.getText().toString());
            try {
                FileOutputStream fileOut = openFileOutput(p.getProjectName(),MODE_PRIVATE);
                fileOut.write(p.toString().getBytes());
                Toast.makeText(getApplicationContext(),"Project Saved", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
