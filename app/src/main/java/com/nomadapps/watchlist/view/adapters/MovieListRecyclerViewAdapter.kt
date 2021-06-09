package com.nomadapps.watchlist.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_row_design.view.*
import kotlin.collections.ArrayList
import com.nomadapps.watchlist.model.Result
import com.nomadapps.watchlist.view.R

class MovieListRecylerViewAdapter(
    private var items: ArrayList<Result>,
    val onClickListener: OnClickListener
) : RecyclerView.Adapter<MovieListRecylerViewAdapter.MyViewHolder>(), Filterable {

    interface OnClickListener {
        fun onItemClick(position: Result)
    }

    var movieList = ArrayList<Result>()

    init {
        movieList = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater =
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row_design, parent, false)
        return MyViewHolder(inflater)
    }

    override fun getItemCount(): Int {
        return movieList.size;
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = movieList[position]
        holder.itemView.nameOfGame.text = currentItem.title
        holder.itemView.rating.text = "Rate - " + currentItem.vote_average
        holder.itemView.date.text = "Released Date - " + currentItem.release_date
        Picasso.get().load("https://image.tmdb.org/t/p/w185"+currentItem.backdrop_path).into(holder.itemView.typeImageView)

        holder.itemView.rowLayout.setOnClickListener {
            onClickListener.onItemClick(currentItem)

        }

    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    movieList = items
                } else {
                    val resultList = ArrayList<Result>()
                    for (row in items) {
                        if (row.title.lowercase().contains(charSearch.lowercase())) {
                            resultList.add(row)
                        }
                    }
                    movieList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = movieList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null) {
                    movieList = results.values as ArrayList<Result>
                    notifyDataSetChanged()
                }

            }

        }
    }
}