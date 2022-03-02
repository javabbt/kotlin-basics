package fr.iem

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ExampleTest {

    @Before
    fun setUp() {
        println("ExampleTest -> Set Up ")
    }

    @Test
    fun additionIsCorrect() {
        val result = 2 + 2
        assertEquals(4, result)
    }


    @After
    fun tearDown() {
        println("ExampleTest -> Tear Down ")
    }
}