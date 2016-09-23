package uml_components;

import java.util.List;
import utils.AccessModifier;
/**
 * Created by Aashish Indorewala on 27-Aug-16.
 */
public interface IMethod {
	
	public void setAccessModifer(AccessModifier access);
	public void setStatic(boolean isStatic);
	public void setFinal(boolean isFinal);
	public void setReturnType(String returnType);
	public void setMethodName(String methodName);
	public void setParameters(String parameters);
	public AccessModifier getAccessModifier();
	public boolean isStatic();
	public boolean isFinal();
	public String getReturnType();
	public String getMethodName();
	public String getParameters();


}
