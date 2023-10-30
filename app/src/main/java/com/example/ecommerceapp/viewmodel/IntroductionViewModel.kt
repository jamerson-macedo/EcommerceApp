package com.example.ecommerceapp.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.R
import com.example.ecommerceapp.util.Constants.INTRODUCTION_KEY
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class IntroductionViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val firebaseAuth: FirebaseAuth
) :
    ViewModel() {
    private val _nagivate = MutableStateFlow(0)
    val nagivate = _nagivate

    companion object {
        // referencia para as 2 activities
        const val SHOPPING_ACTIVITY = 13
        val ACCOUNT_OPTIONS = R.id.action_introductionFragment_to_accountOptionsFragment
    }

    init {
        // checando se o botao foi clicado
        val btnClicked = sharedPreferences.getBoolean(INTRODUCTION_KEY, false)
        val userLogin = firebaseAuth.currentUser
        if (userLogin != null) {
            // se o usuario ja exister ele manda ir para a principal
            viewModelScope.launch {
                _nagivate.emit(SHOPPING_ACTIVITY)
            }
        } else if (btnClicked) {
            // se o botao for clicado entao ele manda para as opcoes
            viewModelScope.launch {
                _nagivate.emit(ACCOUNT_OPTIONS)
            }
        } else {
            Unit
        }
    }

    fun startButtonCLICK() {
        // coloca que ja fopi clicado
        sharedPreferences.edit().putBoolean(INTRODUCTION_KEY, true).apply()
    }

}