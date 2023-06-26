package com.axb.criminalintent.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.axb.criminalintent.R
import com.axb.criminalintent.bean.Crime
import com.axb.criminalintent.viewmodel.CrimeDetailViewModel
import java.util.UUID

private const val TAG = "CrimeFragment"
private const val ARG_CRIME_ID = "crime_id"

class CrimeFragment : Fragment() {

    private lateinit var crime: Crime
    private lateinit var titleField: TextView
    private lateinit var dateButton: Button
    private lateinit var solvedCheckBox: CheckBox

    private val crimeDetailViewModel: CrimeDetailViewModel by lazy {
        ViewModelProviders.of(this)[CrimeDetailViewModel::class.java]
    }

    /*
    * 1.Fragment.onCreate(Bundle?)是公共函数， 而Activity.onCreate(Bundle?)是受保护函数
    * 2.类似于activity， fragment同样具有保存及获取状态的bundle
    * 3.fragment的视图并没有在Fragment.onCreate(Bundle?)函数中生成。 虽然我们在该函数中配置了fragment实例， 但创建和配置fragment视图是另一个Fragment生命周期函数完成
    *    的： onCreateView(LayoutInflater, ViewGroup?, Bundle?)。
    *
    * */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        crime = Crime()

        //从argument中获取crime ID
        val crimeId: UUID = arguments?.getSerializable(ARG_CRIME_ID) as UUID
        Log.d(TAG, "onCreate: args bundle crime ID: $crimeId ")

        crimeDetailViewModel.loadCrime(crimeId)
    }


    /*
    * onCreateView 创建和配置fragment视图
    * @param 第一个参数是直接通过调用LayoutInflater.inflate(...)函数并传入布局的资源ID生成的
    * @param 第二个参数是视图的父视图， 我们通常需要父视图来正确配置部件。
    * @param 第三个参数告诉布局生成器是否立即将生成的视图添加给父视图。
    * */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime, container, false)
        titleField = view.findViewById(R.id.crime_title) as EditText
        dateButton = view.findViewById(R.id.crime_date) as Button
        dateButton.apply {
            text = crime.date.toString()
            isEnabled = false
        }
        solvedCheckBox = view.findViewById(R.id.crime_solved) as CheckBox

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeDetailViewModel.crimeLiveData.observe(
            viewLifecycleOwner,
            Observer { crime ->
                crime?.let { this.crime = crime }
                updateUI()
            })
    }

    /*
    * 在onStart()生命周期回调里给EditText部件添加监听器，
    *
    * TextWatcher监听器是设置在onStart()里的。 有些监听器不仅能在用户与之交互时触发，
    * 也能在因设备旋转， 视图恢复后导致数据重置时触发。 能响应数据输入的监听器有EditText的
    * TextWatcher以及CheckBox的OnCheckChangedListener。
    * 而OnClickListener只能响应用户交互。 之前在开发GeoQuiz时， 我们只会用到点击事件监听器， 不
    * 会遇到设备旋转后再触发监听事件的场景。 因此， 所有的监听器触发事件工作都是在onCreate(...)里完成的。
    *
    * */
    override fun onStart() {
        super.onStart()

        val titleWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            /*
             * 调用CharSequence（代表用户输入） 的toString()函数。 该函数
             * 最后返回用来设置Crime标题的字符串
             * */

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                crime.title = sequence.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        titleField.addTextChangedListener(titleWatcher)

        solvedCheckBox.apply {
            setOnCheckedChangeListener { _, isChecked ->
                crime.isSolved = isChecked
            }
        }
    }

    override fun onStop() {
        super.onStop()
        crimeDetailViewModel.saveCrime(crime)
    }

    private fun updateUI() {
        titleField.text = crime.title
        dateButton.text = crime.date.toString()
        solvedCheckBox.apply {
            isChecked = crime.isSolved
            jumpDrawablesToCurrentState()
        }
    }

    companion object {
        fun newInstance(crimeId: UUID): CrimeFragment {
            val args = Bundle().apply {
                putSerializable(ARG_CRIME_ID, crimeId)
            }

            return CrimeFragment().apply { arguments = args }
        }

    }

}