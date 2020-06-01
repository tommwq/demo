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
