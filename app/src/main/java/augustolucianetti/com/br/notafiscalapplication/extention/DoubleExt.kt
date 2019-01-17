package br.com.augustolucianetti.calculaflex.extention

fun Double.format(digits: Int) = String.format("%.${digits}f", this)