package com.head_first.aashi.uml_2_java;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import org.junit.Test;
import java.io.File;
import java.io.FileNotFoundException;


/**
 * Class to implement Junit testing to check the Project Save Feature/User Story
 * Created by moses
 */
public class SaveProjectFeatureTest extends ActivityInstrumentationTestCase2<ProjectPage> {



    private Button checkSaveProjectButton;
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
            if(!file.exists()){
                throw new FileNotFoundException();
            }

    }
}
