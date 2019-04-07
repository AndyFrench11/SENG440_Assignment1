package com.example.afr66

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.net.Uri
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
import android.support.v4.content.ContextCompat.startActivity



/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class MyBookRecyclerViewAdapter(

    private var mValues: MutableList<Book>,
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

    fun customDataSetChanged(context: Context) {
        val jsonReaderWriter = JSONReaderWriter()
        mValues = jsonReaderWriter.readBooks(context!!)
        notifyDataSetChanged()
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
        holder.mDetailsView.text = context.getString(R.string.list_current_chapter) + item.currentChapter +
                context.getString(R.string.hyphen) +
                context.getString(R.string.list_current_page) + item.currentPage

        // Display an image to image view from url
        if(item.thumbnailURL != "") {
            DownloadBookImageTask(holder.mImageView)
                .execute(item.thumbnailURL)
        }


        holder.mShareButton.setOnClickListener{

            val alertDialog: AlertDialog? = context?.let {
                val builder = AlertDialog.Builder(it)
                // Set other dialog properties
                builder.setTitle(R.string.share_book)

                builder.setItems(R.array.contact_types) { dialog, which ->
                    when(which) {
                        0 -> {
                            val sendIntent = Intent(Intent.ACTION_VIEW);
                            sendIntent.setData(Uri.parse("sms:"));
                            sendIntent.putExtra(
                                "sms_body",
                                        context.getString(R.string.share_first_line) +
                                                context.getString(R.string.share_second_line) +
                                                item.title +
                                                context.getString(R.string.share_by) +
                                                item.authors.joinToString() +
                                                context.getString(R.string.share_exclamation_mark) +
                                                context.getString(R.string.share_final_line)
                            )
                            startActivity(context, sendIntent, null)
                        }
                        1 -> {
                            val emailIntent = Intent(
                                Intent.ACTION_SENDTO, Uri.parse("mailto:")
                            )
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.download_bookmark))
                            emailIntent.putExtra(
                                Intent.EXTRA_TEXT,
                                context.getString(R.string.share_first_line) +
                                        context.getString(R.string.share_second_line) +
                                        item.title +
                                        context.getString(R.string.share_by) +
                                        item.authors.joinToString() +
                                        context.getString(R.string.share_exclamation_mark) +
                                        context.getString(R.string.share_final_line)
                            )
                            startActivity(context, emailIntent, null)
                        }
                    }
                }


                // Create the AlertDialog
                builder.create()
            }
            alertDialog?.show()
        }

        holder.mDeleteButton.setOnClickListener{
            val alertDialog: AlertDialog? = context?.let {
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setPositiveButton(R.string.update_book_ok,
                        DialogInterface.OnClickListener { dialog, id ->
                            mValues.removeAt(position)
                            val jsonReaderWriter = JSONReaderWriter()
                            jsonReaderWriter.updateBooks(item, context, true)
                            notifyDataSetChanged()
                        })
                    setNegativeButton(R.string.update_book_cancel,
                        DialogInterface.OnClickListener { dialog, id ->
                            // User cancelled the dialog
                        })
                }
                // Set other dialog properties
                builder.setMessage(context.getString(R.string.delete_book_sure) + item.title + context.getString(R.string.question_mark))
                builder.setTitle(R.string.delete_book)

                // Create the AlertDialog
                builder.create()
            }
            alertDialog?.show()
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
