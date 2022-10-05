package com.crazycoder.prodzy.utils

import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.crazycoder.prodzy.R

/**
 * Purpose of this Class is to display different dialog in application.
 */
object DialogUtil {

    /**
     * Displays alert dialog to show common messages.
     * @param message Message to be shown in the Dialog displayed
     * @param context Context of the Application, where the Dialog needs to be displayed
     */
    fun createDialogWithSingleButton(
        context: Context, dialogTitle: String, message: String,
        positiveBtnText: String, positiveListener: DialogInterface.OnClickListener
    ): AlertDialog {

        return AlertDialog.Builder(context)
            .setTitle(dialogTitle)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(positiveBtnText, positiveListener)
            .create()
    }

    /**
     * Displays alert dialog to show common messages.
     * @param message Message to be shown in the Dialog displayed
     * @param context Context of the Application, where the Dialog needs to be displayed
     */
    fun createDialogWithTwoButtons(
        context: Context, dialogTitle: String, message: String,
        positiveBtnText: String, positiveListener: DialogInterface.OnClickListener,
        negativeBtnText: String, negativeListener: DialogInterface.OnClickListener
    ): AlertDialog {

        return AlertDialog.Builder(context)
            .setTitle(dialogTitle)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(positiveBtnText, positiveListener)
            .setNegativeButton(negativeBtnText, negativeListener)
            .create()
    }


    /**
     * Displays toast message
     * @param mContext requires to create Toast
     */
    fun showToast(mContext: Context, message: String) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT)
            .show()
    }

    fun showAlertDialog(context: Context, message: String): AlertDialog {
        val positiveListener = DialogInterface.OnClickListener { dialog, _ ->
            run { }
        }
        val dialogTitle = context.getString(R.string.app_name)
        val positiveBtnText = context.getString(R.string.action_ok)
        return AlertDialog.Builder(context)
            .setTitle(dialogTitle)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(positiveBtnText, positiveListener)
            .create()
    }
}