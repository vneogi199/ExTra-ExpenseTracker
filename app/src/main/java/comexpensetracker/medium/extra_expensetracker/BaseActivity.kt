package comexpensetracker.medium.extra_expensetracker

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import io.hasura.sdk.HasuraErrorCode
import io.hasura.sdk.exception.HasuraException


/**
 * Created by akash on 23/7/17.
 */
open class BaseActivity: AppCompatActivity() {

    var pd: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pd = ProgressDialog(this)
    }

    protected fun showErrorAlert(message: String?, listener: DialogInterface.OnClickListener?) {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("Error")
        alertDialog.setMessage(message)
        if (listener != null) {
            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Dismiss", listener)
        } else {
            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Dismiss", DialogInterface.OnClickListener { _, _ -> alertDialog.dismiss() })
        }
        alertDialog.show()
    }

    protected fun showProgressIndicator() {
        pd?.setMessage("Please wait")
        pd?.show()
    }

    protected fun hideProgressIndicator() {
        pd?.dismiss()
    }

    fun handleError(e: HasuraException) {
        if (e.code == HasuraErrorCode.UNAUTHORISED) {
            showErrorAlert("You login session has expired. Please log in again", DialogInterface.OnClickListener { _, _ -> completeUserLogout() })
        } else
            showErrorAlert(e.message, null)
    }

    fun completeUserLogout() {
        //AuthenticationActivity.startActivity(this)
    }

}
