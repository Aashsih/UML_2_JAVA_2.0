package uml_components;

import java.util.List;
/**
 * Created by Aashish Indorewala on 27-Aug-16.
 */
public interface IUML {
	//Getters
	public String getClassName();
	public String getClassType(); 
	public List<Method> getMethodList();
	public List<Variable> getVariableList();
	
	//Setters
	public void setClassName(String className);
	public void setClassType(String classType);
	public void setMethodList(List<Method> methodList);
	public void setVariableList(List<Variable> variableList);
}
