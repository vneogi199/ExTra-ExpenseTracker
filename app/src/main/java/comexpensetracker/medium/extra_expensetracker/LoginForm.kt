package comexpensetracker.medium.extra_expensetracker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit



/**
 * Created by vinit on 13/7/17.
 */
class LoginForm : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btnLogin : Button = findViewById(R.id.btn_login) as Button
        btnLogin.setOnClickListener(View.OnClickListener {
            var input_username = findViewById(R.id.input_username) as EditText
            var inputPassword = findViewById(R.id.input_password) as EditText
            val retrofit = Retrofit.Builder()
                    .baseUrl(Endpoint.AUTH_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            val auth = retrofit.create(HasuraAuthInterface::class.java)

            auth.login(AuthRequest(input_username.text.toString(), inputPassword.text.toString())).enqueue(object : Callback<AuthResponse> {
                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    if (response.isSuccessful) {
                        val authResponse = response.body()
                        Toast.makeText(this@LoginForm, "Logged in " + authResponse, Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@LoginForm, AddExpense::class.java))
                    } else {
                        Toast.makeText(this@LoginForm, "else", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Toast.makeText(this@LoginForm, "onFailure", Toast.LENGTH_LONG).show()
                }
            })
        })
    }
}