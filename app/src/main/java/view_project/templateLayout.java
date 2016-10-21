package view_project;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.head_first.aashi.uml_2_java.R;

import java.util.ArrayList;
import java.util.List;

import project.IProject;
import uml_components.Field;
import uml_components.IMethod;
import uml_components.IUML;
import uml_components.IVariable;
import uml_components.Method;
import uml_components.UML;
import utils.AccessModifier;
import utils.ClassType;

/**
 * Created by Aman on 19/10/2016.
 */

public class TemplateLayout extends Fragment{

    private IUML uml;

    private View rootView;
    //List of Fields in a class
    private List<FieldLayoutTemplate> fieldLayouts;
    //List of Methods in a class
    private List<MethodLayoutTemplate> methodLayouts;

    private class FieldLayoutTemplate{

        private TextView fieldAccessModifier;
        private TextView fieldName;
        private TextView dataType;

        public FieldLayoutTemplate(TextView fieldAccessModifier, TextView fieldName, TextView dataType){
            this.fieldAccessModifier = fieldAccessModifier;
            this.fieldName = fieldName;
            this.dataType = dataType;

        }

        //Getters and Setters
        public TextView getFieldAccessModifier() {
            return fieldAccessModifier;
        }

        public void setFieldAccessModifier(TextView fieldAccessModifier) {
            this.fieldAccessModifier = fieldAccessModifier;
        }
        public TextView getFieldName() {
            return fieldName;
        }

        public void setFieldName(TextView fieldName) {
            this.fieldName = fieldName;
        }

        public TextView getDataType() {
            return dataType;
        }

        public void setDataType(TextView dataType) {
            this.dataType = dataType;
        }

    }

    private class MethodLayoutTemplate {

        private TextView methodAccessModifier;
        private TextView methodName;
        private TextView parameters;
        private TextView returnType;


        public MethodLayoutTemplate(TextView methodAccessModifier, TextView methodName,TextView paramters, TextView returnType){
            this.methodAccessModifier = methodAccessModifier;
            this.methodName = methodName;
            this.parameters = paramters;
            this.returnType = returnType;
        }

        //Getters and Setters
        public TextView getMethodAccessModifier() {
            return methodAccessModifier;
        }

        public void setMethodAccessModifier(TextView methodAccessModifier) {
            this.methodAccessModifier = methodAccessModifier;
        }

        public TextView getMethodName() {
            return methodName;
        }

        public void setMethodName(TextView methodName) {
            this.methodName = methodName;
        }

        public TextView getParameters() {
            return parameters;
        }

        public void setParameters(TextView parameters) {
            this.parameters = parameters;
        }

        public TextView getReturnType() {
            return returnType;
        }

        public void setReturnType(TextView returnType) {
            this.returnType = returnType;
        }

    }



//------------------------------------------------------------------------------------------------------------------------------

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private TemplateLayout.OnFragmentInteractionListener mListener;

    public TemplateLayout() {
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
    public static TemplateLayout newInstance(String param1, String param2) {
        TemplateLayout fragment = new TemplateLayout();
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
        rootView = inflater.inflate(R.layout.template_view, container, false);

        TextView classType = (TextView)rootView.findViewById(R.id.classTypeTemplate);

        classType.setText(uml.getClassType().toString());

        TextView className = (TextView)rootView.findViewById(R.id.classNameTemplate);

        className.setText(uml.getClassName());

        for(IVariable aField : uml.getVariableList()){

            LinearLayout fields = (LinearLayout) rootView.findViewById(R.id.fieldsTemplate);
            View fieldLayout = getActivity().getLayoutInflater().inflate(R.layout.field_layout_template, null);
            addFieldLayout(fieldLayout, aField);
            fields.addView(fieldLayout);

        }

        for(IMethod aMethod : uml.getMethodList()){

            LinearLayout methods = (LinearLayout) rootView.findViewById(R.id.methodsTemplate);
            View methodLayout = getActivity().getLayoutInflater().inflate(R.layout.method_layout_template, null);
            intialiseMethodLayout(methodLayout, aMethod);
            methods.addView(methodLayout);

        }

        return rootView;
    }

    private void addFieldLayout(View fieldLayout, IVariable aField){
        TextView fieldAccessModifier = (TextView)fieldLayout.findViewById(R.id.fieldAccessModifierTemplate);
        fieldAccessModifier.setText(aField.getAccessModifier().getSymbol());

        TextView fieldName = (TextView)fieldLayout.findViewById(R.id.varNameTemplate);
        fieldName.setText(aField.getVarName());

        TextView dataType = (TextView)fieldLayout.findViewById(R.id.dataTypeTemplate);;
        dataType.setText(aField.getType());

        FieldLayoutTemplate fieldLayoutTemplate = new FieldLayoutTemplate(fieldAccessModifier,fieldName,dataType);
        fieldLayouts.add(fieldLayoutTemplate);
    }

    private void intialiseMethodLayout(View method, IMethod aMethod){

        TextView methodAccessModifier = (TextView)method.findViewById(R.id.methodAccessModifierTemplate);
        methodAccessModifier.setText(aMethod.getAccessModifier().getSymbol());

        TextView methodName = (TextView) method.findViewById(R.id.methodNameTemplate);
        methodName.setText(aMethod.getMethodName());

        TextView parameters = (TextView) method.findViewById(R.id.parametersTemplate);
        parameters.setText(aMethod.getParameters());

        TextView returnType = (TextView) method.findViewById(R.id.returnTypeTemplate);
        returnType.setText(aMethod.getReturnType());

        MethodLayoutTemplate methodLayout = new MethodLayoutTemplate(methodAccessModifier, methodName, parameters, returnType);
        methodLayouts.add(methodLayout);
    }

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

    public void setUml(IUML uml){

        this.uml = uml ;
    }

}
