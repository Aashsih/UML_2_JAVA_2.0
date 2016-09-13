package project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import uml_components.UML;
/**
 * Created by Aashish Indorewala on 27-Aug-16.
 */
public class Project implements Serializable, IProject{
	private List<UML> umlList;
	private String projectName;

	public Project(List<UML> umlList, String projectName) {
		this.umlList = umlList;
		this.projectName = projectName;
	}

	public Project(){
		this.projectName = "";
		this.umlList = new ArrayList<>();
	}

	@Override
	public final List<UML> getUmlList() {
		return umlList;
	}
	@Override
	public final void setUmlList(List<UML> umlList) {
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
