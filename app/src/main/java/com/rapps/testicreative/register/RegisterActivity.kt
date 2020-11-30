package com.rapps.testicreative.register

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProviders
import com.rapps.testicreative.databinding.ActivityRegisterBinding
import com.rapps.testicreative.model.Register
import com.rapps.testicreative.model.StateViewModel
import com.rapps.testicreative.verifyEmail.VerifyEmailActivity

class RegisterActivity : AppCompatActivity() {

    lateinit var binding: ActivityRegisterBinding
    lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        observeViewModel()
        initListener()
    }

    private fun observeViewModel() {
        viewModel.responseRegister.observe(this, { response ->
            when (response) {
                is StateViewModel.Success -> {
                    binding.pbLoading.isVisible = false
                    val builder = AlertDialog.Builder(this)
                    builder.apply {
                        setTitle("Success")
                        setMessage(response.messages)
                        setCancelable(false)
                        setPositiveButton("Verify email"){dialog,_ ->
                            dialog.dismiss()
                            val intent = Intent(this@RegisterActivity,VerifyEmailActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }

                    builder.show()
                }
                is StateViewModel.Error-> {
                    binding.pbLoading.isVisible = false
                    val builder = AlertDialog.Builder(this)
                    builder.apply {
                        setMessage(response.error + "Check your inputs")
                        setTitle("Error")
                        setPositiveButton("Ok"){dialog,_ ->
                            dialog.dismiss()
                        }
                    }
                    builder.show()
                }
                is StateViewModel.Loading ->{
                    binding.pbLoading.isVisible = true
                }

            }

        })

    }

    private fun initListener() {
        binding.btnRegister.setOnClickListener {
            if (binding.cbAcceptTerms.isChecked) {

                val title = binding.etTitle.editText?.text.toString()
                val fullName = binding.etFullname.editText?.text.toString()
                val email = binding.etEmail.editText?.text.toString()
                val password = binding.etPassword.editText?.text.toString()
                val confirmPassword = binding.etConfirmPassword.editText?.text.toString()
                val objRegister = Register(title, fullName, email, password, confirmPassword)

                viewModel.registerUser(objRegister)
            }
        }
    }
}