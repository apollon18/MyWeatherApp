package demo.app.models

import demo.app.helpers.Utilities
import io.realm.RealmList
import io.realm.RealmObject


open class CityModel(
    var name: String? = null,
    var country: String? = null,
    var currentCondition: CurrentCondition? = null,
    var weather: RealmList<Weather>? = null
) : RealmObject() {

    constructor(cityData: DataModel): this() {
        this.name = cityData.request?.get(0)?.query?.let { Utilities.getCityName(it) }
        this.country = cityData.request?.get(0)?.query?.let { Utilities.getCityCountry(it) }
        this.currentCondition = cityData.current_condition?.get(0)
        this.weather = cityData.weather
    }
}