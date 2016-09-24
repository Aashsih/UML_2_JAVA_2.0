package uml_components;

import java.util.List;

import utils.ClassType;

/**
 * Created by Aashish Indorewala on 27-Aug-16.
 *
 *This represents an interface between the user and the UML Class
 *This should be used to interact with the user eg:
 * IUML aUML = new UML():
 */
public interface IUML {
	//Getters
	public String getClassName();
	public ClassType getClassType();
	public List<IMethod> getMethodList();
	public List<IVariable> getVariableList();
	
	//Setters
	public void setClassName(String className);
	public void setClassType(ClassType classType);
	public void setMethodList(List<IMethod> methodList);
	public void setVariableList(List<IVariable> variableList);
}
