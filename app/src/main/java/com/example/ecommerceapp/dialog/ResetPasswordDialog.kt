package com.example.ecommerceapp.dialog

import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.example.ecommerceapp.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.setupDialog(onsendClick:(String)->Unit) {
    val dialog = BottomSheetDialog(requireContext(),R.style.DialogStyle)
    val view = layoutInflater.inflate(R.layout.reset_password, null)
    dialog.setContentView(view)
    // fazendo com que ele fdique afrente do teclado
    dialog.behavior.state=BottomSheetBehavior.STATE_EXPANDED
    dialog.show()

    val buttonCancel = view.findViewById<AppCompatButton>(R.id.btn_cancel_reset)
    val buttonSend = view.findViewById<AppCompatButton>(R.id.btn_send_reset)
    val edtEmail = view.findViewById<EditText>(R.id.edt_reset_email)
    buttonSend.setOnClickListener {
        val email=edtEmail.text.toString().trim()
        onsendClick(email)
        dialog.dismiss()
    }
    buttonCancel.setOnClickListener {
        dialog.dismiss()
    }
}