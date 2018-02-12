package com.example.riamon_v.java_epicture_2017.SignLoginHandling;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.riamon_v.java_epicture_2017.DatabaseManagment.DatabaseHandler;
import com.example.riamon_v.java_epicture_2017.DatabaseManagment.User;
import com.example.riamon_v.java_epicture_2017.MainActivity;
import com.example.riamon_v.java_epicture_2017.R;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {// implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_SIGNUP = 0;

    private EditText mUserName;
    private EditText mPasswordView;
    private Button loginButton;
    private Button loginButtonImgur;
    private TextView signupLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUserName = (EditText) findViewById(R.id.userName);
        mPasswordView = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.btn_login);
        loginButtonImgur = (Button) findViewById(R.id.btn_login_imgur);

        if (isAlreadyConnected() != null) {
            onLoginSuccess();
        }

        loginButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               login();
            }
        });
        loginButtonImgur.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ImgurLoginActivity.class));
            }
        });

        signupLink = findViewById(R.id.link_signup);
       signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
     }

    private User isAlreadyConnected() {
        User user;

        user = DatabaseHandler.getInstance(this).getUserDao().getConnectedUser(true);
        if (user == null) {
            user = DatabaseHandler.getInstance(this).getUserDao().getConnectedUserFlick();
            if (user == null) {
                user = DatabaseHandler.getInstance(this).getUserDao().getConnectedUserImgur();
                if (user == null)
                    return null;
            }
        }
        return user;
    }

    private void login() {
        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        // TODO: Implement your own authentication logic here.
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                onLoginSuccess();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    private void onLoginSuccess() {
        loginButton.setEnabled(true);
        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    private void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        loginButton.setEnabled(true);
    }

    private boolean validate() {
        //boolean valid = true;
        User user;

        String email = mUserName.getText().toString();
        String password = mPasswordView.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mUserName.setError(getString(R.string.error_invalid_email));
            return false;
        }
        else
            mUserName.setError(null);

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mPasswordView.setError("between 4 and 10 alphanumeric characters");
            return false;
        }
        else
            mPasswordView.setError(null);

        user = DatabaseHandler.getInstance(this).getUserDao().getUserByLogin(email);
        if (user == null ) {
            mUserName.setError("User not recognize");
            return false;
        } else if (!password.equals(user.getPassword())) {
            mUserName.setError(null);
            mPasswordView.setError("Wrong password");
            return false;
        }
        mPasswordView.setError(null);
        user.setConnect(true);
        DatabaseHandler.getInstance(this).getUserDao().updateUser(user);
        return true;
    }
}

