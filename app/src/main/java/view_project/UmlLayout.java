package view_project;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.head_first.aashi.uml_2_java.R;

import utils.AccessModifier;
import utils.ClassType;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UmlLayout.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UmlLayout#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UmlLayout extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View rootView;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public UmlLayout() {
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
    public static UmlLayout newInstance(String param1, String param2) {
        UmlLayout fragment = new UmlLayout();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.uml_layout, container, false);
        Spinner classType = (Spinner)rootView.findViewById(R.id.classType);
        classType.setAdapter(new ArrayAdapter<ClassType>(rootView.getContext(),
                android.R.layout.simple_list_item_1, ClassType.values()));
        Button addField = (Button)rootView.findViewById(R.id.addField);
        addField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout fields = (LinearLayout)rootView.findViewById(R.id.fields);
                //need to add the field to the list and set an onChangeListener on the List
                View field = getActivity().getLayoutInflater().inflate(R.layout.field_layout, null);
                Spinner accessModifier = (Spinner)field.findViewById(R.id.fieldAccessModifier);

                accessModifier.setAdapter(new ArrayAdapter<String>(rootView.getContext(),
                        android.R.layout.simple_list_item_1, getAccessModifiers()));
                fields.addView(field);
            }
        });
        Button addMethod = (Button)rootView.findViewById(R.id.addMethod);
        addMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout methods = (LinearLayout)rootView.findViewById(R.id.methods);
                //need to add the field to the list and set an onChangeListener on the List
                View method = getActivity().getLayoutInflater().inflate(R.layout.method_layout, null);
                Spinner accessModifier = (Spinner)method.findViewById(R.id.methodAccessModifier);
                accessModifier.setAdapter(new ArrayAdapter<String>(rootView.getContext(),
                        android.R.layout.simple_list_item_1, getAccessModifiers()));
                methods.addView(method);
            }
        });
        return rootView;
    }

    private final String[] getAccessModifiers(){
        String[] symbols = new String[AccessModifier.values().length];
        int i = 0;
        for(AccessModifier symbol : AccessModifier.values()){
            symbols[i++] = (symbol.getSymbol());
        }
        return symbols;
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

//    public void addField(View view){
//        LinearLayout fields = (LinearLayout)getVIew().findViewById(R.id.fields);
//        //need to add the field to the list and set an onChangeListener on the List
//        View field = getLayoutInflater().inflate(R.layout.field_layout, null);
//        fields.addView(field);
//    }
//
//    public void addMethod(View view){
//        LinearLayout methods = (LinearLayout) findViewById(R.id.methods);
//        //need to add the field to the list and set an onChangeListener on the List
//        View method = getLayoutInflater().inflate(R.layout.method_layout, null);
//        methods.addView(method);
//    }
}
