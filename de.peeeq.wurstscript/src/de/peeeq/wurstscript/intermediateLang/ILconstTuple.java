package de.peeeq.wurstscript.intermediateLang;

import de.peeeq.wurstscript.types.WurstTypeString;
import de.peeeq.wurstscript.types.WurstType;

public class ILconstTuple extends ILconstAbstract {

	private ILconst[] values;

	public ILconstTuple(ILconst ... values) {
		this.values = values;
	}

	
	@Override
	public String print() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for (ILconst v : values) {
			sb.append(v.print());
			sb.append(", ");
		}
		sb.append(")");
		return sb.toString();
	}

	public WurstType getType() {
		return WurstTypeString.instance();
	}


	@Override
	public boolean isEqualTo(ILconst other) {
		if (other instanceof ILconstTuple) {
			ILconstTuple o = (ILconstTuple) other;
			if (o.values.length != values.length){
				return false;
			}
			for (int i=0; i<values.length; i++) {
				if (!values[i].equals(o.values[i])) {
					return false;
				}
			}
			return true;
		}
		return false;
	}


	public ILconst getValue(int index) {
		return values[index];
	}


	public ILconstTuple updated(int tupleIndex, ILconst newVal) {
		ILconst[] newValues = new ILconst[values.length]; 
		System.arraycopy(values, 0, newValues, 0, values.length);
		newValues[tupleIndex] = newVal;
		return new ILconstTuple(newValues);
	}


}
