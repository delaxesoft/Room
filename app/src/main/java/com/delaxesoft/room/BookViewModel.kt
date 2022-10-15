package com.delaxesoft.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Dao
import kotlinx.coroutines.launch

class BookViewModel(application: Application):AndroidViewModel(application) {
    //access it, we will need a Dao object
     private  val bookRepository=BookRepository(application)
     val allBooks:LiveData<List<Book>>

    init{
        //fetch the database instance
        //DAo that will be used to insert the Data
        allBooks=bookRepository.allBooks
    }
    //wrapper function for the insert operation link btw UI and database
    //makes use of asynctask , but i will use couroutine
    fun insert(book:Book) {
        viewModelScope.launch {
            bookRepository.insert(book)
        }
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