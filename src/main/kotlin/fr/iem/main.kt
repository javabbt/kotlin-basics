package fr.iem

import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import fr.data.ironman.IronManItem
import fr.data.ironman.response.Response
import fr.iem.Constants.docPath
import fr.iem.Constants.firstJson
import fr.iem.Constants.hashArgs
import fr.iem.Constants.helpArgs
import fr.iem.Constants.jsonArgs
import fr.iem.Constants.secondJson
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.security.MessageDigest


/** Entry point of the application */
fun main(args: Array<String>) {
    if (args.isEmpty()) {
        runBlocking { readDoc(docPath) }
    } else if (args.size == 1) {
        when (args[0]) {
            helpArgs -> {
                runBlocking {
                    readDoc(docPath)
                }
            }
            jsonArgs[0], jsonArgs[1] -> {
                runBlocking {
                    deserialize(firstJson, null, IronManItem::class.java)
                    deserialize(secondJson, null, Response::class.java)
                }
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
    runBlocking { readDoc(docPath) }
}

object Constants {
    const val helpArgs = "--help"
    val jsonArgs = arrayOf("--j", "--json")
    val hashArgs = arrayOf("--md5", "--h")
    const val firstJson = "src/main/resources/json/iron_man.json"
    const val secondJson = "src/main/resources/json/response.json"
    const val docPath = "src/main/resources/documentation.txt"
}

suspend fun <T> deserialize(path: String?, json: String?, toClass: Class<T>) {
    println("***************************************************************")
    println()
    val returnData = deserializeJson(path , json , toClass)
    println(returnData)
    println()
    println("***************************************************************")
}

/**
 * function for deserialising jsons
 **/
suspend fun <T> deserializeJson(path: String?, json: String?, toClass: Class<T>): T? =
    withContext(Dispatchers.Default) {
        var inputStream: InputStream? = null
        var jsonReader: JsonReader? = null
        var returnData: T? = null
        try {
            path?.apply {
                inputStream =
                    File(path).inputStream()
                jsonReader = JsonReader(InputStreamReader(inputStream!!))
            }
            val gson = Gson()
            returnData = if (json == null) (gson.fromJson(jsonReader, toClass) as T)
            else gson.fromJson(json, toClass) as T
        } catch (e: Exception) {
            runCatching {
                inputStream?.close()
                jsonReader?.close()
                returnData = null
            }
        }
        returnData
    }

/**
 *function for reading the documentation file.
 **/
suspend fun readDoc(docPath: String) {
    coroutineScope {
        launch(Dispatchers.IO) {
            var inputStream: InputStream? = null
            try {
                inputStream =
                    File(docPath).inputStream()
                val lineList = mutableListOf<String>()
                inputStream.bufferedReader().forEachLine { lineList.add(it) }
                lineList.forEach { println(it) }
            } catch (e: Exception) {
                println("Something went wrong >> ${e.message}")
            } finally {
                inputStream?.close()
            }
        }
    }
}



fun String.encrypt(): String {
    val digest = MessageDigest.getInstance("MD5")
    val result = digest.digest(this.toByteArray(Charsets.UTF_8))
    return result.toHex()
}

fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }
