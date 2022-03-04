package fr.iem

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import fr.data.ironman.IronManItem
import fr.data.ironman.response.Response
import fr.iem.Constants.firstJson
import fr.iem.Constants.hashArgs
import fr.iem.Constants.helpArgs
import fr.iem.Constants.jsonArgs
import fr.iem.Constants.secondJson
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.security.MessageDigest

/** Entry point of the application */
fun main(args: Array<String>) {
    if (args.isEmpty())
        readDoc()
    else if (args.size == 1) {
        /**
         * this part should normally be done with Coroutines
         * but not the purpose of the exercise
         **/
        when (args[0]) {
            helpArgs -> {
                readDoc()
            }
            jsonArgs[0], jsonArgs[1] -> {
                deserializeJson(firstJson, 1)
                deserializeJson(secondJson, 2)
            }
        }
    } else {
        if (args[0] == hashArgs[0] || args[0] == hashArgs[1]) {
            val sb = StringBuilder()
            for (i in 1 until args.size)
                sb.append(args[i] + " ").trim()
            val encryptedString = sb.toString().encrypt()
            println("Encrypted string $encryptedString")
        } else displayInvalidArgument()
    }
}

fun displayInvalidArgument() {
    println("You entered invalid arguments")
    readDoc()
}

object Constants {
    const val helpArgs = "--help"
    val jsonArgs = arrayOf("--j", "--json")
    val hashArgs = arrayOf("--md5", "--h")
    const val firstJson = "/home/yannick/kotlin-basics/src/main/resources/json/iron_man.json"
    const val secondJson = "/home/yannick/kotlin-basics/src/main/resources/json/response.json"
}

fun deserializeJson(path: String, response: Int) {
    println("***************************************************************")
    println()
    var inputStream: InputStream? = null
    var jsonReader: JsonReader? = null
    try {
        inputStream =
            File(path).inputStream()
        jsonReader = JsonReader(InputStreamReader(inputStream))
        val gson = Gson()
        when (response) {
            1 -> {
                val stringResponse = gson.fromJson(jsonReader, IronManItem::class.java) as IronManItem
                println(stringResponse)
            }
            2 -> {
                val stringResponse = gson.fromJson(jsonReader, Response::class.java) as Response
                println(stringResponse)
            }
        }

    } catch (e: Exception) {
        println("Something went wrong >> ${e.message}")
    } finally {
        inputStream?.close()
        jsonReader?.close()
    }
    println()
    println("***************************************************************")
}

/** function for reading the documentation file**/
fun readDoc() {
    var inputStream: InputStream? = null
    try {
        inputStream =
            File("/home/yannick/kotlin-basics/src/main/resources/documentation.txt").inputStream()
        val lineList = mutableListOf<String>()
        inputStream.bufferedReader().forEachLine { lineList.add(it) }
        lineList.forEach { println(it) }
    } catch (e: Exception) {
        println("Something went wrong >> ${e.message}")
    } finally {
        inputStream?.close()
    }
}

fun String.encrypt(): String {
    val digest = MessageDigest.getInstance("MD5")
    val result = digest.digest(this.toByteArray(Charsets.UTF_8))
    return result.toHex()
}

fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }
