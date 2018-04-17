package com.github.sidky.bigpicture.data

import android.arch.persistence.room.*
import java.util.*

@Entity(tableName = "big_picture")
data class BigPictureEntry(@PrimaryKey val id: Int?,
                           @ColumnInfo(name="title") val title: String,
                           @ColumnInfo(name="description") val description: String,
                           @ColumnInfo(name="url") val url: String,
                           @ColumnInfo(name="date") val date: Date?)

object Converters {
    @JvmStatic @TypeConverter
    fun fromTimestamp(timestamp: Long?) = timestamp?.let { Date(timestamp) }

    @JvmStatic @TypeConverter
    fun fromDate(date: Date?) = date?.time
}

@Dao
interface BigPictureDao {
    @Query("SELECT * FROM big_picture")
    fun feed(): List<BigPictureEntry>

    @Insert
    fun addFeedItem(feed: List<BigPictureEntry>)

    @Query("DELETE FROM big_picture")
    fun deleteAllFeed()

    @Transaction
    fun clearAndUpdateFeed(feed: List<BigPictureEntry>) {
        deleteAllFeed()
        addFeedItem(feed)
    }
}

@Database(entities = [BigPictureEntry::class], version = 1)
@TypeConverters(Converters::class)
abstract class BigPictureDb : RoomDatabase() {
    abstract fun dao(): BigPictureDao
}