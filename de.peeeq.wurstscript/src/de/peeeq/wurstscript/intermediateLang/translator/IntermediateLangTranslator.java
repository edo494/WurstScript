package de.peeeq.wurstscript.intermediateLang.translator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


import katja.common.NE;
import de.peeeq.wurstscript.ast.AST.SortPos;
import de.peeeq.wurstscript.ast.ArgumentsPos;
import de.peeeq.wurstscript.ast.ClassDefPos;
import de.peeeq.wurstscript.ast.CompilationUnitPos;
import de.peeeq.wurstscript.ast.Expr;
import de.peeeq.wurstscript.ast.ExprAssignablePos;
import de.peeeq.wurstscript.ast.ExprBinaryPos;
import de.peeeq.wurstscript.ast.ExprBoolValPos;
import de.peeeq.wurstscript.ast.ExprFuncRefPos;
import de.peeeq.wurstscript.ast.ExprFunctionCallPos;
import de.peeeq.wurstscript.ast.ExprIntValPos;
import de.peeeq.wurstscript.ast.ExprMemberArrayVarPos;
import de.peeeq.wurstscript.ast.ExprMemberMethodPos;
import de.peeeq.wurstscript.ast.ExprMemberVarPos;
import de.peeeq.wurstscript.ast.ExprNewObjectPos;
import de.peeeq.wurstscript.ast.ExprPos;
import de.peeeq.wurstscript.ast.ExprRealValPos;
import de.peeeq.wurstscript.ast.ExprStringValPos;
import de.peeeq.wurstscript.ast.ExprThisPos;
import de.peeeq.wurstscript.ast.ExprUnaryPos;
import de.peeeq.wurstscript.ast.ExprVarAccessPos;
import de.peeeq.wurstscript.ast.ExprVarArrayAccessPos;
import de.peeeq.wurstscript.ast.FuncDefPos;
import de.peeeq.wurstscript.ast.FunctionDefinitionPos;
import de.peeeq.wurstscript.ast.GlobalVarDefPos;
import de.peeeq.wurstscript.ast.IndexesPos;
import de.peeeq.wurstscript.ast.InitBlockPos;
import de.peeeq.wurstscript.ast.LocalVarDefPos;
import de.peeeq.wurstscript.ast.NativeFuncPos;
import de.peeeq.wurstscript.ast.NativeTypePos;
import de.peeeq.wurstscript.ast.NoExpr;
import de.peeeq.wurstscript.ast.NoExprPos;
import de.peeeq.wurstscript.ast.OpAndPos;
import de.peeeq.wurstscript.ast.OpBinaryPos;
import de.peeeq.wurstscript.ast.OpDivIntPos;
import de.peeeq.wurstscript.ast.OpDivRealPos;
import de.peeeq.wurstscript.ast.OpEqualsPos;
import de.peeeq.wurstscript.ast.OpGreaterEqPos;
import de.peeeq.wurstscript.ast.OpGreaterPos;
import de.peeeq.wurstscript.ast.OpLessEqPos;
import de.peeeq.wurstscript.ast.OpLessPos;
import de.peeeq.wurstscript.ast.OpMinus;
import de.peeeq.wurstscript.ast.OpMinusPos;
import de.peeeq.wurstscript.ast.OpModIntPos;
import de.peeeq.wurstscript.ast.OpModRealPos;
import de.peeeq.wurstscript.ast.OpMultPos;
import de.peeeq.wurstscript.ast.OpNot;
import de.peeeq.wurstscript.ast.OpNotPos;
import de.peeeq.wurstscript.ast.OpOrPos;
import de.peeeq.wurstscript.ast.OpPlusPos;
import de.peeeq.wurstscript.ast.OpPos;
import de.peeeq.wurstscript.ast.OpUnary;
import de.peeeq.wurstscript.ast.OpUnaryPos;
import de.peeeq.wurstscript.ast.OpUnequalsPos;
import de.peeeq.wurstscript.ast.StmtDecRefCountPos;
import de.peeeq.wurstscript.ast.StmtDestroyPos;
import de.peeeq.wurstscript.ast.StmtErrPos;
import de.peeeq.wurstscript.ast.StmtIfPos;
import de.peeeq.wurstscript.ast.StmtIncRefCountPos;
import de.peeeq.wurstscript.ast.StmtReturnPos;
import de.peeeq.wurstscript.ast.StmtSetPos;
import de.peeeq.wurstscript.ast.StmtWhilePos;
import de.peeeq.wurstscript.ast.TypeExprPos;
import de.peeeq.wurstscript.ast.VarDefPos;
import de.peeeq.wurstscript.ast.WEntityPos;
import de.peeeq.wurstscript.ast.WPackagePos;
import de.peeeq.wurstscript.ast.WParameterPos;
import de.peeeq.wurstscript.ast.WStatementPos;
import de.peeeq.wurstscript.ast.WStatementsPos;
import de.peeeq.wurstscript.attributes.Attributes;
import de.peeeq.wurstscript.intermediateLang.ILStatement;
import de.peeeq.wurstscript.intermediateLang.ILarraySetVar;
import de.peeeq.wurstscript.intermediateLang.ILconstBool;
import de.peeeq.wurstscript.intermediateLang.ILconstFuncRef;
import de.peeeq.wurstscript.intermediateLang.ILconstInt;
import de.peeeq.wurstscript.intermediateLang.ILconstNum;
import de.peeeq.wurstscript.intermediateLang.ILconstString;
import de.peeeq.wurstscript.intermediateLang.ILexitwhen;
import de.peeeq.wurstscript.intermediateLang.ILfunction;
import de.peeeq.wurstscript.intermediateLang.ILfunctionCall;
import de.peeeq.wurstscript.intermediateLang.ILif;
import de.peeeq.wurstscript.intermediateLang.ILloop;
import de.peeeq.wurstscript.intermediateLang.ILprog;
import de.peeeq.wurstscript.intermediateLang.ILreturn;
import de.peeeq.wurstscript.intermediateLang.ILsetBinary;
import de.peeeq.wurstscript.intermediateLang.ILsetBinaryCR;
import de.peeeq.wurstscript.intermediateLang.ILsetVar;
import de.peeeq.wurstscript.intermediateLang.ILsetVarArray;
import de.peeeq.wurstscript.intermediateLang.ILvar;
import de.peeeq.wurstscript.intermediateLang.IlbuildinFunctionCall;
import de.peeeq.wurstscript.intermediateLang.Iloperator;
import de.peeeq.wurstscript.intermediateLang.IlsetConst;
import de.peeeq.wurstscript.intermediateLang.IlsetUnary;
import de.peeeq.wurstscript.types.PScriptTypeBool;
import de.peeeq.wurstscript.types.PScriptTypeInt;
import de.peeeq.wurstscript.types.PScriptTypeUnknown;
import de.peeeq.wurstscript.types.PScriptTypeVoid;
import de.peeeq.wurstscript.types.PscriptType;
import de.peeeq.wurstscript.utils.NotNullList;
import de.peeeq.wurstscript.types.PScriptTypeArray;
/**
 * translates an AST into the intermediate language
 */
