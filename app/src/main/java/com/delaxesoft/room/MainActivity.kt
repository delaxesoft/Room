package com.delaxesoft.room
import android.app.Activity
import android.app.SearchManager
import android.app.SearchableInfo
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.delaxesoft.room.databinding.ActivityMainBinding
import java.util.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(),BookListAdapter.OnDeleteClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bookViewModel: BookViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //add viewmodel

        val booklistAdapter = BookListAdapter(this,this)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = booklistAdapter


        bookViewModel = ViewModelProvider(this)[BookViewModel::class.java]
        bookViewModel.allBooks.observe(this, Observer { books ->
            books?.let {
                booklistAdapter.setBooks(books)
            }

        })


        binding.fab.setOnClickListener {
            val intent = Intent(this, NewBookActivity::class.java)
            startActivityForResult(intent, NEW_BOOK_ACTIVITY_REQUEST_CODE)

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_BOOK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            val id = UUID.randomUUID().toString()
            val authorName = data!!.getStringExtra(NewBookActivity.NEW_AUTHOR)
            val bookName = data.getStringExtra(NewBookActivity.NEW_BOOK)
            val description = data.getStringExtra(NewBookActivity.NEW_DESCRIPTION)
             val currentTime = Calendar.getInstance().time

            val book = Book(id, authorName.toString(), bookName.toString(), description.toString(),currentTime)

            bookViewModel.insert(book)

            Toast.makeText(applicationContext, R.string.saved, Toast.LENGTH_LONG).show()

        } else if (requestCode == UPDATE_BOOK_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            val id = data!!.getStringExtra(EditBookActivity.ID).toString()
            val authorName = data.getStringExtra(EditBookActivity.UPDATED_AUTHOR)
            val bookName = data.getStringExtra(EditBookActivity.UPDATED_BOOK)
            val description = data.getStringExtra(EditBookActivity.UPDATED_DESCRIPTION)

            val currentTime = Calendar.getInstance().time

            val book = Book(id, authorName.toString(), bookName.toString(), description.toString(),currentTime)

            // Code to update
            bookViewModel.update(book)

            Toast.makeText(applicationContext, R.string.updated, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(applicationContext, R.string.not_saved, Toast.LENGTH_LONG).show()
        }

    }


    companion object {
        private const val NEW_BOOK_ACTIVITY_REQUEST_CODE = 1
        const val UPDATE_BOOK_ACTIVITY_REQUEST_CODE = 2
        const val EXTRA_BOOK_ID = "id"
        const val EXTRA_BOOK_NAME = "name"
        const val EXTRA_BOOK_AUTHOR = "author"
        const val EXTRA_BOOK_DESCRIPTION = "description"

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater:MenuInflater=menuInflater
        inflater.inflate(R.menu.menu_main,menu)
        //get the searchView and set the searchable configuration
        val  searchManager:SearchManager=getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val  searchView:SearchView=menu.findItem(R.id.search).actionView as SearchView

        //setting the searchResultActivity to show the result
        val componentName=ComponentName(this,SearchResultActivity::class.java)
        val searchableInfo:SearchableInfo=searchManager.getSearchableInfo(componentName)
        searchView.setSearchableInfo(searchableInfo)
        return true
    }



    override fun onDeleteClickListener(myBook: Book) {
         bookViewModel.delete(myBook)
        Toast.makeText(applicationContext,R.string.deleted,Toast.LENGTH_LONG).show()
    }

}
