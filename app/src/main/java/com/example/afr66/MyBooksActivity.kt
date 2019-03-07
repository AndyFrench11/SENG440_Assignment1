package com.example.afr66

import android.R
import android.app.ListActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView

class MyBooksActivity : ListActivity() {

    class Friend(val name: String, val home: String, val email: String, val phone: String) {
        override fun toString(): String {
            return name + ", ${home}";
        }
    }

    private val friends = arrayOf<Friend>(
        Friend("Andy Pandy", "Christchurch, New Zealand",
            "foo@canterbury.ac.nz", "123456"),
        Friend("Pandy Andy", "Auckland, New Zealand",
            "foo@canterbury.ac.nz", "123456"),
        Friend("gday Maaaaate", "Christchurch, New Zealand",
            "foo@canterbury.ac.nz", "123456"),
        Friend("Bonjour!!", "Christchurch, New Zealand",
            "foo@canterbury.ac.nz", "123456")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listAdapter = ArrayAdapter<Friend>(this, R.layout.simple_list_item_1, friends)
    }

    override fun onListItemClick(l: ListView?, v: View?, friendId: Int, id: Long) {
        Log.d("FOO", "$friendId")
    }
}