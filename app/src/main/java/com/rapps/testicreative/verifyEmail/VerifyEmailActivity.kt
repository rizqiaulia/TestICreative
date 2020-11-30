package com.rapps.testicreative.verifyEmail

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProviders
import com.rapps.testicreative.databinding.ActivityVerifyEmailBinding
import com.rapps.testicreative.model.StateViewModel

class VerifyEmailActivity : AppCompatActivity() {
    lateinit var binding: ActivityVerifyEmailBinding
    lateinit var viewModel: VerifyEmailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProviders.of(this).get(VerifyEmailViewModel::class.java)
        observeViewModel()
        initListener()
    }

    private fun observeViewModel() {
        viewModel.responseVerify.observe(this, { response ->
            when (response) {
                is StateViewModel.Success -> {
                    binding.pbLoading.isVisible =false
                    val builder = AlertDialog.Builder(this)
                    builder.apply {
                        setTitle("Success")
                        setMessage(response.messages)
                        setCancelable(false)
                        setPositiveButton("Go to Login ") { dialog, _ ->
                            dialog.dismiss()

                        }
                    }


                    builder.show()
                }
                is StateViewModel.Error -> {
                    binding.pbLoading.isVisible =false
                    val builder = AlertDialog.Builder(this)
                    builder.apply {
                        setMessage(response.error)
                        setTitle("Error")
                        setPositiveButton("Ok") { dialog, _ ->
                            dialog.dismiss()
                        }
                    }
                    builder.show()
                }

                is StateViewModel.Loading ->{
                    binding.pbLoading.isVisible =true
                }
            }
        })
    }

    private fun initListener() {
        binding.btnVerifyEmail.setOnClickListener {
            if (binding.etVerifyCode.editText!!.text.isNotEmpty()) {
                viewModel.verifyEmail(binding.etVerifyCode.editText!!.text.toString())
            }
        }
    }
}