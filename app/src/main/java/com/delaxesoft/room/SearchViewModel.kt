package com.delaxesoft.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SearchViewModel(application: Application): AndroidViewModel(application) {
    private  val bookDao:BookDao

    init{
        val bookDB:BookRoomDatabase= BookRoomDatabase.getDatabase(application)
        bookDao= bookDB.bookDao()
    }
    fun getBookByBookOrAuthor(searchQuery:String): LiveData<List<Book>>?{
        return bookDao.getBooksByBookorAuthor(searchQuery)
    }

    fun update(book:Book){
        viewModelScope.launch {
            bookDao.update(book)
        }
    }

    fun delete(book:Book){
        viewModelScope.launch {
            bookDao.delete(book)
        }
    }
}