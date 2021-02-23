package com.example.adminpanel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.adminpanel.api.model.Customer
import com.example.adminpanel.databinding.CustomerListItemBinding

class CustomerAdapter(private var onDeleteClickListener : OnDeleteClickListener) :
    ListAdapter<Customer, CustomerAdapter.CustomerViewHolder>(CustomerCallback()) {
    class CustomerViewHolder private constructor(private val binding: CustomerListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindCustomer(customer: Customer, onDelete: OnDeleteClickListener) {
            binding.customer = customer
            binding.onDeleteClickListener = onDelete
        }

        companion object {
            fun from(parent: ViewGroup): CustomerViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CustomerListItemBinding.inflate(layoutInflater, parent, false)
                return CustomerViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CustomerViewHolder.from(parent)

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val customer = getItem(position)
        holder.bindCustomer(customer, onDeleteClickListener)
    }
}

class CustomerCallback : DiffUtil.ItemCallback<Customer>() {
    override fun areItemsTheSame(oldItem: Customer, newItem: Customer) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Customer, newItem: Customer) =
        oldItem == newItem
}

interface OnDeleteClickListener {
    fun onClick(customer : Customer)
}