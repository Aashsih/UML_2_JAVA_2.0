//package com.head_first.aashi.uml_2_java;
//
//import android.content.Context;
//import android.support.test.espresso.matcher.ViewMatchers;
//import android.test.ActivityInstrumentationTestCase2;
//import android.widget.Button;
//import org.junit.Test;
//import java.io.File;
//import java.io.FileNotFoundException;
//
//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.click;
//
//
///**
// * Class to implement Junit testing to check the Project Save Feature/User Story
// * Created by moses
// */
//public class SaveProjectFeatureTest extends ActivityInstrumentationTestCase2<ProjectPage> {
//
//
//
//    private Button checkSaveProjectButton;
//    ProjectPage mainActivity;
//    private Context context;
//
//    public SaveProjectFeatureTest() {
//        super("com.head_first.aashi.uml_2_java", ProjectPage.class);
//    }
//
//    protected void setUp() throws Exception {
//        super.setUp();
//
//        checkSaveProjectButton();
//        isFileTOInternalStorage();
//        isProjectSharedOnClick();
//
//        //Check if the Save Project Button Exists
//         checkSaveProjectButton = (Button) mainActivity.findViewById(R.id.saveProjectTest);
//
//    }
//
//    /*
//    Check if the Save Project Button Works
//     */
//    @Test
//    public void checkSaveProjectButton() {
//
//        mainActivity = getActivity();
//        context = mainActivity.getApplication().getApplicationContext();
//        onView(ViewMatchers.withId(R.id.saveProjectTest)).perform(click());
//        assertTrue(mainActivity.getFileSaved());
//
//    }
//    /*
//    Check if the File is present in the Internal Storage of the Application
//     */
//    @Test
//    public void isFileTOInternalStorage() {
//
//        mainActivity = getActivity();
//        context = mainActivity.getApplication().getApplicationContext();
//        onView(ViewMatchers.withId(R.id.saveProjectTest)).perform(click());
//        String fileName = mainActivity.getProjectName().getText().toString();
//            String path = context.getFilesDir().getAbsolutePath() + "/" + fileName;
//            File file = new File(path);
//            assertTrue(file.exists());
//
//    }
//
//    /*
//   Check if Share Project Feature is Enabled
//    */
//    @Test
//    public void isProjectSharedOnClick() {
//
//
//        mainActivity = getActivity();
//        context = mainActivity.getApplication().getApplicationContext();
//        onView(ViewMatchers.withId(R.id.shareProject)).perform(click());
//        assertTrue(mainActivity.getProjectShared());
//
//    }
//
//
//}
