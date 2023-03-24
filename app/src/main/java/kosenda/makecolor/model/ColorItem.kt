package kosenda.makecolor.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "colors")
data class ColorItem(
    @ColumnInfo(name = "hex")
    var hex: String,
    @ColumnInfo(name = "category")
    var category: String,
    @ColumnInfo(name = "memo")
    var memo: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,
)
