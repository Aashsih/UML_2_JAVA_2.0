package com.head_first.aashi.uml_2_java;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import project.Project;


/**
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

   protected void onStartProject(View v){
       if(v.getId()==R.id.startProject){
           Intent projectNew = new Intent(this, ProjectPage.class);
           startActivity(projectNew);
       }
   }

    protected void onOpenProject(View v){
        if(v.getId()==R.id.startProject){
            Intent projectNew = new Intent(this, ProjectPage.class);
            startActivity(projectNew);
        }
    }

}


