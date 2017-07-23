package comexpensetracker.medium.extra_expensetracker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        try {
            Hasura.setProjectConfig(ProjectConfig.Builder()
                    .setCustomBaseDomain("extraexpensetracker.hasura.me").enableOverHttp()
                    .build())
                    .initialise(this)
        } catch (e: HasuraInitException) {
            e.printStackTrace()
        }

        val client = Hasura.getClient()
        var user:HasuraUser = client.user
        val btnLogin : Button = findViewById(R.id.btn_login) as Button
        btnLogin.setOnClickListener({
            var input_username = findViewById(R.id.input_username) as EditText
            var inputPassword = findViewById(R.id.input_password) as EditText
            user.username = input_username.text.toString()
            user.password = inputPassword.text.toString()
            user.login(object : AuthResponseListener {
                override fun onSuccess(p0: String?) {
                    Toast.makeText(applicationContext, "Logged in", Toast.LENGTH_SHORT).show()
                    val myIntent = Intent(this@LoginForm, AddExpense::class.java)
                    startActivity(myIntent)
                }

                override fun onFailure(e: HasuraException) {
                    Toast.makeText(applicationContext, e.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        })
    }
}