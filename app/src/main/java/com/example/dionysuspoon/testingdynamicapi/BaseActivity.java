package com.example.dionysuspoon.testingdynamicapi;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dionysus.Poon on 3/5/2018.
 */

public class BaseActivity extends AppCompatActivity {

    public String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        getSupportActionBar().hide();
        startFrag(R.id.rl_main,new BaseFragment(),getFragmentManager());


    }


    //start the fragment without back stack (if clicked back, it would not go back to this fragment)
    public static void startFrag(int container, Fragment fragment, FragmentManager fm) {

        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(container, fragment)
                .commit();

    }
    //start the fragment without back stack (if clicked back, it would not go back to this fragment)
    public static void startBackFrag(int container, Fragment fragment, FragmentManager fm) {

        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(container, fragment)
                .addToBackStack(null)
                .commit();

    }

    /**
     * Get the token from the API and store it inside the master data
     */
    public void sendTokenRequest( Runnable method) {
        Log.i(TAG,"Call Token");
        RequestQueue queue = Volley.newRequestQueue(this);

        ProgressDialog progressDialog = new ProgressDialog(this);

        String url = "https://api.ump.com.hk:12930/Token";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    Log.d(TAG, response);

                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        String token = jsonObject.getString("access_token");
                        GlobalConstants.TOKEN = token;
                        //ESubmissionMasterData masterData = new ESubmissionMasterData();
                        //masterData.setToken(jsonObject.getString("access_token"));

                        Log.i(TAG, "Token is " + token);

                        if (method != null) {
                            method.run();
                        }

                    } catch (JSONException e) {
                        progressDialog.dismiss();

                        Log.e(TAG, "Unexpected JSON response from Token API");
                        Log.e(TAG, response);
                        e.printStackTrace();

                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle(R.string.attention_alert_title);
                        builder.setMessage(R.string.system_error);

                        builder.setPositiveButton(R.string.disclaimer_ok, null);
                        builder.create().show();

                    }
                },
                error -> {
                    progressDialog.dismiss();

                    // mTextView.setText("That didn't work!");
                    Log.e(TAG, "Login API didn't work with status code: " + ((error.networkResponse != null) ? (error.networkResponse.statusCode) : " null network response") + " " + error.getCause());

                    if (error.getMessage() != null) {
                        Log.e(TAG, error.getMessage());
                    }
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(R.string.attention_alert_title);
                    builder.setMessage(R.string.server_communication_error);

                    builder.setPositiveButton(R.string.disclaimer_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("grant_type", "password");
                params.put("username", "Jason");
                params.put("password", "Jason");

                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        queue.add(stringRequest);
    }





    @Override
    //The method for back button & it is android 's self-defined back button
    public void onBackPressed() {
        // Catch back action and pops from backstack
        // (if you called previously to addToBackStack() in your transaction

        if(getFragmentManager().getBackStackEntryCount()>0){
            Toast.makeText(this,"1",Toast.LENGTH_SHORT).show();
            getFragmentManager().popBackStack();
        }else {
            if(!getClass().getSimpleName().equals("MainActivity")) {
                Toast.makeText(getBaseContext(), "2.1", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                finish();
            }else{
                Toast.makeText(getBaseContext(), "2.2", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
