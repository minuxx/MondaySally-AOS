package com.moon.android.mondaysally.ui.main.twinkle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.FragmentTwinkleBottomSheetBinding

class BottomSheetDialogFragment() :BottomSheetDialogFragment(){
    lateinit var binding: FragmentTwinkleBottomSheetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_twinkle_bottom_sheet, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.fragmentTwinkleBottomSheetTvDelete.setOnClickListener{

        }
        binding.fragmentTwinkleBottomSheetTvModify.setOnClickListener{

        }
        super.onViewCreated(view, savedInstanceState)
    }
}