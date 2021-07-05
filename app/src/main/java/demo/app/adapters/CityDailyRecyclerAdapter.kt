package demo.app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import demo.app.R
import demo.app.databinding.ListItemDailyDetailsBinding
import demo.app.helpers.Utilities
import demo.app.models.Weather
import io.realm.RealmList


class CityDailyRecyclerAdapter(private val mContext: Context,
                               private var days: RealmList<Weather>, ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewBinding = ListItemDailyDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return days.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        val day = days[position]
        setupMainUi(viewHolder, day)
    }
    private fun setupMainUi(viewHolder: ViewHolder, day: Weather?) {
        day?.date?.let { viewHolder.dayName.text = Utilities.transformDateToDay(it) }
        day?.maxtempC?.let { viewHolder.maxTemp.text = mContext.getString(R.string.celsius_text, it) }
        day?.mintempC?.let { viewHolder.minTemp.text = mContext.getString(R.string.celsius_text, it) }
    }

    inner class ViewHolder(binding: ListItemDailyDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var dayName: TextView = binding.dayText
        var maxTemp: TextView = binding.maxTemp
        var minTemp: TextView = binding.minTemp
    }
}