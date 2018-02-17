package com.example.riamon_v.java_epicture_2017.SignLoginHandling;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.riamon_v.java_epicture_2017.DatabaseManagment.DatabaseHandler;
import com.example.riamon_v.java_epicture_2017.DatabaseManagment.User;
import com.example.riamon_v.java_epicture_2017.R;


public class SignupActivity extends AppCompatActivity {
        private static final String TAG = "SignupActivity";

        private EditText nameView;
        private EditText emailView;
        private EditText passwordView;
        private Button signupButton;
        private TextView loginLink;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_signup);

            nameView = findViewById(R.id.input_name);
            emailView = findViewById(R.id.input_email);
            passwordView = findViewById(R.id.input_password);
            signupButton = findViewById(R.id.btn_signup);
            signupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signup();
                }
            });

            loginLink = findViewById(R.id.link_login);
            loginLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Finish the registration screen and return to the Login activity
                    finish();
                }
            });
        }


        /**
         * Sign and login the user correctly
         */
        public void signup() {
           // Log.d(TAG, "Signup");

            if (!validate()) {
                onSignupFailed();
                return;
            }

            signupButton.setEnabled(false);

            final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Creating Account...");
            progressDialog.show();

            String name = nameView.getText().toString();
            String email = emailView.getText().toString();
            String password = passwordView.getText().toString();
            User newUser = new User(name, email, password);
            newUser.setConnect(true);

            DatabaseHandler.getInstance(this).getUserDao().insertUser(newUser);
            // TODO: Implement your own signup logic here.

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onSignupSuccess or onSignupFailed
                            // depending on success
                            onSignupSuccess();
                            // onSignupFailed();
                            progressDialog.dismiss();
                        }
                    }, 3000);
        }


        public void onSignupSuccess() {
            signupButton.setEnabled(true);
            setResult(RESULT_OK, null);
            finish();
        }

        public void onSignupFailed() {
            Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

            signupButton.setEnabled(true);
        }

        /**
         * Verify all the user's information before validation
         * @return
         */
        public boolean validate() {
            boolean valid = true;

            String name = nameView.getText().toString();
            String email = emailView.getText().toString();
            String password = passwordView.getText().toString();

            if (name.isEmpty() || name.length() < 3) {
                nameView.setError("at least 3 characters");
                valid = false;
            } else {
                nameView.setError(null);
            }

            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailView.setError("enter a valid email address");
                valid = false;
            } else {
                emailView.setError(null);
            }

            if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                passwordView.setError("between 4 and 10 alphanumeric characters");
                valid = false;
            } else {
                passwordView.setError(null);
            }

            return valid;
        }
}
