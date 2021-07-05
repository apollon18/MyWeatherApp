package demo.app.models

class SearchResultModel {
    var result: ArrayList<Result>? = null
}

class AreaName {
    var value: String? = null
}

class Country {
    var value: String? = null
}

class Region {
    var value: String? = null
}

class WeatherUrl {
    var value: String? = null
}

class Result {
    var areaName: ArrayList<AreaName>? = null
    var country: ArrayList<Country>? = null
    var region: ArrayList<Region>? = null
    var latitude: String? = null
    var longitude: String? = null
    var population: String? = null
    var weatherUrl: ArrayList<WeatherUrl>? = null
    var isSelected: Boolean = false
}