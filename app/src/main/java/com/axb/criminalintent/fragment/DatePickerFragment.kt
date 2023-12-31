package com.axb.criminalintent.fragment

import android.app.DatePickerDialog
import android.app.Dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

private const val ARG_DATE = "date"

class DatePickerFragment : DialogFragment() {

    /*
    * 回调接口
    * */
    interface Callbacks {
        fun onDateSelected(date: Date)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        /*
        * 发回日期
        * */

        val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val resultDate: Date = GregorianCalendar(year, month, day).time
            targetFragment?.let { fragment -> (fragment as Callbacks).onDateSelected(resultDate) }
        }

        val date = arguments?.getSerializable(ARG_DATE) as Date
        val calendar = Calendar.getInstance()
        calendar.time = date
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

        return DatePickerDialog(requireContext(), dateListener, initialYear, initialMonth, initialDay)
    }

    companion object {
        fun newInstance(date: Date): DatePickerFragment {
            val args = Bundle().apply {
                putSerializable(ARG_DATE, date)
            }
            return DatePickerFragment().apply { arguments = args }
        }
    }
}