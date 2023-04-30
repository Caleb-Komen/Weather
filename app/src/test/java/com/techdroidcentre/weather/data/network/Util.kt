package com.techdroidcentre.weather.data.network

object Util {
    fun readFromFile(fileName: String): String {
        return javaClass.classLoader?.let {
            it.getResource(fileName)?.readText()
        } ?: "{}"
    }
}