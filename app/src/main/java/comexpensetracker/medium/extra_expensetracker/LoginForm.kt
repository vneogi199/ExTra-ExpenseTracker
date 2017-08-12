package comexpensetracker.medium.extra_expensetracker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import comexpensetracker.medium.extra_expensetracker.R.id.input_layout_username
import comexpensetracker.medium.extra_expensetracker.R.id.input_username
import io.hasura.sdk.Hasura
import io.hasura.sdk.exception.HasuraException
import io.hasura.sdk.HasuraUser
import io.hasura.sdk.ProjectConfig
import io.hasura.sdk.exception.HasuraInitException
import io.hasura.sdk.responseListener.AuthResponseListener

/**
 * Created by vinit on 13/7/17.
 */
class LoginForm : BaseActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var input_username: EditText? = null
        var inputPassword: EditText? = null
        var inputLayoutUsername: TextInputLayout? = null
        var inputLayoutPassword: TextInputLayout? = null


        fun validateUsername(): Boolean {
            if (input_username.toString().trim().isEmpty()) {
                inputLayoutUsername?.error = getString(R.string.err_msg_name)
                return false
            } else {
                inputLayoutUsername?.isErrorEnabled = false
            }

            return true
        }

        fun validatePassword(): Boolean {
            if (inputPassword?.text.toString().trim().isEmpty()) {
                inputLayoutPassword?.error = getString(R.string.err_msg_password)
                return false
            } else {
                inputLayoutPassword?.isErrorEnabled = false
            }

            return true
        }
        try {
            Hasura.setProjectConfig(ProjectConfig.Builder()
                    .setCustomBaseDomain("cobalt21.hasura-app.io")
                    //.enableOverHttp()
                    .build())
                    .initialise(this)
        } catch (e: HasuraInitException) {
            e.printStackTrace()
        }

        inputLayoutUsername = findViewById(R.id.input_layout_username) as TextInputLayout
        inputLayoutPassword = findViewById(R.id.input_layout_password) as TextInputLayout

        val client = Hasura.getClient()
        var user: HasuraUser = client.user
        val btnLogin: Button = findViewById(R.id.btn_login) as Button
        btnLogin.setOnClickListener({
            if (!validateUsername()) return@setOnClickListener

            if (!validatePassword()) return@setOnClickListener

            input_username = findViewById(R.id.input_username) as EditText
            inputPassword = findViewById(R.id.input_password) as EditText
            user.username = input_username!!.text.toString()
            user.password = inputPassword!!.text.toString()
            user.login(object : AuthResponseListener {
                override fun onSuccess(p0: String?) {
                    Toast.makeText(applicationContext, "Logged in", Toast.LENGTH_SHORT).show()
                    val myIntent = Intent(this@LoginForm, ViewAnalytics::class.java)
                    startActivity(myIntent)
                }

                override fun onFailure(e: HasuraException) {
                    Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        })
    }
}