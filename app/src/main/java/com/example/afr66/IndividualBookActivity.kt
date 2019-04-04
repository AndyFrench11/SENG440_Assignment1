package com.example.afr66

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_individual_book.*


class IndividualBookActivity : Activity() {

    lateinit var chapterPicker : NumberPicker
    lateinit var pagePicker : NumberPicker

    private lateinit var toolbar : android.widget.Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_book)

        //Set all content of the view
        val data = intent.extras
        var book = data.getParcelable<Book>("book")
        var singleBookImage = findViewById<ImageView>(R.id.singleBookImage)
        var singleBookTitle = findViewById<TextView>(R.id.singleBookTitle)
        singleBookTitle.text = book.title
        var singleBookDescription = findViewById<TextView>(R.id.singleBookDescription)
        singleBookDescription.movementMethod = ScrollingMovementMethod()
        singleBookDescription.text = book.description
        var singleBookAuthors = findViewById<TextView>(R.id.singleBookAuthors)
        singleBookAuthors.text = book.authors.joinToString()
        var singleBookCategories = findViewById<TextView>(R.id.singleBookCategories)
        singleBookCategories.text = book.categories.joinToString()
        var singleBookPublishedDate = findViewById<TextView>(R.id.singleBookPublishedDate)
        singleBookPublishedDate.text = book.publishedDate
        var singleBookCurrentChapterStatusValue = findViewById<TextView>(R.id.singleBookCurrentChapterStatusValue)
        singleBookCurrentChapterStatusValue.text = book.currentChapter.toString()
        var singleBookCurrentPageStatusValue = findViewById<TextView>(R.id.singleBookCurrentPageStatusValue)
        singleBookCurrentPageStatusValue.text = book.currentPage.toString()

        var bookProgressBar = findViewById<ProgressBar>(R.id.bookProgressBar)
        bookProgressBar.scaleY = 3f
        var progressDecimal = book.currentPage / book.pageCount.toDouble() * 100.00 //book.currentPage
        bookProgressBar.progress = progressDecimal.toInt()


        if(book.thumbnailURL != "") {
            DownloadBookImageTask(singleBookImage)
                .execute(book.thumbnailURL)
        }

        toolbar = findViewById(R.id.toolbar)

        setActionBar(toolbar)
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.title = book.title
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val alertDialog: AlertDialog? = this?.let {
            val builder = AlertDialog.Builder(it)

            builder.apply {
                setPositiveButton(R.string.update_book_ok,
                    DialogInterface.OnClickListener { dialog, id ->

                        singleBookCurrentChapterStatusValue.text = chapterPicker.value.toString()
                        singleBookCurrentPageStatusValue.text = pagePicker.value.toString()
                        progressDecimal = pagePicker.value / book.pageCount.toDouble() * 100.00 //book.currentPage
                        bookProgressBar.progress = progressDecimal.toInt()
                        val jsonReaderWriter = JSONReaderWriter()
                        jsonReaderWriter.updateBooksNewChapterOrPage(book, context, chapterPicker.value, pagePicker.value)
                        Toast.makeText(context, "Successfully updated your Bookmark!", Toast.LENGTH_LONG).show()

                    })
                setNeutralButton(R.string.update_book_cancel,
                    DialogInterface.OnClickListener { dialog, id ->

                    })
            }

            builder?.setTitle(R.string.single_book_update_bookmark).setMessage("Update the chapter and page number you are currently on!")

            val inflater = this.layoutInflater
            val updateBookmarkView = inflater.inflate(R.layout.update_bookmark_dialog, null)

            chapterPicker = (updateBookmarkView as ViewGroup).getChildAt(0) as NumberPicker
            chapterPicker.minValue = 0
            chapterPicker.value = book.currentChapter
            chapterPicker.maxValue = 99

            pagePicker = (updateBookmarkView as ViewGroup).getChildAt(1) as NumberPicker
            pagePicker.minValue = 0
            pagePicker.value = book.currentPage
            pagePicker.maxValue = book.pageCount

            builder.setView(updateBookmarkView)

            // Create the AlertDialog
            builder.create()
        }

        updateBookmarkButton.setOnClickListener {
            alertDialog?.show()
        }



    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
        //TODO Make fragment load again
        //supportFragmentManager.beginTransaction().replace(R.id.fragment_container, BookFragment()).commit()
    }
}
