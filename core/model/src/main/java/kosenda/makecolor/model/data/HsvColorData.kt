package kosenda.makecolor.model.data

import com.godaddy.android.colorpicker.HsvColor

data class HsvColorData(
    val hsv1: HsvColor = HsvColor(0f, 1f, 1f, 1f),
    val hsv2: HsvColor = HsvColor(30f, 1f, 1f, 1f),
    val hsv3: HsvColor = HsvColor(60f, 1f, 1f, 1f),
    val hsv4: HsvColor = HsvColor(90f, 1f, 1f, 1f),
    val hsv5: HsvColor = HsvColor(120f, 1f, 1f, 1f),
)
