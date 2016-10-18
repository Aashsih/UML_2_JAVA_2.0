package com.head_first.aashi.uml_2_java.home_page;

/**
 * Created by Aashish Indorewala on 18-Oct-16.
 */
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.app.ActivityManager;
import android.content.Context;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.is;

import com.head_first.aashi.uml_2_java.ProjectHome;
import com.head_first.aashi.uml_2_java.R;

import java.util.List;

import view_project.ProjectLayoutManager;
import view_project.ProjectViewer;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@LargeTest

public class OpenSavedProject {
    private String mStringToBetyped;

    @Rule
    public final ActivityTestRule<ProjectHome> mActivityRule = new ActivityTestRule<>(
            ProjectHome.class);

    @Before
    public void initValidString() {
        onView(ViewMatchers.withId(R.id.addClass)).perform(click());
        // Specify a valid string.
        mStringToBetyped = "Espresso";
    }
    @Test
    public void assertProjectViewerActivityLaunchedOnOpeningProject(){


    }
}
