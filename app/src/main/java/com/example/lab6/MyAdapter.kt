package com.example.lab6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(var listItem: List<ListItem>): RecyclerView.Adapter<MyAdapter.MyHolder>() {

    var checkedItems = ArrayList<Int>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, null)
        return MyHolder(v)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val items = listItem[position]
        holder.tvTitle.text = items.titleText
        holder.tvContent.text = items.contentText
        holder.tvCheck.isChecked = items.checked
        holder.tvImage.setImageResource(items.image_id)

        holder.setItemClickListener(object: MyHolder.ItemCLickListener {
            override fun onItemClick(v: View, pos: Int) {
                val tvCheck = v as CheckBox
                val currentPosition = listItem[pos]
                if (tvCheck.isChecked) {
                    currentPosition.checked = true
                    checkedItems.add(currentPosition.id)
                } else if(!tvCheck.isChecked) {
                    currentPosition.checked = false
                    checkedItems.remove(currentPosition.id)
                }
            }
        })
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    class MyHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        var tvContent: TextView = itemView.findViewById(R.id.tvContent)
        var tvImage: ImageView = itemView.findViewById(R.id.tvImage)
        var tvCheck: CheckBox = itemView.findViewById(R.id.tvCheck)

        private lateinit var myItemClickListener: ItemCLickListener
        init { tvCheck.setOnClickListener(this) }

        fun setItemClickListener(ic: ItemCLickListener) {
            this.myItemClickListener = ic
        }

        override fun onClick(v: View?) {
            if (v != null) {
                this.myItemClickListener.onItemClick(v, layoutPosition)
            }
        }

        interface ItemCLickListener {
            fun onItemClick(v: View, pos: Int)
        }

    }
}