class Logger private constructor(val defaultTag: String = "") {
    
    fun debug(tag: String, message: String) {
        var fileName = "unknown"
        var lineNumber = -1
        
        val throwable = Throwable()
        val stacks = throwable.getStackTrace()
        if (stacks.size > 1) {
            fileName = stacks[1].getFileName()
            lineNumber = stacks[1].getLineNumber()
        }
        
        debug(tag, message, fileName, lineNumber)
    }

    fun debug(message: String) {
        var fileName = "unknown"
        var lineNumber = -1
        
        val throwable = Throwable()
        val stacks = throwable.getStackTrace()
        if (stacks.size > 1) {
            fileName = stacks[1].getFileName()
            lineNumber = stacks[1].getLineNumber()
        }
        
        debug(defaultTag, message, fileName, lineNumber)
    }

    private fun debug(tag: String, message: String, fileName: String, lineNumber: Int) {
        val logRecord = "[$fileName:$lineNumber] $message"
        // TODO use android.util.Log.d()
        println("$tag $logRecord")
    }

    companion object {
        fun getLogger(tag: String): Logger {
            return Logger(tag)
        }
    }
}


fun main(args: Array<String>) {
    val logger = Logger.Companion.getLogger("foo")
    logger.debug("bar")
    logger.debug("xyz", "bar")
}
