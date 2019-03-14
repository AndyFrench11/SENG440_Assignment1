package com.example.afr66

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class IndividualBookActivity : Activity() {

    private lateinit var nameLabel : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_individual_book)
        val bookName = intent.getStringExtra("bookName")
        nameLabel = findViewById(R.id.NameLabel)
        nameLabel.text = bookName

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
    }
}
