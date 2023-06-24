package com.axb.criminalintent.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/*
* 第一个@Entity是个类级别的注解， 这个注解表示被注解的类定义了一张或多张数据库表结构。
*
* @PrimaryKey注解添加给了id属性  这个注解的作用是指定数据库里哪一个字段是主键（primary key)
* 主键是数据库中的某个字段， 其值在一条记录里具有唯一性， 可以用来查找单条记录。
* */
@Entity
data class Crime(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var title: String = "",
    var date: Date = Date(),
    var isSolved: Boolean = false
)
