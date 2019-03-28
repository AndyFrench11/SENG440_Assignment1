package com.example.afr66

import android.content.Context
import android.util.JsonReader
import android.util.JsonWriter
import android.util.Log
import java.io.*

class JSONReaderWriter() {
    fun readBooks(context: Context) : MutableList<Book> {

        var books : MutableList<Book> = ArrayList<Book>()

        try {

            val file =  context.openFileInput("books.json")
            val reader = JsonReader(InputStreamReader(file))


            reader.beginArray()
            while (reader.hasNext()) {
                reader.beginObject()
                while (reader.hasNext()) {
                    reader.skipValue()
                    val title = reader.nextString()
                    reader.skipValue()
                    val subtitle = reader.nextString()
                    reader.skipValue()
                    val description = reader.nextString()
                    reader.skipValue()
                    val pageCount = reader.nextInt()
                    reader.skipValue()
                    val authors = readStringsArray(reader)
                    reader.skipValue()
                    val publishedDate = reader.nextString()
                    reader.skipValue()
                    val categories = readStringsArray(reader)
                    reader.skipValue()
                    val thumbnailURL = reader.nextString()

                    books.add(Book(title, subtitle, description, pageCount,
                        authors, publishedDate, categories, thumbnailURL))
                }
                reader.endObject()
            }
            reader.endArray()
            reader.close()


        } catch (e: FileNotFoundException) {
            Log.d("Files", "JSON File has not been created yet")
        } catch (e: EOFException) {
            Log.d("Files", "JSON File has not been created yet")
        }

        return books

    }

    fun updateBooks(updateBook: Book, context: Context, delete: Boolean) {

        var books : MutableList<Book> = readBooks(context)

        val file = context.openFileOutput("books.json", Context.MODE_PRIVATE)
        val writer = JsonWriter(OutputStreamWriter(file))

        writer.setIndent("  ")

        if(delete) {
            //May need to check if equals method is applied
            books.remove(updateBook)
        } else {
            books.add(updateBook)
        }

        writer.beginArray()

        for(book in books) {
            writer.beginObject()
            writer.name("title").value(book.title)
            writer.name("subtitle").value(book.subtitle)
            writer.name("description").value(book.description)
            writer.name("pageCount").value(book.pageCount)
            writer.name("authors")
            writeStringsArray(writer, book.authors)
            writer.name("publishedDate").value(book.publishedDate)
            writer.name("categories")
            writeStringsArray(writer, book.categories)
            writer.name("thumbnailURL").value(book.thumbnailURL)
            writer.endObject()
        }


        writer.endArray()

        writer.close()

    }


    fun writeStringsArray(writer: JsonWriter, strings: List<String>) {
        writer.beginArray()
        for (value in strings) {
            writer.value(value)
        }
        writer.endArray()
    }

    fun readStringsArray(reader: JsonReader): List<String> {
        val strings : MutableList<String> = ArrayList<String>()

        reader.beginArray()
        while (reader.hasNext()) {
            strings.add(reader.nextString())
        }
        reader.endArray()
        return strings
    }
}