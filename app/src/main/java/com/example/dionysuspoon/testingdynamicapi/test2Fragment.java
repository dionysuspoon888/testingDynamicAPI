package com.example.dionysuspoon.testingdynamicapi;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


/**
 * Created by Dionysus.Poon on 4/5/2018.
 */

public class test2Fragment extends BaseFragment {
    LinearLayout ll_membercard_register2_pr_show_q;
    ArrayList<String> question_list;
    ArrayList<String> itm_list;
    ArrayList<RadioButton> radioButtonsList = new ArrayList<>();
    ArrayList<RadioButton> radioButtons2List= new ArrayList<>();

    TreeMap<Integer,TreeMap> treeMapForQ ;
    TreeMap<Integer,ValueForseq>[] treeMapForseq;
    TreeMap<Integer,TreeMap> treeMapForWhole;

    RequestQueue queue;
    ProgressDialog progressDialog;


    Button b_test2;

    RadioGroup radioGroup;

    int seq_act = 0;

    View v;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.test2,container,false);


        // Instantiate
        queue = Volley.newRequestQueue(getActivity());
        progressDialog = new ProgressDialog(getActivity());


        b_test2 = v.findViewById(R.id.b_test2);


        ll_membercard_register2_pr_show_q = v.findViewById(R.id.ll_membercard_register2_pr_show_q);
        question_list = new ArrayList<>();
        itm_list = new ArrayList<>();

        //initView(100);

        sendTokenRequest(EMemberCardURL.UMPFWDServerLink,() -> pr_show_qAPI());
        //initViewPro();

        b_test2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_membercard_register2_pr_show_q.removeAllViews();
                sendTokenRequest(EMemberCardURL.UMPFWDServerLink,() -> pr_show_qAPI());
            }
        });

        return v;
    }

    public void initView(int length){
        for(int j = 0;j<length;j++) {
            question_list.add("Q"+(j+1));
        }

        for(int k = 0;k<2*length;k++) {
            if(k%2 == 0) {
                itm_list.add("Y");
            }else{
                itm_list.add("N");
            }
        }

        for(int i =0;i<length;i++) {
            addQView(question_list.get(i), i);
        }
    }
    public void treeMapForq_id(int q_id){

        treeMapForQ.put(q_id,treeMapForseq[q_id]);


    }

    public void treeMapForseq(int q_id,int seq,String fld_type,String itm) {
        if(treeMapForseq[q_id] == null){
            treeMapForseq[q_id] = new TreeMap<>();
        }

        treeMapForseq[q_id].put(seq, new ValueForseq(fld_type, itm));

    }

    public void initViewPro(){

        for (int q_id : treeMapForQ.keySet()) {
            Log.i("111"," q_id is: "+q_id);
            for(int seq : treeMapForseq[q_id].keySet()){
                Log.i("222"," seq is: "+seq);
                Log.i("333"," q_id is: "+q_id+"seq is : "+seq+"itm is: "+treeMapForseq[q_id].get(seq).getItm()+"fld_type is: "+treeMapForseq[q_id].get(seq).getFld_type());

            }
        }


        for (int q_id : treeMapForQ.keySet()) {
            addQViewPro(q_id);
        }
    }




    class ValueForseq{

        String fld_type;
        String itm;
        public ValueForseq(String fld_type, String itm){
            this.fld_type = fld_type;
            this.itm = itm;
        }

        public String getFld_type() {
            return fld_type;
        }

        public void setFld_type(String fld_type) {
            this.fld_type = fld_type;
        }

        public String getItm() {
            return itm;
        }

        public void setItm(String itm) {
            this.itm = itm;
        }



    }

    public void addQViewPro(int q_id) throws NullPointerException {


        try {
            //Base Box
            radioGroup = new RadioGroup(getActivity());
            radioGroup.setOrientation(LinearLayout.HORIZONTAL);
            radioGroup.setPadding(dpToPx(6), dpToPx(6), dpToPx(6), dpToPx(6));
            LinearLayout.LayoutParams radioGroupLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //radioGroupLP.setMargins(8, 0, 0, 0);
            // layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
            radioGroup.setLayoutParams(radioGroupLP);

            Log.i("444", " q_id is: " + q_id);
            for (int seq : treeMapForseq[q_id].keySet()) {
                Log.i("555", " seq is: " + seq);
                Log.i("666", " q_id is: " + q_id + "seq is : " + seq + "itm is: " + treeMapForseq[q_id].get(seq).getItm() + "fld_type is: " + treeMapForseq[q_id].get(seq).getFld_type());
                addWidget(q_id, treeMapForseq[q_id].get(seq).getFld_type(), treeMapForseq[q_id].get(seq).getItm());
                Log.i("999", "actual seq:" + seq_act + "The item:" + treeMapForseq[q_id].get(seq).getItm());
            }



            //Boxing
            HorizontalScrollView horizontalScrollView = new HorizontalScrollView(getActivity());
            horizontalScrollView.addView(radioGroup);
            LinearLayout linearLayout = new LinearLayout(getActivity());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.addView(horizontalScrollView);


            LinearLayout linearLayoutAdded = new LinearLayout(getActivity());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            String tagForll = "ll_"+q_id;
            linearLayoutAdded.setTag(tagForll);
            GlobalConstants.llcontainerForQuestion.put(q_id,tagForll);
            Log.i("12345","" + linearLayoutAdded.getTag());

            linearLayout.addView(linearLayoutAdded);

            ll_membercard_register2_pr_show_q.addView(linearLayout);
            //ll_membercard_register2_pr_show_q.addView(radioGroup);
        }catch (NullPointerException e){e.printStackTrace();}

    }


    public void addWidget(int q_id,String fld_type,String itm)throws NullPointerException {
        try {
            seq_act++;
            switch (fld_type) {
                case "TextView":
                    TextView textView = new TextView(getActivity());
                    textView.setText(itm);
                    textView.setTextSize(18);
                    LinearLayout.LayoutParams textViewLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    //layoutParams.setMargins(12, 0, 0, 0);
                    // layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                    textView.setLayoutParams(textViewLP);
                    textView.setMinWidth(dpToPx(180));
                    radioGroup.addView(textView);
                    Log.i("1000", "add it TV when  " + seq_act);

                    break;

                case "RadioButton":
                    RadioButton radioButton = new RadioButton(getActivity());
                    radioButton.setText(itm);
                    LinearLayout.LayoutParams radioButtonLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    radioButtonLP.setMargins(dpToPx(12), 0, 0, 0);
                    radioButtonLP.gravity = Gravity.CENTER_HORIZONTAL;
                    radioButton.setLayoutParams(radioButtonLP);
                    radioGroup.addView(radioButton);
                    Log.i("1000", "add it RB when  " + seq_act);

                    // ll_membercard_register2_pr_show_q.addView(radioGroup);
                    radioButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View vv) {
                            GlobalConstants.rb_pr_show_qForRadioButton.put(q_id, (itm.equals("Y")) ? true : false);
                            TextView tv = new TextView(getActivity());
                            tv.setText((itm.equals("Y")) ? "Y" : "N");
                            tv.setTextSize(18);
                            Log.i("1234","" +GlobalConstants.llcontainerForQuestion.get(q_id));
                            LinearLayout ll_added = ll_membercard_register2_pr_show_q.findViewWithTag(GlobalConstants.llcontainerForQuestion.get(q_id));
                            ll_added.removeAllViews();
                            ll_added.addView(tv);

                        }
                    });

                    break;
                default:
                    break;
            }
        }catch (NullPointerException e){e.printStackTrace();}
    }



    // RadioButton radioButtonN = new RadioButton(getActivity());
