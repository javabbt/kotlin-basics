package fr.iem

import fr.iem.Constants.hashArgs
import fr.iem.Constants.helpArgs
import fr.iem.Constants.jsonArgs
import java.io.File
import java.io.InputStream

/** Entry point of the application */
fun main(args: Array<String>) {
    if(args.isEmpty())
        readDoc()
    else if(args.size == 1){
        when(args[0]){
            helpArgs -> {
                readDoc()
            }
            jsonArgs[0],jsonArgs[1] -> {
                deserializeJson()
            }
        }
    }else if(args.size == 2){
        if(args[0] == hashArgs[0] || args[0] == hashArgs[1]){

        }else displayInvalidArgument()
    }else displayInvalidArgument()
}

fun displayInvalidArgument() {

}

object Constants{
    const val helpArgs = "--help"
    val jsonArgs = arrayOf("--j" , "--json")
    val hashArgs = arrayOf("--md5" , "--h")
}

fun deserializeJson() {

}

fun readDoc() {
    val inputStream: InputStream = File("/home/yannick/kotlin-basics/src/main/resources/documentation.txt").inputStream()
    val lineList = mutableListOf<String>()
    inputStream.bufferedReader().forEachLine { lineList.add(it) }
    lineList.forEach{println(it)}
}
