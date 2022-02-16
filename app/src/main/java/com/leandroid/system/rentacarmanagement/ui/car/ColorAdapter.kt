package com.leandroid.system.rentacarmanagement.ui.car

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.leandroid.system.rentacarmanagement.databinding.ItemColorBinding
import com.leandroid.system.rentacarmanagement.model.Color

class ColorAdapter(context: Context,
                   resource: Int,
) : ArrayAdapter<Color>(context, resource, mutableListOf()) {

    private val colors = mutableListOf<Color>()

    override fun getItem(position: Int): Color? {
        return colors[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return colors.size
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemColorBinding.inflate(inflater, parent, false)
        val country = getColorForPosition(position)
        bind(binding, country)
        return binding.root
    }

    fun getColorForPosition(position: Int): Color {
        return colors[position]
    }

    private fun bind(binding: ItemColorBinding, color: Color) {
        binding.tvColor.text = color.name
    }

    fun setColors(brands: MutableList<Color>) {
        this.colors.clear()
        this.colors.addAll(colors)

        notifyDataSetChanged()
    }
}