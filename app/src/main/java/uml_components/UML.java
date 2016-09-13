package uml_components;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Aashish Indorewala on 27-Aug-16.
 */
public class UML implements Serializable, IUML{
	private String className;
	private String classType;
	private List<Method> methodList;
	private List<Variable> variableList;
	
	
	
	
	public UML(String className, String classType, List<Method> methodList, List<Variable> variableList) {
		
		this.className = className;
		this.classType = classType;
		this.methodList = methodList;
		this.variableList = variableList;
	}
	//Getters
	@Override
	public final String getClassName() {
		return className;
	}
	@Override
	public final String getClassType() {
		return classType;
	}
	@Override
	public final List<Method> getMethodList() {
		return methodList;
	}
	@Override
	public final List<Variable> getVariableList() {
		return variableList;
	}
	
	//Setters
	@Override
	public final void setClassName(String className) {
		this.className = className;
	}
	@Override
	public final void setClassType(String classType) {
		this.classType = classType;
	}
	@Override
	public final void setMethodList(List<Method> methodList) {
		this.methodList = methodList;
	}
	@Override
	public final void setVariableList(List<Variable> variableList) {
		this.variableList = variableList;
	}
	
	
	
	
	
	
	
	
	
}
