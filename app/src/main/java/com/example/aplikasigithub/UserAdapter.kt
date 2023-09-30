package com.example.aplikasigithub


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.aplikasigithub.Model.ItemsItem
import com.example.aplikasigithub.databinding.ItemUserBinding
import java.lang.StringBuilder

class UserAdapter (private val data :MutableList<ItemsItem> = mutableListOf(),
                   private  val listener: (ItemsItem) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    fun setData (data: MutableList<ItemsItem>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class UserViewHolder(private val v : ItemUserBinding) : RecyclerView.ViewHolder(v.root) {
        fun bind(item: ItemsItem) {
            v.image.load(item.avatarUrl) {}
            v.username.text = item.login
            v.iduser.text = item.id?.toString() ?: "------"


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener(item)
        }
    }

}