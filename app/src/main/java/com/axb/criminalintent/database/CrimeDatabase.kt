package com.axb.criminalintent.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.axb.criminalintent.bean.Crime

/*
*  @Database注解告诉Room， CrimeDatabase类就是应用里的数据库。
*  这个注解本身也需要两个参数。
*     第一个参数是实体类集合， 告诉Room在创建和管理数据库表时该用哪个实体类。 这里只传入了Crime类， 因为整个应用就这么一个实体。
*     第二个参数是数据库版本。
*
*  @TypeConverters(CrimeTypeConverters::class) 把类型转换类添加到数据库类里。
*        通过添加@TypeConverters注解， 并传入CrimeTypeConverters类， 你告诉数据库， 需要转换数据类型时，
*        请使用CrimeTypeConverters类里的函数。
*
* */

@Database(entities = [Crime::class], version = 2)

@TypeConverters(CrimeTypeConverters::class)

abstract class CrimeDatabase : RoomDatabase() {

    /*
    * 把DAO类和数据库类关联起来
    * */
    abstract fun crimeDao(): CrimeDao
}

/*
* 数据库升级
* 第一个是迁移前的数据库版本， 第二个是迁移到的版本。 这里就是版本号1和2。
* */
val migration_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE Crime ADD COLUMN suspect TEXT NOT NULL DEFAULT ''"
        )
    }
}