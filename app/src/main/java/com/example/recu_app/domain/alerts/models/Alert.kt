package com.example.recu_app.domain.alerts.models

import java.time.LocalDate

data class Alert(
    val id: Int = 0,
    val userId: Int,
    val textShort: String,
    val message: String,
    val alertDate: LocalDate
)