package utils;
/**
 * Created by Aashish Indorewala on 27-Aug-16.
 */
public enum AccessModifier {
	PUBLIC("+"),PRIVATE("-"),PROTECTED("#"),DEFAULT(" ");

	private final String symbol;

	private AccessModifier(String symbol) {
		this.symbol = symbol;
	}
	public String getSymbol() {
		return symbol;
	}

}
