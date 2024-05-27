package com.example.recu_app.utils

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migrations {

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "CREATE TABLE alerts_new (id INTEGER, title TEXT NOT NULL, description TEXT NOT NULL, deadLine TEXT NOT NULL DEFAULT '0', PRIMARY KEY(id))"
            )
            database.execSQL(
                "INSERT INTO alerts_new (id, title, description, deadLine) SELECT id, title, description, deadLine FROM alerts"
            )
            database.execSQL("DROP TABLE alerts")
            database.execSQL("ALTER TABLE alerts_new RENAME TO alerts")
        }
    }
}
