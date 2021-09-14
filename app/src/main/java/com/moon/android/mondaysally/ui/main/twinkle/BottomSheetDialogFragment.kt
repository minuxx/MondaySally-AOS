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
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_twinkle_bottom_sheet,
            container,
            false
        )
        return binding.root
    }

    private var onDeleteClickListener: (() -> Unit)? = null
    private var onEditClickListener: (() -> Unit)? = null

    fun setOnDeleteClickListener(listener: () -> Unit) {
        onDeleteClickListener = listener
    }
    fun setOnEditClickListener(listener: () -> Unit) {
        onEditClickListener = listener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.fragmentTwinkleBottomSheetTvDelete.setOnClickListener{
            onDeleteClickListener?.let { click ->
                click()
            }
        }
        binding.fragmentTwinkleBottomSheetTvModify.setOnClickListener{
            onEditClickListener?.let { click ->
                click()
            }
        }
        super.onViewCreated(view, savedInstanceState)
    }
}