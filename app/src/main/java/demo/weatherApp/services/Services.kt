package demo.app.services

import demo.weatherApp.helpers.RealmHelper
import demo.weatherApp.helpers.Utilities
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Services {

    companion object {

        fun searchCities(name: String, callback: Callback<ApiSearchResultResponse>) {

            val call: Call<ApiSearchResultResponse?>? = RestClient.REST_CLIENT?.getSearchResult(ApiConstants.API_KEY, name, ApiConstants.FORMAT,
                ApiConstants.IS_POPULAR, ApiConstants.NUMBER_OF_RESULTS)

            call?.enqueue(object : Callback<ApiSearchResultResponse?> {
                override fun onResponse(
                    call: Call<ApiSearchResultResponse?>,
                    response: Response<ApiSearchResultResponse?>
                ) {
                    callback.onResponse(call, response)
                }

                override fun onFailure(call: Call<ApiSearchResultResponse?>, t: Throwable) {
                    callback.onFailure(call, t)
                }
            })
        }

        fun getData(city: String, isHourly: Boolean, callback: Callback<ApiDataResponse>) {

            val call: Call<ApiDataResponse?>? = RestClient.REST_CLIENT?.getData(ApiConstants.API_KEY, city, ApiConstants.FORMAT,
                ApiConstants.NUMBER_OF_DAYS, Utilities.convertBooleanToYesOrNo(isHourly), Utilities.getSystemLanguageForService())

            call?.enqueue(object : Callback<ApiDataResponse?> {
                override fun onResponse(
                    call: Call<ApiDataResponse?>,
                    response: Response<ApiDataResponse?>
                ) {
                    RealmHelper.saveCityDataToUserModel(response.body()?.data)
                    callback.onResponse(call, response)
                }

                override fun onFailure(call: Call<ApiDataResponse?>, t: Throwable) {
                    callback.onFailure(call, t)
                }
            })
        }
    }
}