package ru.alexb.currencyrates.ui.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.currency_item.view.*
import ru.alexb.currencyrates.R
import ru.alexb.currencyrates.ui.controller.MainViewController
import ru.alexb.currencyrates.ui.viewmodel.CurrencyItem

class CurrencyRecyclerViewAdapter(
    private val layoutManager: LinearLayoutManager,
    private val viewController: MainViewController
) : RecyclerView.Adapter<CurrencyRecyclerViewAdapter.CurrencyViewHolder>() {
    private var items: List<CurrencyItem> = ArrayList()

    fun updateItems(newItems: List<CurrencyItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.currency_item, parent, false)
        return CurrencyViewHolder(view)
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
            amount.setText(item.amount)
        }
    }

    class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val root: View = itemView.rootView
        val icon: ImageView = itemView.iconView
        val code: TextView = itemView.codeView
        val name: TextView = itemView.nameView
        val amount: EditText = itemView.amountView
    }
}