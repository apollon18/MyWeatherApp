package demo.app.services

import com.google.gson.annotations.SerializedName
import demo.weatherApp.models.DataModel

class ApiDataResponse {

    @SerializedName("data")
    var data: DataModel? = null
}