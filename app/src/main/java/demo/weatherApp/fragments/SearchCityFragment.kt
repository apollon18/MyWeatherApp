package demo.weatherApp.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import demo.weatherApp.activities.MainListActivity
import demo.weatherApp.adapters.SearchResultsRecyclerAdapter
import demo.app.databinding.FragmentSearchCityBinding
import demo.weatherApp.listeners.SearchResultClickListener
import demo.weatherApp.models.Result
import demo.app.services.ApiDataResponse
import demo.app.services.ApiSearchResultResponse
import demo.app.services.Services
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchCityFragment : Fragment(), SearchResultClickListener {

    private var mContext: MainListActivity? = null
    private var viewBinding: FragmentSearchCityBinding? = null
    private var selectedCity: Result? = null
    private var results: ArrayList<Result>? = null
    private var searchText: String? = null
    private var resultsAdapter: SearchResultsRecyclerAdapter? = null
    private lateinit var linearLayoutManager: LinearLayoutManager

    companion object {
        fun getInstance(): SearchCityFragment {
            return SearchCityFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as MainListActivity
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSearchCityBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initHeaderButtons()
        initSearchField()
    }

    private fun initHeaderButtons() {
        viewBinding?.closeButton?.setOnClickListener {
            mContext?.onSearchFragmentClosed(false) }
    }

    private fun initSearchField() {
        mContext?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        val textWatcher  = object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                if (editable.isNotEmpty()) {
                   searchText = editable.toString()
                }
            }
        }
        viewBinding?.searchText?.addTextChangedListener(textWatcher)
        viewBinding?.searchText?.maxLines = 1
        viewBinding?.searchText?.setOnClickListener {
            (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
            InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
            viewBinding?.searchText?.requestFocus()
        }

        viewBinding?.searchBarAction?.setOnClickListener { getSearchResults() }
    }

    private fun initResultsAdapter() {
        checkForResults()
        if (resultsAdapter == null) {
            linearLayoutManager = LinearLayoutManager(mContext)
            viewBinding?.resultsList?.layoutManager = linearLayoutManager
            resultsAdapter = SearchResultsRecyclerAdapter(results = results, this@SearchCityFragment)
            viewBinding?.resultsList?.adapter = resultsAdapter
        } else {
            resultsAdapter?.updateList(results)
        }
    }

    private fun checkForResults() {
        if (results?.isEmpty() == true) {
            showEmptyState()
        } else {
            showList()
        }
    }

    private fun showEmptyState() {
        viewBinding?.emptyStateLayout?.visibility = View.VISIBLE
        viewBinding?.resultsList?.visibility = View.GONE
    }

    private fun showList() {
        viewBinding?.emptyStateLayout?.visibility = View.GONE
        viewBinding?.resultsList?.visibility = View.VISIBLE
    }

    private fun getSearchResults() {
        if (searchText == null) {
            return
        }
        loaderVisibility(show = true)
        Services.searchCities(searchText!!, object : Callback<ApiSearchResultResponse> {
            override fun onResponse(
                call: Call<ApiSearchResultResponse>,
                response: Response<ApiSearchResultResponse>
            ) {
               results = response.body()?.data?.result
                initResultsAdapter()
                loaderVisibility(show = false)
            }

            override fun onFailure(call: Call<ApiSearchResultResponse>, t: Throwable) {
                loaderVisibility(show = false)
                //show failure alert
            }
        })
    }

    override fun onResultClicked(result: Result) {
        selectedCity = result
        citySelectedActions()
    }

    private fun citySelectedActions() {
        loaderVisibility(show = true)
        selectedCity?.areaName?.get(0)?.value?.let {
            Services.getData(it, true, object : Callback<ApiDataResponse> {
                override fun onResponse(
                    call: Call<ApiDataResponse>,
                    response: Response<ApiDataResponse>
                ) {
                    loaderVisibility(show = false)
                    mContext?.onSearchFragmentClosed(true)
                }

                override fun onFailure(call: Call<ApiDataResponse>, t: Throwable) {
                    loaderVisibility(show = false)
                    //alert dialog
                }
            })
        }
    }

    private fun loaderVisibility(show: Boolean) {
        if (show) {
            viewBinding?.progressBarLayout?.root?.visibility = View.VISIBLE
        } else {
            viewBinding?.progressBarLayout?.root?.visibility = View.GONE
        }
    }
}