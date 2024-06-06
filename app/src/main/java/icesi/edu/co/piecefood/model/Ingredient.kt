package icesi.edu.co.piecefood.model

data class Ingredient(
    var id: String = "",
    var name: String = "",
    var type: String = "",
    var calories: Int = 0,
    var proteins: Int = 0,
    var fat: Int = 0
)
