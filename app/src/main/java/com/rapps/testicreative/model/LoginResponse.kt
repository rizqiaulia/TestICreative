package com.rapps.testicreative.model

data class LoginResponse(
    var id:String,
    var title:String,
    var fullName:String,
    var email:String,
    var role:String,
    var avatar: Avatar,
    var created: String,
    var updated: String,
    var isVerified: Boolean,
    var jwtToken: String,
    var refreshToken: String
)