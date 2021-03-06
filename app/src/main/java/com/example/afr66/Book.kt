package com.example.afr66

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
public class Book(
    val title: String,
    val subtitle: String,
    val description: String,
    val pageCount: Int,
    val authors: List<String>,
    val publishedDate: String,
    val categories: List<String>,
    val thumbnailURL : String,
    var currentChapter: Int,
    var currentPage: Int) : Parcelable {}