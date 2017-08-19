package comexpensetracker.medium.extra_expensetracker

import android.app.ProgressDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity


/**
 * Created by akash on 23/7/17.
 */
open class BaseActivity: AppCompatActivity() {

    var pd: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pd = ProgressDialog(this)
    }

    protected fun hideProgressIndicator() {
        pd?.dismiss()
    }


}
