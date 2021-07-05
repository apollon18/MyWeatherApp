package demo.app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import demo.app.R
import demo.app.databinding.ListItemHourlyDetailsBinding
import demo.app.helpers.Utilities
import demo.app.models.Hourly
import io.realm.RealmList


class CityHourlyRecyclerAdapter(private val mContext: Context,
                                private var hours: ArrayList<Hourly>, ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewBinding = ListItemHourlyDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return hours.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        val hour = hours[position]
        setupMainUi(viewHolder, hour)
    }
    private fun setupMainUi(viewHolder: ViewHolder, hour: Hourly?) {
        hour?.time?.let { viewHolder.hour.text = Utilities.transformTimeTo24ClockTime(it) }
        hour?.tempC?.let { viewHolder.temp.text = mContext.getString(R.string.celsius_text, it) }
        hour?.weatherIconUrl?.get(0)?.value?.let { setIcon(viewHolder.weatherIcon, it) }
    }

    private fun setIcon(view: ImageView, iconUrl: String) {
        Glide.with(mContext).load(iconUrl).centerCrop().placeholder(R.drawable.error_image).error(R.drawable.error_image).into(view)
    }

    inner class ViewHolder(binding: ListItemHourlyDetailsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var hour: TextView = binding.hourText
        var temp: TextView = binding.tempText
        var weatherIcon: ImageView = binding.weatherIcon
    }
}