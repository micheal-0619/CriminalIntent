package com.axb.criminalintent.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.axb.criminalintent.R
import com.axb.criminalintent.fragment.CrimeFragment
import com.axb.criminalintent.fragment.CrimeListFragment
import java.util.UUID


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), CrimeListFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        /*
        * 创建并提交了一个fragment事务
        * add(...)函数及其相关代码才是重点。
        * add(...)函数是整个事务的核心， 它有两个参数： 容器视图资源ID和新创建的CrimeFragment。
        * */

        if (currentFragment == null) {
            //单例模式
            val fragment = CrimeListFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    /*
    * 按回退键， 整个应用界面退出了。 这是因为启动应用后， MainActivity是应用回退栈里唯一一个实例
    * 显然， 通过按回退键， 用户期望从crime明细界面回到crime列表项界面。 要实现这个效果， 需要把替换事务
    * 添加到回退栈里   .addToBackStack(null)
    * */
    override fun onCrimeSelected(crimeID: UUID) {
        Log.d(TAG, "MainActivity.onCrimeSelected: $crimeID")

        //val fragment = CrimeFragment()
        val fragment=CrimeFragment.newInstance(crimeID)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()

    }
}