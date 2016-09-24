package uml_components;

import utils.AccessModifier;
/**
 * Created by Aashish Indorewala on 27-Aug-16.
 *
 * This represents an interface between the user and the Variable and Field class
 * The Variable class should be used through the interface eg:
 * The Variable and Field classes should be used through this interface eg:
 * IVariable aVariable = new Variable();
 * IVariable aField = new Field();
 */
public interface IVariable {
	public  void setAccessModifier(AccessModifier modifier);
	
	public  void setStatic(boolean isStatic);
	
	public  AccessModifier getAccessModifier();
	
	public  boolean isStatic();
	
	public String getVarName();
	public String getType();
	public boolean isFinal();
	public void setVarName(String varName);
	public void setType(String type);
	public void setFinal(boolean isFinal);
}
