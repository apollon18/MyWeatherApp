package demo.app.listeners

import demo.app.models.Result


interface SearchResultClickListener {

    fun onResultClicked(result: Result)
}