package de.peeeq.pscript.pscript.util;
import de.peeeq.pscript.pscript.*;
public abstract class FuncDefSwitchVoid {
	abstract public void caseNativeFunc(NativeFunc nativeFunc);
	public void doSwitch(FuncDef funcDef) {
if ( funcDef == null) throw new IllegalArgumentException("Switch element must not be null.");
		if (funcDef instanceof NativeFunc) { caseNativeFunc((NativeFunc)funcDef); return; }
		throw new Error("Switch did not match any case: " + funcDef);
	}
}
