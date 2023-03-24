package kosenda.makecolor.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "categories")
data class Category(
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "size")
    var size: Int,
    @ColumnInfo(name = "alias")
    var alias: String = "",
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
)
