package uml_components;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import utils.ClassType;

/**
 * Created by Aashish Indorewala on 27-Aug-16.
 */
public class UML implements Serializable, IUML{
	private String className;
	private ClassType classType;
	private List<IMethod> methodList;
	private List<IVariable> variableList;
	
	
	public UML(){
		this.className = "Class Name";
		this.classType = ClassType.CLASS;
		this.methodList = new ArrayList<>();
		this.variableList = new ArrayList<>();
	}
	
	public UML(String className, ClassType classType, List<IMethod> methodList, List<IVariable> variableList) {
		
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
	public final ClassType getClassType() {
		return classType;
	}
	@Override
	public final List<IMethod> getMethodList() {
		return methodList;
	}
	@Override
	public final List<IVariable> getVariableList() {
		return variableList;
	}
	
	//Setters
	@Override
	public final void setClassName(String className) {
		this.className = className;
	}
	@Override
	public final void setClassType(ClassType classType) {
		this.classType = classType;
	}
	@Override
	public final void setMethodList(List<IMethod> methodList) {
		this.methodList = methodList;
	}
	@Override
	public final void setVariableList(List<IVariable> variableList) {
		this.variableList = variableList;
	}
	
	
	
	
	
	
	
	
	
}
