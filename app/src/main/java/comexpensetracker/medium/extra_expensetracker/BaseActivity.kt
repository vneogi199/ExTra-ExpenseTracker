package comexpensetracker.medium.extra_expensetracker

import android.support.v7.app.AppCompatActivity
import android.content.DialogInterface
import io.hasura.sdk.HasuraErrorCode
import io.hasura.sdk.exception.HasuraException
import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AlertDialog


/**
 * Created by akash on 23/7/17.
 */
public open class BaseActivity: AppCompatActivity() {

    var pd: ProgressDialog = null!!

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
            alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Dismiss", DialogInterface.OnClickListener { dialog, which -> alertDialog.dismiss() })
        }
        alertDialog.show()
    }

    protected fun showProgressIndicator() {
        pd.setMessage("Please wait")
        pd.show()
    }

    protected fun hideProgressIndicator() {
        pd.dismiss()
    }

    fun handleError(e: HasuraException) {
        if (e.code == HasuraErrorCode.UNAUTHORISED) {
            showErrorAlert("You login session has expired. Please log in again", DialogInterface.OnClickListener { dialog, which -> completeUserLogout() })
        } else
            showErrorAlert(e.message, null)
    }

    fun completeUserLogout() {
        //AuthenticationActivity.startActivity(this)
    }

}
