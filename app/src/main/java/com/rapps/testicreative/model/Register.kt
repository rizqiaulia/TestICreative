package com.rapps.testicreative.model

data class Register (
    var title:String,
    var fullName:String,
    var email:String,
    var password:String,
    var confirmPassword:String,
    var acceptTerms :Boolean = true
)