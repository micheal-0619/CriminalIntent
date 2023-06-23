package com.axb.criminalintent.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.axb.criminalintent.R
import com.axb.criminalintent.bean.Crime
import com.axb.criminalintent.viewmodel.CrimeListViewModel

private const val TAG = "CrimeListFragment"

class CrimeListFragment : Fragment() {

    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: CrimeAdapter? = null

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this)[CrimeListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: Total crimes: ${crimeListViewModel.crimes.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)
        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager =
            LinearLayoutManager(context)//LinearLayoutManager 竖直列表的形式摆放列表项

        updateUI()
        return view
    }

    /*
    * 关联RecyclerView
    *
    * */
    private fun updateUI() {
        val crimes = crimeListViewModel.crimes
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }

    /*
    * CrimeHolder 内部类
    * CrimeHolder的构造函数首先接收并保存view，
    * 然后将其作为值参传递给RecyclerView.ViewHolder的构造函数
    * */
    private inner class CrimeHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.crime_title)
        val dateTextView: TextView = view.findViewById(R.id.crime_date)
    }

    /*
    * RecyclerView自己不创建ViewHolder， 它请Adapter来帮忙。 Adapter是一个控制器对象，
    * 其作为沟通的桥梁， 从模型层获取数据， 然后提供给RecyclerView显示。
    *
    * Adapter负责：   1.创建必要的ViewHolder；    2.绑定ViewHolder至模型层数据。
    *
    * RecyclerView负责：1.请Adapter创建ViewHolder；2.请Adapter绑定ViewHolder至具体的模型层数据
    *
    * */
    private inner class CrimeAdapter(var crimes: List<Crime>) :
        RecyclerView.Adapter<CrimeHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            return CrimeHolder(view)
        }

        override fun getItemCount() = crimes.size

        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            val crime = crimes[position]
            holder.apply {
                titleTextView.text = crime.title
                dateTextView.text = crime.date.toString()
            }
        }

    }

    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }
}