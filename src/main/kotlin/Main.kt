import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.util.zip.GZIPInputStream

private var matches = 0

fun main() {
    val os = System.getProperty("os.name")
    println("Enter a path to recursively search in (default is the vanilla launcher's log directory for your platform): ")
    val path = readLine()?.ifEmpty {
        if (os.contains("win", true)) {
            "${System.getenv("APPDATA")}/.minecraft/logs"
        } else if (os == "Linux") {
            "${System.getenv("HOME")}/.minecraft/logs"
        } else if (os.contains("mac", true)) {
            "/Users/${System.getProperty("user.name")}/Library/Application Support/minecraft/logs"
        } else {
            println("Unsupported operating system for automatic detection, try again but enter the directory you want")
            null
        }
    } ?: return
    for (file in File(path).walkTopDown().filter { it.canonicalPath.matches(".*logs/.*\\.log.*".toRegex()) }) {
        val filePath = file.canonicalPath
        try {
            if (file.name.endsWith(".gz")) {
                GZIPInputStream(file.inputStream()).bufferedReader().use {
                    processReader(it, filePath)
                }
            } else {
                file.bufferedReader().use {
                    processReader(it, filePath)
                }
            }
        } catch (e: IOException) {
            println("Unsupported file $filePath, ignoring")
        }
    }
    if (matches != 0) {
        println("You had $matches match(es), analyze them above to see if they are malicious, they may not be")
    }
}

private const val MATCH = "\${"

fun processReader(reader: BufferedReader, filePath: String) {
    reader.readLines().filter { it.contains(MATCH, true) }.forEach {
        println("$it at $filePath")
        matches++
    }
}