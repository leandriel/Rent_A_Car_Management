package com.leandroid.system.rentacarmanagement.ui.car

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.leandroid.system.rentacarmanagement.databinding.ItemBrandBinding
import com.leandroid.system.rentacarmanagement.model.Brand

class BrandAdapter(
    context: Context,
    resource: Int,
) : ArrayAdapter<Brand>(context, resource, mutableListOf()) {

    private val brands = mutableListOf<Brand>()

    override fun getItem(position: Int): Brand? {
        return brands[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return brands.size
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemBrandBinding.inflate(inflater, parent, false)
        val country = getBrandForPosition(position)
        bind(binding, country)
        return binding.root
    }

    fun getBrandForPosition(position: Int): Brand {
        return brands[position]
    }

    private fun bind(binding: ItemBrandBinding, brand: Brand) {
        binding.tvBrand.text = brand.name
    }

    fun setBrands(brands: MutableList<Brand>) {
        this.brands.clear()
        this.brands.addAll(brands)

        notifyDataSetChanged()
    }
}
