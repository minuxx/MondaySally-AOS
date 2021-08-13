package com.moon.android.mondaysally.ui.team_code

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moon.android.mondaysally.data.entities.Code
import com.moon.android.mondaysally.data.remote.Fail
import com.moon.android.mondaysally.data.repository.SharedPrefRepository
import com.moon.android.mondaysally.data.repository.network.AuthNetworkRepository
import com.moon.android.mondaysally.utils.ApiException
import kotlinx.coroutines.launch


class TeamCodeViewModel(
    private val authNetworkRepository: AuthNetworkRepository,
    private val sharedPrefRepository: SharedPrefRepository
) : ViewModel() {

    var teamCode = ObservableField("")

    var goNextActivity: MutableLiveData<Boolean> = MutableLiveData()
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    var fail: MutableLiveData<Fail> = MutableLiveData()

    private fun checkTeamCode(code: String) = viewModelScope.launch {
        try {
            val authResponse = authNetworkRepository.checkTeamCode(Code(code))
            if (authResponse.code == 200) {
                authResponse.auth?.let {
                    authResponse.auth.jwtToken?.let { it2 -> sharedPrefRepository.saveJwtToken(it2) }
                    goNextActivity.value = true
                }
            } else {
                fail.value = Fail(authResponse.message, authResponse.code)
            }
        } catch (e: ApiException) {
            fail.value = Fail(e.message!!, 404)
        } catch (e: Exception) {
            fail.value = Fail(e.message!!, 404)
        }
    }

    fun whenBtnDoneClicked() {
        teamCode.get()?.let {
//            Log.d("체크", it)
            loading.value = true
            checkTeamCode(it)
        }
    }

}