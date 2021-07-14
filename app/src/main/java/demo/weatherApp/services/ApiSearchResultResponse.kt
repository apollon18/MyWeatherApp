package demo.app.services

import com.google.gson.annotations.SerializedName
import demo.weatherApp.models.SearchResultModel

class ApiSearchResultResponse {

    @SerializedName("search_api")
    var data: SearchResultModel? = null
}