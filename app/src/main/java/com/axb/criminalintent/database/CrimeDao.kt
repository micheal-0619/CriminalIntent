package com.axb.criminalintent.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.axb.criminalintent.bean.Crime
import java.util.UUID

/*
* @Dao注解告诉Room， CrimeDao是一个数据访问对象。 把CrimeDao和数据库类关联起来后，
* Room会自动给CrimeDao接口里的函数生成实现代码。
*
* */

@Dao
interface CrimeDao {

    /*
    * SELECT * FROM crime语句告诉Room取出crime数据库表里所有记录及其所有字段。 SELECT * FROM
    * crime WHERE id=(:id)是取出匹配给定ID的某条记录的所有字段。
    *
    * */
    @Query("SELECT * FROM crime")
    fun getCrimes(): LiveData<List<Crime>>

    @Query("SELECT * FROM crime WHERE id=(:id)")
    fun getCrime(id: UUID): LiveData<Crime?>

    @Update
    fun updateCrime(crime: Crime)

    @Insert
    fun addCrime(crime: Crime)

}