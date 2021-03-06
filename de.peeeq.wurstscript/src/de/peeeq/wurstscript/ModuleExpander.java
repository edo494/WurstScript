package de.peeeq.wurstscript;

import com.google.common.base.Preconditions;

import de.peeeq.wurstscript.ast.Ast;
import de.peeeq.wurstscript.ast.ClassOrModule;
import de.peeeq.wurstscript.ast.CompilationUnit;
import de.peeeq.wurstscript.ast.ModuleDef;
import de.peeeq.wurstscript.ast.ModuleUse;
import de.peeeq.wurstscript.ast.WEntity;
import de.peeeq.wurstscript.ast.WPackage;

public class ModuleExpander {


	public void expandModules(CompilationUnit cu) {
		for (WPackage t : cu.getPackages()) {
			expandModules((WPackage) t);
		}
	}

	private void expandModules(WPackage p) {
		for (WEntity e : p.getElements()) {
			if (e instanceof ClassOrModule) {
				expandModules((ClassOrModule) e);
			}
		}
	}

	private void expandModules(ClassOrModule m) {
		Preconditions.checkNotNull(m);
		if (m.getModuleInstanciations().size() > 0) {
			return;
		}
		

		for (ModuleUse moduleUse : m.getModuleUses()) {
			ModuleDef usedModule = moduleUse.attrModuleDef();
			if (usedModule == null) {
				moduleUse.addError("not found");
				continue;
			}
			expandModules(usedModule);

			
			
			m.getModuleInstanciations().add(
					Ast.ModuleInstanciation(moduleUse.getSource(), Ast.Modifiers(), 
							usedModule.getName(), usedModule.getMethods().copy(), usedModule.getVars().copy(), usedModule.getConstructors().copy(), 
							usedModule.getModuleInstanciations().copy(), usedModule.getModuleUses().copy(), usedModule.getOnDestroy().copy()));
		}
		
	}

	
}
