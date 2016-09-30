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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.head_first.aashi.uml_2_java.R;

import java.util.ArrayList;
import java.util.List;

import uml_components.Field;
import uml_components.IMethod;
import uml_components.IUML;
import uml_components.IVariable;
import uml_components.Method;
import uml_components.UML;
import utils.AccessModifier;
import utils.ClassType;


/**
 *This class represents a UML Class Diagram where the user can enter the class details as a Fragment.
 * This class is responsible for the storage and display of the UML Class diagram for one class.
 */
public class UmlLayout extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Root View for the fragment
    private View rootView;

    //List of Fields in a class
    private List<FieldLayout> fieldLayouts;
    //List of Methods in a class
    private List<MethodLayout> methodLayouts;
    //Stores all the information inorder to restore the Fragment
    private IUML uml;
    //Position at which this class is stored in the Project
    private int classPositionInProject;


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
        //Initialize the fields and methods for the class
        fieldLayouts = new ArrayList<>();
        methodLayouts = new ArrayList<>();

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
                LinearLayout fields = (LinearLayout) rootView.findViewById(R.id.fields);
                View field = getActivity().getLayoutInflater().inflate(R.layout.field_layout, null);//inflate field_layout
                addFieldLayout(field);
                fields.addView(field);//Add the inflated field_layout to the Parent layout
            }
        });
        Button addMethod = (Button)rootView.findViewById(R.id.addMethod);
        addMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout methods = (LinearLayout) rootView.findViewById(R.id.methods);
                View method = getActivity().getLayoutInflater().inflate(R.layout.method_layout, null);//inflate the mathod_layout
                intialiseMethodLayout(method);
                methods.addView(method);//Add the inflated method_layout to the Parent layout
            }
        });
        Button save = (Button)rootView.findViewById(R.id.saveClass);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save the current state of the Uml Class Diagram

                //Get the ProjectLayoutManager
                ProjectLayoutManager currentProjectLayoutManager = ((ProjectViewer)getActivity()).getProjectManager();
                //Get the UML list to store information into it form the Class Diagram
                List<IUML> umlForFragment = currentProjectLayoutManager.getProject().getUmlList();

                //Store the importStatements
                String importStatementss = ((EditText)(rootView.findViewById(R.id.importStatements))).getText().toString();
                //Store the class name
                String className = ((EditText)(rootView.findViewById(R.id.className))).getText().toString();
                //Store the class type
                ClassType classType = ClassType.valueOf(((Spinner)(rootView.findViewById(R.id.classType))).getSelectedItem().toString());
                //Store the methods
                List<IMethod> methods = getMethods();
                //Store the fields
                List<IVariable> fields = getFields();
                //Save the Information in the UML List
                if(umlForFragment.size() > classPositionInProject){
                    umlForFragment.set(classPositionInProject,new UML(importStatementss,className,classType,methods,fields));
                    //Change the name of the CheckBox on the UI to the Class Name entered by the user
                    ((CheckBox)(currentProjectLayoutManager.getClassList().get(classPositionInProject))).setText(className);
                }

                Toast.makeText(getContext(),"Saved", Toast.LENGTH_SHORT).show();

            }
        });

        return rootView;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    @Override
    public void onStart(){
        super.onStart();
        if(uml != null){
            setUpFragment(uml);
        }
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

    //Get all the Methods that the user created in the Class Diagram
    private final List<IMethod> getMethods(){
        List<IMethod> methods = new ArrayList<>();
        for(MethodLayout methodLayout : methodLayouts){//For all the Method Layout added to the class diagram
            //Store information of the method
            AccessModifier accessModifier = AccessModifier.valueOfSymbol(((String)(methodLayout.getMethodAccessModifier().getSelectedItem())).toString());
            boolean isStatic = methodLayout.getIsStatic().isChecked();
            boolean isFinal = methodLayout.getIsFinal().isChecked();
            String returnType = methodLayout.getReturnType().getText().toString();
            String methodName = methodLayout.getMethodName().getText().toString();
            String parameters = methodLayout.getParameters().getText().toString();

            methods.add(new Method(accessModifier, isStatic, isFinal, returnType, methodName, parameters));
        }
        return methods;
    }

    //Get all the Fields that the user created in the Class Diagram
    private final List<IVariable> getFields(){
        List<IVariable> fields = new ArrayList<>();
        for(FieldLayout fieldLayout : fieldLayouts){//For all the Field Layout added to the class diagram
            AccessModifier accessModifier = AccessModifier.valueOfSymbol(((String)(fieldLayout.getFieldAccessModifier().getSelectedItem())).toString());
            boolean isStatic = fieldLayout.getIsStatic().isChecked();
            boolean isFinal = fieldLayout.getIsFinal().isChecked();
            String type = fieldLayout.getDataType().getText().toString();
            String fieldName = fieldLayout.getFieldName().getText().toString();

            fields.add(new Field(type,fieldName,isFinal,accessModifier,isStatic));
        }
        return fields;
    }

    //This method adds a FieldLayout to the list of fieldLayouts maintained by the UmlLayout
    private void addFieldLayout(View field){
        Spinner fieldAccessModifier = (Spinner)field.findViewById(R.id.fieldAccessModifier);
        EditText fieldName = (EditText)field.findViewById(R.id.varName);
        EditText dataType = (EditText)field.findViewById(R.id.dataType);;
        CheckBox isFinal = (CheckBox)field.findViewById(R.id.isFinal);
        CheckBox isStatic = (CheckBox)field.findViewById(R.id.isStatic);
        FieldLayout fieldLayout = new FieldLayout(fieldAccessModifier,fieldName,dataType,isFinal,isStatic);
        fieldAccessModifier.setAdapter(new ArrayAdapter<String>(rootView.getContext(),
                android.R.layout.simple_list_item_1, getAccessModifiers()));
        fieldLayouts.add(fieldLayout);
    }
    //This method adds a MethodLayout to the list of methodLayouts maintained by the UmlLayout
    private void intialiseMethodLayout(View method){

        Spinner methodAccessModifier = (Spinner)method.findViewById(R.id.methodAccessModifier);
        EditText methodName = (EditText) method.findViewById(R.id.methodName);
        EditText parameters = (EditText) method.findViewById(R.id.parameters);
        EditText returnType = (EditText) method.findViewById(R.id.returnType);
        CheckBox isFinal = (CheckBox) method.findViewById(R.id.isMethodFinal);
        CheckBox isStatic = (CheckBox) method.findViewById(R.id.isMethodStatic);
        MethodLayout methodLayout = new MethodLayout(methodAccessModifier, methodName, parameters, returnType, isFinal, isStatic);
        methodAccessModifier.setAdapter(new ArrayAdapter<String>(rootView.getContext(),
                android.R.layout.simple_list_item_1, getAccessModifiers()));
        methodLayouts.add(methodLayout);
    }

    //This method restores the saved state of the Fragment
    private final void setUpFragment(IUML uml) {
        ((EditText) (rootView.findViewById(R.id.importStatements))).setText(uml.getImportStatements());
        ((EditText) (rootView.findViewById(R.id.className))).setText(uml.getClassName());
        ((Spinner) (rootView.findViewById(R.id.classType))).setSelection(ClassType.valueOf(uml.getClassType().toString()).ordinal());

        //Layout where the field_layout is inflated
        LinearLayout fields = (LinearLayout) rootView.findViewById(R.id.fields);
        //Layout where the method_layout is inflated
        LinearLayout methods = (LinearLayout) rootView.findViewById(R.id.methods);

        for (IVariable field : uml.getVariableList()) {//for all the fields stored for this class
            //Inflate the field_layout
            View fieldLayout = getActivity().getLayoutInflater().inflate(R.layout.field_layout, null);

            //Retrieve all the the information for the Views
            Spinner fieldAccessModifier = ((Spinner)fieldLayout.findViewById(R.id.fieldAccessModifier));
            fieldAccessModifier.setAdapter(new ArrayAdapter<String>(rootView.getContext(),
                    android.R.layout.simple_list_item_1, getAccessModifiers()));
            fieldAccessModifier.setSelection(AccessModifier.valueOf(field.getAccessModifier().toString()).ordinal());
            EditText fieldName = ((EditText)fieldLayout.findViewById(R.id.varName));
            fieldName.setText(field.getVarName());
            EditText dataType = ((EditText)fieldLayout.findViewById(R.id.dataType));
            dataType.setText(field.getType());
            CheckBox isFinal = ((CheckBox)fieldLayout.findViewById(R.id.isFinal));
            isFinal.setChecked(field.isFinal());
            CheckBox isStatic = ((CheckBox)fieldLayout.findViewById(R.id.isStatic));
            isStatic.setChecked(field.isStatic());

            FieldLayout fieldLayoutInfo = new FieldLayout(fieldAccessModifier,fieldName,dataType,isFinal,isStatic);
            //add the field layout to the list of fieldLayouts
            fieldLayouts.add(fieldLayoutInfo);
            //Add the view to the Parent Layout
            fields.addView(fieldLayout);

        }

        for (IMethod method : uml.getMethodList()) {//for all the methods stored for this class
            //Inflate the method_layout
            View methodLayout = getActivity().getLayoutInflater().inflate(R.layout.method_layout, null);

            //Retrieve all the the information for the Views
            Spinner methodAccessModifier = (Spinner)methodLayout.findViewById(R.id.methodAccessModifier);
            methodAccessModifier.setAdapter(new ArrayAdapter<String>(rootView.getContext(),
                    android.R.layout.simple_list_item_1, getAccessModifiers()));
            methodAccessModifier.setSelection(AccessModifier.valueOf(method.getAccessModifier().toString()).ordinal());
            EditText methodName = ((EditText) methodLayout.findViewById(R.id.methodName));
            methodName.setText(method.getMethodName());
            EditText parameters = ((EditText) methodLayout.findViewById(R.id.parameters));
            parameters.setText(method.getParameters());
            EditText returnType = ((EditText) methodLayout.findViewById(R.id.returnType));
            returnType.setText(method.getReturnType());
            CheckBox isFinal = ((CheckBox) methodLayout.findViewById(R.id.isMethodFinal));
            isFinal.setChecked(method.isFinal());
            CheckBox isStatic = ((CheckBox) methodLayout.findViewById(R.id.isMethodStatic));
            isStatic.setChecked(method.isStatic());

            MethodLayout methodLayoutInfo = new MethodLayout(methodAccessModifier, methodName, parameters, returnType, isFinal, isStatic);
            //add the method layout to the list of methodLayouts
            methodLayouts.add(methodLayoutInfo);
            //Add the view to the Parent Layout
            methods.addView(methodLayout);
        }
    }

    //Get String[] of all the AccessModifier values to create the ArrayAdapter.
    private final String[] getAccessModifiers(){
        String[] symbols = new String[AccessModifier.values().length];
        int i = 0;
        for(AccessModifier symbol : AccessModifier.values()){
            symbols[i++] = (symbol.getSymbol());
        }
        return symbols;
    }
    //Information to restore the saved state
    public final void setUML(IUML uml){
        this.uml = uml;
    }
    //Set the position of this Uml Diagram in the project
    public final void setClassPositionInProject(int position){
        this.classPositionInProject = position;
    }

    public final List<FieldLayout> getFieldLayouts(){

        return fieldLayouts;
    }

    public final List<MethodLayout> getMethodLayouts(){

        return methodLayouts;
    }


}
