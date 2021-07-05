package demo.app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import demo.app.databinding.ListItemMainListBinding
import demo.app.listeners.SearchResultClickListener
import demo.app.models.Result

class SearchResultsRecyclerAdapter(private var results: ArrayList<Result>?,
                                   private val listener: SearchResultClickListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewBinding = ListItemMainListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        if (results == null) {
            return 0
        }
        return results?.size!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        val city = results?.get(position)
        setupUi(viewHolder, city)
        setupListener(viewHolder, city)
    }

    private fun setupUi(viewHolder: ViewHolder, city: Result?) {
        city?.areaName?.get(0)?.value?.let { viewHolder.cityName.text = it }
        city?.country?.get(0)?.value?.let { viewHolder.cityCountry.text = it }
        viewHolder.infoLayout.visibility = View.GONE
    }

    private fun setupListener(viewHolder: ViewHolder, city: Result?) {
        viewHolder.cityItem.setOnClickListener {
            if (city != null) {
                listener.onResultClicked(city)
            }
        }
    }

    inner class ViewHolder(binding: ListItemMainListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var cityItem: RelativeLayout = binding.cityItem
        var cityName: TextView = binding.cityNameText
        var cityCountry: TextView = binding.cityCountryText
        var infoLayout: RelativeLayout = binding.infoLayout
        var temperature: TextView = binding.tempText
        var weatherIcon: ImageView = binding.weatherIcon
    }

    fun updateList(newList: ArrayList<Result>?) {
        results?.clear()
        newList?.let { results?.addAll(it) }
        this.notifyDataSetChanged()
    }
}