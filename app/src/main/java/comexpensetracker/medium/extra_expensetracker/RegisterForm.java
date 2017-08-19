package comexpensetracker.medium.extra_expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

import io.hasura.sdk.Callback;
import io.hasura.sdk.Hasura;
import io.hasura.sdk.HasuraClient;
import io.hasura.sdk.HasuraUser;
import io.hasura.sdk.ProjectConfig;
import io.hasura.sdk.exception.HasuraException;
import io.hasura.sdk.exception.HasuraInitException;
import io.hasura.sdk.responseListener.SignUpResponseListener;


public class RegisterForm extends AppCompatActivity {

    private EditText inputName, inputEmail, inputMobileNo, inputPassword, inputConfirmPassword;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutMobileNo, inputLayoutPassword, inputLayoutConfirmPassword;
    private Button btnSignUp;
    HasuraUser user;
    HasuraClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);

        try {
            Hasura.setProjectConfig(new ProjectConfig.Builder()
                    .setCustomBaseDomain("cobalt21.hasura-app.io")
                    //.enableOverHttp()  //enable this
                    .build())
                    .initialise(this);
        } catch (HasuraInitException e) {
            e.printStackTrace();
        }
        client = Hasura.getClient();
        user = client.getUser();

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutMobileNo = (TextInputLayout) findViewById(R.id.input_layout_mobile_no);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.input_layout_confirm_password);
        inputName = (EditText) findViewById(R.id.input_name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputMobileNo = (EditText) findViewById(R.id.input_mobile_no);
        inputPassword = (EditText) findViewById(R.id.input_password);
        inputConfirmPassword = (EditText) findViewById(R.id.input_confirm_password);
        btnSignUp = (Button) findViewById(R.id.btn_signup);

        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputMobileNo.addTextChangedListener(new MyTextWatcher(inputMobileNo));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));
        inputConfirmPassword.addTextChangedListener(new MyTextWatcher(inputConfirmPassword));

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
    }

    /**
     * Validating form
     */
    private void submitForm() {
        if (!validateName()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }

        if (!validateMobileNo()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        if (!validateConfirmPassword()) {
            return;
        }
        final String username = inputName.getText().toString();
        String password = inputPassword.getText().toString();
        user.setUsername(username);
        user.setPassword(password);
        user.signUp(new SignUpResponseListener() {
            @Override
            public void onSuccessAwaitingVerification(HasuraUser user) {
                //The user is registered on Hasura, but either his mobile or email needs to be verified.
                Toast.makeText(getApplicationContext(), "Registered. Please verify your email.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(HasuraUser user) {
                //Now Hasura.getClient().getCurrentUser() will have this user
                try{
                    //JSON query for inserting user (REFERENCE)
//                    {
//                        "type" : "insert",
//                        "args" : {
//                            "table" : "user_info",
//                            "objects": [{"name" : "username"}]
//                        }
//                    }

                    JSONObject nameJSON = new JSONObject();
                    nameJSON.put("name", username);
                    nameJSON.put("user_id", user.getId());

                    JSONArray colsList = new JSONArray();
                    colsList.put(nameJSON);

                    JSONObject args = new JSONObject();
                    args.put("table", "user_info");
                    args.put("objects", colsList);

                    JSONObject insertUserJSON = new JSONObject();
                    insertUserJSON.put("type", "insert");
                    insertUserJSON.put("args", args);

                    client.useDataService()
                            .setRequestBody(insertUserJSON)
                            .expectResponseType(RegisterUserResponse.class)
                            .enqueue(new Callback<RegisterUserResponse, HasuraException>() {
                                @Override
                                public void onSuccess(RegisterUserResponse registerUserResponse) {
                                    Toast.makeText(getApplicationContext(), "User Created ", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(HasuraException e) {
                                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                catch (JSONException e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getApplicationContext(), "Thank You! ", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(RegisterForm.this, LoginForm.class);
                startActivity(myIntent);
            }

            @Override
            public void onFailure(HasuraException e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateMobileNo() {

        String mobile = inputMobileNo.getText().toString().trim();

        if (mobile.isEmpty() || !isValidMobile(mobile)) {
            inputLayoutMobileNo.setError(getString(R.string.err_msg_mobile_no));
            requestFocus(inputMobileNo);
            return false;
        } else {
            inputLayoutMobileNo.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateConfirmPassword() {
        String password = inputPassword.getText().toString().trim();
        String confirm_password = inputConfirmPassword.getText().toString().trim();
        if (confirm_password.isEmpty() || !isValidConfirmPassword(password, confirm_password)) {
            inputLayoutConfirmPassword.setError(getString(R.string.err_msg_confirm_password));
            requestFocus(inputConfirmPassword);
            return false;
        } else {
            inputLayoutConfirmPassword.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidMobile(String phone) {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            check = phone.length() == 10;
        } else {
            check=false;
        }
        return check;
    }

    private boolean isValidConfirmPassword(String password, String confirm_password) {
        return password.equals(confirm_password);
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void loginActivityOpen(View view) {
        Intent i = new Intent(RegisterForm.this, LoginForm.class);
        startActivity(i);
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_mobile_no:
                    validateMobileNo();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
                case R.id.input_confirm_password:
                    validateConfirmPassword();
                    break;
            }
        }
    }

}