//        radioButtonN.setText(itm_list.get(2 * i + 1));
//
//        LinearLayout.LayoutParams radioButtonNLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        radioButtonNLP.setMargins(dpToPx(12), 0, 0, 0);
//        radioButtonN.setLayoutParams(radioButtonNLP);

//        radioButtonsList.add(radioButtonY);
//        radioButtons2List.add(radioButtonN);
//



    // radioGroup.addView(radioButtonN);

    //Must be setting the Check after adding to RadioGroup. Otherwise,it won't work

//For local store
//        if (GlobalConstants.rb_pr_show_q != null) {
//            if (GlobalConstants.rb_pr_show_q.get(i + 1) != null) {
//                if (GlobalConstants.rb_pr_show_q.get(i + 1).equals("Y")) {
//                    radioButtonY.setChecked(true);
//                    radioButtonN.setChecked(false);
//                } else if (GlobalConstants.rb_pr_show_q.get(i + 1).equals("N")) {
//                    radioButtonY.setChecked(false);
//                    radioButtonN.setChecked(true);
//                }
//            } else {
//                radioButtonY.setChecked(false);
//                radioButtonN.setChecked(false);
//            }
//        } else {
//            radioButtonY.setChecked(false);
//            radioButtonN.setChecked(false);
//        }


    // ll_membercard_register2_pr_show_q.removeAllViews();


    //m