public class IntermediateLangTranslator {

	private CompilationUnitPos cu;
	private ILprog prog = new ILprog();

	// stores the name for every element
	private Map<SortPos, String> elementNames = new HashMap<SortPos, String>();
	private Set<String> usedNames = new HashSet<String>();
	private Attributes attr;
	private Map<FunctionDefinitionPos, ILfunction> functions = new HashMap<FunctionDefinitionPos, ILfunction>();
	private Map<VarDefPos, ILvar> vars = new HashMap<VarDefPos, ILvar>();
	private long varUniqueNameCounter = 0;
	
	public IntermediateLangTranslator(CompilationUnitPos cu, Attributes attr) {
		this.cu = cu;
		this.attr = attr;
	}

	public ILprog translate() {

		for (WPackagePos p : cu) {
			translatePackage(p);
		}

		return prog;

	}

	private void translatePackage(final WPackagePos p) {
		for (WEntityPos e : p.elements()) {
			e.Switch(new WEntityPos.Switch<Void, NE>() {

				@Override
				public Void CaseNativeTypePos(NativeTypePos term) throws NE {
					// nothing to do here
					return null;
				}

				@Override
				public Void CaseClassDefPos(ClassDefPos term) throws NE {
					transltateClassDef(p, term);
					return null;
				}

				@Override
				public Void CaseFuncDefPos(FuncDefPos term) throws NE {
					translateFuncDef(p, term);
					return null;
				}

				@Override
				public Void CaseGlobalVarDefPos(GlobalVarDefPos term) throws NE {
					translateGlobalVarDef(p, term);
					return null;
				}

				@Override
				public Void CaseInitBlockPos(InitBlockPos term) throws NE {
					translateInitBlock(p, term);
					return null;
				}

				@Override
				public Void CaseNativeFuncPos(NativeFuncPos term) throws NE {
					// nothing to do here
					return null;
				}
			});
		}
	}

