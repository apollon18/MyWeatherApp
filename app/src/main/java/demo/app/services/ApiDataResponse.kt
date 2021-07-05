package demo.app.services

import com.google.gson.annotations.SerializedName
import demo.app.models.DataModel

class ApiDataResponse {

    @SerializedName("data")
    var data: DataModel? = null
}