// edit kotlinc.bat, wrap %~dps0kotlin-compiler with "：
//   "%~dps0kotlin-compiler"
//
// kotlinc main.kt -include-runtime -d main.jar
// java -jar main.jar

package com.test

fun main(args: Array<String>) {
	println(getGreeting("guest"))
	println(getGreetingShort("guest"))

	val foo = Foo()
	foo.foo()

	val d = Derived()
	d.vfct()

	val b = MyBar()
	b.bar()

	guess(8)

	for (i in 6 downTo 0 step 2) {
		println(i)
	}

	println(Abc.a())
}

fun guess(x: Int) {
	if (x < 7) {
		println("less")
	} else if (x > 7) {
		println("greater")
	} else {
		println("equal")
	}
}

interface Bar {
	fun bar()
}

class MyBar(): Bar {
	override fun bar() {
		println("MyBar")
	}
}

open class Base {
	open fun vfct() {
		println("Base")
	}
}

class Derived(): Base() {
	override fun vfct() {
		println("Derived")
	}
}

class Foo() {
	fun foo() {
		println("foo")
	}
}

fun getGreeting(name: String): String {
	return "Hello, $name!"
}

fun getGreetingShort(name: String) = "Hi, $name!"

class Person1 constructor(firstName: String) {
}

class Person2(firstName: String) {
}

class Person3(name: String) {
	init {
		println("init")
	}
}

class Person4(val firstName: String, val lastName: String, var age: Int) {
}

class Person5 {
	constructor(firstName: String) {
	}
}

class Person6(val name: String) {
	constructor(name: String, lastName: String): this(name) {
	}
}

val constValue = 1
var variableValue = 2

class Abc {
	companion object {
		@JvmStatic
		fun a(): Int = 1
	}
}
