package de.peeeq.switchgenerator;

import de.peeeq.pscript.pscript.*;

abstract class ExprSwitch <T> {
	abstract public T caseExprAssignment(ExprAssignment exprAssignment);
	abstract public T caseExprOr(ExprOr exprOr);
	abstract public T caseExprAnd(ExprAnd exprAnd);
	abstract public T caseExprEquality(ExprEquality exprEquality);
	abstract public T caseExprComparison(ExprComparison exprComparison);
	abstract public T caseExprAdditive(ExprAdditive exprAdditive);
	abstract public T caseExprMult(ExprMult exprMult);
	abstract public T caseExprSign(ExprSign exprSign);
	abstract public T caseExprNot(ExprNot exprNot);
	abstract public T caseExprMember(ExprMember exprMember);
	abstract public T caseExprIntVal(ExprIntVal exprIntVal);
	abstract public T caseExprNumVal(ExprNumVal exprNumVal);
	abstract public T caseExprStrval(ExprStrval exprStrval);
	abstract public T caseExprBoolVal(ExprBoolVal exprBoolVal);
	abstract public T caseExprBuildinFunction(ExprBuildinFunction exprBuildinFunction);
	abstract public T caseExprFunctioncall(ExprFunctioncall exprFunctioncall);
	abstract public T caseExprIdentifier(ExprIdentifier exprIdentifier);
	public T match(Expr expr) {
		if (expr instanceof ExprAssignment) return caseExprAssignment((ExprAssignment)expr);
		if (expr instanceof ExprOr) return caseExprOr((ExprOr)expr);
		if (expr instanceof ExprAnd) return caseExprAnd((ExprAnd)expr);
		if (expr instanceof ExprEquality) return caseExprEquality((ExprEquality)expr);
		if (expr instanceof ExprComparison) return caseExprComparison((ExprComparison)expr);
		if (expr instanceof ExprAdditive) return caseExprAdditive((ExprAdditive)expr);
		if (expr instanceof ExprMult) return caseExprMult((ExprMult)expr);
		if (expr instanceof ExprSign) return caseExprSign((ExprSign)expr);
		if (expr instanceof ExprNot) return caseExprNot((ExprNot)expr);
		if (expr instanceof ExprMember) return caseExprMember((ExprMember)expr);
		if (expr instanceof ExprIntVal) return caseExprIntVal((ExprIntVal)expr);
		if (expr instanceof ExprNumVal) return caseExprNumVal((ExprNumVal)expr);
		if (expr instanceof ExprStrval) return caseExprStrval((ExprStrval)expr);
		if (expr instanceof ExprBoolVal) return caseExprBoolVal((ExprBoolVal)expr);
		if (expr instanceof ExprBuildinFunction) return caseExprBuildinFunction((ExprBuildinFunction)expr);
		if (expr instanceof ExprFunctioncall) return caseExprFunctioncall((ExprFunctioncall)expr);
		if (expr instanceof ExprIdentifier) return caseExprIdentifier((ExprIdentifier)expr);
		throw new Error("Switch did not match any case.");
	}
}

public class SwitchTest {

	public SwitchTest() {
		new ExprSwitch<Integer>() {

			@Override
			public Integer caseExprAssignment(ExprAssignment exprAssignment) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Integer caseExprOr(ExprOr exprOr) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Integer caseExprAnd(ExprAnd exprAnd) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Integer caseExprEquality(ExprEquality exprEquality) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Integer caseExprComparison(ExprComparison exprComparison) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Integer caseExprAdditive(ExprAdditive exprAdditive) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Integer caseExprMult(ExprMult exprMult) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Integer caseExprSign(ExprSign exprSign) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Integer caseExprNot(ExprNot exprNot) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Integer caseExprMember(ExprMember exprMember) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Integer caseExprIntVal(ExprIntVal exprIntVal) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Integer caseExprNumVal(ExprNumVal exprNumVal) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Integer caseExprStrval(ExprStrval exprStrval) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Integer caseExprBoolVal(ExprBoolVal exprBoolVal) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Integer caseExprBuildinFunction(
					ExprBuildinFunction exprBuildinFunction) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Integer caseExprFunctioncall(
					ExprFunctioncall exprFunctioncall) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Integer caseExprIdentifier(ExprIdentifier exprIdentifier) {
				// TODO Auto-generated method stub
				return null;
			}
		}.match(null);
	}
	
}