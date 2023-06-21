package com.axb.criminalintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.axb.criminalintent.fragment.CrimeFragment

class MainActivity : AppCompatActivity() {
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
            val fragment = CrimeFragment()
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, fragment)
                .commit()
        }
    }
}