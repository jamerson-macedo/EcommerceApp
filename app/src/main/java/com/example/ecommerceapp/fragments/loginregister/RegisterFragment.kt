package com.example.ecommerceapp.fragments.loginregister

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ecommerceapp.R
import com.example.ecommerceapp.data.User
import com.example.ecommerceapp.databinding.FragmentRegisterBinding
import com.example.ecommerceapp.util.RegisterValidation
import com.example.ecommerceapp.util.Resource
import com.example.ecommerceapp.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class RegisterFragment() : Fragment(R.layout.fragment_register) {
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvWelcomme2.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.apply {
            btnRegister.setOnClickListener {
                val user = User(
                    // trim removve os espacos
                    edtNameRegister.text.toString().trim(),
                    edtLastnameRegister.text.toString().trim(),
                    edtEmailRegister.text.toString().trim(),

                    )
                val password = edtPasswordRegister.text.toString()
                viewModel.createaccountWithEmail(user, password)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.register.collect {
                when (it) {
                    is Resource.Loading -> {
                        binding.btnRegister.startAnimation()
                    }

                    is Resource.Success -> {
                        Log.i("teste", it.data.toString())
                        binding.btnRegister.revertAnimation()
                    }

                    is Resource.Error -> {
                        Log.i("teste", it.message.toString())
                        binding.btnRegister.revertAnimation()
                    }

                    else -> {
                        Unit
                    }
                }
            }

        }
        lifecycleScope.launchWhenStarted {
            viewModel.validation.collect { validation ->
                if (validation.email is RegisterValidation.Failed) {
                    withContext(Dispatchers.Main) {
                        binding.edtEmailRegister.apply {
                            requestFocus()
                            error = validation.email.message
                        }
                    }
                }
                if (validation.password is RegisterValidation.Failed) {
                    withContext(Dispatchers.Main) {
                        binding.edtPasswordRegister.apply {
                            requestFocus()
                            error = validation.password.message
                        }
                    }
                }

            }
        }
    }
}