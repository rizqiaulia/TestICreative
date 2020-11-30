package com.rapps.testicreative.model

sealed class StateViewModel {
    data class Success (val messages:String,val code:Int):StateViewModel()
    data class Error (val error:String,val code:Int):StateViewModel()
    object Loading : StateViewModel()
}