package com.axb.criminalintent.viewmodel

import androidx.lifecycle.ViewModel
import com.axb.criminalintent.CrimeRepository
import com.axb.criminalintent.bean.Crime

class CrimeListViewModel : ViewModel() {

/*    val crimes = mutableListOf<Crime>()

    init {
        for (i in 0 until 100) {
            val crime = Crime()
            crime.title = "Crime #$i"
            crime.isSolved = i % 2 == 0
            crimes += crime
        }
    }*/
    private val crimeRepository = CrimeRepository.get()
    val crimeListLiveData = crimeRepository.getCrimes()

    fun addCrime(crime: Crime) {
        crimeRepository.addCrime(crime)
    }
}