package com.example.afr66

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.text.method.ScrollingMovementMethod
import android.widget.ProgressBar


class IndividualBookActivity : Activity() {

    private lateinit var toolbar : android.widget.Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_book)

        //Set all content of the view
        val data = intent.extras
        val book = data.getParcelable<Book>("book")
        var singleBookImage = findViewById<ImageView>(R.id.singleBookImage)
        var singleBookTitle = findViewById<TextView>(R.id.singleBookTitle)
        singleBookTitle.text = book.title
        var singleBookDescription = findViewById<TextView>(R.id.singleBookDescription)
        singleBookDescription.movementMethod = ScrollingMovementMethod()
        singleBookDescription.text = book.description
        var singleBookSubtitle = findViewById<TextView>(R.id.singleBookSubtitle)
        singleBookSubtitle.text = book.subtitle
        var singleBookAuthors = findViewById<TextView>(R.id.singleBookAuthors)
        singleBookAuthors.text = book.authors.joinToString()
        var singleBookCategories = findViewById<TextView>(R.id.singleBookCategories)
        singleBookCategories.text = book.categories.joinToString()
        var singleBookPublishedDate = findViewById<TextView>(R.id.singleBookPublishedDate)
        singleBookPublishedDate.text = book.publishedDate
        var singleBookCurrentChapterStatusValue = findViewById<TextView>(R.id.singleBookCurrentChapterStatusValue)
        singleBookCurrentChapterStatusValue.text = "5"//TODO book.currentChapter
        var singleBookCurrentPageStatusValue = findViewById<TextView>(R.id.singleBookCurrentPageStatusValue)
        singleBookCurrentPageStatusValue.text = "200"//TODO book.currentChapter

        var bookProgressBar = findViewById<ProgressBar>(R.id.bookProgressBar)
        bookProgressBar.scaleY = 3f
        var progressDecimal = 50.00 / book.pageCount.toDouble() * 100.00 //book.currentPage
        bookProgressBar.progress = progressDecimal.toInt() //TODO book.currentPage




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



    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
    }
}
