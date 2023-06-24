package com.axb.criminalintent.database

import androidx.room.TypeConverter
import java.util.Date
import java.util.UUID


/*
* 创建类型转换器
*
* 前两个函数用于处理Date对象，
* 后两个函数用于处理UUID对象。
* 需要导包时， 确认导入了java.util.Date版本的Date类
*
* */

class CrimeTypeConverters {

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long?): Date? {
        return millisSinceEpoch?.let {
            Date(it)
        }
    }

    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }
}