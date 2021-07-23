package com.moon.android.mondaysally.ui.terms

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moon.android.mondaysally.data.entities.Code
import com.moon.android.mondaysally.data.remote.Fail
import com.moon.android.mondaysally.data.repository.SharedPrefRepository
import com.moon.android.mondaysally.data.repository.auth.AuthNetworkRepository
import com.moon.android.mondaysally.utils.ApiException
import kotlinx.coroutines.launch


class TermsViewModel(
    private val authNetworkRepository: AuthNetworkRepository
) : ViewModel() {

    var teamCode = ObservableField("")

    var goNextActivity: MutableLiveData<Boolean> = MutableLiveData()
    var fail: MutableLiveData<Fail> = MutableLiveData()


    fun whenBtnDoneClicked() {
        teamCode.get()?.let {
//            Log.d("체크", it)
        }
    }


}