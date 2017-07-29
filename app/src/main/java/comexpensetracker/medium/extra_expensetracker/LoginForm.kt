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
        try {
            Hasura.setProjectConfig(ProjectConfig.Builder()
                    .setCustomBaseDomain("camaraderie53.hasura-app.io")
                    //.enableOverHttp()
                    .build())
                    .initialise(this)
        } catch (e: HasuraInitException) {
            e.printStackTrace()
        }

        var inputLayoutUsername = findViewById(R.id.input_layout_username) as TextInputLayout
        var inputLayoutPassword = findViewById(R.id.input_layout_password) as TextInputLayout

        val client = Hasura.getClient()
        var user: HasuraUser = client.user
        val btnLogin: Button = findViewById(R.id.btn_login) as Button
        btnLogin.setOnClickListener({
            var input_username = findViewById(R.id.input_username) as EditText
            var inputPassword = findViewById(R.id.input_password) as EditText
            user.username = input_username.text.toString()
            user.password = inputPassword.text.toString()
            user.login(object : AuthResponseListener {
                override fun onSuccess(p0: String?) {
                    Toast.makeText(applicationContext, "Logged in", Toast.LENGTH_SHORT).show()
                    //val myIntent = Intent(this@LoginForm, AddExpense::class.java)
                    //startActivity(myIntent)
                }

                override fun onFailure(e: HasuraException) {
                    Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        })
    }

    fun View.showKeyboard() {
        this.requestFocus()
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

    private void submitForm()
    {
        if (!validateUsername()) {
            return
        }

        if (!validatePassword()) {
            return
        }
    }

    private fun validateUsername(): Boolean {
        if (input_username.toString().trim().isEmpty()) {
            input_layout_username.setError(getString(R.string.err_msg_name))
            showKeyboard(input_username)
            return false
        } else {
            input_layout_username.setErrorEnabled(false)
        }

        return true
    }

    private fun validatePassword(): Boolean {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password))
            requestFocus(inputPassword)
            return false
        } else {
            inputLayoutPassword.setErrorEnabled(false)
        }

        return true
    }

}