package demo.app.activities

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import demo.app.R
import demo.app.adapters.CityDailyRecyclerAdapter
import demo.app.adapters.CityHourlyRecyclerAdapter
import demo.app.databinding.ActivityCityDetailsBinding
import demo.app.helpers.RealmHelper
import demo.app.models.CityModel
import demo.app.models.Hourly
import io.realm.RealmList

class CityDetailsActivity: AppCompatActivity() {

    private var viewBinding: ActivityCityDetailsBinding? = null
    private var city: CityModel? = null
    private var dailyRecyclerAdapter: CityDailyRecyclerAdapter? = null
    private var hourlyRecyclerAdapter: CityHourlyRecyclerAdapter? = null
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var horizontalLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityCityDetailsBinding.inflate(layoutInflater)
        setContentView(viewBinding?.root)

        city = getCityModel()
        if (city == null) {
            showErrorAlert()
        }

        initDetails()
        initRecyclers()
    }

    private fun getCityModel(): CityModel? {
        val cityName = intent.extras?.getString("cityName")
        val cityCountry = intent.extras?.getString("cityCountry")
        if (cityCountry != null && cityName != null) {
            return RealmHelper.getCityFromDatabase(cityName = cityName, cityCountry = cityCountry)
        }
        return null
    }

    private fun initDetails() {
        viewBinding?.closeButton?.setOnClickListener { finish() }
        viewBinding?.cityName?.text = city?.name
        viewBinding?.cityCountry?.text = city?.country
        viewBinding?.description?.text = city?.currentCondition?.weatherDesc?.get(0)?.value
        setIcon(city?.currentCondition?.weatherIconUrl?.get(0)?.value)
    }

    private fun setIcon(value: String?) {
        viewBinding?.weatherIcon?.let {
            Glide.with(this).load(value).centerCrop().placeholder(R.drawable.error_image).error(R.drawable.error_image).into(it)
        }
    }

    private fun initRecyclers() {
        initDailyRecycler()
        initHourlyRecycler()
    }

    private fun initHourlyRecycler() {
        horizontalLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val filterHours = city?.weather?.get(0)?.hourly?.filter { it.time != "24" }
        viewBinding?.hourlyList?.layoutManager = horizontalLayoutManager
        hourlyRecyclerAdapter = city?.weather?.get(0)?.hourly?.let {
            CityHourlyRecyclerAdapter(
                mContext = this@CityDetailsActivity,
                hours = filterHours as ArrayList<Hourly>
            )
        }
        viewBinding?.hourlyList?.adapter = hourlyRecyclerAdapter
    }

    private fun initDailyRecycler() {
        linearLayoutManager = LinearLayoutManager(this)
        viewBinding?.dailyList?.layoutManager = linearLayoutManager
        dailyRecyclerAdapter = city?.weather?.let {
            CityDailyRecyclerAdapter(
                mContext = this@CityDetailsActivity,
                days = it)
        }
        viewBinding?.dailyList?.adapter = dailyRecyclerAdapter
    }

    private fun showErrorAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.app_name))
        builder.setMessage(resources.getString(R.string.error_dialog_text))
        builder.setCancelable(false)
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { _, _ ->
            finish()
        })
        builder.show()
    }
}