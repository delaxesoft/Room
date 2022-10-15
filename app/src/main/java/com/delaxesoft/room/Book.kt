package com.delaxesoft.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName="books")
class Book(
    @PrimaryKey
    val id: String,

    val author: String,

    val books: String,

    val description:String,
    @ColumnInfo(name="last_updated")
    val lastUpdated:Date?

)