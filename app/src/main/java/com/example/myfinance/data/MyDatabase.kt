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
    version = 3
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
                .addMigrations(MIGRATION_2_3)
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

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Создаем новую таблицу с нужной структурой
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS `operations_new` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `amount` REAL NOT NULL,
                `categoryId` INTEGER NOT NULL,
                `description` TEXT,
                FOREIGN KEY(`categoryId`) REFERENCES `categories`(`id`) ON DELETE CASCADE
            )
        """.trimIndent())

        database.execSQL("""
            INSERT INTO operations_new (amount, categoryId, description)
            SELECT amount, 0 AS categoryId, description FROM operations
        """.trimIndent())

        // Удаляем старую таблицу
        database.execSQL("DROP TABLE operations")

        // Переименовываем новую таблицу в старую
        database.execSQL("ALTER TABLE operations_new RENAME TO operations")
    }
}
