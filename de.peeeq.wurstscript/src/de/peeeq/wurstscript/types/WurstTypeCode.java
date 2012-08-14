package de.peeeq.wurstscript.types;

import de.peeeq.wurstscript.ast.AstElement;


public class WurstTypeCode extends WurstTypePrimitive {

	private static final WurstTypeCode instance = new WurstTypeCode();

	// make constructor private as we only need one instance
	private WurstTypeCode() {
		super("code");
	}
	
	@Override
	public boolean isSubtypeOf(WurstType other, AstElement location) {
		return other instanceof WurstTypeCode;
	}

	public static WurstTypeCode instance() {
		return instance;
	}


}
