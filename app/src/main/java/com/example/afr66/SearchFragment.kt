package com.example.afr66


import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.json.JSONObject
import java.io.BufferedInputStream
import java.net.URL
import java.nio.charset.Charset
import javax.net.ssl.HttpsURLConnection


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val parameters = mapOf("q" to "quilting")
        val url = parameterizeUrl("https://www.googleapis.com/books/v1/volumes", parameters)

        BookDownloader().execute(url)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    fun parameterizeUrl(url: String, parameters: Map<String, String>): URL {
        val builder = Uri.parse(url).buildUpon()
        parameters.forEach { key, value -> builder.appendQueryParameter(key, value) }
        val uri = builder.build()
        return URL(uri.toString())
    }


}
