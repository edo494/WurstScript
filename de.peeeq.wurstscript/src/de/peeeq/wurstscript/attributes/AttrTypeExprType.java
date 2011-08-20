package de.peeeq.wurstscript.attributes;

import katja.common.NE;
import de.peeeq.wurstscript.ast.ClassDefPos;
import de.peeeq.wurstscript.ast.NativeTypePos;
import de.peeeq.wurstscript.ast.NoTypeExprPos;
import de.peeeq.wurstscript.ast.TypeDefPos;
import de.peeeq.wurstscript.ast.TypeExprPos;
import de.peeeq.wurstscript.types.NativeTypes;
import de.peeeq.wurstscript.types.PScriptTypeUnknown;
import de.peeeq.wurstscript.types.PscriptNativeType;
import de.peeeq.wurstscript.types.PscriptType;
import de.peeeq.wurstscript.types.PscriptTypeClass;


/**
 * this attribute gives you the type for a type expr
 *
 */
public class AttrTypeExprType extends Attribute<TypeExprPos, PscriptType> {


	public AttrTypeExprType(Attributes attr) {
		super(attr);
	}

	@Override
	protected PscriptType calculate(TypeExprPos node) {
		final String typename = node.typeName().term();
		TypeDefPos t = attr.typeDef.get(node);
		return t.Switch(new TypeDefPos.Switch<PscriptType, NE>() {

			@Override
			public PscriptType CaseNativeTypePos(NativeTypePos term)
					throws NE {
				PscriptType typ = NativeTypes.nativeType(term.name().term());
				if (typ != null) {
					return typ;
				}
				if (term.typ() instanceof NoTypeExprPos) {
					attr.addError(term.source(), "Unknown base type: " + term.name().term());
					return PScriptTypeUnknown.instance();
				}
				PscriptType superType = get((TypeExprPos) term.typ());
				return PscriptNativeType.instance(typename, superType);
			}

			@Override
			public PscriptType CaseClassDefPos(ClassDefPos term)
					throws NE {
				return new PscriptTypeClass(term);
			}
		});
	}




}
