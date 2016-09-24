package utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Aashish Indorewala on 27-Aug-16.
 */
public enum AccessModifier {
	PUBLIC("+"),PRIVATE("-"),PROTECTED("#"),DEFAULT(" ");

	private final String symbol;

	private static Map<String, AccessModifier> map = new HashMap<>();

	static {
		for(AccessModifier accessModifier : AccessModifier.values()){
			map.put(accessModifier.symbol,accessModifier);
		}
	}

	private AccessModifier(String symbol) {
		this.symbol = symbol;
	}
	public String getSymbol() {
		return symbol;
	}


	public static AccessModifier valueOfSymbol(String symbol){
		return map.get(symbol);
	}

}
