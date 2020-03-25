package ru.alexb.currencyrates.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.currency_item_base.view.*
import kotlinx.android.synthetic.main.currency_item_base.view.codeView
import kotlinx.android.synthetic.main.currency_item_base.view.iconView
import kotlinx.android.synthetic.main.currency_item_base.view.nameView
import kotlinx.android.synthetic.main.currency_item_regular.view.*
import ru.alexb.currencyrates.R
import ru.alexb.currencyrates.ui.controller.MainViewController
import ru.alexb.currencyrates.ui.viewmodel.CurrencyItem

class CurrencyRecyclerViewAdapter(
    private val layoutManager: LinearLayoutManager,
    private val viewController: MainViewController
) : RecyclerView.Adapter<CurrencyViewHolder>() {
    private var items: List<CurrencyItem> = ArrayList()
    private var editedHolder: BaseViewHolder? = null

    fun updateItems(newItems: List<CurrencyItem>) {
        items = newItems
        if (editedHolder == null) {
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int =
        if (position == 0) BASE_TYPE else REGULAR_TYPE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return when (viewType) {
            BASE_TYPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.currency_item_base, parent, false)
                BaseViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.currency_item_regular, parent, false)
                RegularViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val item = items[position]
        holder.apply {
            root.setOnClickListener {
                viewController.setBaseCurrency(item.code)
                layoutManager.scrollToPositionWithOffset(0, 0)
            }
            icon.setImageResource(item.iconResId)
            code.text = item.code
            name.text = item.name
        }

        when (holder) {
            is BaseViewHolder -> holder.amount.apply {
                setText(item.amount)
                setOnFocusChangeListener { _, hasFocus ->
                    editedHolder = if (hasFocus) holder else null
                }
                setOnEditorActionListener { v, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        v.clearFocus()
                        viewController.setAmount(v.text.toString())
                        true
                    } else {
                        false
                    }
                }
            }
            is RegularViewHolder -> holder.amount.apply {
                text = item.amount
            }
        }
    }

    companion object {
        private const val BASE_TYPE = 1
        private const val REGULAR_TYPE = 2
    }
}

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
