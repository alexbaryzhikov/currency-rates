package ru.alexb.currencyrates.ui.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.alexb.currencyrates.R
import ru.alexb.currencyrates.ui.controller.MainViewController
import ru.alexb.currencyrates.ui.viewmodel.CurrencyItem

class CurrencyRecyclerViewAdapter(
    private val layoutManager: LinearLayoutManager,
    private val viewController: MainViewController
) : RecyclerView.Adapter<CurrencyViewHolder>() {
    private var items: List<CurrencyItem> = listOf()
    private var isEditing = false
    private val amountWatcher = AmountTextWatcher(::onAmountChanged)

    private fun onAmountChanged(amount: CharSequence?) {
        val validAmount = if (amount.isNullOrBlank()) "0" else amount
        viewController.setAmount(validAmount.toString())
    }

    fun updateItems(newItems: List<CurrencyItem>) {
        items = newItems
        if (isEditing) {
            val first = layoutManager.findFirstVisibleItemPosition().coerceAtLeast(1)
            val last = layoutManager.findLastVisibleItemPosition()
            for (i in first..last) {
                notifyItemChanged(i, items[i].amount)
            }
        } else {
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
                    if (hasFocus) {
                        isEditing = true
                        addTextChangedListener(amountWatcher)
                    } else {
                        isEditing = false
                        removeTextChangedListener(amountWatcher)
                    }
                }
                setOnEditorActionListener { v, actionId, _ ->
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        v.clearFocus()
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

    override fun onBindViewHolder(
        holder: CurrencyViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else if (holder is RegularViewHolder) {
            holder.amount.text = payloads.first() as String
        }
    }

    companion object {
        private const val BASE_TYPE = 1
        private const val REGULAR_TYPE = 2
    }
}
