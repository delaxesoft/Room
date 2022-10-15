@file:Suppress("DEPRECATION")

package com.delaxesoft.room

import android.app.Activity
import android.app.SearchManager
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.delaxesoft.room.MainActivity.Companion.UPDATE_BOOK_ACTIVITY_REQUEST_CODE
import com.delaxesoft.room.R.string.updated
import com.delaxesoft.room.databinding.ActivityMainBinding

class SearchResultActivity : AppCompatActivity(),
                              BookListAdapter.OnDeleteClickListener {
    private lateinit var searchBinding: ActivityMainBinding
    private  var bookListAdapter: BookListAdapter?=null
    private lateinit var searchViewModel: SearchViewModel
    private val TAG:String?=this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(searchBinding.root)
        setSupportActionBar(searchBinding.toolbar)

        searchBinding.fab.visibility = View.INVISIBLE

        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        bookListAdapter = BookListAdapter(this, this)

        searchBinding.recyclerView.adapter = bookListAdapter
        searchBinding.recyclerView.layoutManager = LinearLayoutManager(this)



        handleIntent(intent)
    }
    private fun handleIntent(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            
            val searchQuery: String? = intent.getStringExtra(SearchManager.QUERY)
            Log.i(TAG, "Search Query is $searchQuery")
            //val searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]
            searchViewModel.getBookByBookOrAuthor("%$searchQuery%")
                ?.observe(this, Observer { books ->
                    books?.let {
                        bookListAdapter!!.setBooks(books)
                    }

                })

        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == UPDATE_BOOK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            val id = data!!.getStringExtra(EditBookActivity.ID).toString()
            val authorName  = data.getStringExtra(EditBookActivity.UPDATED_AUTHOR)
            val bookName = data.getStringExtra(EditBookActivity.UPDATED_BOOK)
            val description =data.getStringExtra(EditBookActivity.UPDATED_DESCRIPTION)
            // val currentTime = Calendar.getInstance().time

            val book = Book(id, authorName.toString(), bookName.toString(), description.toString(),lastUpdated = null)

            // Code to update
            searchViewModel.update(book)

            Toast.makeText(applicationContext, updated, Toast.LENGTH_LONG).show()


        } else {
            Toast.makeText(applicationContext, R.string.not_saved, Toast.LENGTH_LONG).show()
        }
    }





    override fun onDeleteClickListener(myBook: Book) {
        searchViewModel.delete(myBook)
        Toast.makeText(applicationContext,"Book Deleted",Toast.LENGTH_LONG).show()
    }

}