	protected void translateInitBlock(WPackagePos p, InitBlockPos term) {
		String name = getNameFor(term, p.name().term() + "_init");
		ILfunction initFunc = new ILfunction(name);
		prog.addInitializer(initFunc);
		translateFunctionBody(initFunc, term.body());

	}

	protected void translateFuncDef(WPackagePos p, FuncDefPos term) {
		ILfunction func = getFunction(term);
		prog.addFunction(func);
		if (term.signature().typ() instanceof TypeExprPos) {
			func.setReturnType(attr.typeExprType.get((TypeExprPos) term.signature().typ()));
		} else {
			func.setReturnType(PScriptTypeVoid.instance());
		}
	
		// TODO set parameters, returntype
		translateFunctionBody(func, term.body());
	}

	/**
	 * get the ILfunction for a given function
	 */
	private ILfunction getFunction(final FunctionDefinitionPos calledFunc) {
		if (functions.containsKey(calledFunc)) {
			return functions.get(calledFunc);
		}
		
		final WPackagePos p = attr.nearestPackage.get(calledFunc);
		
		String funcName = calledFunc.Switch(new FunctionDefinitionPos.Switch<String, NE>() {

			@Override
			public String CaseFuncDefPos(FuncDefPos term) throws NE {
				return getNameFor(calledFunc, p.name().term() + "_" + calledFunc.signature().name().term());
			}

			@Override
			public String CaseNativeFuncPos(NativeFuncPos term) throws NE {
				return calledFunc.signature().name().term();
			}
		});
		
		
		ILfunction func = new ILfunction(funcName);
		functions.put(calledFunc, func);
		return func;
	}

	private void translateFunctionBody(ILfunction func, WStatementsPos body) {
		func.getBody().addAll(translateStatements(func, body));

	}

	private List<ILStatement> translateStatements(ILfunction func, WStatementsPos statements) {
		List<ILStatement> result = new NotNullList<ILStatement>();
		for (WStatementPos s : statements) {
			result.addAll(translateStatement(func, s));
		}
		return result;
	}

