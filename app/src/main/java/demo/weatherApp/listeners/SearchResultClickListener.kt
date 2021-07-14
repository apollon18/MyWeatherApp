package demo.weatherApp.listeners

import demo.weatherApp.models.Result


interface SearchResultClickListener {

    fun onResultClicked(result: Result)
}