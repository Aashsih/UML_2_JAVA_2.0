package com.head_first.aashi.uml_2_java;

        import org.hamcrest.Description;
        import org.hamcrest.Matcher;
        import org.hamcrest.TypeSafeMatcher;
        import org.junit.Rule;
        import org.junit.Test;
        import org.junit.runner.RunWith;

        import android.support.test.espresso.matcher.ViewMatchers;
        import android.support.test.filters.LargeTest;
        import android.support.test.rule.ActivityTestRule;
        import android.support.test.runner.AndroidJUnit4;
        import android.widget.Spinner;

        import static android.support.test.espresso.action.ViewActions.clearText;
        import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
        import static android.support.test.espresso.action.ViewActions.typeText;
        import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
        import static android.support.test.espresso.matcher.ViewMatchers.withText;
        import static junit.framework.Assert.assertEquals;
        import static junit.framework.Assert.assertNotNull;
        import static org.hamcrest.Matchers.is;

        import view_project.ProjectLayoutManager;
        import view_project.ProjectViewer;
        import view_project.UmlLayout;


        import static android.support.test.espresso.Espresso.onView;
        import static android.support.test.espresso.action.ViewActions.click;
        import static android.support.test.espresso.matcher.ViewMatchers.withId;
        import static junit.framework.Assert.assertTrue;
        import static org.junit.Assert.assertThat;

/**
 * Created by Aman on 26/09/2016.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MethodFieldTest {

    @Rule
    public final ActivityTestRule<ProjectViewer> mActivityRule = new ActivityTestRule<>(ProjectViewer.class);


    public void performClicks(){

        ProjectViewer projectViewTester = mActivityRule.getActivity();
        ProjectLayoutManager projectLayoutManager = projectViewTester.getProjectManager();
        int currentCheckBox = projectLayoutManager.getClassList().size();
        onView(ViewMatchers.withId(R.id.addClass)).perform(click());
        onView(ViewMatchers.withId(currentCheckBox)).perform(click());
        onView(ViewMatchers.withId(R.id.editClass)).perform(click());
    }
    @Test
    public void isFieldAddedOnClick(){

        ProjectViewer projectViewTester = mActivityRule.getActivity();
        ProjectLayoutManager projectLayoutManager = projectViewTester.getProjectManager();
        performClicks();
        //get the list from projetLayoutManager
        UmlLayout aUML =  projectLayoutManager.getUmlFragment(projectLayoutManager.getClassList().size() - 1);
        int count = aUML.getFieldLayouts().size();
        onView(ViewMatchers.withId(R.id.addField)).perform(click());

        assertThat(aUML.getFieldLayouts().size(),this.withListSize(count+1));

    }

    private static Matcher<Integer> withListSize (final int size) {
        return new TypeSafeMatcher<Integer>() {

            @Override
            public boolean matchesSafely (final Integer integer) {
                return integer == size;
            }

            @Override
            public void describeTo (final Description description) {
                description.appendText ("List of classes should have " + size + " items");
            }
        };
    }

    @Test
    public void isMethoddAddedOnClick(){

        ProjectViewer projectViewTester = mActivityRule.getActivity();
        ProjectLayoutManager projectLayoutManager = projectViewTester.getProjectManager();
        performClicks();
        UmlLayout aUML =  projectLayoutManager.getUmlFragment(projectLayoutManager.getClassList().size() - 1);
        int count = aUML.getFieldLayouts().size();
        onView(ViewMatchers.withId(R.id.addMethod)).perform(click());

        assertThat(aUML.getMethodLayouts().size(),this.withListSize(count+1));

    }

    @Test
    public void isFieldAndMethodTypeChecked(){

        ProjectViewer projectViewTester = mActivityRule.getActivity();
        ProjectLayoutManager projectLayoutManager = projectViewTester.getProjectManager();
        performClicks();
        UmlLayout aUML =  projectLayoutManager.getUmlFragment(projectLayoutManager.getClassList().size() - 1);
        //test for checking both static and final type
        //field : final
        onView(ViewMatchers.withId(R.id.addField)).perform(click());
        onView(ViewMatchers.withId(R.id.isFinal)).perform(click());
        assertTrue(ViewMatchers.isChecked().matches(aUML.getFieldLayouts().get(0).getIsFinal()));
        //field : static
        onView(ViewMatchers.withId(R.id.isStatic)).perform(click());
        assertTrue(ViewMatchers.isChecked().matches(aUML.getFieldLayouts().get(0).getIsStatic()));
        //method : final
        onView(ViewMatchers.withId(R.id.addMethod)).perform(click());
        onView(ViewMatchers.withId(R.id.isMethodFinal)).perform(click());
        assertTrue(ViewMatchers.isChecked().matches(aUML.getMethodLayouts().get(0).getIsFinal()));
        //method : static
        onView(ViewMatchers.withId(R.id.isMethodStatic)).perform(click());
        assertTrue(ViewMatchers.isChecked().matches(aUML.getMethodLayouts().get(0).getIsStatic()));
    }

    @Test
    public void isMethodAndFieldEditable(){

        ProjectViewer projectViewTester = mActivityRule.getActivity();
        ProjectLayoutManager projectLayoutManager = projectViewTester.getProjectManager();
        performClicks();

        UmlLayout aUML =  projectLayoutManager.getUmlFragment(projectLayoutManager.getClassList().size() - 1);
        onView(ViewMatchers.withId(R.id.addField)).perform(click());
        //EditText t = aUML.getFieldLayouts().get(0).getFieldName();
        //onView(withId(R.id.varName)).perform(typeText("Something"),closeSoftKeyboard());
        onView(withId(R.id.varName)).perform(clearText(), typeText("Something"),closeSoftKeyboard());
        //assertEquals("Something" ,onView(withId(R.id.varName)));


        onView(ViewMatchers.withId(R.id.addMethod)).perform(click());
        onView(withId(R.id.methodName)).perform(clearText(),typeText("Something"),closeSoftKeyboard());
       // assertEquals("methodSomething name" ,aUML.getFieldLayouts().get(0).getFieldName());

    }

    @Test
    public void isFieldEncapsulable(){

        ProjectViewer projectViewTester = mActivityRule.getActivity();
        ProjectLayoutManager projectLayoutManager = projectViewTester.getProjectManager();
        performClicks();
        UmlLayout aUML =  projectLayoutManager.getUmlFragment(projectLayoutManager.getClassList().size() - 1);
        onView(ViewMatchers.withId(R.id.addField)).perform(click());

        onView(ViewMatchers.withId(R.id.fieldAccessModifier)).perform(click());
        Spinner mSpinner = (Spinner)projectViewTester.findViewById(R.id.fieldAccessModifier);

        assertNotNull(mSpinner);
    }

    @Test
    public void isMethodEncapsulable() {

        ProjectViewer projectViewTester = mActivityRule.getActivity();
        ProjectLayoutManager projectLayoutManager = projectViewTester.getProjectManager();
        performClicks();
        UmlLayout aUML = projectLayoutManager.getUmlFragment(projectLayoutManager.getClassList().size() - 1);

        onView(ViewMatchers.withId(R.id.addMethod)).perform(click());
        onView(ViewMatchers.withId(R.id.methodAccessModifier)).perform(click());
        Spinner mSpinner = (Spinner)projectViewTester.findViewById(R.id.methodAccessModifier);

        assertNotNull(mSpinner);
    }
}
