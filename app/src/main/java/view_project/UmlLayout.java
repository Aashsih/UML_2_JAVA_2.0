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
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.head_first.aashi.uml_2_java.R;

import java.util.ArrayList;
import java.util.List;

import project.IProject;
import project.Project;
import uml_components.Field;
import uml_components.IMethod;
import uml_components.IUML;
import uml_components.IVariable;
import uml_components.Method;
import uml_components.UML;
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
    private List<FieldLayout> fieldLayouts;
    private List<MethodLayout> methodLayouts;
    private IUML uml;
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
    public final void setUML(IUML uml){
        this.uml = uml;
    }
    public final void setClassPositionInProject(int position){
        this.classPositionInProject = position;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        //setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fieldLayouts = new ArrayList<>();
        methodLayouts = new ArrayList<>();
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
                //need to add the field to the list and set an onChangeListener on the List
                View field = getActivity().getLayoutInflater().inflate(R.layout.field_layout, null);
                intialiseFieldLayout(field);
                fields.addView(field);
            }
        });
        Button addMethod = (Button)rootView.findViewById(R.id.addMethod);
        addMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout methods = (LinearLayout) rootView.findViewById(R.id.methods);
                //need to add the field to the list and set an onChangeListener on the List
                View method = getActivity().getLayoutInflater().inflate(R.layout.method_layout, null);
                intialiseMethodLayout(method);
                methods.addView(method);
            }
        });
        Button save = (Button)rootView.findViewById(R.id.saveClass);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProjectLayoutManager currentProjectLayoutManager = ((ProjectViewer)getActivity()).getProjectManager();
                List<IUML> umlForFragment = currentProjectLayoutManager.getProject().getUmlList();
                String className = ((EditText)(rootView.findViewById(R.id.className))).getText().toString();

                ClassType classType = ClassType.valueOf(((Spinner)(rootView.findViewById(R.id.classType))).getSelectedItem().toString());
                List<IMethod> methods = getMethods();
                List<IVariable> fields = getFields();
                //dont always add..if it is the same class then just update.
                if(umlForFragment.size() > classPositionInProject){
                    umlForFragment.set(classPositionInProject,new UML(className,classType,methods,fields));
                    ((CheckBox)(currentProjectLayoutManager.getClassList().get(classPositionInProject))).setText(className);
                }
//                else{
//                    umlForFragment.add(new UML(className,classType,methods,fields));
//                }

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

    private final List<IMethod> getMethods(){
        List<IMethod> methods = new ArrayList<>();
        for(MethodLayout methodLayout : methodLayouts){

            boolean isStatic = methodLayout.getIsStatic().isChecked();
            boolean isFinal = methodLayout.getIsFinal().isChecked();
            String returnType = methodLayout.getReturnType().getText().toString();
            String methodName = methodLayout.getMethodName().getText().toString();
            String parameters = methodLayout.getParameters().getText().toString();
            //AccessModifier accessModifier = Enum.valueOf(AccessModifier.class,test);
            AccessModifier accessModifier = AccessModifier.valueOfSymbol(((String)(methodLayout.getMethodAccessModifier().getSelectedItem())).toString());
            //AccessModifier accessModifier = (AccessModifier)(methodLayout.getMethodAccessModifier().getSelectedItem());
            methods.add(new Method(accessModifier, isStatic, isFinal, returnType, methodName, parameters));
        }
        return methods;
    }


    private final List<IVariable> getFields(){
        List<IVariable> fields = new ArrayList<>();
        for(FieldLayout fieldLayout : fieldLayouts){
            AccessModifier accessModifier = AccessModifier.valueOfSymbol(((String)(fieldLayout.getFieldAccessModifier().getSelectedItem())).toString());
            boolean isStatic = fieldLayout.getIsStatic().isChecked();
            boolean isFinal = fieldLayout.getIsFinal().isChecked();
            String type = fieldLayout.getDataType().getText().toString();
            String fieldName = fieldLayout.getFieldName().getText().toString();


            fields.add(new Field(type,fieldName,isFinal,accessModifier,isStatic));
        }
        return fields;
    }

    private final String[] getAccessModifiers(){
        String[] symbols = new String[AccessModifier.values().length];
        int i = 0;
        for(AccessModifier symbol : AccessModifier.values()){
            symbols[i++] = (symbol.getSymbol());
        }
        return symbols;
    }

    private void intialiseFieldLayout(View field){
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
    private final void setUpFragment(IUML uml) {

        ((EditText) (rootView.findViewById(R.id.className))).setText(uml.getClassName());
        ((Spinner) (rootView.findViewById(R.id.classType))).setSelection(ClassType.valueOf(uml.getClassType().toString()).ordinal());
        LinearLayout fields = (LinearLayout) rootView.findViewById(R.id.fields);
        LinearLayout methods = (LinearLayout) rootView.findViewById(R.id.methods);
        for (IVariable field : uml.getVariableList()) {
            View fieldLayout = getActivity().getLayoutInflater().inflate(R.layout.field_layout, null);
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
            fieldLayouts.add(fieldLayoutInfo);

            fields.addView(fieldLayout);
            //fieldLayouts.add(fieldLayout);
        }
        for (IMethod method : uml.getMethodList()) {
            View methodLayout = getActivity().getLayoutInflater().inflate(R.layout.method_layout, null);
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
            methodLayouts.add(methodLayoutInfo);

            methods.addView(methodLayout);
        }
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
