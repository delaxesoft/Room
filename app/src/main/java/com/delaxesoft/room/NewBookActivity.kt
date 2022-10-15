package com.delaxesoft.room

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.delaxesoft.room.databinding.ActivityNewBinding

class NewBookActivity  : AppCompatActivity() {

    private lateinit var newBinding: ActivityNewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newBinding =  ActivityNewBinding.inflate(layoutInflater)
        setContentView(newBinding.root)
        newBinding.txvLastUpdated.visibility= View.INVISIBLE

        newBinding.bSave.setOnClickListener{

            val resultIntent= Intent()
            if(TextUtils.isEmpty(newBinding.author.text)
                || TextUtils.isEmpty(newBinding.books.text)){
                setResult(Activity.RESULT_CANCELED,resultIntent)
            }else{
                val author=newBinding.author.text.toString()
                val book=newBinding.books.text.toString()
                val description=newBinding.description.text.toString()

                resultIntent.putExtra(NEW_AUTHOR,author)
                resultIntent.putExtra(NEW_BOOK,book)
                resultIntent.putExtra(NEW_DESCRIPTION,description)
                setResult(Activity.RESULT_OK,resultIntent)

            }
            finish()

        }
        newBinding.bCancel.setOnClickListener{
            finish()
        }

    }
    companion object{
        const val NEW_AUTHOR="new_author"
        const val NEW_BOOK="new_book"
        const val NEW_DESCRIPTION="new_description"
    }
}