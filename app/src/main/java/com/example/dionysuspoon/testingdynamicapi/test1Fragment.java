package com.example.dionysuspoon.testingdynamicapi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dionysus.Poon on 4/5/2018.
 */

public class test1Fragment extends BaseFragment {
    ProgressDialog progressDialog;
    RequestQueue queue;

    LinearLayout ll_dynamic_code;
    TextView dynamic_code_text_view;
    EditText dynamic_code_edit_text;
    EditText schemeCodeEditText;


    String msg;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.test1,container,false);
        // Instantiate
        queue = Volley.newRequestQueue(getActivity());
        progressDialog = new ProgressDialog(getActivity());
        dynamic_code_text_view = view.findViewById(R.id.dynamic_code_text_view);
        dynamic_code_edit_text = view.findViewById(R.id.dynamic_code_edit_text);
        ll_dynamic_code = view.findViewById(R.id.ll_dynamic_code);


        schemeCodeEditText = view.findViewById(R.id.scheme_code_edit_text);
        //schemeCodeEditText.setText(preferences.getString(LAST_SCHEME_CODE, "")); // default value
        schemeCodeEditText.setOnFocusChangeListener((v, hasFocus) -> {
            Log.d(TAG, "Scheme Code Edit Text Focus Change");

            //EXIT the focus
            if (!hasFocus) {

                sendTokenRequest(() -> card_listAPI());

//                EditText schemeCodeEditTextView = (EditText) v;
//                changeLoginIDLabel(view, schemeCodeEditTextView, true);
            }
        });
        // Set the login ID label according to scheme
        changeLoginIDLabel(view, schemeCodeEditText, false);

        EditText passwordEditText = view.findViewById(R.id.password_edit_text);
        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                    if(schemeCodeEditText.getText().toString().trim().equals("F631")) {
                        sendTokenRequest(() -> pr_show_qAPI(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle(R.string.attention_alert_title);
                                builder.setMessage(msg);
                                builder.setPositiveButton(R.string.disclaimer_ok, null);
                                builder.create().show();

                            }
                        }));
                    }

                }
            }
        });

        return view;
    }

        /**
         * Change Login ID Label if match scheme of F631
         *
         * @param view Fragment View in onCreateView
         * @param schemeCodeEditTextView SchemeEditText
         */
        private void changeLoginIDLabel(View view, EditText schemeCodeEditTextView, boolean showAlert) {
            String schemeCode = schemeCodeEditTextView.getText().toString();            if (schemeCode.toUpperCase().equals("F631")) {
                TextView loginIDTextView = view.findViewById(R.id.login_id_text_view);
                String newLabelText = getString(R.string.UMP_Membership_No);
                boolean isNotChanged = !newLabelText.equals(loginIDTextView.getText());

                if (showAlert && isNotChanged){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(R.string.attention_alert_title);
                    builder.setMessage(R.string.please_enter_first_four_hkid);

                    builder.setPositiveButton(R.string.disclaimer_ok, null);
                    builder.create().show();
                }

                loginIDTextView.setText(newLabelText);
            }
     }

    // Call API with sending data to server
    public void card_listAPI() {
        Log.i(TAG,"card_listAPI SS outside");



        String url = "https://api.ump.com.hk:12930" + "/api/Card/card_list";

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("online_member_id","M0000331");

        } catch (JSONException e) {
            // Normally it just work
            e.printStackTrace();
        }

        Log.i(TAG,"after Json");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url,jsonObject,
                response -> {
                    Log.i(TAG,"after JsonUrl");
                    Log.i(TAG, "success at jsonObjectRequest ");
                    try {
                        boolean result = response.getBoolean("result");
                        Log.i(TAG,"access");

                        if(schemeCodeEditText.getText().toString().trim().equals("F631")){
                            result = true;
                        }else {
                            result = false;
                        }
                        if (!result){
                            Log.i(TAG,"fails");
                            ll_dynamic_code.setVisibility(View.GONE);


                        } else {

                            // Data from the API
                            JSONArray cardList = response.getJSONArray("card_status");
                            ArrayList<String> card_nameList = new ArrayList<>();
                            for (int i = 0; i < cardList.length(); i++) {
                                JSONObject cardJSON = cardList.getJSONObject(i);
                                // Json  for NonPanelClaim array

                                String card_id = cardJSON.getString("card_id");
                                String card_name = cardJSON.getString("card_name");
                                String card_image = cardJSON.getString("card_image");

                                String user_name = cardJSON.getString("full_name");
                                String code_id = cardJSON.getString("code_id");

                                Log.i(TAG,"card_listAPI cardid passing is : "+card_id);
                                card_nameList.add(card_name);
                            }

                            dynamic_code_text_view.setText(card_nameList.get(0));
                            ll_dynamic_code.setVisibility(View.VISIBLE);





                        }
                    } catch (JSONException e) {
                        // normally it just works
                        e.printStackTrace();
                        Log.e(TAG, "JSONException", e);
                    }
                },
                error -> {
                    Log.e(TAG, "error ");
                    if (error.getCause() != null) {
                        Log.e(TAG, "error", error.getCause());
                    }
                })  {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>(super.getHeaders());

                //Token
                String tokenB = "Bearer " + GlobalConstants.TOKEN;
                headers.put("Authorization", tokenB);

                return headers;
            }
        };
        queue.add(jsonObjectRequest);

    }

     public void pr_answer_q(Runnable method) {
         String url = "https://api.ump.com.hk:12930" + "/api/PreRegistration/pr_answer_q";
         JSONObject jsonObject = new JSONObject();

         try {
             JSONArray jsonArray = new JSONArray();

             JSONObject j = new JSONObject();
             for (int i = 1; i <= 5; i++) {
                 j.put("q_id", i);
                 j.put("item_chosen", "Y");
                 jsonArray.put(j);
             }
             jsonObject.put("q_answer", jsonArray);

         } catch (JSONException e) {
             e.printStackTrace();
         }


         JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                 response -> {

                     try {
                         boolean result = response.getBoolean("result");
                         Log.i(TAG,"access");
                         if (!result){
                             Log.i(TAG,"fails");


                         } else {

                         }
                     } catch (JSONException e) {
                         //nomally it just works
                         e.printStackTrace();
                         Log.e(TAG, "JSONException", e);
                     }


                 },

                 error -> {
                     Log.e(TAG, "error ");
                     if (error.getCause() != null) {
                         Log.e(TAG, "error", error.getCause());
                     }

                     AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                     builder.setTitle(R.string.attention_alert_title);
                     builder.setMessage(R.string.server_communication_error);
                     builder.setPositiveButton(R.string.disclaimer_ok, null);
                     builder.create().show();

                 }) {
             @Override
             public Map<String, String> getHeaders() throws AuthFailureError {
                 HashMap<String, String> headers = new HashMap<>(super.getHeaders());
                 //Token
                 String tokenB = "Bearer " + GlobalConstants.TOKEN;
                 headers.put("Authorization", tokenB);

                 return headers;
             }
         };
     }


    // Call API without sending data to server
    public void pr_show_qAPI(Runnable method) {

        String url =  "https://api.ump.com.hk:12930"+"/api/PreRegistration/pr_show_q";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                response -> {
                    Log.i(TAG, "success at jsonObjectRequest ");
                    try {

                        // Data putted inside the fragment
                        // Data from the API
                        JSONArray qList = response.getJSONArray("q");
                        JSONArray q_itemList = response.getJSONArray("q_itm");

                        TextView[] textViews = new TextView[qList.length()];


                        for (int j = 0; j < q_itemList.length(); j++) {
                            JSONObject q_itmJSON = q_itemList.getJSONObject(j);
                            int q_id =q_itmJSON.getInt("q_id");
                            String itm = q_itmJSON.getString("itm");
                            String seq = q_itmJSON.getString("seq");


                        }
                        msg = response.getString("msg");

                        method.run();
                    } catch (JSONException e) {
                        // normally it just works
                        e.printStackTrace();
                        Log.e(TAG, "JSONException", e);
                    }
                },
                error -> {
                    Log.e(TAG, "error ");
                    if (error.getCause() != null) {
                        Log.e(TAG, "error", error.getCause());
                    }

                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>(super.getHeaders());

                //Token
                String tokenB = "Bearer " + GlobalConstants.TOKEN;
                headers.put("Authorization", tokenB);

                return headers;
            }
        };
        queue.add(jsonObjectRequest);

    }


}


