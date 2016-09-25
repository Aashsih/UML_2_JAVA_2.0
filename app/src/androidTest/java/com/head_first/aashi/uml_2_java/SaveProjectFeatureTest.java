package com.head_first.aashi.uml_2_java;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;


/**
 * Created by moses on 26/09/2016.
 */
public class SaveProjectFeatureTest extends ActivityInstrumentationTestCase2<ProjectPage> {



    private Button checkSaveProjectButton;
    private View mainLayout;
    ProjectPage mainActivity;
    private Context context;

    public SaveProjectFeatureTest() {
        super("com.head_first.aashi.uml_2_java", ProjectPage.class);
    }

    protected void setUp() throws Exception {
        super.setUp();

         mainActivity = getActivity();
         context = mainActivity.getApplication().getApplicationContext();

        //Check if the Save Project Button Exists
         checkSaveProjectButton = (Button) mainActivity.findViewById(R.id.saveProjectTest);

        //Save a dummy Project file and check if the file is stored in the Internal Storage
         mainActivity.saveProjectToRunTest();
         Boolean fileSaved = mainActivity.getFileSaved();
         if(fileSaved){
             String fileName = mainActivity.getProjectName().getText().toString();
             isFilePresent(fileName);
         }
    }

    /*
    Check if the File is present in the Internal Storage of the Application
     */
    @Test
    public void isFilePresent(String fileName) throws Exception {
        String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File file = new File(path);
        assertTrue(file.exists());
    }
}
