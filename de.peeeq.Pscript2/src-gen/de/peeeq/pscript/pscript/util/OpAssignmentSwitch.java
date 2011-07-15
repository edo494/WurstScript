package de.peeeq.pscript.pscript.util;
import de.peeeq.pscript.pscript.*;
public abstract class OpAssignmentSwitch <T> {
	abstract public T caseOpPlusAssign(OpPlusAssign opPlusAssign);
	abstract public T caseOpMinusAssign(OpMinusAssign opMinusAssign);
	abstract public T caseOpAssign(OpAssign opAssign);
	public T doSwitch(OpAssignment opAssignment) {
		if (opAssignment instanceof OpPlusAssign) return caseOpPlusAssign((OpPlusAssign)opAssignment);
		if (opAssignment instanceof OpMinusAssign) return caseOpMinusAssign((OpMinusAssign)opAssignment);
		if (opAssignment instanceof OpAssign) return caseOpAssign((OpAssign)opAssignment);
		throw new Error("Switch did not match any case.");
	}
}
