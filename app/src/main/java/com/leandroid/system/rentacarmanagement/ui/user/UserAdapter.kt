package com.leandroid.system.rentacarmanagement.ui.user

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.leandroid.system.rentacarmanagement.R
import com.leandroid.system.rentacarmanagement.databinding.ItemCarBinding
import com.leandroid.system.rentacarmanagement.model.Car
import com.leandroid.system.rentacarmanagement.model.User
import com.leandroid.system.rentacarmanagement.ui.utils.RecyclerListener

class UserAdapter(private val listener: RecyclerListener) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var originUsers: MutableList<User> = mutableListOf()
    private var users: MutableList<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return UserViewHolder(layoutInflater.inflate(R.layout.item_user, parent, false), listener)
    }

    override fun onBindViewHolder(holderCar: UserViewHolder, position: Int) {
        val item = users[position]
        holderCar.bind(item)
    }

    fun getItemByPosition(position: Int) = users[position]

    fun filterByBrand(text: String) {
        users = if (text.isEmpty())
            originUsers
        else
            originUsers.filter { it.userName.contains(text) }.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = users.size

    fun setUsers(users: MutableList<User>) {
        this.originUsers = users
        this.users = users
        notifyDataSetChanged()
    }

    inner class UserViewHolder(view: View, private val listener: RecyclerListener) :
        RecyclerView.ViewHolder(view), PopupMenu.OnMenuItemClickListener {
        private val binding = ItemCarBinding.bind(view)

        fun bind(user: User) {
            with(binding) {
                root.setOnClickListener {
                    showPopupMenu(it)
                }

                tvDetails.text = user.userName
                //tvComment.text = user.comment
            }
//        Picasso.get().load(movie.urlImage).into(binding.movieImage)
        }

        private fun showPopupMenu(view: View) {
            PopupMenu(binding.root.context, view).apply {
                inflate(R.menu.menu_popup)
                setOnMenuItemClickListener(this@UserViewHolder)
                show()
            }
        }

        override fun onMenuItemClick(item: MenuItem?): Boolean {
            return when (item?.itemId) {
                R.id.action_popup_show -> {
                    listener.onClick(getItemByPosition(absoluteAdapterPosition).id)
                    true
                }
                R.id.action_popup_edit -> {
                    listener.onMenuClickEdit(getItemByPosition(absoluteAdapterPosition).id)
                    true
                }
                R.id.action_popup_delete -> {
                    listener.onMenuClickDelete(getItemByPosition(absoluteAdapterPosition).id)
                    true
                }
                else -> false
            }
        }
    }
}