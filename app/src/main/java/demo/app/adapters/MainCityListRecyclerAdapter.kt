package demo.app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import demo.app.R
import demo.app.databinding.ListItemMainListBinding
import demo.app.listeners.MainListCityListener
import demo.app.models.CityModel


class MainCityListRecyclerAdapter(private val mContext: Context,
                                  private var cities: ArrayList<CityModel>,
                                  private val listener: MainListCityListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewBinding = ListItemMainListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder
        val city = cities[position]
        setupMainUi(viewHolder, city)
        setupMainListener(viewHolder, city)
    }
    private fun setupMainUi(viewHolder: ViewHolder, city: CityModel) {
        city.name?.let { viewHolder.cityName.text = it }
        city.country?.let { viewHolder.cityCountry.text = it }
        city.currentCondition?.temp_C.let {
            viewHolder.temperature.text = mContext.getString(R.string.celsius_text, it)
        }
        city.currentCondition?.weatherIconUrl.let {
            if (it != null) {
                it[0]?.value?.let { l -> setIcon(viewHolder.weatherIcon, l) }
            }
        }
    }

    private fun setIcon(view: ImageView, iconUrl: String) {
        Glide.with(mContext).load(iconUrl).centerCrop().placeholder(R.drawable.error_image).error(R.drawable.error_image).into(view)
    }

    private fun setupMainListener(viewHolder: ViewHolder, city: CityModel) {
        viewHolder.cityItem.setOnClickListener { listener.onCityDetailsClicked(city) }
        viewHolder.cityItem.setOnLongClickListener {
            listener.onCityDetailsLongClicked(city)
            return@setOnLongClickListener true
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
        var selectedIcon: ImageView = binding.selectedIcon
    }

    fun updateList(newList: ArrayList<CityModel>) {
        cities.clear()
        cities.addAll(newList)
        this.notifyDataSetChanged()
    }
}