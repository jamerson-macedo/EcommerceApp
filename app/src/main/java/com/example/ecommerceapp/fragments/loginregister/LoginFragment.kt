package com.example.ecommerceapp.fragments.loginregister

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ecommerceapp.R
import com.example.ecommerceapp.activities.ShoppingActivity
import com.example.ecommerceapp.databinding.FragmentLoginBinding
import com.example.ecommerceapp.dialog.setupDialog
import com.example.ecommerceapp.util.Resource
import com.example.ecommerceapp.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment() : Fragment(R.layout.fragment_login) {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvWelcomme2.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.tvEsqueceuSenha.setOnClickListener {
            setupDialog { email ->
                viewModel.resetPassword(email)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.reset.collect {
                when (it) {
                    is Resource.Success -> {
                        Snackbar.make(requireView(),"um link foi enviado",Snackbar.LENGTH_LONG).show()

                    }

                    is Resource.Loading -> {


                    }

                    is Resource.Error -> {
                        Snackbar.make(requireView(),"Erro:${it.message}",Snackbar.LENGTH_LONG).show()
                    }

                    else -> Unit
                }
            }

        }

        binding.apply {
            btnLogin.setOnClickListener {
                val email = edtEmailLogin.text.toString().trim()
                val password = edtPasswordLogin.text.toString()
                viewModel.login(email, password)

            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.login.collect {
                when (it) {
                    is Resource.Success -> {
                        binding.btnLogin.revertAnimation()
                        Intent(requireActivity(), ShoppingActivity::class.java).also { intent ->
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }

                    }

                    is Resource.Loading -> {
                        binding.btnLogin.startAnimation()

                    }

                    is Resource.Error -> {
                        binding.btnLogin.revertAnimation()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()

                    }

                    else -> Unit
                }
            }
        }
    }

}