package com.delaxesoft.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SearchViewModel(application: Application): AndroidViewModel(application) {
    private  val bookRepository=BookRepository(application)
    val allBooks:LiveData<List<Book>>

    init{
        //fetch the database instance
        //DAo that will be used to insert the Data
        allBooks=bookRepository.allBooks
    }
    fun getBookByBookOrAuthor(searchQuery:String): LiveData<List<Book>>?{
        return bookRepository.getBooksByBookorAuthor(searchQuery)
    }

    fun update(book:Book){
        viewModelScope.launch {
            bookRepository.update(book)
        }
    }

    fun delete(book:Book){
        viewModelScope.launch {
            bookRepository.delete(book)
        }
    }
}