package demo.app.listeners

import demo.app.models.CityModel

interface MainListCityListener {

    fun onCityDetailsClicked(cityModel: CityModel)

    fun onCityDetailsLongClicked(cityModel: CityModel)
}