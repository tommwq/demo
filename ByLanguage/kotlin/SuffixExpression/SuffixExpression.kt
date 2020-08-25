
class Stack<T>(): ArrayList<T>() {
    fun push(value: T) {
        add(value)
    }
    
    fun pop() {
        val count = count()
        if (count > 0) removeAt(count - 1) else throw IllegalStateException()
    }

    fun top(): T {
        val count = count()
        return if (count > 0) get(count - 1) else throw IllegalStateException()
    }
}

val Variable = 1
val Value = 2
val Plus = 3
val Multiply = 4

data class Token (val type: Int, val token: String)

fun main(args: Array<String>) {

    val expression = "u1 + u2 * 3"

    

    val stack = Stack<Token>()
    
    
    println("ok")
}

fun 
