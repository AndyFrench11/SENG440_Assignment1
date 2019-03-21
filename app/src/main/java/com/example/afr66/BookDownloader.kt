package com.example.afr66

import android.net.Uri
import android.os.AsyncTask
import android.widget.Toast
import org.json.JSONObject
import java.io.BufferedInputStream
import java.lang.ref.WeakReference
import java.net.URL
import java.nio.charset.Charset
import java.security.AccessController.getContext
import javax.net.ssl.HttpsURLConnection

class BookDownloader() :  AsyncTask<URL, Void, List<Book>>() {

    override fun doInBackground(vararg urls: URL): List<Book> {
        val url = urls[0]
        val result = getJson(url)

        val booksJson = result.getJSONArray("articles")
        val books = emptyList<Book>()
//        val books = (0 until booksJson.length()).map { i ->
//            val book = booksJson.getJSONObject(i)
//            Book(book.getString("title"), book.getString("url"))
//        }

        return books

    }

    override fun onPostExecute(result: List<Book>?) {
        super.onPostExecute(result)
        println("On post Execute")
        //Toast.makeText(getContext(), result?.joinToString(","), Toast.LENGTH_SHORT).show()
    }

    fun getJson(url: URL): JSONObject {
        val connection = url.openConnection() as HttpsURLConnection
        try {
            val json = BufferedInputStream(connection.inputStream).readBytes().toString(Charset.defaultCharset())
            return JSONObject(json)
        } finally {
            connection.disconnect()
        }
    }



}
