package de.peeeq.parseq.ast;

import java.util.List;

public class AttributeDef {

	public final String typ;
	public final String attr;
	public final String returns;
	public final String implementedBy;

	public AttributeDef(String typ, String attr, String returns2, String implementedBy2) {
		this.typ = typ;
		this.attr = attr;
		this.returns = returns2;
		this.implementedBy = implementedBy2;
	}

}