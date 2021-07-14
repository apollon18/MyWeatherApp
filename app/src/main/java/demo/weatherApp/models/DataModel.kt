package demo.weatherApp.models

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmObject


open class DataModel : RealmObject() {
    var request: RealmList<Request>? = null
    var current_condition: RealmList<CurrentCondition>? = null
    var weather: RealmList<Weather>? = null
}


open class Request : RealmObject() {
    var type: String? = null
    var query: String? = null
}

open class WeatherIconUrl : RealmObject() {
    var value: String? = null
}

open class WeatherDesc : RealmObject() {
    var value: String? = null
}

open class LangEl : RealmObject() {
    var value: String? = null
}

open class CurrentCondition : RealmObject() {
    var observation_time: String? = null
    var temp_C: String? = null
    var temp_F: String? = null
    var weatherCode: String? = null
    var weatherIconUrl: RealmList<WeatherIconUrl>? = null
    var weatherDesc: RealmList<WeatherDesc>? = null
    var lang_el: RealmList<LangEl>? = null
    var windspeedMiles: String? = null
    var windspeedKmph: String? = null
    var winddirDegree: String? = null
    var winddir16Point: String? = null
    var precipMM: String? = null
    var precipInches: String? = null
    var humidity: String? = null
    var visibility: String? = null
    var visibilityMiles: String? = null
    var pressure: String? = null
    var pressureInches: String? = null
    var cloudcover: String? = null
    var uvIndex: String? = null

    @SerializedName("FeelsLikeC")
    var feelsLikeC: String? = null

    @SerializedName("FeelsLikeF")
    var feelsLikeF: String? = null
}

open class Hourly : RealmObject() {
    var time: String? = null
    var tempC: String? = null
    var tempF: String? = null
    var windspeedMiles: String? = null
    var windspeedKmph: String? = null
    var winddirDegree: String? = null
    var winddir16Point: String? = null
    var weatherCode: String? = null
    var weatherIconUrl: RealmList<WeatherIconUrl>? = null
    var weatherDesc: RealmList<WeatherDesc>? = null
    var lang_el: RealmList<LangEl>? = null
    var precipMM: String? = null
    var precipInches: String? = null
    var humidity: String? = null
    var visibility: String? = null
    var visibilityMiles: String? = null
    var pressure: String? = null
    var pressureInches: String? = null
    var cloudcover: String? = null
    var chanceofrain: String? = null
    var chanceofremdry: String? = null
    var chanceofwindy: String? = null
    var chanceofovercast: String? = null
    var chanceofsunshine: String? = null
    var chanceoffrost: String? = null
    var chanceofhightemp: String? = null
    var chanceoffog: String? = null
    var chanceofsnow: String? = null
    var chanceofthunder: String? = null
    var uvIndex: String? = null

    @SerializedName("HeatIndexC")
    var heatIndexC: String? = null

    @SerializedName("HeatIndexF")
    var heatIndexF: String? = null

    @SerializedName("DewPointC")
    var dewPointC: String? = null

    @SerializedName("DewPointF")
    var dewPointF: String? = null

    @SerializedName("WindChillC")
    var windChillC: String? = null

    @SerializedName("WindChillF")
    var windChillF: String? = null

    @SerializedName("WindGustMiles")
    var windGustMiles: String? = null

    @SerializedName("WindGustKmph")
    var windGustKmph: String? = null

    @SerializedName("FeelsLikeC")
    var feelsLikeC: String? = null

    @SerializedName("FeelsLikeF")
    var feelsLikeF: String? = null
}

open class Weather : RealmObject() {
    var date: String? = null
    var maxtempC: String? = null
    var maxtempF: String? = null
    var mintempC: String? = null
    var mintempF: String? = null
    var avgtempC: String? = null
    var avgtempF: String? = null
    var totalSnow_cm: String? = null
    var sunHour: String? = null
    var uvIndex: String? = null
    var hourly: RealmList<Hourly>? = null
}