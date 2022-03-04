package fr.iem

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.api.RickApi
import fr.data.ironman.response.Response
import fr.iem.data.RawResponse
import fr.iem.rules.ApiMockServerRule
import fr.iem.rules.CoroutineTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.net.ssl.HttpsURLConnection

@ExperimentalCoroutinesApi
class MainKtTest{
    private val mockWebServer = MockWebServer()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    val apiMockServer = ApiMockServerRule()

    private lateinit var api: RickApi

    companion object {
        const val BODY_SEARCH_SUCCESS = "src/main/resources/json/rickandmortyresponse.json"
    }

    @Before
    fun setUp() {
        api = apiMockServer.createMockApi()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testingDeserializationIsCorrect(){
        val rawResponse = RawResponse("Yannick" , "Loic" , 23)
        val jsonString: String = Gson().toJson(rawResponse)
        runBlocking {
            val response = deserializeJson(null , jsonString , RawResponse::class.java) as RawResponse
            assertEquals(response.name , rawResponse.name)
            assertEquals(response.age , rawResponse.age)
            assertEquals(response.surName , rawResponse.surName)
        }
    }

    @Test
    fun testingRickAndMortyApiCall() = runBlocking{
        apiMockServer.enqueueMockResponse(
            HttpsURLConnection.HTTP_OK,
            BODY_SEARCH_SUCCESS
        )
        val response = api.getCharacter(1)
        val body = checkNotNull(response.body())
        println("yeah $body")
        assertEquals(361, body.id)
    }
}
