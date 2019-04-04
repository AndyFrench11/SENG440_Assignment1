package com.example.afr66

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.provider.Settings.Global.getString
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*


import com.example.afr66.BookFragment.OnListFragmentInteractionListener

import kotlinx.android.synthetic.main.fragment_book.view.*
import kotlinx.android.synthetic.main.fragment_search_book.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class MyBookRecyclerViewAdapter(

    private val mValues: MutableList<Book>,
    private val mListener: OnListFragmentInteractionListener?

) : RecyclerView.Adapter<MyBookRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    lateinit var context : Context

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Book
            //selectedIndex = v.adapterPosition
            //notifyItemChanged(selectedIndex)
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.fragment_book, parent, false)
        return ViewHolder(view)
    }

    private var selectedIndex: Int = RecyclerView.NO_POSITION

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        //holder.mIdView.text = item.id
        holder.mContentView.text = item.title
        holder.mAuthorsView.text = item.authors.joinToString()
        //TODO Change this to strings
        holder.mDetailsView.text = "Chapter: " + item.currentChapter + " - " + "Page: " + item.currentPage

        // Display an image to image view from url
        if(item.thumbnailURL != "") {
            DownloadBookImageTask(holder.mImageView)
                .execute(item.thumbnailURL)
        }


        holder.mShareButton.setOnClickListener{
            //TODO Finish off the Action
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            startActivity(context, intent, null)
        }

        holder.mDeleteButton.setOnClickListener{
            //TODO add a dialog!
            mValues.removeAt(position)
            val jsonReaderWriter = JSONReaderWriter()
            jsonReaderWriter.updateBooks(item, context, true)
            notifyDataSetChanged()
        }

        holder.isActive = selectedIndex == position

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mImageView : ImageView = mView.myBookPhoto
        val mContentView: TextView = mView.myBookTitle
        val mAuthorsView: TextView = mView.myBookAuthors
        val mDetailsView: TextView = mView.myBookDetails
        val mShareButton: Button = mView.shareButton
        val mDeleteButton: Button = mView.deleteButton

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
