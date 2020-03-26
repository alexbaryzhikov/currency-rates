package ru.alexb.currencyrates.ui.view.adapter

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.currency_item_base.view.*
import kotlinx.android.synthetic.main.currency_item_base.view.codeView
import kotlinx.android.synthetic.main.currency_item_base.view.iconView
import kotlinx.android.synthetic.main.currency_item_base.view.nameView
import kotlinx.android.synthetic.main.currency_item_regular.view.*

sealed class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val root: View = itemView.rootView
    val icon: ImageView = itemView.iconView
    val code: TextView = itemView.codeView
    val name: TextView = itemView.nameView
}

class BaseViewHolder(itemView: View) : CurrencyViewHolder(itemView) {
    val amount: EditText = itemView.amountEditView
}

class RegularViewHolder(itemView: View) : CurrencyViewHolder(itemView) {
    val amount: TextView = itemView.amountView
}
