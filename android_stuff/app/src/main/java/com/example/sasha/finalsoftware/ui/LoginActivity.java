package com.example.sasha.finalsoftware.ui;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.cs4531.finalsoftware.R;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import static java.lang.Thread.sleep;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    //Google Sign In
    GoogleSignInClient mGoogleSignInClient;
    static final int RC_SIGN_IN = 9001;

    //user
    Button signO;
    GoogleSignInAccount account;
    Button signIn;
    //intent
    Intent intent;
    //search engine button
    Button searchButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signO = findViewById(R.id.SignOut);
        intent = new Intent(this, Loading.class);
        searchButton = findViewById(R.id.Search);
        searchButton.setVisibility(View.GONE);
        //Google Sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        account = GoogleSignIn.getLastSignedInAccount(this);
        findViewById(R.id.SignIn).setOnClickListener((View.OnClickListener) this);
        signIn = findViewById(R.id.SignIn);
        signIn.setVisibility(View.VISIBLE);
        updateUI(account);
        searchB();



    }

    private void searchB(){
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    private void updateUI(GoogleSignInAccount account) {
       // startActivity(intent);
        if (account != null) {
            String personN = account.getDisplayName();
            signIn.setVisibility(View.GONE);
            searchButton.setVisibility(View.VISIBLE);
           // startActivity(new Intent(LoginActivity.this, Loading.class));
          }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.SignIn:
                signIn();
                break;
            // ...
        }
    }



    private void signIn()  {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
      // startActivity(intent);
       // startActivity(new Intent(LoginActivity.this, Loading.class));
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            // Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }


}
