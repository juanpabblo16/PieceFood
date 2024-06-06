package icesi.edu.co.piecefood.model

data class User (

    var id:String = "",
    var username:String = "",
    var email:String = "",
    var name:String = "",
    var portions: List<Portion>? = null

)
