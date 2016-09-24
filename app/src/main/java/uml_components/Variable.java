package uml_components;

import java.io.Serializable;

import utils.AccessModifier;

/**
 * Created by Aashish Indorewala on 27-Aug-16.
 *
 * This class stores all the information for a variable that could be passed as a parameter.
 * This class will not be used as of now but forms a basis for the Field class.
 * This class is created to be modified later in case compiler checks for user's inputs are to be made.
 *
 */
public class Variable implements Serializable,IVariable{
	protected String varName;
	protected String type;
	protected boolean isFinal;
	
	public Variable(String type,String varName, boolean isFinal){
		this.setType(type);
		this.setVarName(varName);
		this.setFinal(isFinal);

	}

	public Variable(){
		this("","",false);
	}

	//Getters and Setters
	public final String getVarName() {
		return varName;
	}
	public final String getType() {
		return type;
	}
	public final boolean isFinal() {
		return isFinal;
	}
	public final void setVarName(String varName){
		//check rules before assigning a value
		this.varName = varName;	
	}
	public final void setType(String type){
		//check rules before assigning a value
		this.type = type;	
	}
	public final void setFinal(boolean isFinal){
		this.isFinal = isFinal;
	}

	//The following methods are not Required for a Variable
	@Override
	public void setAccessModifier(AccessModifier modifier) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setStatic(boolean isStatic) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public AccessModifier getAccessModifier() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isStatic() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
