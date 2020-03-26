package ru.alexb.currencyrates.ui.view.adapter

import android.text.Editable
import android.text.TextWatcher

class AmountTextWatcher(private val onAmountChanged: (CharSequence?) -> Unit) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        onAmountChanged(s)
    }

    override fun afterTextChanged(s: Editable?) {}
}