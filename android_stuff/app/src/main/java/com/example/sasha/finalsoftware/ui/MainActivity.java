package com.example.sasha.finalsoftware.ui;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.cs4531.finalsoftware.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import org.jetbrains.annotations.Nullable;

public class MainActivity extends LoginActivity {
    //SignOut Button
    Button GsignOut;
    TextView wel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonToCompare = findViewById(R.id.compButton);
        Button buttonToSearch = findViewById(R.id.searchButton);
        wel = findViewById(R.id.welcome);
        wel.setTextSize(20);
        wel.setTextColor(Color.RED);
        wel.setText("Welcome, " + account.getDisplayName() +"!");
        buttonToCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CompareNameActivity.class));
            }
        });

        buttonToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });

        GsignOut = findViewById(R.id.SignOut);
        Gsignout();
    }


    public void switchToNamesListed(View myView) {
        Intent myIntent = new Intent(this, NamesListedActivity.class);
        startActivity(myIntent);
    }

    private void Gsignout(){

        GsignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleSignInClient.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        //    startActivity(new Intent(MainActivity.this, LoginActivity.class));
    }


}
