package demo.weatherApp.models


import demo.app.services.ApiConstants
import io.realm.RealmList
import io.realm.RealmObject

open class UserModel(
    var id: String = ApiConstants.USER_DB_ID,
    var cities : RealmList<CityModel> = RealmList<CityModel>()
) : RealmObject()