	private List<ILStatement> translateStatement(final ILfunction func, WStatementPos s) {
		final List<ILStatement> result = new NotNullList<ILStatement>();
		return s.Switch(new WStatementPos.Switch<List<ILStatement>, NE>() {

			@Override
			public List<ILStatement> CaseExprMemberMethodPos(ExprMemberMethodPos term) throws NE {
				// TODO translate member method
				throw new Error("not implemented");
			}

			@Override
			public List<ILStatement> CaseExprFunctionCallPos(ExprFunctionCallPos term) throws NE {
				result.addAll(translateFunctionCall(func, null, term, term.args()));				
				return result;
			}

			@Override
			public List<ILStatement> CaseExprNewObjectPos(ExprNewObjectPos term) throws NE {
				// TODO implement new expr
				throw new Error("not implemented");
			}

			@Override
			public List<ILStatement> CaseStmtIfPos(StmtIfPos term) throws NE {
				ILvar cond = getNewLocalVar(func, PScriptTypeBool.instance(), "ifCondition");
				// translate condition
				result.addAll(translateExpr(func, cond, term.cond()));
				List<ILStatement> thenBlock = translateStatements(func, term.thenBlock());
				List<ILStatement> elseBlock = translateStatements(func, term.elseBlock());
				result.add(new ILif(cond, thenBlock, elseBlock));
				return result;
			}

			@Override
			public List<ILStatement> CaseStmtWhilePos(StmtWhilePos term) throws NE {
				ILvar whileCondition = getNewLocalVar(func, PScriptTypeBool.instance(), "whileCondition");
				ILvar negatedWhileCondition = getNewLocalVar(func, PScriptTypeBool.instance(), "exitwhenCondition");

				List<ILStatement> body = new NotNullList<ILStatement>();
				// calculate while condition
				body.addAll(translateExpr(func, whileCondition, term.cond()));
				// exitwhen not whileCondition
				body.add(new IlsetUnary(negatedWhileCondition, Iloperator.NOT, whileCondition));
				body.add(new ILexitwhen(negatedWhileCondition));
				body.addAll(translateStatements(func, term.body()));
				result.add(new ILloop(body));
				return result;
			}

			@Override
			public List<ILStatement> CaseLocalVarDefPos(LocalVarDefPos term) throws NE {
				ILvar v = getILvarForVarDef(term);
				func.getLocals().add(v);

				if (term.initialExpr() instanceof ExprPos) {
					// translate initial expr
					result.addAll(translateExpr(func, v, (ExprPos) term.initialExpr()));
				}
				return result;
			}

			private ILvar getArrayMemberVar(VarDefPos memberVar) {
				// TODO
				throw new Error("not implemented");
			}

			@Override
			public List<ILStatement> CaseStmtSetPos(final StmtSetPos term) throws NE {

				return term.left().Switch(new ExprAssignablePos.Switch<List<ILStatement>, NE>() {

					@Override
					public List<ILStatement> CaseExprMemberVarPos(ExprMemberVarPos left) throws NE {
						VarDefPos memberVar = attr.varDef.get(left);

						ILvar arVar = getArrayMemberVar(memberVar);

						// evaluate receiver object
						ILvar receiver = getNewLocalVar(func, PScriptTypeInt.instance(), "receiver");
						result.addAll(translateExpr(func, receiver, left.left()));

						PscriptType typ = attr.exprType.get(term.right());
						// evaluate right side
						ILvar tempResult = getNewLocalVar(func, typ, "temp");
						result.addAll(translateExpr(func, tempResult, term.right()));

						// arVar[receiver] = tempResult
						result.add(new ILarraySetVar(arVar, receiver, tempResult));
						return result;
					}

					@Override
					public List<ILStatement> CaseExprMemberArrayVarPos(ExprMemberArrayVarPos term) throws NE {
						// TODO class array members
						throw new Error("not implemented");
					}

					@Override
					public List<ILStatement> CaseExprVarAccessPos(ExprVarAccessPos left) throws NE {
						VarDefPos varDef = attr.varDef.get(left);
						ILvar v = getILvarForVarDef(varDef);

						result.addAll(translateExpr(func, v, term.right()));
						return result;
					}

					@Override
					public List<ILStatement> CaseExprVarArrayAccessPos(ExprVarArrayAccessPos arAccess) throws NE {
						VarDefPos varDef = attr.varDef.get(arAccess);
						PScriptTypeArray type = (PScriptTypeArray) attr.varDefType.get(varDef);
						ILvar indexResult = getNewLocalVar(func, PScriptTypeInt.instance(), "index");
						result.addAll(calculateIndexes(func, type, indexResult, arAccess.indexes()));
						
						ILvar tempResult = getNewLocalVar(func, type.getBaseType(), "temp");
						result.addAll(translateExpr(func, tempResult, term.right()));
						
						ILvar arrayVar = getILvarForVarDef(varDef);
						result.add(new ILarraySetVar(arrayVar , indexResult, tempResult));
						return result;
					}
				});
			}

			@Override
			public List<ILStatement> CaseStmtReturnPos(StmtReturnPos term) throws NE {
				PscriptType type = attr.exprType.get(term.obj());
				ILvar returnVar = getNewLocalVar(func, type, "tempReturn");
				result.addAll(translateExpr(func, returnVar, term.obj()));
				result.add(new ILreturn(returnVar));
				return result;
			}

			@Override
			public List<ILStatement> CaseStmtDestroyPos(StmtDestroyPos term) throws NE {
				// TODO destroy statement
				throw new Error("not implemented");
			}

			@Override
			public List<ILStatement> CaseStmtIncRefCountPos(StmtIncRefCountPos term) throws NE {
				throw new Error("not implemented");
			}

			@Override
			public List<ILStatement> CaseStmtDecRefCountPos(StmtDecRefCountPos term) throws NE {
				throw new Error("not implemented");
			}

			@Override
			public List<ILStatement> CaseStmtErrPos(StmtErrPos term) throws NE {
				throw new Error("not implemented");
			}
		});
	}