//        radioButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                GlobalConstants.rb_pr_show_q.put(i + 1, "Y");  //1 for Yes Button
//                //Toast.makeText(getActivity(),"Y"+i,Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        radioButtonN.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                GlobalConstants.rb_pr_show_q.put(i + 1, "N");  //0 for No Button
//
//
//            }
//        });




    public void addQView(String questions, int i) throws NullPointerException{

        RadioGroup radioGroup = new RadioGroup(getActivity());
        TextView textView = new TextView(getActivity());

        RadioButton radioButtonY = new RadioButton(getActivity());
        RadioButton radioButtonN = new RadioButton(getActivity());


        radioGroup.setOrientation(LinearLayout.HORIZONTAL);
        radioGroup.setPadding(dpToPx(6), dpToPx(6), dpToPx(6), dpToPx(6));
        LinearLayout.LayoutParams radioGroupLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //radioGroupLP.setMargins(8, 0, 0, 0);
        // layoutParams.gravity = Gravity.CENTER_HORIZONTAL;

        radioGroup.setLayoutParams(radioGroupLP);


        textView.setText(questions);
        textView.setTextSize(18);
        LinearLayout.LayoutParams textViewLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //layoutParams.setMargins(12, 0, 0, 0);
        // layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        textView.setLayoutParams(textViewLP);

        radioButtonY.setText(itm_list.get(2 * i));
        LinearLayout.LayoutParams radioButtonYLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        radioButtonYLP.setMargins(dpToPx(12), 0, 0, 0);
        radioButtonYLP.gravity = Gravity.CENTER_HORIZONTAL;
        radioButtonY.setLayoutParams(radioButtonYLP);


        radioButtonN.setText(itm_list.get(2 * i + 1));

        LinearLayout.LayoutParams radioButtonNLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        radioButtonNLP.setMargins(dpToPx(12), 0, 0, 0);
        radioButtonN.setLayoutParams(radioButtonNLP);


        radioButtonsList.add(radioButtonY);
        radioButtons2List.add(radioButtonN);



        radioGroup.addView(textView);
        radioGroup.addView(radioButtonY);
        radioGroup.addView(radioButtonN);

        //Must be setting the Check after adding to RadioGroup. Otherwise,it won't work


        if (GlobalConstants.rb_pr_show_q != null) {
            if (GlobalConstants.rb_pr_show_q.get(i + 1) != null) {
                if (GlobalConstants.rb_pr_show_q.get(i + 1).equals("Y")) {
                    radioButtonY.setChecked(true);
                    radioButtonN.setChecked(false);
                } else if (GlobalConstants.rb_pr_show_q.get(i + 1).equals("N")) {
                    radioButtonY.setChecked(false);
                    radioButtonN.setChecked(true);
                }
            } else {
                radioButtonY.setChecked(false);
                radioButtonN.setChecked(false);
            }
        } else {
            radioButtonY.setChecked(false);
            radioButtonN.setChecked(false);
        }


        // ll_membercard_register2_pr_show_q.removeAllViews();
        ll_membercard_register2_pr_show_q.addView(radioGroup);


        //m
        radioButtonY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GlobalConstants.rb_pr_show_q.put(i + 1, "Y");  //1 for Yes Button
                //Toast.makeText(getActivity(),"Y"+i,Toast.LENGTH_SHORT).show();
            }
        });


        radioButtonN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalConstants.rb_pr_show_q.put(i + 1, "N");  //0 for No Button


            }
        });

    }

    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px)
    {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    // Call API without sending data to server
    public void pr_show_qAPI() {


        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);


        progressDialog.setIcon(null);
        progressDialog.setTitle("");
        if(isAdded()) {
            progressDialog.setMessage(getString(R.string.cms_progress_dialog_msg));
        }
        progressDialog.show();

        String url =  EMemberCardURL.UMPFWDServerLink+"/api/PreRegistration/pr_show_q";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                response -> {
                    Log.i(TAG, "success at jsonObjectRequest ");
                    try {

                        // Data putted inside the fragment
                        // Data from the API
                        JSONArray qList = response.getJSONArray("q");
                        JSONArray q_itemList = response.getJSONArray("q_itm");

                        TextView[] textViews = new TextView[qList.length()];


                        treeMapForQ = new TreeMap<>();
                        treeMapForseq = new TreeMap[qList.length()+1];

                        for (int j = 0; j < q_itemList.length(); j++) {
                            JSONObject q_itmJSON = q_itemList.getJSONObject(j);
                            int q_id =q_itmJSON.getInt("q_id");
                            String itm = q_itmJSON.getString("itm");
                            String seq_String = q_itmJSON.getString("seq");
                            int seq = Integer.parseInt(seq_String);

                            treeMapForseq(q_id,seq,"RadioButton",itm);

                        }


                        for (int i = 0; i < qList.length(); i++) {
                            JSONObject qJSON = qList.getJSONObject(i);

                            String questions = qJSON.getString("q");
                            int q_id = qJSON.getInt("q_id");
                            treeMapForseq(q_id,0,"TextView",questions);
                            treeMapForq_id(q_id);
                            //addQView(questions, i);
                        }


                        initViewPro();
                        progressDialog.dismiss();
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        // normally it just works
                        e.printStackTrace();
                        Log.e(TAG, "JSONException", e);
                    }
                },
                error -> {
                    progressDialog.dismiss();
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

    /**
     * Get the token from the API and store it inside the master data
     */
    public void sendTokenRequest(String ServerURL, Runnable method) {
        Log.i(TAG,"Call Token");
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        ProgressDialog progressDialog = new ProgressDialog(getActivity());

        String url = ServerURL + "/Token";

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

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

}

