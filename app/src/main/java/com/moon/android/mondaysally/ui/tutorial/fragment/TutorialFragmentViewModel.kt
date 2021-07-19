package com.moon.android.mondaysally.ui.tutorial.fragment

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel


class TutorialFragmentViewModel() : ViewModel() {

    var title = ObservableField("")
    var content = ObservableField("")
    var imageId = ObservableField("")

}