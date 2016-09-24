package uml_components;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import utils.ClassType;

/**
 * Created by Aashish Indorewala on 27-Aug-16.
 *
 * This Class represents one UML Class Diagram containing:
 * 1. Class Name
 * 2. Class Type (CLASS,ENUMERATION,INTERFACE)
 * 3. Methods : List of methods
 * 4. Fields : list of fields
 *
 * This class should only be instantiated through the interface IUML eg:
 * IUML aUML = new UML();
 */
public class UML implements Serializable, IUML{
	private String importStatements;
	private String className;
	private ClassType classType;
	private List<IMethod> methodList;
	private List<IVariable> variableList;
	
	
	public UML(){
		this.importStatements = "";
		this.className = "Class Name";
		this.classType = ClassType.CLASS;
		this.methodList = new ArrayList<>();
		this.variableList = new ArrayList<>();
	}
	
	public UML(String importStatements,String className, ClassType classType, List<IMethod> methodList, List<IVariable> variableList) {
		this.importStatements = importStatements;
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

	@Override
	public String getImportStatements() {
		return importStatements;
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

	@Override
	public void setImportStatements(String importStatements) {
		this.importStatements = importStatements;
	}


}
