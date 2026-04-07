package com.aurelio.baldor.core.domain.model

import java.util.Date

data class UserData(
    val usuario: String,
    val username: String,
    val codigo: String,
    val role: Int,
    val role_des: String,
    val entidad: String,
    val estado: String,
    val date_reg: String
)
