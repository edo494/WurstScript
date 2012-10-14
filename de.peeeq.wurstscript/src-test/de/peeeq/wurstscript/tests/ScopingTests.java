package de.peeeq.wurstscript.tests;

import org.junit.Test;

import de.peeeq.wurstscript.utils.Utils;

public class ScopingTests extends PscriptTest {

//	@Test
//	public void test_duplicates_cu() {
//		testAssertErrorsLines(false, "already defined",
//				"package A",
//				"endpackage",
//				"package A",
//				"endpackage",
//				"package B",
//				"	import A",
//				"endpackage");
//	}
	
	@Test
	public void test_duplicates_jass_func() {
		testAssertErrorsLines(false, "already defined",
				"function foo takes nothing returns integer",
				"	return 3",
				"endfunction",
				"function foo takes nothing returns integer",
				"	return foo()",
				"endfunction",
				"package A",
				"	init",
				"		foo()",
				"endpackage");
	}
	
	
	@Test
	public void test_import_same() {
		testAssertErrorsLines(false, "ambiguous",
				"package A",
				"	public int x = 2",
				"endpackage",
				"package B",
				"	public int x = 3",
				"endpackage",
				"package test",
				"	import B",
				"	import A",
				"	native testSuccess()",
				"	init",
				"		if x == 3",
				"			testSuccess()",
				"endpackage");
	}
	
	@Test
	public void test_import_same_package() {
		testAssertOkLines(false,
				"package A",
				"	public int x = 2",
				"endpackage",
				"package B",
				"	public int x = 3",
				"endpackage",
				"package test",
				"	import B",
				"	import A",
				"	native testSuccess()",
				"	int x = 4",
				"	init",
				"		if x == 4", // prefer var from current package
				"			testSuccess()",
				"endpackage");
	}
	
}