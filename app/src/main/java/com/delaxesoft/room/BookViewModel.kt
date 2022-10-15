package com.delaxesoft.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Dao
import kotlinx.coroutines.launch

class BookViewModel(application: Application):AndroidViewModel(application) {
    //access it, we will need a Dao object
     private  val bookDao:BookDao
     val allBooks:LiveData<List<Book>>

    init{
        //fetch the database instance
        val bookDb=BookRoomDatabase.getDatabase(application)
        //DAo that will be used to insert the Data
        bookDao=bookDb.bookDao()
        allBooks=bookDao.allBooks
    }
    //wrapper function for the insert operation link btw UI and database
    //makes use of asynctask , but i will use couroutine
    fun insert(book:Book) {
        viewModelScope.launch {
            bookDao.insert(book)
        }
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