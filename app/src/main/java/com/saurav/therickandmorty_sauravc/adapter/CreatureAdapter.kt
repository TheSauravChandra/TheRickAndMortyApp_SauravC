package com.saurav.therickandmorty_sauravc.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saurav.therickandmorty_sauravc.R
import com.saurav.therickandmorty_sauravc.beans.Creature
import com.saurav.therickandmorty_sauravc.databinding.CreatureMiniCardBinding

class CreatureAdapter(private val context: Context) : RecyclerView.Adapter<CreatureAdapter.ViewHolder>() {
    private var list = ArrayList<Creature>()

    // for shared animation we need views too, & next page needs data to show.
    private var callBack: ((item: Creature?, card: CreatureMiniCardBinding) -> Unit)? = null

    fun attachCallback(callBack: (item: Creature?, card: CreatureMiniCardBinding) -> Unit) {
        this.callBack = callBack
    }

    fun getList() = list

    fun updateList(items: ArrayList<Creature>) {
        // diff util delegated
        val old = list
        // old & new lists taken, magic done & minimal steps poured to update UI.
        val diffRes: DiffUtil.DiffResult = DiffUtil.calculateDiff(CreatureDiffUtil(old, items))
        list = items
        diffRes.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(var binding: CreatureMiniCardBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setData(data: Creature?) {
            data?.run {
                binding.name = name ?: ""
                image?.let {
                    Glide.with(context)
                        .load(it)
                        .into(binding.ivPic)
                } ?: run {
                    binding.ivPic.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_android_black_24dp))
                }
            }

            binding.root.setOnClickListener {
                callBack?.let { it(data, binding) }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CreatureMiniCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(list[position])
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = list.size

    class CreatureDiffUtil(private val oldList: ArrayList<Creature>, private val newList: ArrayList<Creature>) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

    }

}