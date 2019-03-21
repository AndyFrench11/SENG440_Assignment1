package com.example.afr66

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.widget.Button
import android.widget.TextView




class IndividualBookActivity : Activity() {

    private lateinit var nameLabel : TextView
    private lateinit var toolbar : android.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_book)
        val bookName = intent.getStringExtra("bookName")
        nameLabel = findViewById(R.id.NameLabel)
        nameLabel.text = bookName

        toolbar = findViewById(R.id.toolbar)

        setActionBar(toolbar)
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.title = bookName
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }



    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
    }
}
