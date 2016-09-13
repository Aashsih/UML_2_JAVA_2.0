package uml_components;
import utils.AccessModifier;
/**
 * Created by Aashish Indorewala on 27-Aug-16.
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
