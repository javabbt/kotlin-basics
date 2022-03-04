package fr.iem

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.data.ironman.response.Response
import fr.iem.data.RawResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainKtTest{

    @Before
    fun setUp() {
        println("ExampleTest -> Set Up ")
    }

    @After
    fun tearDown() {
        println("ExampleTest -> Tear Down ")
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
}
