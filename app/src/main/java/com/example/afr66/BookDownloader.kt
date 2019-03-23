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
import android.R.attr.key
import org.json.JSONArray


class BookDownloader() :  AsyncTask<SearchFragment.Sender, Void, List<Book>>() {

    var finalBooks : List<Book> = emptyList()
    var adapter : MySearchBookRecyclerViewAdapter = MySearchBookRecyclerViewAdapter(emptyList())

    override fun doInBackground(vararg sender: SearchFragment.Sender): List<Book> {

        adapter = sender[0].adapter
        val url = sender[0].url
        val result = getJson(url)
        val itemsArray = result.getJSONArray("items")
        val books = (0 until itemsArray.length()).map { i ->
            val book = itemsArray.getJSONObject(i)
            val volumeInfo = book.getJSONObject("volumeInfo")
            val title = volumeInfo.getString("title")
            var subtitle = ""

            if(volumeInfo.has("subtitile")) {
                subtitle = volumeInfo.getString("subtitle")
            }

            var description = ""
            if(volumeInfo.has("description")) {
                description = volumeInfo.getString("description")
            }


            var pageCount = 0
            if(volumeInfo.has("pageCount")) {
                pageCount = volumeInfo.getInt("pageCount")
            }

            val authorsArray = volumeInfo.getJSONArray("authors")
            val authors = (0 until authorsArray.length()).map { i ->
                val author = authorsArray.getString(i)
                author
            }

            var publishedDate = ""
            if(volumeInfo.has("publishedDate")) {
                publishedDate = volumeInfo.getString("publishedDate")
            }


            var categories = emptyList<String>()
            if(volumeInfo.has("categories")) {
                val categoriesArray = volumeInfo.getJSONArray("categories")
                categories = (0 until categoriesArray.length()).map { i ->
                    val category = categoriesArray.getString(i)
                    category
                }
            }

            var thumbnailURL = ""
            if(volumeInfo.has("imageLinks")) {
                val imageLinks = volumeInfo.getJSONObject("imageLinks")
                thumbnailURL = imageLinks.getString("thumbnail")
            }



            Book(title, subtitle, description, pageCount, authors, publishedDate, categories, thumbnailURL)

        }


        return books

    }

    override fun onPostExecute(result: List<Book>) {
        super.onPostExecute(result)
        adapter.update(result)
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
