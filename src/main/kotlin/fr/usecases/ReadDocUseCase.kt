package fr.usecases

import fr.utils.UseCase
import kotlinx.coroutines.*
import java.io.File
import java.io.InputStream

class ReadDocUseCase(private val docPath: String) : UseCase<String> {

    override suspend fun execute(): Result<String> {
        val str = readDoc(docPath)
        return Result.success(str)
    }

    /**
     *function for reading the documentation file.
     **/
    private suspend fun readDoc(docPath: String): String = withContext(Dispatchers.IO) {
        var inputStream: InputStream? = null
        val builder = StringBuilder()
        try {
            inputStream =
                File(docPath).inputStream()
            val lineList = mutableListOf<String>()
            inputStream.bufferedReader().forEachLine { lineList.add(it) }
            lineList.forEach { builder.append(it).append("\n") }
        } catch (e: Exception) {
            builder.append("Something went wrong >> ${e.message}")
        } finally {
            inputStream?.close()
        }
        builder.toString()
    }

}