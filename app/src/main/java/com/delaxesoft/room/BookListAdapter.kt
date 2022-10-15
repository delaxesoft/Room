package com.delaxesoft.room

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater

import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.delaxesoft.room.MainActivity.Companion.EXTRA_BOOK_AUTHOR
import com.delaxesoft.room.MainActivity.Companion.EXTRA_BOOK_DESCRIPTION
import com.delaxesoft.room.MainActivity.Companion.EXTRA_BOOK_ID
import com.delaxesoft.room.MainActivity.Companion.EXTRA_BOOK_NAME
import com.delaxesoft.room.databinding.ListItemBinding
import java.text.SimpleDateFormat
import java.util.*

class BookListAdapter(private val context: Context,
                      private val onDeleteClickListener: OnDeleteClickListener) :
                      RecyclerView.Adapter<BookListAdapter.BookViewHolder>() {
      private var bookList:List<Book>  =mutableListOf()

    interface OnDeleteClickListener{
        fun onDeleteClickListener(myBook:Book)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book=bookList[position]
        holder.setData(book.author,book.lastUpdated,position)
        holder.setListeners()
    }

    override fun getItemCount(): Int =bookList.size
    fun setBooks(books:List<Book>){
        bookList=books
        notifyDataSetChanged()
    }

    inner class BookViewHolder( val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root){
        private var pos:Int=0


     fun setData(author:String, lastUpdated: Date?, position:Int){
         binding.tvAuthor.text=author
         binding.tvLastUpdated.text=getFormattedDate(lastUpdated)
         this.pos=position

     }



        fun setListeners(){
            binding.root.setOnClickListener{
                //initialize intent
                val intent= Intent(context,EditBookActivity::class.java)
                //pass the parametrs of the companion object
                intent.putExtra(EXTRA_BOOK_ID, bookList[pos].id)
                intent.putExtra(EXTRA_BOOK_AUTHOR, bookList[pos].author)
                intent.putExtra(EXTRA_BOOK_NAME, bookList[pos].books)
                intent.putExtra(EXTRA_BOOK_DESCRIPTION, bookList[pos].description)
                intent.putExtra("lastUpdated",getFormattedDate(bookList[pos].lastUpdated))
                (context as Activity).startActivityForResult(intent, MainActivity.UPDATE_BOOK_ACTIVITY_REQUEST_CODE)


            }
            binding.ivRowDelete.setOnClickListener{
                onDeleteClickListener.onDeleteClickListener(bookList[pos])

            }

        }
        private fun getFormattedDate(lastUpdated: Date?): String? {
            var time="last updated"
            time +=lastUpdated?.let{
                val sdf=SimpleDateFormat("HH:mm d MMM, yyyy", Locale.getDefault())
                sdf.format(lastUpdated)
            }?:"Not Found"
            return time

        }
    }

}

