package com.android.internal.test.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.internal.test.DemandeActivity
import com.android.internal.test.ProcessActivity
import com.android.internal.test.R
import com.android.internal.test.model.Process
import kotlinx.android.synthetic.main.row_process.view.*


class ProcessAdapter(private val onProcessClick: ProcessActivity) : RecyclerView.Adapter<ProcessAdapter.MyViewHolder>(){

    private var myList = emptyList<Process>()


    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    //Méthode de création des cardViews
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_process,parent,false))
    }
    //Méthode qui retourne la taille de la liste
    override fun getItemCount(): Int {
        return myList.size
    }

    //Méthode qui fait le lien entre la liste et les cardviews
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.itemView.displayDescription.text=myList[position].displayDescription
        holder.itemView.deploymentDate.text=myList[position].deploymentDate
        holder.itemView.displayName.text=myList[position].displayName
        holder.itemView.name.text = myList[position].name
        holder.itemView.description.text = myList[position].description
        holder.itemView.deployedBy.text=myList[position].deployedBy
        holder.itemView.id_.text = myList[position].id
        holder.itemView.activationState.text = myList[position].activationState
        holder.itemView.version.text = myList[position].version
        holder.itemView.configurationState.text = myList[position].configurationState
        holder.itemView.last_update_date.text = myList[position].last_update_date
        holder.itemView.actorinitiatorid.text = myList[position].actorinitiatorid

        //Quand on clique sur une cardview, on sera redirigé vers la DemandeActivity
        holder.itemView.setOnClickListener {
            val intent= Intent(holder.itemView.context, DemandeActivity::class.java)
            intent.putExtra("idProc",holder.itemView.id_.text.toString())
            holder.itemView.context.startActivity(intent)




        }


    }

    //Méthode qui sera appelé à chaque fois que les données changent
    fun setData(newList: List<Process>){
        myList = newList
        notifyDataSetChanged()
    }


}