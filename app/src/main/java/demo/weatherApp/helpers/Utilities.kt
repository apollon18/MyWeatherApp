package demo.weatherApp.helpers

import java.text.SimpleDateFormat
import java.util.*

class Utilities {

    companion object {

        fun convertBooleanToYesOrNo(value: Boolean): String {
            return if (value) {
                "yes"
            } else {
                "no"
            }
        }

        fun getSystemLanguageForService(): String {
            return if (Locale.getDefault().displayLanguage == "el") {
                "el"
            } else {
                "en"
            }
        }

        fun userHasFavoriteCity(): Boolean {
            val userModel = RealmHelper.getUserModelFromDatabase()
            return userModel?.cities?.isNotEmpty() == true
        }

        fun getCityName(query: String): String {
            val items = query.split(",")
            return items[0]
        }

        fun getCityCountry(query: String): String {
            val items = query.split(",")
            return items[1]
        }

        fun transformDateToDay(date: String): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val tempDate: Date = dateFormat.parse(date)
            return SimpleDateFormat("EEEE").format(tempDate)
        }

        fun transformTimeTo24ClockTime(time: String): String {
            return when (time) {
                "0" -> "00:00"
                "300" -> "03:00"
                "600" -> "06:00"
                "900" -> "09:00"
                "1200" -> "12:00"
                "1500" -> "15:00"
                "1800" -> "18:00"
                "2100" -> "21:00"
                else -> ""
            }
        }
    }
}