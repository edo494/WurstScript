---
layout: default
title: WurstScript Manual
---


_by peq & Frotty_ _Version: 12.10.12_ 


WurstScript is a programming language named after the german word for sausage.

The sausage is a symbol for encapsulation (Peel/Pelle), compactness (sausage meat/Brät) and modularization (cut it into slices!). And because you normally know whats inside a sausage the project is also open source and easy to use (cook).

Remember: WurstScript and its related tools are in a probably unstable state and under heavy development, so you may encounter errors and bugs we don't know about. Please report any
problem with our [issue tracker at GitHub](https://github.com/peq/WurstScript/issues/new).

# Syntax
The WurstScript Syntax uses indention to define Blocks, rather than using curly
braces (as in Java) or keywords like 'endif' (as in Jass).

In general WurstScript tries to avoid using symbols as much as possible to
provide a clear and readable look. At the same time most of Jass' verbosity got removed. 

# Basics


Wurst code is organized into _packages_. All your wurst code has to be inside a _package_. 
Packages can also _import_ other packages in order to use variables, functions, classes, etc. from the imported package. Packages can have an _init_ block to do stuff when the map is loaded.


	package HelloWurst
		// you can import stuff from other packages:
		import PrintingHelper
        // NOTE: Since the Wurst.wurst-Update this isn't needed anymore.
	
		// the init block is called at map start
		init
			/* call a function */
			print("Hello Wurst!")

	endpackage

You can still use normal jass code outside of packages, but inside packages you have to adhere
to the wurst rules.

## Functions

A _function_ definition consists of a name, a list of formal parameters and a return 
type. The return type is declared after the formal parameters using the _returns_ keyword.
If the function does not return a value this part is omitted.

	// this function returns the maximum of two integers
	function max(int a, int b) returns int
		if a > b
			return a
		else
			return b
			
	// this function prints the maximum of two integers
	function printMax(int a, int b)
		print(max(a,b).toString())

## Variables

Global (local) variables can be declared anywhere in a package (function). 
A constant value may be declared using the _let_ keyword. Variables are declared
by using the _var_ keyword or writing the type of the variable before its name.
Remember that functionnames have to start with a lowercase letter.

	// declaring a constant - the type is inferred from the initial expression
	let x = 5
	// declaring a variable - the inferring works the same as 'let', but the value cn be changed
	var y = 5
	// declaring a variable with explicit type
	int z = 7
	// declaring an array
	int array a
    
    // inside a function
    function getUnitInfo( unit u )
        player p = u.getOwner()
        var name = u.getName()
        print( name )
        real x = u.getX()
        real y = u.getY()
        
        
With these basic concepts you should be able to do anything you already know for jass.
The syntax is a little bit different of course, but this is covered in the next chapter.


## Expressions

Semi-Formal syntax:

	Expr ::= 
		  Expr + Expr       
		| Expr - Expr   
		| Expr / Expr       // real division
		| Expr div Expr     // integer division
		| Expr % Expr       // real modulo
		| Expr mod Expr     // integer modulo
		| Expr and Expr     
		| Expr or Expr
		| Expr < Expr 
		| Expr <= Expr 
		| Expr > Expr 
		| Expr >= Expr 
		| Expr == Expr 
		| Expr != Expr 
		| - Expr            
		| not Expr 		
		| IDENTIFIER // variable access
		| IDENTIFIER(Expr, Expr, ...) // function call 	
		| Expr . IDENTIFIER // member variable		
		| Expr . IDENTIFIER(Expr, Expr, ...) // member function		

An _IDENTIFIER_ is a name of a variable or function. It may start with letters and may
contain letters, numbers and underscores. 

The definition above does not show calls to generic functions. These will be handled in 
a separate chapter about generics.


## Statements

### Skip

The simplest statement is the _skip_ statement. It has no effect and can be used to create empty blocks.

### Ifs


	if x > y 
		... // the content inside the block simply has to be indent
	else if x < y // closing the if, opening the elseif-block
		...
	else
		...
	// Closing the if by indenting back

	if x > y or x <= z and "blub" != "blah"
		print("if is true")
	print("if done.")
    
### Switchs
    
    // i is of type int
    switch i
        case 1
            print("1")
        case 3
            print("3")
        case 88
            print("88")
        default
            print("not implemented")
            
As you see in the example, a switch statement is basically a nicer syntax for
nesting ifs and else ifs, with the special default case.

### Loops

    while a > b // while-loop with input condition
		...
        
	for int i = 0 to 10 // for-loop
		...

	for int i = 0 to 10 step 2 // for-loop with step 2
		...

	for int i = 10 downto 0 // wurst can also count down wards
        ...






	for unit u in someGroup // loop over all units in a group
		...

	for unit u from someGroup // loop over all units in group and remove the units from the group
		...

	for int i in myList
		...


#### For-in/from Loops

The for-in loop lets you iterate over any object which provides an iterator. 
A for-in loop can be transformed into an equivalent while-loop very easily:


	for A a in b
		Statements
	
	// is equivalent to:
	let iterator = b.iterator()
	while iterator.hasNext()
		A a = iterator.next()
		Statements*
	iterator.close()


Note that iterator.close() will also be called before any return statement inside the body of the while loop.

If you already have an iterator or want to access further functions of the iterator you can use the for-from loop.
The translation is very similar:

	for A a from i
		Statements
	
	// is equivalent to:
	while i.hasNext()
		A a = i.next()
		Statements


Note that you have to close the iterator i yourself.

So how do you write your own iterator? Just add a function "hasNext" which returns a boolean and a function "next" which
returns the next element for your type and you have an iterator which can be used in for-from loops.

To make a type usable in for-in loops you have to provide a function "iterator" which returns an iterator. Such an iterator
should also provide a close functions which clears all resources allocated by the iterator. Most often the iterator just
destroys itself in the close function.

### Assignment Shorthands

	
WurstScript supports the following shorthands for assignments:
	
	i++         // i = i + 1
	i--         // i = i - 1
	x += y      // x = x + y
	x -= y      // x = x - y
	x *= y      // x = x * y
	x /= y      // x = x / y

Because these shorthands simply get translated into their equivalents, they can
be used with overloaded operators, too.

## Functions ##

	function foo() // parentheses instead of "takes", "returns nothing" obsolete.
		...

	function foo2( unit u ) // parameters
		RemoveUnit( u )

	function bar( integer i ) returns int // "returns" [type]
		return i + 4
	
	function blub() returns int // without parameters
		return someArray[5]

	function foobar()
		int i // local variable
		i = i + 1 // variable assignment
		int i2 = i // support for locals anywhere inside a function









# Classes

Classes are easy, powerful and very helpful constructs. A _class_ defines data and related functions working with this data. Take a look at this small example:



	Caster dummyCaster = new Caster(200.0, 400.0)
	dummyCaster.castFlameStrike(500.0, 30.0)
	destroy dummyCaster		


In this example we created a Caster named "dummyCaster" at the location(200, 400). 
Then we ordered dummyCaster to cast a flame strike at another position and finally we destroyed "dummyCaster".

This example shows you how to create a new object (line 1), invoke a function on an object (line 2) and how to destroy an object (line 3).
But how can you define a new object type like "Caster"? This is where classes come in. A class defines a new kind of object.
A class defines variables and functions, which every instance of this class should understand.
A class can also define how a new object is constructed ("construct") and what should happen, when it is destroyed ("ondestroy").

Defining a caster-class might look like this:


	class Caster // opening the class-block. "Caster" is the name of the class
		unit u // class variables can be defined anywhere inside a class
	
		construct(real x, real y)
			u = CreateUnit(...)
	
		function castFlameStrike(real x, real y)
			UnitIssueOrder(u, ...)
	
		ondestroy
			KillUnit(u)



## Constructors

WurstScript allows you to define your own constructors for each class. A constructor
is a function to _construct_ a new instance of a class.
The constructor is called when creating the class via the _new_ keyword and allows operations being done to the class-instance before returning it.


	class Pair
		int a
		int b

		construct( int a, int b )
		    this.a = a
		    this.b = b

		function add() returns int
		    return a + b


	function test()
		Pair p = new Pair(2,4)
		int sum = p.add()
		print(sum)




In this example the constructor takes two integers a and b as parameters and sets the class variables to those. 
You can define more than one constructor.


	class Pair
		int a
		int b

		construct( int a, int b )
		    this.a = a
		    this.b = b
	
		construct( int a, int b, int c )
		    this.a = a
		    this.b = b
			a += c
			b += c

	function test()
		Pair p = new Pair(2,4)
		Pair p2 = new Pair(3,4,5)




In this example the class pair has two constructors - one taking 2 and the second one taking three parameters.
Depending on parameter-type and -count Wurst automatically decides which constructor to take when using "new".

## This 

The _this_ keyword refers to the current instance of the class on which the function was called. This also allows us to name the parameters the same as the class variables.
However it can be left out in classfunctions.

## ondestroy

Each class can have one _ondestroy_ block. This block is executed before the instance is destroyed.
Ondestroy blocks are defined as previously shown


	class UnitWrapper
		unit u
	
		...
	
		ondestroy
			RemoveUnit(u)



## Static Elements

You can declare variables and functions as _static_ by writing the keyword "static" in front of the declaration. Static variables and functions belong to the class whereas
other elements belong to instances of the class. So you can call static functions without having an instance of the class.


	class Terrain
		static real someVal = 12.
	
		static function getZ( real x, real y ) returns real
			...
	
	function foo()
		real z = Terrain.getZ( 0, 0 ) // call with $Classname$.$StaticFunctionName$()
		real r = Terrain.someVal // Same with members


## Visibility Rules 

By default class elements are visible everywhere. You can add the modifiers "private" or "protected" in front of a variable or function definition to restrict its visibility.
Private elements can only be seen from within the class. Protected elements can be seen within the enclosing package.

## Subclassing

A class can _extend_ an other class. The class then inherits all the non-private functions and variables from that class
and can be used anywhere where the super class can be used. 

Constructors of the class have to specify how the super class should be constructed. This is done using a _super_ call, 
which defines the arguments for the super constructor. There can not be any statement before this call.

Functions inherited from super classes can be overridden in the subclass. Such functions have to be annotated with _override_.
    
          
### Example

	class Missile 		
		construct(string fx, real x, real y)
			// ...
		
		function onCollide(unit u)
			skip
	
		// ...
		
	// a fireball missile is a special kind of missile
	class FireballMissile extends Missile	
		// we construct it using a fireball fx
		construct(real x, real y)
			super("Abilities\\Weapons\\RedDragonBreath\\RedDragonMissile.mdl", x, y)
		// a fireball does some special things when it collides
		// with a unit, so we override the onCollide method
		override function onCollide(unit u)
			// create a big explosion here ;)
			//...
			
			
Note that overridden functions also get called when the instance is casted to a supertype.

### Example

    Class A
        string name
        
        construct(string name)
            this.name = name
            
        function printName()
            print("Instance of A named: " + name )


    Class B extends A
    
        construct(string name)
            super(name)
            
        override function printName()
            print("Instance of B named: " + name )
            
    init 
        A a = new B("first") // This works because B extends A
        a.printName() // This will print "Instance of B named: first", because a is an Instance of B.
        
This is especially usefull when iterating through ClassInstances of the same supertype,
meaning you don't have to cast the instance to it's proper subtype.

## Typecasting

You need typecasting for several reasons.
One being to save class instances and for example attaching them onto a timer, like done in TimerUtils.wurst
This process also needs to be reversed (casting from int to a classtype)
But typecasting is also necessary when using subtyping, in order to down- and upcast instances.
In order to typecast, you use the keyword _castTo_

### Examples
	class Test
		int val
		
	init
		Test t = new Test()
		int i = t castTo int
		
		
	--
	
	class A
	
	class B extends A
	
	init
		B b = new B()
		A a = b castTo a
		
		
## Dynamic dispatch

Wurst features dynamic dispatching when accessing classinstances.
What this means is simple: If you have a classinstance of a subclass B, casted into a variable of
the superclass A, calling a function with that reference will automatically call the overridden function from
the original type.
It is easier to understand with an example:

###Example

	class A
		function printOut()
			print("I'm A")
			
	class B extends A
		override function printOut()
			print("I'm B")
			
	init
		A a = new B()
		a.printOut()
		// this will print "I'm B", even though it's a type A variable
		
## instanceof

If you want to typecast a classinstance, remember it can only be cast to an int, or a sub/super- class.
To ensure correct casting, a typecheck is needed.
In Wurst you can check the type of a classinstance with the _instanceof_ keyword.

###Example

	class A
	
	class B extends A

	init
		A a = new B()
		
		if a instanceof B
			print("It's a B")

## Abstract Classes

An _abstract_ class is a class, that is declared abstract — it may or may not
include abstract functions. You cannot create an instance of an abstract class,
but subclass it.

An abstract function is declared with the keyword 'abstract' and by leaving out
an implementation.
    
    abstract function onHit()

Abstract classes are similar to interfaces, but they can have own, implemented
functions and variables like normal classes.

The advantage of an abstract class is, that you can reference and call the
abstract functions inside the abstract class, without having a vlid
implementation.

Take Collision-Responses as example. You have several Bodies in your map, and
you want each of them to behave differently.
You could do this by centralizing the updating function, or by using abstract
classes like so:

    abstract class CollidableObject

	abstract function onHit()
	
	function checkCollision(CollidableObject o)
	    if this.inRange(o)
	      onHit()
	      o.onHit()
	    
    class Ball extends CollidableObject
	
	override function onHit()
	    print("I'm a ball")
		
    class Rect extends CollidableObject
	
	override function onHit()
	    print("I'm a Rect")
	   
	   
	   
Because CollidableObject requires it's subclasses to implement the function
onHit; it can be called within the abstract class and without knowing it's
type.

If a subclass of an abstract class does not implement all abstract functions
from its superclass, it has to be abstract, too.

            
# Interfaces 


	interface Listener
		function onClick()

		function onAttack( real x, real y ) returns boolean
	

	class ExpertListener implements Listener
		function onClick()
			print("clicked")

	
		function onAttack( real x, real y ) returns boolean
			AddSpecialEffect(EFFECT_STRING, x ,y)



An _interface_ is a group of related functions with empty bodies.
If a class implements a certain interface it has to replace all the empty functions from the interface.
A class can _implement_ multiple interfaces, meaning that it complies to more interface requirements at the same time.

	class ExampleClass implements Interface1, Interface2, ...
		    // all functions from the implemented interfaces


With interfaces (and modules if implicit) you can now up- and downcast any Class that implements it.
This is especially useful for saving all instances from classes that inherit 1 interface in only 1 List/Array

## Defender methods

An interface can have functions with an implementation. This implementation is used, when a class implementing the interface
does not provide an implementation of the method itself. Usually this is not needed but in some cases it might
be necessary in order to evolve an interface without breaking its implementing classes.



# Generics

Generics make it possible to abstract from specific types and only program with placeholders
for types. This is especially useful for container types (e.g. Lists) where we do not want to code a
ListOfA, ListOfB, ListOfC for every class A, B, C which we need a list for.
Think of it as creating a general List with all it's functionality, but for an
unknown type, that gets defined when creating the instance.

With generics we can instead write only one implementation for lists and use it with all types.
Generic type parameter and arguments are
written in angled  bracket s  (<  an d >) after the identifier.



	// a generic interface for Sets with type parameter T
	interface Set<T>
		// adds an element to the set
		function add(T t)
		
		// removes an element from the set
		function remove(T t)
		
		// returns the number of elements in the set
		function size() returns int
		
		// checks whether a certain element is in the set
		function contains(T t) returns boolean
		
	class SetImpl<T> implements Set<T>
		// [...] implementation of the interface

If we have a class defined like this, we can then use it with a concrete type (e.g. Missile):

	// create a set of missiles
	Set<Missile> missiles = new SetImpl<Missile>();
	// add a missile m
	missiles.add(m);

Generic parameters in Wurst can be bound to integers, class types and interface types but 
not to other native types or to tuple types.


# Modules

A _module_ is a small package which provides some functionality for classes. Classes can _use_ modules to inherit the functionality of the module.

You can use the functions from the used module as if they were declared in the class. You can also _override_ functions defined in a module to adjust its behavior.

If you know object oriented languages like Java or C#: Modules are like abstract classes and using a module is like inheriting from an abstract class but *without the sub-typing*. (WurstScript takes a different approach to enable polymorphism, but this is not implemented yet)

## Example 1 

In this example we just have a class which uses a module A. The resulting program behaves as if the code from module A would be pasted into Class C.


	module A
		public function foo()
		    ...


	class C
		use A


## Example 2

Modules are more than just a mechanism to copy code. Classes and modules can override functions defined in used modules:


	// a module to store an integer variable
	module IntContainer
		int x

		public function getX() returns int
		    return int

		public function setX(int x)
		    this.x = x

	// a container which only stores positive ints
	module PositiveIntContainer
		use IntContainer
		
		// override the setter to only store positive integers
		override function setX(int x)
		    if x >= 0
		        IntContainer.setX(x)


## Visibility & Usage Rules

 * Variables of modules are always private
 * private functions are only usable from the module itself
 * each function of a module must be declared public or private
 * if a class uses a module it inherits only the public functions of the module
    * you can use a module with *private* (not implemented yet). This will let you use the functionality of the module without exposing its functions to the outside.




## Overriding Functions

 * You can *override* functions which are defined in used modules by writing the override keyword in front of a function.
 * If two modules are used, which have the same function, it *must* be overridden by the underlying class or module in order to avoid ambiguousness (of course this is only possible if the function signatures match. We are thinking about a solution for this)
 * private functions cannot be overridden

## Abstract Functions

Modules can declare abstract functions: Functions without a given implementation. Abstract functions have to be implemented by the underlying classes.

## Thistype

You can use _thistype_ inside a module to refer to the type of the class which uses the module. This can be useful if you need to cast the class to an integer and back.

# Enums

In Wurst, _Enums_ can be used to set up collections of named (int) constants.
These Constants can then be accessed via the Enum's name:

    enum State
        FLYING
        GROUND
        WATER
        
    init
        State s = State.GROUND
        
You can also use enums inside of classes

    class C
        State currentState
        
        construct( State state )
            currentState = state
            
To check the current value of an enum, you can use the switch statement.
Note that all Enummembers have to be checked (or a defaut).

    switch currentState
        case State.FLYING
            print("flying")
        case State.GROUND
            print("ground")
        case State.WATER
            print("water")
            
        
        


# Tuple Types 

With _tuple_ types you can group several variables into one bundle. This can be used to return more than one value from a function, to create custom types and of course for better readability.

Note that tuples are not like classes. There are some important differences:
- You do not destroy tuple values.
- When you assign a tuple to a different variable or pass it to a function you create a copy of the tuple. Changes to this copy will not affect the original tuple.
- Tuple types cannot be bound to type parameters, so you can not have a List{vec} if vec is a tuple type.
- As tuple types are not created on the heap you have no performance overhead compared to using single variables.


	// Example 1:

		// define a tuple
		tuple vec(real x, real y, real z)

		init
			// create a new tuple value
			vec v = vec(1,2,3)
			// change parts of the tuple
			v.x = 4
			// create a copy of v and call it u
			vec u = v
			u.y = 5
			if v.x == 4 and v.y == 2 and u.y == 5
				testSuccess()


	// Example 2:

		tuple pair(real x, real y)
		init
			pair p = pair(1,2)
			// swap the values of p.x and p.y
			p = pair(p.y, p.x)
			if p.x == 2 and p.y == 1
				testSuccess()
				
				
Because tuples don't have any functions themselves, you can add extension
functions to an existing tuple type in order to achieve class-like
functionality.
Remember that you can't modify the value of a tuple in it's extension function
- so you have to return a new tuple everytime if you wan't to change something.
Look at the Vector package in the Standard Library for some tuple usage
examples. (Vectors.wurst)



# Extension Functions

Extension functions enable you to "add" functions to existing types without
creating a new derived type, recompiling, or otherwise modifying the original
type. 
Extension functions are a special kind of static function, but they are called
as if they were instance functions on the extended type.

## Declaration

	public function TYPE.EXTFUNCTIONNAME(PARAMETERS) returns ...
		BODY
		// The keyword "this" inside the body refers to the instance of the extended type

## Examples 


	// Declaration
	public function unit.getX() returns real
		return GetUnitX(this)

	// Works with any type
	public function real.half() returns real
		return this/2

	// Parameters
	public function int.add( int value )
		return this + value
	 
	// Usage
	unit u = CreateUnit(...)
	...
	print( u.getX().half() )

	// Also classes, e.g. setter and getter for private vars
	public function BlubClass.getPrivateMember() returns real
		return this.privateMember

# Operator Overloading

Operator Overloading allows you to change the behaviour of internal operators +, -, \* and / for custom arguments.
A quick example from the standard library (Vectors.wurst):

    // Defining the "+" operator for the tupletype vec3
    public function vec3.op_plus( vec3 v ) returns vec3
        return vec3(this.x + v.x, this.y + v.y, this.z + v.z)
        
    // Usage example
    vec3 a = (1.,1.,1.)
    vec3 b = (1.,1.,1.)
    // Without Operator Overloading (the add function was replaced by it)
    vec3 c = a.add( b )
    // With operator Overloading
    vec3 c = a + b
   
You can overload operators for existing types via Extension-Functions or via class-functions for the specific classtype.
In order to define an overloading function it has to be named as following:

    +  "op_plus"
    -  "op_minus"
    *  "op_mult"
    /  "op_divReal"
    
# Packages 
As mentioned above every code-segment written in Wurst has to be inside a _package_,
packages define the code organization and separate name-spaces.
Packages can also have global variables - every variable that is not inside another block (function/class/module)
is declared global for that package.
Packages can import other packages to access classes, functions, variables, etc. that are not defined private.


	// declaring
	package $PackageName$
		...
	endpackage
	// importing
	package Blub
		import SomePackage
		import AnotherPackge // importing more than 1 package
		import MyExternalWurstFile // Import a scriptfile from the class-lib directory 
	endpackage


When importing an external Scriptfile you just write the Filename without .wurst.
Remember to name the package inside your file the same as the name of the scriptfile.

## Public declarations
All members of a package ar private by default.
If you want to make them visible inside packages that import that package you have to add the keyword "public".

## Constants
You can declare a variable constant to prohibited changes after initialization.
This has no impact on the generated code but throws an error when trying to compile.



### Examples

	package First
		int i // (private by default) Visible inside the package
		public int j // public, gets exported
	endpackage

	package Second
		import First
	
		int k = i // Error
		int m = j // Works, because j is public
	endpackage


	package Foo
		constant int myImportantNumber = 21364 // has to be initialized with declaration

		function blub()
			myImportantNumber = 123 // Error
		
		private constant int myPrivateNumber = 123 // Correct keyword order
	endpackage






## Init blocks
Another package feature are init blocks.
Every package can have one init block anywhere inside it. 
All operations inside the init block of a package are being executed at mapstart. 

At the beginning of an init block you can assume that all global variables inside the 
current package are initialized.

If a given package A imports package B, the initializer in package B is run before A's.
If packages import each other, the order is undefined.


*Note:* Since wc3 has a micro op limitation, too many operations inside init-blocks may stop it from fully executing. In order to avoid this you should only place map-init Stuff inside the init blocks and use timers and own inits for the other stuff.

# Compiletime Functions
Compiletime Functions are functions, that get executed when compiling yur script/map.
They mainly offer the possibility to create Object-Editor Objects via code.

## Declaration

    @compiltetime function foo()
    
Compiltetime functions cannot take nor return anything.

# Standard Library 

Wurst comes with a library of some useful standard functions

## Collections 

### Lists 

Suppose you have an interface Closable and classes Door, Chest and Gate  implementing this interface. Furthermore the interface I
provides functions "open" and "close". You can use Lists to store different kind of Closable things in one place.


	Gate gate = ...
	Door door = ...
	Chest chest = ...
	// create a new list
	LinkedList{Closeable} closeables = new LinkedList{Closeable}()
	// add the different items to the list
	closeables.add(gate)
	closeables.add(door)
	closeables.add(chest)
	// now close everything in the list
	for Closeable c1 in closeables
		c1.close()
	// and open everything again (but not the chest, as we want to keep our treasure)
	closeables.remove(chest) // remove the chest from the list
	for Closeable c2 in closeables
		c2.open()



# vJass vs Wurst

If you plan to convert from using vJass to Wurst, it will probably be helpful
to read this segment.
Specific vJass syntax and features are directly compared to the equivalent
WurstCode.


You might be wondering why Wurst is missing so many vJass features.
The answer is simple: Wurst utilizes several better/smarter constructs to achieve
similar functionality like in vJass and beyond.

Textmacros and member-arrays aside, most vJass features either got useless due
to Wurst or got replaced.

## Feature table

<table>
<tr><td> <strong>Feature</strong> 		</td><td> <strong>vJass</strong> 	</td><td> <strong>Wurst</strong> 	</td><td></td></tr>
<tr><td> <strong>code organization</strong> 	</td><td> libraries 			</td><td> packages 			</td><td></td></tr>
<tr><td> <strong>nested scopes</strong> 	</td><td> yes 				</td><td> -  				</td><td></td></tr>
<tr><td> <strong>classes</strong> 		</td><td> structs 			</td><td> classes 			</td><td></td></tr>
<tr><td> <strong>modules</strong> 		</td><td> yes 		</td><td> yes 					</td><td></td></tr>
<tr><td> <strong>function interfaces</strong> 	</td><td> yes 		</td><td> not yet 			</td><td></td></tr>
<tr><td> <strong>interfaces</strong> 		</td><td> yes 		</td><td> yes 					</td><td></td></tr>
<tr><td> <strong>textmacros</strong> 		</td><td> yes 		</td><td> - 					</td><td></td></tr>
<tr><td> <strong>keyword</strong> 		</td><td> yes 		</td><td> - 					</td><td></td></tr>
<tr><td> <strong>struct onInit</strong> 	</td><td> yes 		</td><td> - 					</td><td></td></tr>
<tr><td> <strong>stub methods</strong> 	</td><td> yes 		</td><td> overriding and abstract classes 	</td><td></td></tr>
<tr><td> <strong>Dynamic arrays</strong> 	</td><td> yes 		</td><td> - 					</td><td></td></tr>
<tr><td> <strong>Array members</strong> 	</td><td> yes 		</td><td> - 					</td><td></td></tr>
<tr><td> <strong>Delegate</strong> 		</td><td> yes 		</td><td> - 					</td><td></td></tr>
<tr><td> <strong>Array structs</strong> 	</td><td> yes 		</td><td> - 					</td><td></td></tr>
<tr><td> <strong>Keys</strong> 			</td><td> yes 		</td><td> - 					</td><td></td></tr>
<tr><td> <strong>Sized arrays</strong> 	</td><td> yes 		</td><td> - 					</td><td></td></tr>
<tr><td> <strong>2D arrays</strong> 		</td><td> yes 		</td><td> - 					</td><td></td></tr>
<tr><td> <strong>Colon</strong> 		</td><td> yes 		</td><td> - 					</td><td></td></tr>
<tr><td> <strong>Operator overloading</strong>	</td><td> for [] 	</td><td> for +,-,*,/ 		</td><td></td></tr>
<tr><td> <strong>hook</strong> 			</td><td> yes 		</td><td> - 					</td><td></td></tr>
<tr><td> <strong>inject</strong> 		</td><td> yes 		</td><td> - 					</td><td></td></tr>
<tr><td> <strong>Loading from SLK</strong> 	</td><td> yes 		</td><td> - 					</td><td></td></tr>
<tr><td> <strong>Script optimization</strong> 	</td><td> only inlining	</td><td> yes 					</td><td></td></tr>
<tr><td> <strong>Function inlining</strong> 	</td><td> limited 	</td><td> yes 					</td><td></td></tr>
<tr><td> <strong>External tools</strong> 	</td><td> yes 		</td><td> - 					</td><td></td></tr>
<tr><td> <strong>Typechecker</strong> 		</td><td> limited 	</td><td> yes 					</td><td></td></tr>
<tr><td> <strong>Function sorting</strong> 	</td><td> - 		</td><td> yes 					</td><td></td></tr>
<tr><td> <strong>Extension functions</strong> 	</td><td> - 		</td><td> yes 					</td><td></td></tr>
<tr><td> <strong>generics</strong> 		</td><td> - 		</td><td> yes 					</td><td></td></tr>
<tr><td> <strong>tuple types</strong> 		</td><td> - 		</td><td> yes 					</td><td></td></tr>
<tr><td> <strong>closures</strong> 		</td><td> - 		</td><td>planned </td><td></td></tr>
<tr><td> <strong>compiletimefunctions</strong>	</td><td> - 		</td><td> yes 					</td><td></td></tr>
</table>


## xGlobals 

Instead of a globals block, every variable outside a block inside a package is considered a global in that package.

_*vJass:*_

	globals
		integer foo
	endglobals


_*Wurst:*_

	package test
		...
		int foo
	endpackage


## xFunctions

_*vJass:*_

	function foo takes nothing returns nothing

	endfunction

	function bar takes real r returns real

	endfunction


_*Wurst:*_

	function foo()
		...

	function bar(real r) returns real
		...


## xLocals 

locals can be declared and initialized anywhere inside a function.

_*vJass:*_

	function foo takes nothing returns nothing
		local integer i = 0
		local integer j
		i = i + 1
		j = i
	endfunction


_*Wurst:*_

	function foo()
		int i = 0
		i++
		int j = i


## xLibrarys/Scopes 

Name-spaces/code organisation in wurst is handled by packages.
All code in Wurst has to be inside a package.

_*vJass:*_

	library foo

	endlibrary


_*Wurst:*_

	package foo

	endpackage


### ximporting 

_*vJass:*_

	library foo requires/needs/uses bar, blub, stuff

	endlibrary


_*Wurst:*_

	package foo
		import bar
		import blub
		import stuff

	endpackage


### xinit-blocks 

_*vJass:*_

	library foo initializer init

		private function init takes nothing returns nothing
		    ...
		endfunction

	endlibrary

_*Wurst:*_

	package foo
		
		init
		    ...
	endpackage



## xStructs / Classes 

_*vJass:*_

	struct BlubStruct
		static method create takes nothing returns BlubStruct
		    local thistype this = thistype.allocate()
		    // constructor code
		    return this
		endmethod

		method add takes integer a, integer b returns integer
		    return a + b
		endmethod

		static method sub takes integer a, integer b returns integer
		    return a - b
		endmethod

		method onDestroy takes nothing returns nothing
		    // destructor code
		endmethod
	endstruct

	// create instance:
	local BlubStruct blub = BlubStruct.create()
	// call method
	call blub.add( 1, 1 )
	// call static method
	call BlubStruct.sub( 2, 1 )
	// destroy instance
	call blub.destroy


_*Wurst:*_

	class BlubClass
		construct()
		    // constructor code

		function add(int a, int b) returns int
		    return a + b

		static function sub(int a, int b) returns int
		    return a - b

		ondestroy
		    // destructor code

	// create instance:
	BlubClass blub = new BlubClass()
	// call function
	blub.add( 1, 1 )
	// call static function
	BlubClass.sub( 2, 1 )
	// destroy instance
	destroy blub



## xModules 

_*vJass:*_

	module Bla
		...
	endmodule

	struct C
		implement Bla
		...
	endstruct


_*Wurst:*_


	module Bla
		...


	class C
		use Bla
		...



Wurst-modules are also more powerful than vJass modules. They allow overriding and can declare abstract functions.

# Eclipse Plugin

Installation: You can find information on how to install the plugin at the [Wurst Homepage](http://peq.github.com/WurstScript/).

## Usage

1. Go to *File*-*New Project* and select *New Wurst Project*. Choose a name and press Finish.
2. Add a new file named *wurst.dependencies*. Each line in this file points to a folder on which your project depends.
	As every project depends on the standard library you have to add the path to *Wurstpack/wurstscript/lib* in your Wurstpack folder.
3. Create a new file with the *.wurst* filename extension and an arbitrary name. You can now start writing your code.

### Useful Features 

* Press *Ctrl*+*space* to open the auto complete assistant.
* Hold down *Ctrl* and click on a function or variable to jump to the point where it was defined. 


# Optimizer

The Wurstcompiler has a build-in scriptoptimizer which will, when enabled, optimize the Jass code in various ways.
Jass optimization got very important to provide playable framerates when using very enhanced and complex systems.
On the one hand the optimizer cleans the code, making it smaller in size and removing useless stuff in order to reduce RAM-usage.
On the other hand it also has some optimizations to increase the speed of execution and performance of the code.

## Cleaning


Stuff that is being removed, changed or not even printed

* Comments
* Unneeded White-spaces
* Excessive parentheses
* Some useless Jassconstants replaced with "null"

## Name compression

Smaller names execute faster, so all the names of functions and variables are being compressed to the shortest name possible.


## Inlining

Inlining is not an easy task, but brings great performance boosts to systems which use many different functions. 
It also makes coding easier and more readable, because you don't have to care about the performance loss
when splitting stuff into too many functions.

In the current state, every function that has none or only 1 return statement gets inlined (when the inliner is activated).
Unlike the vJass Inliner, the Wurst Inliner can handle functions with more than 1 line,
also the parameter-order and usage doesn't matter.

All blizzard.j functions, such as BJs and Swaps, also get inlined.

Global variables that have a constant value get inlined as well as constant locals. (not yet implemented)


## Garbage Removal

The optimizer tries to get rid of every unused function and variable to lower the mapscript's filesize.

