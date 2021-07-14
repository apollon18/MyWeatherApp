package demo.weatherApp.helpers

import android.content.Context
import demo.weatherApp.models.CityModel
import demo.weatherApp.models.DataModel
import demo.weatherApp.models.UserModel
import demo.app.services.ApiConstants
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList
import io.realm.kotlin.where

class RealmHelper {

    companion object {

        fun initRealmDatabase(context: Context) {
            Realm.init(context)
            val realmConfiguration : RealmConfiguration = RealmConfiguration.Builder().allowWritesOnUiThread(true).build()
            Realm.setDefaultConfiguration(realmConfiguration)
        }

        fun getUserModelFromDatabase(): UserModel? {
            var realm: Realm? = null
            var userModel : UserModel? = null
            try {
                realm = Realm.getDefaultInstance()
                if (realm != null) {
                    userModel = realm.where<UserModel>().equalTo("id", ApiConstants.USER_DB_ID).findFirst()
                    userModel?.let {
                        return realm.copyFromRealm(userModel)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                realm?.close()
            }
            return userModel
        }

        fun getUserCitiesFromDatabase(): ArrayList<CityModel> {
            var realm: Realm? = null
            var cities = ArrayList<CityModel>()
            var userModel : UserModel? = null
            try {
                realm = Realm.getDefaultInstance()
                if (realm != null) {
                    userModel = realm.where<UserModel>().equalTo("id", ApiConstants.USER_DB_ID).findFirst()
                    userModel?.let {
                       if (userModel.cities.isNotEmpty()) {
                           cities = realm.copyFromRealm(userModel.cities) as ArrayList<CityModel>
                       }
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                realm?.close()
            }
            return cities
        }

        fun saveCityDataToUserModel(cityData: DataModel?) {
            var realm: Realm? = null
            val cityToSave = cityData?.let { CityModel(it) }
            var userModel : UserModel? = null
            try {
                realm = Realm.getDefaultInstance()
                if (realm != null) {
                    userModel = realm.where<UserModel>().equalTo("id", ApiConstants.USER_DB_ID).findFirst()
                    if (userModel == null) {
                        userModel = UserModel(ApiConstants.USER_DB_ID, RealmList<CityModel>())
                    }
                    realm.executeTransaction {
                        userModel.cities.add(cityToSave)
                        it.insertOrUpdate(userModel)
                    }
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            } finally {
                realm?.close()
            }
        }

        fun getCityFromDatabase(cityName: String, cityCountry: String): CityModel? {
            var realm: Realm? = null
            var cityModel : CityModel? = null
            try {
                realm = Realm.getDefaultInstance()
                if (realm != null) {
                    cityModel = realm.where<CityModel>().equalTo("name", cityName).and().equalTo("country", cityCountry).findFirst()
                    cityModel?.let {
                        return realm.copyFromRealm(cityModel)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                realm?.close()
            }
            return cityModel
        }

        fun deleteOldCitiesFromDatabase() {
            var realm: Realm? = null
            var userModel : UserModel? = null
            try {
                realm = Realm.getDefaultInstance()
                if (realm != null) {
                    userModel = realm.where<UserModel>().equalTo("id", ApiConstants.USER_DB_ID).findFirst()
                    if (userModel != null) {
                        realm.executeTransaction {
                            userModel.cities.clear()
                            it.insertOrUpdate(userModel)
                        }
                    }
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            } finally {
                realm?.close()
            }
        }

        fun deleteCityFromDatabase(cityModel: CityModel) {
            var realm: Realm? = null
            var temp : CityModel? = null
            var userModel : UserModel? = null
            try {
                realm = Realm.getDefaultInstance()
                if (realm != null) {
                    temp = realm.where<CityModel>().equalTo("name", cityModel?.name).and().equalTo("country", cityModel?.country).findFirst()
                    userModel = realm.where<UserModel>().equalTo("id", ApiConstants.USER_DB_ID).findFirst()
                    if (temp != null && userModel != null) {
                        realm.executeTransaction {
                            for (city in userModel.cities) {
                                if (temp?.name == city.name && temp?.country == city.country) {
                                    temp = city
                                    break
                                }
                            }
                            userModel.cities.remove(temp)
                            it.insertOrUpdate(userModel)
                        }
                    }
                }
        } catch (e: Exception) {
                e.printStackTrace()
            }  finally {
                realm?.close()
            }
        }
    }
}