package com.example.codingchallange.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.codingchallange.BR
import com.example.codingchallange.R
import com.example.codingchallange.databinding.ItemRowBinding
import com.example.codingchallange.models.Lf

class AcromineViewAdapter(dataModelList: List<Lf>, private val onClick: ((Lf) -> Unit)? = null) :
    RecyclerView.Adapter<AcromineViewAdapter.ViewHolder>(), CustomClickListener {
    private val dataModelList: List<Lf>

    init {
        this.dataModelList = dataModelList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): ViewHolder {
        val binding: ItemRowBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_row, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel: Lf = dataModelList[position]
        holder.bind(dataModel)
        holder.itemRowBinding.itemClickListener = this
    }

    override fun getItemCount(): Int {
        return dataModelList.size
    }

    inner class ViewHolder(itemRowBinding: ItemRowBinding) :
        RecyclerView.ViewHolder(itemRowBinding.root) {
        var itemRowBinding: ItemRowBinding

        init {
            this.itemRowBinding = itemRowBinding
        }

        fun bind(obj: Any?) {
            itemRowBinding.setVariable(BR.model, obj)
            itemRowBinding.executePendingBindings()
        }
    }

    override fun cardClicked(f: Lf) {
        onClick?.invoke(f)
    }
}

interface CustomClickListener {
    fun cardClicked(f: Lf)
}