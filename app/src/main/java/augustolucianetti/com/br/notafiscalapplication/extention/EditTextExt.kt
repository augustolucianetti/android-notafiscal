package br.com.augustolucianetti.calculaflex.extention

import android.widget.EditText

fun EditText.getValue() = this.text.toString()

fun EditText.getDouble() = this.getValue().toDouble()