	protected List<ILStatement> translateFunctionCall(ILfunction func, final ILvar resultVar, ExprFunctionCallPos term, ArgumentsPos args) {
		final List<ILStatement> result = new NotNullList<ILStatement>();
		final FunctionDefinitionPos calledFunc = attr.funcDef.get(term);
		
		// add call dependency
		prog.addCallDependency(func, getFunction(calledFunc));
		
		
		// translate Arguments:
		int argCount = term.args().size();

		final PscriptType[] argumentTypes = new PscriptType[argCount];
		final ILvar[] argumentVars = new ILvar[argCount];

		
		for (int i = 0; i < argCount; i++) {
			ExprPos arg = term.args().get(i);
			WParameterPos param = calledFunc.signature().parameters().get(i);
			argumentTypes[i] = attr.varDefType.get(param);

			argumentVars[i] = getNewLocalVar(func, argumentTypes[i], calledFunc.signature().name().term() + "_param" + i);
			result.addAll(translateExpr(func, argumentVars[i], arg));
		}
		// make the call:
		calledFunc.Switch(new FunctionDefinitionPos.Switch<Void, NE>() {

			@Override
			public Void CaseFuncDefPos(FuncDefPos term) throws NE {
				result.add(new ILfunctionCall(resultVar, getFunction(calledFunc).getName(), argumentTypes, argumentVars));
				return null;
			}

			@Override
			public Void CaseNativeFuncPos(NativeFuncPos term) throws NE {
				result.add(new IlbuildinFunctionCall(resultVar, term.signature().name().term(), argumentVars));
				return null;
			}
		});
		
		return result;
	}

	private ILvar getILvarForVarDef(VarDefPos varDef) {
		if (vars.containsKey(varDef)) {
			return vars.get(varDef);
		}
		PscriptType typ = attr.varDefType.get(varDef);
		String name = varDef.name().term();
		ILvar v = new ILvar(name, typ);
		if (varDef instanceof GlobalVarDefPos) {
			WPackagePos pack = attr.nearestPackage.get(varDef);
			name = pack.name().term() + "_" + name;
		}
		vars.put(varDef, v);
		return v;
	}

	protected ILvar getNewLocalVar(ILfunction func, PscriptType type, String name) {
		// find unique name:
		String varName = name;
		if (func.getLocalNames().contains(varName)) {
			do {
				varUniqueNameCounter ++;
				varName = name + varUniqueNameCounter;
			} while (func.getLocalNames().contains(varName));
		}
		ILvar var = new ILvar(varName, type);
		func.addLocalVar(var);
		return var;
	}

