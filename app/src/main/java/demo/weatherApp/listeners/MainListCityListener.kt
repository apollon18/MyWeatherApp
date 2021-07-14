package demo.weatherApp.listeners

import demo.weatherApp.models.CityModel

interface MainListCityListener {

    fun onCityDetailsClicked(cityModel: CityModel)

    fun onCityDetailsLongClicked(cityModel: CityModel)
}