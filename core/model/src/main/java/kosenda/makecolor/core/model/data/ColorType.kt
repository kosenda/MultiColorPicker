package kosenda.makecolor.core.model.data

enum class ColorType(val index: Int) {
    RGB(index = 0),
    CMYK(index = 1),
    HSV(index = 2),
}

enum class ColorTypeWithHex(val index: Int) {
    RGB(index = 0),
    CMYK(index = 1),
    HSV(index = 2),
    HEX(index = 3),
}
