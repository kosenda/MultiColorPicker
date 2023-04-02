package kosenda.makecolor.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kosenda.makecolor.model.data.ColorItem

@Database(
    version = 2,
    entities = [ColorItem::class, Category::class],
    exportSchema = false,
)
abstract class ColorDatabase : RoomDatabase() {
    abstract fun colorDao(): ColorDao
    companion object {
        private var singleton: ColorDatabase? = null

        fun getInstance(context: Context): ColorDatabase = singleton ?: synchronized(this) {
            singleton ?: buildDataBase(context).also { singleton = it }
        }

        private fun buildDataBase(context: Context): ColorDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                ColorDatabase::class.java,
                "colors",
            )
                .addMigrations(MIGRATION_1_2)
                .build()
        }
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE categories ADD COLUMN alias TEXT NOT NULL DEFAULT ''")
        database.execSQL("ALTER TABLE categories RENAME COLUMN category TO name")
    }
}
