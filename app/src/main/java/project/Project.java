package project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uml_components.IUML;
import uml_components.UML;
/**
 * Created by Aashish Indorewala on 27-Aug-16.
 *
 * This class stores one entire project:
 * A project comprises:
 * 1. List of UMLs ie all the classes in the project
 * 2. Name of the project
 * This class should only be used through its interface : IProject
 */
public class Project implements Serializable, IProject{
	private List<IUML> umlList;
	private String projectName;


	public Project(List<IUML> umlList, String projectName) {
		this.umlList = umlList;
		this.projectName = projectName;
	}

	public Project(){
		this.projectName = "";
		this.umlList = new ArrayList<>();
	}

	//Getters and Setters
	@Override
	public final List<IUML> getUmlList() {
		return umlList;
	}
	@Override
	public final void setUmlList(List<IUML> umlList) {
		this.umlList = umlList;
	}

	@Override
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Override
	public String getProjectName() {
		return this.projectName;
	}

}