	protected List<ILStatement> translateExpr(final ILfunction func, final ILvar resultVar, ExprPos expr) {
		final List<ILStatement> result = new NotNullList<ILStatement>();
		return expr.Switch(new ExprPos.Switch<List<ILStatement>, NE>() {

			@Override
			public List<ILStatement> CaseExprIntValPos(ExprIntValPos term) throws NE {
				result.add(new IlsetConst(resultVar, new ILconstInt(term.val().term())));
				return result;
			}

			@Override
			public List<ILStatement> CaseExprRealValPos(ExprRealValPos term) throws NE {
				result.add(new IlsetConst(resultVar, new ILconstNum(term.val().term())));
				return result;
			}

			@Override
			public List<ILStatement> CaseExprStringValPos(ExprStringValPos term) throws NE {
				result.add(new IlsetConst(resultVar, new ILconstString(term.val().term())));
				return result;
			}

			@Override
			public List<ILStatement> CaseExprBoolValPos(ExprBoolValPos term) throws NE {
				result.add(new IlsetConst(resultVar, new ILconstBool(term.val().term())));
				return result;
			}

			@Override
			public List<ILStatement> CaseExprFuncRefPos(ExprFuncRefPos term) throws NE {
				FunctionDefinitionPos f = attr.funcDef.get(term);
				ILfunction ilfunc = getFunction(f);
				result.add(new IlsetConst(resultVar, new ILconstFuncRef(ilfunc)));
				return result;
			}

			@Override
			public List<ILStatement> CaseExprVarAccessPos(ExprVarAccessPos term) throws NE {
				VarDefPos varDef = attr.varDef.get(term);
				ILvar var = getILvarForVarDef(varDef);
				result.add(new ILsetVar(resultVar, var));
				return result;
			}

			@Override
			public List<ILStatement> CaseExprVarArrayAccessPos(ExprVarArrayAccessPos arAccess) throws NE {
				VarDefPos varDef = attr.varDef.get(arAccess);
				PScriptTypeArray type = (PScriptTypeArray) attr.varDefType.get(varDef);
				ILvar indexResult = getNewLocalVar(func, PScriptTypeInt.instance(), "index");
				result.addAll(calculateIndexes(func, type, indexResult, arAccess.indexes()));
				
				
				ILvar arVar = getILvarForVarDef(varDef);
				result.add(new ILsetVarArray(resultVar, arVar, indexResult));
				return result;
			}

			@Override
			public List<ILStatement> CaseExprThisPos(ExprThisPos term) throws NE {
				ILvar var = getThisVariableForMethod(func);
				result.add(new ILsetVar(resultVar, var));
				return result;
			}

			@Override
			public List<ILStatement> CaseExprBinaryPos(ExprBinaryPos term) throws NE {

				if (term.op() instanceof OpAndPos) {
					result.addAll(translateExpr(func, resultVar, term.left()));
					result.add(new ILif(resultVar, translateExpr(func, resultVar, term.right()), new LinkedList<ILStatement>()));
				} else if (term.op() instanceof OpOrPos) {
					result.addAll(translateExpr(func, resultVar, term.left()));
					result.add(new ILif(resultVar, new LinkedList<ILStatement>(), translateExpr(func, resultVar, term.right())));
				} else {
					// evaluate left expr:
					PscriptType leftType = attr.exprType.get(term.left());
					ILvar leftVar = getNewLocalVar(func, leftType, "leftOperand");
					List<ILStatement> leftExpr = translateExpr(func, leftVar, term.left());
					// evaluate right expr:
					PscriptType rightType = attr.exprType.get(term.left());
					ILvar rightVar = getNewLocalVar(func, rightType, "rightOperand");
					List<ILStatement> rightExpr = translateExpr(func, rightVar, term.right());

					result.addAll(leftExpr);
					result.addAll(rightExpr);
					result.add(new ILsetBinary(resultVar, leftVar, translateOp(term.op()), rightVar));
				}
				return result;
			}

			@Override
			public List<ILStatement> CaseExprUnaryPos(final ExprUnaryPos term) throws NE {
				PscriptType type = attr.exprType.get(term.right());
				ILvar tempVar = getNewLocalVar(func, type, "temp");
				result.addAll(translateExpr(func, tempVar, term.right()));
				result.add(new IlsetUnary(resultVar, translateOp(term.op()), tempVar));
				return result;
			}

			@Override
			public List<ILStatement> CaseExprMemberVarPos(ExprMemberVarPos term) throws NE {
				// TODO Auto-generated method stub
				throw new Error("Not implemented yet.");
			}

			@Override
			public List<ILStatement> CaseExprMemberArrayVarPos(ExprMemberArrayVarPos term) throws NE {
				// TODO Auto-generated method stub
				throw new Error("Not implemented yet.");
			}

			@Override
			public List<ILStatement> CaseExprMemberMethodPos(ExprMemberMethodPos term) throws NE {
				// TODO Auto-generated method stub
				throw new Error("Not implemented yet.");
			}

			@Override
			public List<ILStatement> CaseExprFunctionCallPos(ExprFunctionCallPos term) throws NE {
				// TODO Auto-generated method stub
				result.addAll(translateFunctionCall(func, resultVar, term, term.args()));
				return result;
			}

			@Override
			public List<ILStatement> CaseExprNewObjectPos(ExprNewObjectPos term) throws NE {
				// TODO Auto-generated method stub
				throw new Error("Not implemented yet.");
			}

		});
	}

