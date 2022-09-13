package com.example.postcodesearch

import com.example.postcodesearch.utils.capitalizeWorld
import com.example.postcodesearch.utils.removeAccents
import org.junit.Assert
import org.junit.Test

class StringExtensionTest {

    @Test
    fun whenGetText_shouldCaptalize (){
        val expectedValue = "Teste"
        Assert.assertEquals(expectedValue, "TESTE".capitalizeWorld())
    }

    @Test
    fun whenGetText_shouldRemoveAccents (){
        val expectedValue= "AEIOUAOAEIOUAEIOU"
        Assert.assertEquals(expectedValue, "ÁÉÍÓÚÃÕÂÊÎÔÛÀÈÌÒÙ".removeAccents())
    }
}