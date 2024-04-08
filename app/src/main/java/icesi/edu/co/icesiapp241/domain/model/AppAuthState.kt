package icesi.edu.co.icesiapp241.domain.model

sealed class AppAuthState {

    //Estado1
    data class Loading(val message:String):AppAuthState()
    //Estado 2
    data class Error(val message: String):AppAuthState()
    //Estado 3
    data class Succes(val UserID: String):AppAuthState()

}