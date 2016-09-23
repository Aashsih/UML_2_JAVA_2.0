package com.head_first.aashi.uml_2_java;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Suraaz on 9/22/2016.
 */
public class AddLibrary extends Activity {

    EditText libraryFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.library_files);
        libraryFile = (EditText)findViewById(R.id.libraryfile);

    }

    //Handle the Start Project Button
    protected void onAddLibrary(View v){
        if(v.getId()==R.id.btnLibrary){
            //Update the Library Files Section of the Project using the follwoing String
            String s = libraryFile.getText().toString();
            Toast.makeText(getApplicationContext(),"Library Files Added!",Toast.LENGTH_SHORT).show();
        }
    }
}
