package com.axb.criminalintent.fragment

import android.app.DatePickerDialog
import android.app.Dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class DatePickerFragment : DialogFragment(){

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val calendar = Calendar.getInstance()
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)


        /*
        * DatePickerDialog构造函数需要好几个参数。
        * 第一个参数是用来获取视图相关必需资源的context对象。
        *  第二个参数是日期监听器， 稍后会添加， 现在先传入null。
        * 最后三个参数是供日期选择器初始化使用的年、月、 日初始值。
        * 在知道某crime的具体发生日期前， 先初始化其为当前日期。
        *
        * */

        return DatePickerDialog(requireContext(), null, initialYear, initialMonth, initialDay)
    }
}