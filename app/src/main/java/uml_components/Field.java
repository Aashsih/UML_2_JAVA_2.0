package uml_components;
import utils.AccessModifier;
/**
 * Created by Aashish Indorewala on 27-Aug-16.
 *
 * This class stores all the information for a field of a class (UML diagram)
 * It inherits from the Variable class and adds information like:
 * 1. AccessModifier
 * 2. isStatic
 * This Class should always be used only through the interface IVariable and not directly.
 */
public final class Field extends Variable{

	private AccessModifier modifier;
	private boolean isStatic;
	
	public Field(String type, String varName,boolean isFinal,AccessModifier modifier, boolean isStatic) {
		super(type, varName, isFinal);
		this.setAccessModifier(modifier);
		this.setStatic(isStatic);
	}

	public Field() {
		this("","",false,AccessModifier.DEFAULT,false);
	}

	//Getters and Setters
	@Override
	public final void setAccessModifier(AccessModifier modifier){
		this.modifier = modifier;
	}
	@Override
	public final void setStatic(boolean isStatic){
		this.isStatic = isStatic;
	}
	@Override
	public final AccessModifier getAccessModifier(){
		return this.modifier;
	}
	@Override
	public final boolean isStatic(){
		return isStatic;
	}
}
