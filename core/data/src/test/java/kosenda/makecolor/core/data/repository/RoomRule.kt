package kosenda.makecolor.core.data.repository

import android.content.Context
import androidx.room.Room
import kosenda.makecolor.core.data.database.ColorDatabase
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class RoomRule(private val context: Context) : TestWatcher() {
    private lateinit var database: ColorDatabase
    fun getInstance(): ColorDatabase {
        database = Room
            .databaseBuilder(context = context, ColorDatabase::class.java, "test")
            .allowMainThreadQueries()
            .build()
        return database
    }
    override fun finished(description: Description) {
        if (::database.isInitialized) database.close()
    }
}