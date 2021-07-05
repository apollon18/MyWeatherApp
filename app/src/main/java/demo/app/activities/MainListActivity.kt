package demo.app.activities

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import demo.app.R
import demo.app.adapters.MainCityListRecyclerAdapter
import demo.app.databinding.ActivityMainListBinding
import demo.app.fragments.SearchCityFragment
import demo.app.helpers.RealmHelper
import demo.app.helpers.Utilities
import demo.app.listeners.MainListCityListener
import demo.app.models.CityModel
import okhttp3.internal.notify


class MainListActivity : AppCompatActivity(), MainListCityListener{

    private var viewBinding: ActivityMainListBinding? = null
    private var cityListAdapter: MainCityListRecyclerAdapter? = null
    private var searchFragment: SearchCityFragment? = null
    private var citiesList: ArrayList<CityModel>? = null
    private lateinit var linearLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainListBinding.inflate(layoutInflater)
        setContentView(viewBinding?.root)

        initHeaderButtons()
        initListState()
    }

    private fun initHeaderButtons() {
        viewBinding?.addButton?.setOnClickListener { openSearchFragment() }
        viewBinding?.searchIcon?.setOnClickListener { openSearchFragment() }
//        viewBinding?.settingsButton?.setOnClickListener {  }
    }

    private fun initListState() {
        if (Utilities.userHasFavoriteCity()) {
            initAdapter()
            viewBinding?.emptyStateLayout?.visibility = View.GONE
        } else {
            viewBinding?.cityList?.visibility = View.GONE
            viewBinding?.emptyStateLayout?.visibility = View.VISIBLE
        }
    }

    private fun openSearchFragment() {
        searchFragment = SearchCityFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, searchFragment!!)
            .commit()
        viewBinding?.fragmentContainerView?.visibility = View.VISIBLE
    }

    private fun initAdapter() {
        citiesList = RealmHelper.getUserCitiesFromDatabase()
        checkForList()
        if (cityListAdapter == null) {
            linearLayoutManager = LinearLayoutManager(this)
            viewBinding?.cityList?.layoutManager = linearLayoutManager
            if (citiesList != null) {
                cityListAdapter = MainCityListRecyclerAdapter(
                    mContext = this@MainListActivity,
                    cities = citiesList!!,
                    listener = this@MainListActivity)
            }
            viewBinding?.cityList?.adapter = cityListAdapter
        } else {
            if (citiesList != null) {
                cityListAdapter?.updateList(citiesList!!)
            }
        }
        loaderVisibility(false)
    }

    private fun checkForList() {
        if (citiesList?.isEmpty() == false) {
            showList()
        } else {
            showEmptyState()
        }
    }

    private fun showList() {
        viewBinding?.cityList?.visibility = View.VISIBLE
        viewBinding?.emptyStateLayout?.visibility = View.GONE
    }

    private fun showEmptyState() {
        viewBinding?.cityList?.visibility = View.GONE
        viewBinding?.emptyStateLayout?.visibility = View.VISIBLE
    }

    override fun onCityDetailsClicked(cityModel: CityModel) {
        openCityDetailsActivity(cityModel)
    }

    private fun openCityDetailsActivity(cityModel: CityModel) {
        val detailsIntent = Intent(this@MainListActivity, CityDetailsActivity::class.java)
        detailsIntent.putExtra("cityName", cityModel.name)
        detailsIntent.putExtra("cityCountry", cityModel.country)
        startActivity(detailsIntent)
    }

    override fun onCityDetailsLongClicked(cityModel: CityModel) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.app_name))
        builder.setMessage(resources.getString(R.string.delete_dialog_text))
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { _, _ ->
            RealmHelper.deleteCityFromDatabase(cityModel)
            updateAdapterWithDelay()
        })
        builder.show()
    }

    fun onSearchFragmentClosed(newCitySelected: Boolean) {
        if (newCitySelected) {
            updateAdapterWithDelay()
        }
        viewBinding?.fragmentContainerView?.visibility = View.GONE
        searchFragment?.let { supportFragmentManager.beginTransaction().remove(it) }
        searchFragment = null
    }

    private fun updateAdapterWithDelay() {
        loaderVisibility(true)
        Handler().postDelayed({
            citiesList?.clear()
            initAdapter()
        }, 1500)
    }

    override fun onBackPressed() {
        if (searchFragment != null) {
            viewBinding?.fragmentContainerView?.visibility = View.GONE
            supportFragmentManager.beginTransaction().remove(searchFragment!!)
        } else {
            super.onBackPressed()
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