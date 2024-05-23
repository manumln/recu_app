package com.example.recu_app.domain.alerts.models

class ListAlerts private constructor()
{
    var alerts : MutableList<Alert>  = mutableListOf()
    companion object{
        val list : ListAlerts by lazy {
            ListAlerts()
        }
    }
}