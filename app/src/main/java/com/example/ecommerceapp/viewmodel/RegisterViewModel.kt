package com.example.ecommerceapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.ecommerceapp.data.User
import com.example.ecommerceapp.util.RegisterFieldState
import com.example.ecommerceapp.util.RegisterValidation
import com.example.ecommerceapp.util.Resource
import com.example.ecommerceapp.util.validadeEmail
import com.example.ecommerceapp.util.validadePasWord
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    ViewModel() {
    private val _register = MutableStateFlow<Resource<FirebaseUser>>(Resource.Unspecified())
    val register: Flow<Resource<FirebaseUser>> = _register
    private val _validation = Channel<RegisterFieldState>()
    val validation = _validation.receiveAsFlow()
    fun createaccountWithEmail(user: User, password: String) {
        if (checkvalidate(user, password)) {
            runBlocking {
                _register.emit(Resource.Loading())
            }
            firebaseAuth.createUserWithEmailAndPassword(user.email, password).addOnSuccessListener {
                it.user?.let {
                    _register.value = Resource.Success(it)
                }
            }.addOnFailureListener {
                _register.value = Resource.Error(it.message.toString())
            }
        } else {
            val registerFieldState =
                RegisterFieldState(validadeEmail(user.email), validadePasWord(password))
            runBlocking {
                Log.i("testando",registerFieldState.toString())
                _validation.send(registerFieldState)
            }

        }
    }

    private fun checkvalidate(user: User, password: String): Boolean {
        val emailvalidation = validadeEmail(user.email)
        val passwordvalidation = validadePasWord(password)
        val shouldregister =
            emailvalidation is RegisterValidation.Success && passwordvalidation is RegisterValidation.Success
        return shouldregister
    }
}