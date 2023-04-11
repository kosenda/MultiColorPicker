package kosenda.makecolor.core.ui.data

import kotlinx.serialization.Serializable

@Serializable
data class SplitColorParam(
    val splitColorNum: SplitColorNum,
    val hex1: String,
    val hex2: String,
    val hex3: String,
    val hex4: String,
)
