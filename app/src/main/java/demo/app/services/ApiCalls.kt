package demo.app.services

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCalls {

    @GET(ApiConstants.API_CALLS_URL + "search.ashx")
    fun getSearchResult(@Query("key") key: String?, @Query("q") name: String?, @Query("format") json: String?,
                        @Query("popular") popular: String?, @Query("num_of_results") results: String?):
            Call<ApiSearchResultResponse?>?

    @GET(ApiConstants.API_CALLS_URL + "weather.ashx")
    fun getData(@Query("key") key: String?, @Query("q") city: String?, @Query("format") json: String?,
                @Query("num_of_days") days: String?, @Query("fx24") hourly: String?, @Query("lang") lang: String?):
            Call<ApiDataResponse?>?

}