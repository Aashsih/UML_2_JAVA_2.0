package project;

import java.util.List;

import uml_components.IUML;
import uml_components.UML;
/**
 * Created by Aashish Indorewala on 27-Aug-16.
 */
public interface IProject {
	public List<IUML> getUmlList();

	public void setUmlList(List<IUML> umlList);

	public void setProjectName(String projectName);

	public String getProjectName();
}
