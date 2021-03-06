package view_project;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;

import com.head_first.aashi.uml_2_java.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aashish Indorewala on 21-Oct-16.
 *
 * This class is used to view the whole project together in the form of UML diagrams.
 * This class uses the TemplateLayout Fragment as sub Fragments to display the entire project
 * and also defines an OnTouchListener to move the fragments within this Fragment
 */

public class MultipleClassViewer extends Fragment implements  Serializable{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private View rootView;

    private ProjectLayoutManager projectLayoutManager;
    //List of TemplateLayouts that this Fragment will display
    private List<TemplateLayout> templateLayouts;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MultipleClassViewer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UmlLayout.
     */
    // TODO: Rename and change types and number of parameters
    public static MultipleClassViewer newInstance(String param1, String param2) {
        MultipleClassViewer fragment = new MultipleClassViewer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.multiple_class_view, container, false);
        //Get the projectLayoutManager
        projectLayoutManager = (ProjectLayoutManager) getArguments().getSerializable(ProjectLayoutManager.PROJECT_LAYOUT_MANAGER_TAG);
        //Get list of classes the user selected for multiple view
        List<CheckBox> selectedClasses = projectLayoutManager.getCheckedCheckBoxes();
        //Stores the position of each selected class in the project
        int[] classPosition = new int[selectedClasses.size()];
        templateLayouts = new ArrayList<>();

        //Make a list of templates that need to be displayed
        for(int i = 0; i < selectedClasses.size(); i++){
            //Get the position of each selected class in the project
            classPosition[i] = projectLayoutManager.getClassList().indexOf(selectedClasses.get(i));
            //Delete the current content to update later
            projectLayoutManager.setTemplateFragment(classPosition[i], new TemplateLayout());
            templateLayouts.add(projectLayoutManager.getTemplateFragment(classPosition[i]));
        }

        for(int i = 0; i < classPosition.length; i++){
            Bundle bundle = new Bundle();
            //Pass false as the TemplateLayout Fragment will now be resized
            bundle.putSerializable(TemplateLayout.MATCH_PARENT, false);
            templateLayouts.get(i).setArguments(bundle);

            FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction aTransaction = fragmentManager.beginTransaction();
            aTransaction.add(R.id.childLayout, templateLayouts.get(i));
            aTransaction.addToBackStack(null);
            aTransaction.commit();
            //set the updated TemplateLayout Fragment in the projectLayout Manager
            projectLayoutManager.getTemplateFragment(classPosition[i]).setUml(projectLayoutManager.getProject().getUmlList().get(classPosition[i]));
        }
        return rootView;
    }
    @Override
    public void onSaveInstanceState(Bundle bundle){

    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onDestroy() {

        try {
            super.onDestroy();
        } catch (NullPointerException npe) {

        }
    }

}