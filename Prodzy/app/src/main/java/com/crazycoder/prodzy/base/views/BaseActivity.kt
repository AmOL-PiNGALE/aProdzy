package com.crazycoder.prodzy.base.views

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.crazycoder.prodzy.base.viewmodels.BaseViewModel
import com.crazycoder.prodzy.rest.retrofit.network.NetworkEvent
import com.crazycoder.prodzy.rest.retrofit.network.NetworkState
import com.crazycoder.prodzy.utils.DialogUtil
import io.reactivex.functions.Consumer

open class BaseActivity : AppCompatActivity() {

    private var mProgressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BaseViewModel.isLoading.observe(this, Observer {
            val isLoading = it ?: return@Observer
            if (isLoading)
                showProgressBar()
            else
                hideProgressBar()
        })
    }

    fun setProgressBar(bar: ProgressBar) {
        mProgressBar = bar
    }

    private fun showProgressBar() {
        if (mProgressBar != null && !mProgressBar?.isShown!!) {
            mProgressBar?.visibility = View.VISIBLE

            // Disable user interaction with the screen
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        }
    }

    private fun hideProgressBar() {
        if (mProgressBar != null && mProgressBar?.isShown!!) {
            mProgressBar?.visibility = View.GONE

            // reEnable user interaction with the screen
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    /*
    * we register the BaseActivity as subscriber
    * and specify what needs to be done in case of
    * NO_INTERNET, NO_RESPONSE, UNAUTHORISED error responses
    */
    override fun onResume() {
        super.onResume()
        NetworkEvent.register(this, Consumer {
            when (it) {
                NetworkState.NO_INTERNET -> {
                    val dialog = DialogUtil.showAlertDialog(
                        this,
                        "No network available, please check your WiFi or Data connection"
                    )
                    dialog.show()
                }

                NetworkState.NO_RESPONSE -> {
                }

                NetworkState.UNAUTHORISED -> {
                    //redirect to necessary screen - if session expired
                }

                NetworkState.SERVER_ERROR -> {
                    val dialog = DialogUtil.showAlertDialog(
                        this,
                        "Unable to connect to server, please try after sometime"
                    )
                    dialog.show()
                }
            }
        })
    }

    /*
     * we unregister the activity once it is
     * finished.
     */
    override fun onStop() {
        super.onStop()
        NetworkEvent.unregister(this)
    }

}