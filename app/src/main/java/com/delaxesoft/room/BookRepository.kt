package com.delaxesoft.room

import android.app.Application
import androidx.lifecycle.LiveData

class BookRepository(application: Application) {
    val allBooks: LiveData<List<Book>>
    private val bookDao:BookDao

    init{
        val bookDb:BookRoomDatabase?=BookRoomDatabase.getDatabase(application)
         bookDao=bookDb!!.bookDao()
         allBooks=bookDao.allBooks
    }
    fun getBooksByBookorAuthor(searchQuery:String):LiveData<List<Book>>{
        return bookDao.getBooksByBookorAuthor(searchQuery)
    }

    suspend fun insert(book:Book){
        bookDao.insert(book)
    }
    suspend fun update(book:Book){
        bookDao.update(book)
    }

    suspend fun delete(book:Book){
        bookDao.delete(book)
    }
}