	protected Iloperator translateOp(OpPos op) {
		return op.Switch(new OpPos.Switch<Iloperator, NE>() {

			@Override
			public Iloperator CaseOpOrPos(OpOrPos term) throws NE {
				return Iloperator.OR;
			}

			@Override
			public Iloperator CaseOpAndPos(OpAndPos term) throws NE {
				return Iloperator.AND;
			}

			@Override
			public Iloperator CaseOpEqualsPos(OpEqualsPos term) throws NE {
				return Iloperator.EQUALITY;
			}

			@Override
			public Iloperator CaseOpUnequalsPos(OpUnequalsPos term) throws NE {
				return Iloperator.UNEQUALITY;
			}

			@Override
			public Iloperator CaseOpLessEqPos(OpLessEqPos term) throws NE {
				return Iloperator.LESS_EQ;
			}

			@Override
			public Iloperator CaseOpLessPos(OpLessPos term) throws NE {
				return Iloperator.LESS;
			}

			@Override
			public Iloperator CaseOpGreaterEqPos(OpGreaterEqPos term) throws NE {
				return Iloperator.GREATER_EQ;
			}

			@Override
			public Iloperator CaseOpGreaterPos(OpGreaterPos term) throws NE {
				return Iloperator.GREATER;
			}

			@Override
			public Iloperator CaseOpPlusPos(OpPlusPos term) throws NE {
				return Iloperator.PLUS;
			}

			@Override
			public Iloperator CaseOpMinusPos(OpMinusPos term) throws NE {
				return Iloperator.MINUS;
			}

			@Override
			public Iloperator CaseOpMultPos(OpMultPos term) throws NE {
				return Iloperator.MULT;
			}

			@Override
			public Iloperator CaseOpDivRealPos(OpDivRealPos term) throws NE {
				return Iloperator.DIV_REAL;
			}

			@Override
			public Iloperator CaseOpModRealPos(OpModRealPos term) throws NE {
				return Iloperator.MOD_REAL;
			}

			@Override
			public Iloperator CaseOpModIntPos(OpModIntPos term) throws NE {
				return Iloperator.MOD_INT;
			}

			@Override
			public Iloperator CaseOpDivIntPos(OpDivIntPos term) throws NE {
				return Iloperator.DIV_INT;
			}

			@Override
			public Iloperator CaseOpNotPos(OpNotPos term) throws NE {
				return Iloperator.NOT;
			}
		});
	}

	protected ILvar getThisVariableForMethod(ILfunction func) {
		return new ILvar("this", PScriptTypeInt.instance());
	}

	protected void translateGlobalVarDef(WPackagePos p, GlobalVarDefPos term) {
		ILvar v = getILvarForVarDef(term);
		prog.addGlobalVar(v);
		// TODO inital value + global dependencies
	}

	private String getNameFor(SortPos term, String name) {
		if (elementNames.containsKey(term)) {
			return elementNames.get(term);
		}
		String result = name;
		if (usedNames.contains(name)) {
			// try to find unique name by appending random numbers:
			do {
				varUniqueNameCounter++;
				result = name + varUniqueNameCounter;
			} while (usedNames.contains(result));
		}
		usedNames.add(result);
		elementNames.put(term, result);
		return result;
	}

	protected void transltateClassDef(WPackagePos p, ClassDefPos term) {
		// TODO Auto-generated method stub
		throw new Error("not implemented");
	}
	
	/**
	 * calculate the index for an array
	 * @param func current function
	 * @param type type of the array (stores information about dimensions and about where arraysizes can be found)
	 * @param indexResult the variable where to store the final one dimensional index to
	 * @param indexesPos the indexes
	 * @return
	 */
	protected List<ILStatement> calculateIndexes(ILfunction func, PScriptTypeArray type, ILvar indexResult, IndexesPos indexesPos) {
		if (indexesPos.size() > 1) {
			throw new Error("Multidimensional arrays are not supported yet.");
		}
		
		LinkedList<ILStatement> result = new LinkedList<ILStatement>();
		result.addAll(translateExpr(func, indexResult, indexesPos.first()));			
		return result;	
		
		
//		LinkedList<ILStatement> result = new LinkedList<ILStatement>();
//		ILvar[] indexVar = new ILvar[indexesPos.size()];
//		ILvar[] indexVarM = new ILvar[indexesPos.size()];
//		
//		// calculate indizes
//		for (int i = 0; i < indexesPos.size(); i++) {
//			indexVar[i] = getNewLocalVar(func, PScriptTypeInt.instance(), "index");
//			indexVarM[i] = getNewLocalVar(func, PScriptTypeInt.instance(), "indexM");
//			
//			int rightSize = 1;
//			for (int j = i+1; j < indexesPos.size(); j++) {
//				rightSize *= type.getSize(j);
//			}
//			
//			//result.addAll(translateExpr(indizes.get(i), indexVar[i]));
//			result.addAll(translateExpr(func, indexVar[i], indexesPos.get(i)));
//			result.add(new ILsetBinaryCR(indexVarM[i], indexVar[i], Iloperator.MULT, new ILconstInt(rightSize)));
//		}
//		
//		
//		result.add(new ILsetVar(indexResult, indexVarM[0]));
//		
//		// calculate the sum of the indizes2:
//		for (int i=1; i < indexesPos.size(); i++) {
//			result.add(new ILsetBinary(indexResult, indexResult, Iloperator.PLUS, indexVarM[i]));
//		}
//		return result;
	}

}