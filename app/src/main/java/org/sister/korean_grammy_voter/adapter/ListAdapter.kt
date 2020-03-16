package org.sister.korean_grammy_voter.adapter

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import org.sister.korean_grammy_voter.R
import org.sister.korean_grammy_voter.data.NominationsItem
import java.lang.Exception

class ListAdapter(private val list : List<NominationsItem>,
                  private val idNominations : Int?,
                  private val voteAdapter : ClickAdapter)
    :RecyclerView.Adapter<ListAdapter.ListViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ListViewHolder(inflater,parent)

    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item : NominationsItem = list[position]
        holder.bind(item,idNominations,voteAdapter)
    }

    override fun getItemCount(): Int = list.size

    class ListViewHolder(inflater : LayoutInflater, parent: ViewGroup):
            RecyclerView.ViewHolder(inflater.inflate(R.layout.item_list,parent,false)){
        private var clBackground : ConstraintLayout? = null
        private var tvNominators : TextView? = null
        private var tvPercentage : TextView? = null
        private var btVote : Button? = null
        init {
            clBackground = itemView.findViewById(R.id.cl_background)
            tvNominators = itemView.findViewById(R.id.tv_nominators)
            tvPercentage = itemView.findViewById(R.id.tv_percentage)
            btVote = itemView.findViewById(R.id.bt_vote)
        }
        fun bind(item : NominationsItem,idNominations: Int?,
                 voteAdapter: ClickAdapter){
            Picasso.get().load(item.url).into(object : Target{
                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                    Log.i("LOG","On Prepare")
                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                    Log.e("LOG",e?.message)
                }

                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    clBackground?.background = BitmapDrawable(bitmap)
                }
            })
            tvNominators?.text = item.singer+" \n "+item.name
            tvPercentage?.text = item.percentage
            Log.e("TAG",item.percentage)
            btVote?.setOnClickListener {
                voteAdapter.vote(idNominations,item.id)
            }
        }
    }
}