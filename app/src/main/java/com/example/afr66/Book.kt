package com.example.afr66

public class Book(val id: String, val content: String, val details: String) {
    override fun toString(): String = content
}