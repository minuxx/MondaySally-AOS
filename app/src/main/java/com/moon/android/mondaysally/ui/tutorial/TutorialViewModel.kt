package com.moon.android.mondaysally.ui.tutorial

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moon.android.mondaysally.data.repository.SharedPrefRepository


class TutorialViewModel(private val sharedPrefRepository: SharedPrefRepository) : ViewModel() {

    val pageNumber: MutableLiveData<Int> = MutableLiveData<Int>()
    val exitTutorial: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val goLastPage: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    fun noMoreTutorial() {
        sharedPrefRepository.noMoreTutorial()
    }

    fun whenBtnSkipClicked() {
        if (pageNumber.value == 2){
            exitTutorial.value = true
        }else{
            goLastPage.value = true
        }
    }

    fun whenPageChanged(pageIndex: Int){
        pageNumber.value = pageIndex
    }

}