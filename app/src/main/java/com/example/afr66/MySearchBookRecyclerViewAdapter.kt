package com.example.afr66

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.JsonWriter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton


import com.example.afr66.BookFragment.OnListFragmentInteractionListener

import kotlinx.android.synthetic.main.fragment_book.view.*
import kotlinx.android.synthetic.main.fragment_search_book.view.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import android.widget.CompoundButton
import android.R.attr.button
import android.R.interpolator.bounce
import android.view.animation.Animation






class MySearchBookRecyclerViewAdapter(

    private var mValues: List<Book>

) : RecyclerView.Adapter<MySearchBookRecyclerViewAdapter.ViewHolder>() {

    lateinit var context : Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.fragment_search_book, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mContentView.text = item.title
        holder.mAuthorView.text = item.authors.joinToString()
        // Display an image to image view from url
        if(item.thumbnailURL != "") {
            DownloadBookImageTask(holder.mImageView)
                .execute(item.thumbnailURL)
        }


        holder.mAddButton.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val jsonReaderWriter = JSONReaderWriter()
                jsonReaderWriter.updateBooks(item, context, false)
                var myAnim = AnimationUtils.loadAnimation(context, R.anim.bounce)

                // Use bounce interpolator with amplitude 0.2 and frequency 20
                val interpolator = MyBounceInterpolator(0.2, 20.0)
                myAnim.interpolator = interpolator

                holder.mAddButton.startAnimation(myAnim)

                holder.mAddButton.isEnabled = false;
            }
        })

    }

    fun update(modelList:List<Book>){
        mValues = modelList
        this!!.notifyDataSetChanged()
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mImageView : ImageView = mView.bookPhoto
        val mContentView: TextView = mView.bookTitle
        val mAuthorView: TextView = mView.bookAuthor
        val mAddButton: ToggleButton = mView.addToggleButton

        override fun toString(): String {
            return super.toString() + " '" + mContentView.text + "'"
        }
    }
}