package com.axb.criminalintent

import android.content.Context
import androidx.room.Room
import com.axb.criminalintent.bean.Crime
import com.axb.criminalintent.database.CrimeDatabase
import java.util.UUID


/*
* 仓库模式 Repository
* singleton模式
* */

private const val DATABASE_NAME = "crime-database"

class CrimeRepository private constructor(context: Context) {

    /*
    * 在CrimeRepository里添加两个属性  database 和 crimeDao ， 用来保存数据库和DAO对象
    *
    * Room.databaseBuilder()使用三个参数具体实现了CrimeDatabase抽象类。
    *  第一个参数是Context对象，因为数据库要访问文件系统。 这里传入的是应用上下文。 之前说过， 它要比任何activity类都“活得久”。
    *  第二个参数是Room用来创建数据库的类。
    *  第三个参数是Room将要创建的数据库文件的名字。 由于没有外部访问需要， 因此这里定义使用了私有字符串常量。
    *
    * */
    private val database: CrimeDatabase = Room.databaseBuilder(
        context.applicationContext, CrimeDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val crimeDao = database.crimeDao()

    /*
    * 添加两个仓库函数， 访问到DAO对象的相应数据库操作函数。
    *
    * */
    fun getCrimes(): List<Crime> = crimeDao.getCrimes()
    fun getCrime(id: UUID): Crime? = crimeDao.getCrime(id)

    companion object {
        private var INSTANCE: CrimeRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository {
            return INSTANCE ?: throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}