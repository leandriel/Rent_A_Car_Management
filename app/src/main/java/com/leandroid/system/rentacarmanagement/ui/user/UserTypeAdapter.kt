package com.leandroid.system.rentacarmanagement.ui.user

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.leandroid.system.rentacarmanagement.databinding.ItemTypeBinding
import com.leandroid.system.rentacarmanagement.model.UserType

class UserTypeAdapter(context: Context,
                      resource: Int,
) : ArrayAdapter<UserType>(context, resource, mutableListOf()) {

    private val types = mutableListOf<UserType>()

    override fun getItem(position: Int): UserType? {
        return types[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return types.size
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemTypeBinding.inflate(inflater, parent, false)
        val country = getUserTypeForPosition(position)
        bind(binding, country)
        return binding.root
    }

    fun getUserTypeForPosition(position: Int): UserType {
        return types[position]
    }

    fun getPositionByUserType(userType: UserType): Int {
        return types.indexOf(userType)
    }

    private fun bind(binding: ItemTypeBinding, userType: UserType) {
        binding.tvColor.text = userType.name
    }

    fun setUserTypes(types: MutableList<UserType>) {
        this.types.clear()
        this.types.addAll(types)

        notifyDataSetChanged()
    }
}