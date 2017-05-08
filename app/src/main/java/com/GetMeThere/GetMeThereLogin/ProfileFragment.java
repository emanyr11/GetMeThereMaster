package com.GetMeThere.GetMeThereLogin;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.GetMeThere.GetMeThereLogin.models.ServerRequest;
import com.GetMeThere.GetMeThereLogin.models.ServerResponse;
import com.GetMeThere.GetMeThereLogin.models.User;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

public class ProfileFragment extends Fragment implements View.OnClickListener{

    private TextView tv_name,tv_class,tv_class2,tv_message,tv_date;
    private SharedPreferences pref;
    private long date = System.currentTimeMillis();
    private AppCompatButton btn_change_password,btn_logout;
    private EditText et_old_password,et_new_password;
    private AlertDialog dialog;
    private ProgressBar progress;
    String JSON_STRING;
    String J_STRING1;
    String J_STRING2;
    String email = "email";
    String json_url;
    //String name = "name";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        pref = getActivity().getPreferences(0);
        tv_name.setText(pref.getString(Constants.EMAIL,""));
        email = tv_name.getText().toString();
        if(email.equals("emanyr11@gmail.com"))
        {
            json_url = "https://getmethereapp.000webhostapp.com/e_timetable.php";
        }
        else if (email.equals("nitish@gmail.com"))
        {
            json_url = "https://getmethereapp.000webhostapp.com/n_timetable.php";
        }
        String formattedDate = new SimpleDateFormat("MMM dd EEE, yyyy ").format(Calendar.getInstance().getTime());
        tv_date.setText(formattedDate);
    }

    class BackgroundTask extends AsyncTask<Void,Void,String>
    {
        //String json_url;

        @Override
        protected void onPreExecute() {
          /*  if(email == "emanyr11@gmail.com")
            {
                json_url = "https://getmethereapp.000webhostapp.com/e_timetable.php";
            }
            else
            {
                json_url = "https://getmethereapp.000webhostapp.com/n_timetable.php";
            }*/
            //json_url = "https://getmethereapp.000webhostapp.com/e_timetable.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(json_url);
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();
                    while((JSON_STRING = br.readLine())!= null)
                    {
                        stringBuilder.append(JSON_STRING+"\n");
                    }
                    br.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    JSON_STRING = stringBuilder.toString().trim();
                    JSONObject parentObject = new JSONObject(JSON_STRING);
                    JSONArray parentArray = parentObject.getJSONArray("server_response");
                    JSONObject finalObject = parentArray.getJSONObject(0);
                    String classStart = finalObject.getString("Class Start");
                    String classFinish = finalObject.getString("Class Finish");
                    String location = finalObject.getString("Location");
                    String paper = finalObject.getString("Paper");
                    JSONObject finalObject2 = parentArray.getJSONObject(2);
                    String classStart2 = finalObject2.getString("Class Start");
                    String classFinish2 = finalObject2.getString("Class Finish");
                    String location2 = finalObject2.getString("Location");
                    String paper2 = finalObject2.getString("Paper");
                    J_STRING1 ="Class Time: "+classStart  + " - " + classFinish +"\nLocation: "+ location + "\nPaper: " + paper;
                    J_STRING2 ="Class Time: "+classStart2  + " - " + classFinish2 +"\nLocation: "+ location2 + "\nPaper: " + paper2;
                    return stringBuilder.toString().trim();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
          //tv_class.setText(result);
            tv_class.setText(J_STRING1);
            tv_class2.setText(J_STRING2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        initViews(view);
        return view;
    }




    private void initViews(View view){
        new BackgroundTask().execute();
        tv_name = (TextView)view.findViewById(R.id.tv_name);
        tv_date = (TextView)view.findViewById(R.id.tv_date);
        tv_class = (TextView)view.findViewById(R.id.tv_class);
        tv_class2 = (TextView)view.findViewById(R.id.tv_class2);
        btn_change_password = (AppCompatButton)view.findViewById(R.id.btn_chg_password);
        btn_logout = (AppCompatButton)view.findViewById(R.id.btn_logout);
        btn_change_password.setOnClickListener(this);
        btn_logout.setOnClickListener(this);

    }


    private void showDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_change_password, null);
        et_old_password = (EditText)view.findViewById(R.id.et_old_password);
        et_new_password = (EditText)view.findViewById(R.id.et_new_password);
        tv_message = (TextView)view.findViewById(R.id.tv_message);
        progress = (ProgressBar)view.findViewById(R.id.progress);
        builder.setView(view);
        builder.setTitle("Change Password");
        builder.setPositiveButton("Change Password", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String old_password = et_old_password.getText().toString();
                    String new_password = et_new_password.getText().toString();
                    if(!old_password.isEmpty() && !new_password.isEmpty()){

                        progress.setVisibility(View.VISIBLE);
                        changePasswordProcess(pref.getString(Constants.EMAIL,""),old_password,new_password);

                    }else {

                        tv_message.setVisibility(View.VISIBLE);
                        tv_message.setText("Fields are empty");
                    }
                }
            });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_chg_password:
                showDialog();
                break;
            case R.id.btn_logout:
                logout();
                break;
        }
    }

    private void logout() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(Constants.IS_LOGGED_IN,false);
        editor.putString(Constants.EMAIL,"");
        editor.putString(Constants.NAME,"");
        editor.putString(Constants.UNIQUE_ID,"");
        editor.apply();
        goToLogin();
    }

    private void goToLogin(){

        Fragment login = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame,login);
        ft.commit();
    }

    private void changePasswordProcess(String email,String old_password,String new_password){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setEmail(email);
        user.setOld_password(old_password);
        user.setNew_password(new_password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.CHANGE_PASSWORD_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                if(resp.getResult().equals(Constants.SUCCESS)){
                    progress.setVisibility(View.GONE);
                    tv_message.setVisibility(View.GONE);
                    dialog.dismiss();
                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                }else {
                    progress.setVisibility(View.GONE);
                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText(resp.getMessage());

                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(Constants.TAG,"failed");
                progress.setVisibility(View.GONE);
                tv_message.setVisibility(View.VISIBLE);
                tv_message.setText(t.getLocalizedMessage());


            }
        });
    }


}
