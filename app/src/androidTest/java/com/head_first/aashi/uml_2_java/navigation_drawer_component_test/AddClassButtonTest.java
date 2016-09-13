package com.head_first.aashi.uml_2_java.navigation_drawer_component_test;

/**
 * Created by Aashish Indorewala on 11-Sep-16.
 */
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.ListView;

import com.head_first.aashi.uml_2_java.R;

import view_project.ProjectLayoutManager;
import view_project.ProjectViewer;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddClassButtonTest {

    private String mStringToBetyped;

    @Rule
    public final ActivityTestRule<ProjectViewer> mActivityRule = new ActivityTestRule<>(
            ProjectViewer.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        mStringToBetyped = "Espresso";
    }

    @Test
    public void isClassAddedOnClick() {
        // Type text and then press the button.
//        onView(withId(R.id.addClass))
//                .perform(typeText(mStringToBetyped), closeSoftKeyboard());
        // Check that the text was changed.
//        onView(withId(R.id.textToBeChanged))
//                .check(matches(withText(mStringToBetyped)));

        onView(ViewMatchers.withId(R.id.addClass)).perform(click());

    }
    @Test
    public void isClassAddedToTheList(){
        ProjectViewer projectViewTester = mActivityRule.getActivity();
        ProjectLayoutManager projectLayoutManager = projectViewTester.getProjectManager();
        int count = projectLayoutManager.getClassList().size();
        onView(ViewMatchers.withId(R.id.addClass)).perform(click());
        assertThat(projectLayoutManager.getClassList().size(),this.withListSize(count+1));
     //   onView (withId (android.R.id.list)).check (ViewAssertions.matches (this.withListSize(count+1)));
    }
    public static Matcher<Integer> withListSize (final int size) {
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
}
