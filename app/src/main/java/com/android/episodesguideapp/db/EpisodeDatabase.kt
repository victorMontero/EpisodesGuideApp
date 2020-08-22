package com.android.episodesguideapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.episodesguideapp.models.Episode

@Database(
    entities = [Episode::class],
    version = 1
)

@TypeConverters(Converters::class)
abstract class EpisodeDatabase : RoomDatabase() {

    abstract fun getEpisodeDao(): EpisodeDao

    companion object{
        @Volatile
        private var instance: EpisodeDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also{ instance = it}
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                EpisodeDatabase::class.java,
                "episode_db.db"
            ).build()
    }
}