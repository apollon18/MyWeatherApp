package demo.weatherApp.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import demo.app.R
import demo.app.databinding.ActivitySplashScreenBinding
import demo.app.services.ApiDataResponse
import demo.app.services.Services
import demo.weatherApp.helpers.RealmHelper
import demo.weatherApp.helpers.Utilities
import demo.weatherApp.models.CityModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SplashScreenActivity: AppCompatActivity() {

    private var viewBinding: ActivitySplashScreenBinding? = null
    private var textAnimation: Animation? = null
    private var cityNum: Int? = null
    private var callCounter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(viewBinding?.root)

        initTextAnimation()
        Handler().postDelayed({
            checkForCityData()
        }, 2000)
    }

    private fun initTextAnimation() {
        textAnimation = AnimationUtils.loadAnimation(this@SplashScreenActivity, R.anim.animation_blinking_text)
        viewBinding?.splashLogo?.startAnimation(textAnimation)
    }

    private fun checkForCityData() {
        if (Utilities.userHasFavoriteCity()) {
            updateCityData()
        } else {
            redirectToMainList()
        }
    }

    private fun updateCityData() {
        val userCities = RealmHelper.getUserCitiesFromDatabase()
        if (userCities.isEmpty()) {
            redirectToMainList()
        } else {
            RealmHelper.deleteOldCitiesFromDatabase()
            cityNum = userCities.size
            for (city in userCities) {
                getCityData(city)
            }
        }
    }

    private fun getCityData(city: CityModel) {
        city.name?.let {
            Services.getData(city = it, isHourly = true, object : Callback<ApiDataResponse> {
                override fun onResponse(
                    call: Call<ApiDataResponse>,
                    response: Response<ApiDataResponse>
                ) {
                    callCounter += 1
                    checkForRedirection()
                }

                override fun onFailure(call: Call<ApiDataResponse>, t: Throwable) {
                    callCounter += 1
                    checkForRedirection()
                }
            })
        }
    }

    private fun checkForRedirection() {
        if (callCounter == cityNum) {
            redirectToMainList()
        }
    }

    private fun redirectToMainList() {
        val mainListIntent = Intent(this@SplashScreenActivity, MainListActivity::class.java)
        startActivity(mainListIntent)
        finish()
    }
}