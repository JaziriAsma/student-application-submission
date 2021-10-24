package com.android.internal.test.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.android.internal.test.*
import com.android.internal.test.model.Category
import kotlinx.android.synthetic.main.row_layout.view.*
import kotlinx.android.synthetic.main.row_layout.view.description
import kotlinx.android.synthetic.main.row_layout.view.displayName
import kotlinx.android.synthetic.main.row_layout.view.id_
import kotlinx.android.synthetic.main.row_layout.view.name
import kotlinx.android.synthetic.main.row_process.view.*

class MyAdapter(private val onProcessClick: CategoryActivity)  :RecyclerView.Adapter<MyAdapter.MyViewHolder>(){
    private var myList = emptyList<Category>()


    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    //Méthode de création des cardViews
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false))
    }

    //Méthode qui retourne la taille de la liste
    override fun getItemCount(): Int {
        return myList.size
    }

    //Méthode qui fait le lien entre la liste et les cardviews
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.itemView.createdBy.text=myList[position].createdBy
        holder.itemView.displayName.text = myList[position].displayName
        holder.itemView.name.text = myList[position].name
        holder.itemView.description.text = myList[position].description
        holder.itemView.creation_date.text = myList[position].creation_date
        holder.itemView.id_.text = myList[position].id
        holder.itemView.setOnClickListener {

            //Quand on clique sur une cardview, on sera redirigé vers la ProcessActivity
            val intent= Intent(holder.itemView.context, ProcessActivity::class.java)
            holder.itemView.context.startActivity(intent)




        }

    }

    fun setData(newList:List<Category>){
        myList = newList
        notifyDataSetChanged()
    }


}