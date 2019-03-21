package com.example.afr66

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


import com.example.afr66.BookFragment.OnListFragmentInteractionListener

import kotlinx.android.synthetic.main.fragment_book.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class MyBookRecyclerViewAdapter(

    private val mValues: List<Book>,
    private val mListener: OnListFragmentInteractionListener?

) : RecyclerView.Adapter<MyBookRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Book
            //selectedIndex = v.adapterPosition
            notifyItemChanged(selectedIndex)
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_book, parent, false)
        return ViewHolder(view)
    }

    private var selectedIndex: Int = RecyclerView.NO_POSITION

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        //holder.mIdView.text = item.id
        holder.mContentView.text = item.content

        holder.isActive = selectedIndex == position

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        //val mIdView: TextView = mView.item_number
        val mContentView: TextView = mView.content

        var isActive: Boolean = false
            set(value) {
                field = value
                itemView.setBackgroundColor(if(value) Color.LTGRAY else Color.TRANSPARENT)
            }

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}
