package com.example.afr66


import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_book.view.*
import kotlinx.android.synthetic.main.fragment_search.*
import org.json.JSONObject
import java.io.BufferedInputStream
import java.net.URL
import java.nio.charset.Charset
import java.util.ArrayList
import javax.net.ssl.HttpsURLConnection


/**
 * A simple [Fragment] subclass.
 *
 */
class SearchFragment : Fragment() {

    private lateinit var searchBox : android.widget.SearchView
    private var viewAdapter : MySearchBookRecyclerViewAdapter = MySearchBookRecyclerViewAdapter(emptyList())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val recyclerView = (view as ViewGroup).getChildAt(1)

        // Set the adapter
        if (recyclerView is RecyclerView) {
            with(view) {
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = viewAdapter
                val dividerItemDecoration = DividerItemDecoration(this.context, 1)
                recyclerView.addItemDecoration(dividerItemDecoration)
            }
        }

        searchBox = view.findViewById(R.id.searchView)

        searchBox.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                val parameters = mapOf("q" to searchView.query.toString(), "key" to "AIzaSyCeaVvGlf5ehcr2_uUcV5oU8pdO6b1BNOo")
                val url = parameterizeUrl("https://www.googleapis.com/books/v1/volumes", parameters)

                val bookDownloader = BookDownloader()
                bookDownloader.execute(Sender(url, viewAdapter))


                searchBox.clearFocus()

                return true
            }

        })


        return view

    }


    fun parameterizeUrl(url: String, parameters: Map<String, String>): URL {
        val builder = Uri.parse(url).buildUpon()
        parameters.forEach { key, value -> builder.appendQueryParameter(key, value) }
        val uri = builder.build()
        return URL(uri.toString())
    }

    inner class Sender(val url: URL, val adapter: MySearchBookRecyclerViewAdapter)


}
