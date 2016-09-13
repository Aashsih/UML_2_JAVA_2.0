package uml_components;

import utils.AccessModifier;
/**
 * Created by Aashish Indorewala on 27-Aug-16.
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
