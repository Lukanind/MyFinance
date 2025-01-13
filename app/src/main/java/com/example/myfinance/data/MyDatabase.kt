package com.example.myfinance.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [
        OperationEntity::class,
        CategoryEntity::class,
        UserEntity::class
    ],
    version = 2
)
abstract class MyDatabase : RoomDatabase() {
    abstract val operationDao: OperationDao
    abstract val userDao: UserDao
    abstract val categoryDao: CategoryDao

    companion object{
        fun createDatabase(context: Context): MyDatabase{
            return Room.databaseBuilder(
                context,
                MyDatabase::class.java,
                "test.db"
            )
                //Возможно понадобится миграция
                .addMigrations(MIGRATION_1_2)
                .build()
        }
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Создание новой таблицы CategoryEntity
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS `categories` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `categoryName` TEXT NOT NULL
            )
        """.trimIndent())

        // Создание новой таблицы UserEntity
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS `users` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `userName` TEXT NOT NULL,
                `password` TEXT NOT NULL,
                'role' TEXT NOT NULL
            )
        """.trimIndent())
    }
}