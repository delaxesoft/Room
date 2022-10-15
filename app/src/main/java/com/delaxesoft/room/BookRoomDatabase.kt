package com.delaxesoft.room

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities=[Book::class],version=3)
@TypeConverters(DateTypeConverter::class)
abstract class BookRoomDatabase:RoomDatabase() {

    //associate the Dao with database
    abstract fun bookDao(): BookDao

    //create the database and initialize an instance of it
    //make the room database instance a singleton
    companion object {
        private var bookRoominstance: BookRoomDatabase? = null

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE books"
                        + " ADD COLUMN description TEXT DEFAULT  'Add Description' " +
                        " NOT NULL")
                 }
            }


            private val MIGRATION_2_3: Migration = object : Migration(2, 3) {
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("ALTER TABLE books"
                            + " ADD COLUMN last_updated INTEGER DEFAULT NULL")
                }
            }

            fun getDatabase(context: Context): BookRoomDatabase {
                if (bookRoominstance == null) {
                    synchronized(BookRoomDatabase::class.java) {
                        if (bookRoominstance == null) {
                            bookRoominstance =
                                Room.databaseBuilder<BookRoomDatabase>(context.applicationContext,
                                    BookRoomDatabase::class.java, "book_database").addMigrations(
                                    MIGRATION_1_2, MIGRATION_2_3).build()
                        }
                    }

                }
                return bookRoominstance!!
            }
        }
    }
