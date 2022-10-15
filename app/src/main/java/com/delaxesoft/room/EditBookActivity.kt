package com.delaxesoft.room

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.delaxesoft.room.MainActivity.Companion.EXTRA_BOOK_AUTHOR
import com.delaxesoft.room.MainActivity.Companion.EXTRA_BOOK_DESCRIPTION
import com.delaxesoft.room.MainActivity.Companion.EXTRA_BOOK_ID
import com.delaxesoft.room.MainActivity.Companion.EXTRA_BOOK_NAME
import com.delaxesoft.room.databinding.ActivityNewBinding

class EditBookActivity : AppCompatActivity() {
    var id:String?=null
    private lateinit var editbinding: ActivityNewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editbinding =ActivityNewBinding.inflate(layoutInflater)
        setContentView(editbinding.root)
        //fetch the data using bundle
        val bundle:Bundle?=intent.extras

        bundle?.let{
            id=bundle.getString(EXTRA_BOOK_ID)
            val book=bundle.getString(EXTRA_BOOK_NAME)
            val author=bundle.getString(EXTRA_BOOK_AUTHOR)
            val description=bundle.getString(EXTRA_BOOK_DESCRIPTION)


            editbinding.author.setText(author)
             editbinding.books.setText(book)
            editbinding.description.setText(description)


        }


        editbinding.bSave.setOnClickListener{
            //get the text from teh ui
            val updatedAuthor = editbinding.author.text.toString()
            val updatedBook = editbinding.books.text.toString()
            val updatedDescription=editbinding.description.text.toString()

            //create an Intent
            val resultIntent = Intent()
            resultIntent.putExtra(ID, id)
            resultIntent.putExtra(UPDATED_AUTHOR, updatedAuthor)
            resultIntent.putExtra(UPDATED_BOOK, updatedBook)
            resultIntent.putExtra(UPDATED_DESCRIPTION, updatedDescription)

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
        editbinding.bCancel.setOnClickListener{
            finish()
        }
    }

    companion object{
        const val ID="book_id"
        const val UPDATED_AUTHOR="author_name"
        const val UPDATED_BOOK="book_name"
        const val UPDATED_DESCRIPTION="book_description"

    }
}