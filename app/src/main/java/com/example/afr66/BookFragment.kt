package com.example.afr66

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.ArrayList
import android.support.v7.widget.DividerItemDecoration



/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [BookFragment.OnListFragmentInteractionListener] interface.
 */
class BookFragment() : Fragment() {
    //nstead, arguments can be supplied by the caller with setArguments(Bundle) and later retrieved by the Fragment with getArguments().

    private var listener: OnListFragmentInteractionListener? = null

    var currentBooks : MutableList<Book> = ArrayList<Book>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Read from JSON Storage
        val jsonReaderWriter = JSONReaderWriter()
        currentBooks = jsonReaderWriter.readBooks(context!!)

        val view = inflater.inflate(R.layout.fragment_book_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = MyBookRecyclerViewAdapter(currentBooks, listener)
                //val dividerItemDecoration = DividerItemDecoration(this.context, layoutManager.orientation)
                val dividerItemDecoration = DividerItemDecoration(this.context, 1)
                this.addItemDecoration(dividerItemDecoration)
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        }

    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: Book?)
    }

}
