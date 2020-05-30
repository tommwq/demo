/*
clear; kotlinc -include-runtime Main.kt -d main.jar -classpath "C:\Users\WangQian\.gradle\caches\modules-2\files-2.1\org.jetbrains.kotlinx\kotlinx-coroutines-core\1.1.1\3d2b7321cdef9ebf9cb7729ea4f75a6f6457df86\kotlinx-coroutines-core-1.1.1.jar"; java -classpath "C:\Users\WangQian\.gradle\caches\modules-2\files-2.1\org.jetbrains.kotlinx\kotlinx-coroutines-core\1.1.1\3d2b7321cdef9ebf9cb7729ea4f75a6f6457df86\kotlinx-coroutines-core-1.1.1.jar;main.jar;C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2019.1\plugins\Kotlin\lib\kotlinx-coroutines-jdk8-1.0.1.jar;C:\Users\WangQian\.gradle\caches\modules-2\files-2.1\org.jetbrains.kotlinx\kotlinx-coroutines-core-common\1.0.0\5f7551ec1edc068deaa0397100a1f14f32270274\kotlinx-coroutines-core-common-1.0.0.jar" MainKt
*/

import kotlinx.coroutines.*

@kotlinx.coroutines.ExperimentalCoroutinesApi
class Foo {
    @Volatile var count = 0
    lateinit var deferred: Job // Deferred<Unit>

    fun start() {
        deferred = MainScope().launch {
            //     println("start")
            while (true) {
                count = count + 1
            }
        }

        println(deferred.start())
        println(deferred.isActive)
        println(deferred.isCancelled)
        println(deferred.isCompleted)
        println("ok")
    }

    fun join() {
        Thread.sleep(5000)
        deferred.cancel()
    }
}


@kotlinx.coroutines.ExperimentalCoroutinesApi
fun main(args: Array<String>) {
    val foo = Foo()
    foo.start()

    println("before join")
    foo.join()
    println("after join")
    
    println(foo.count)
}
