package fr.iem.rules

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class ApiMockServerRule : TestWatcher() {
    private lateinit var mockWebServer: MockWebServer

    lateinit var retrofit: Retrofit
        private set

    override fun starting(description: Description?) {
        super.starting(description)
        mockWebServer = MockWebServer()
        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor {
                println(it)
            }.apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            }).build())
            .baseUrl(mockWebServer.url("/"))
            .build()
    }

    override fun finished(description: Description?) {
        super.finished(description)
        print("\n\n")
        mockWebServer.shutdown()
    }


    fun enqueueMockResponse(responseCode: Int, bodyResourcePath: String? = null) {
        mockWebServer.enqueue(MockResponse().apply {
            this.setResponseCode(responseCode)
            bodyResourcePath?.run {
                InputStreamReader(FileInputStream(File(bodyResourcePath))).use { it.readText() }
            }?.also { this.setBody(it) }
        })
    }

    inline fun <reified Api> createMockApi() = this.retrofit.create<Api>()

}