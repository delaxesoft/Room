package com.delaxesoft.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BookDao {
    //method to insert books in our database
    @Insert
     suspend fun insert(book:Book)

     @Query ("SELECT * from books WHERE books LIKE :searchString OR author Like:searchString")
     //convert to livedata so that any change will be noticed
     fun getBooksByBookorAuthor(searchString:String):LiveData<List<Book>>

    @get:Query ("SELECT * from books")
    val allBooks:LiveData<List<Book>>

    @Update
    suspend fun update(book:Book)

    @Delete
    suspend fun delete(book:Book)
}