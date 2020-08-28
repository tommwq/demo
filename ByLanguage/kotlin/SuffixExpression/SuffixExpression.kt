// edit kotlinc.bat, wrap %~dps0kotlin-compiler with "：
//   "%~dps0kotlin-compiler"
//
// kotlinc main.kt -include-runtime -d main.jar
// java -jar main.jar

/*
从左到右遍历中缀表达式的每个数字和符号，若是数字就输出，即成为后缀表达式的一部分；若是符号，则判断其与栈顶符号的优先级，是右括号或优先级低于找顶符号（乘除优先加减）则栈顶元素依次出找并输出，并将当前符号进栈，一直到最终输出后缀表达式为止。
*/

fun main(args: Array<String>) {
    println('ok')
}

fun expressionToSuffixExpression


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

