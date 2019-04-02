package com.example.afr66

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.text.method.ScrollingMovementMethod






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
        singleBookAuthors.text = "Authors: " + book.authors.joinToString()
        var singleBookCategories = findViewById<TextView>(R.id.singleBookCategories)
        singleBookCategories.text = "Categories: " + book.categories.joinToString()
        var singleBookPublishedDate = findViewById<TextView>(R.id.singleBookPublishedDate)
        singleBookPublishedDate.text = "Published Date: " + book.publishedDate
        var singleBookPageCount = findViewById<TextView>(R.id.singleBookPageCount)
        singleBookPageCount.text = "Page Count: " + book.pageCount.toString()
        var singleBookCurrentStatus = findViewById<TextView>(R.id.singleBookCurrentStatus)
        singleBookCurrentStatus.text = "You are currently on page ....."



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
