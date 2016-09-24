package uml_components;
import java.io.Serializable;
/**
 * Created by Aashish Indorewala on 27-Aug-16.
 */
import utils.AccessModifier;

/**
 * Created by Aashish Indorewala on 27-Aug-16.
 *
 * This class stores all the information for one Method:
 * 1. AccessModifier
 * 2. Is method static
 * 3. Is Method final
 * 4. ReturnType
 * 5. MethodName
 * 6. Paramteres
 *
 * This class should only be used through its interface eg:
 * IMethod aMethod = new Method();
 */
public class Method implements Serializable, IMethod{
	
	private AccessModifier access;
	private boolean isStatic;
	private boolean isFinal;
	private String returnType;
	private String methodName;
	private String parameters;
	
	
	
	public Method(AccessModifier access, boolean isStatic, boolean isFinal, String returnType, String methodName,
			String parameters) {
		this.setAccessModifer(access);
		this.setStatic(isStatic);
		this.setFinal(isFinal);
		this.setReturnType(returnType);
		this.setMethodName(methodName);
		this.setParameters(parameters);
		
	}
	//Getters and Setters
	public Method(){
		this(AccessModifier.DEFAULT,false,false,"","","");
	}

	@Override
	public final void setAccessModifer(AccessModifier access) {
		this.access = access;
	}

	@Override
	public final void setStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}
	@Override
	public final void setFinal(boolean isFinal) {
		this.isFinal = isFinal;
	}
	@Override
	public final void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	@Override
	public final void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	@Override
	public final void setParameters(String parameters) {
		this.parameters = parameters;
	}
	@Override
	public final AccessModifier getAccessModifier() {
		return access;
	}
	@Override
	public final boolean isStatic() {
		return isStatic;
	}
	@Override
	public final boolean isFinal() {
		return isFinal;
	}
	@Override
	public final String getReturnType() {
		return returnType;
	}
	public final String getMethodName() {
		return methodName;
	}
	public final String getParameters() {
		return parameters;
	}
	
//	public static void main(String[] args){
//		IMethod mehtod = new Method(AccessModifier.DEFAULT,true,true,"","",null);
//		mehtod.getReturnType();
//	}
	

}
