package com.example.mainps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    EditText LoginID, LoginPW;
    Button btnLogin, btnjoin;
    int type = 0;
    Intent intent;
    boolean test = true;
    boolean Login = true;
    String a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginID = findViewById(R.id.LoginID);
        LoginPW = findViewById(R.id.LoginPW);
        btnLogin = findViewById(R.id.btnLogin);
        btnjoin = findViewById(R.id.btnjoin);


        Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                Log.e("test3",a);
                if (a.equals("1")) {
                    Toast.makeText(getApplicationContext(), "로그인 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    if (a.equals("2")) {
                        intent = new Intent(getApplicationContext(), Frament.FragmentMainActivity.class);
                        startActivity(intent);
                    } else if (a.equals("3")) {
                        intent = new Intent(getApplicationContext(), Frament.Fragment_artist_main_Activity.class);
                        startActivity(intent);
                    }
                }
            }
        };


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (LoginID.length() == 0) {
                    Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (LoginPW.length() == 0) {
                    Toast.makeText(getApplicationContext(), "패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else if (LoginID.length() == 0 || LoginPW.length() == 0) {

                } else {
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("id", LoginID.getText().toString())
                            .addFormDataPart("pw", LoginPW.getText().toString())
                            .addFormDataPart("type", String.valueOf(type))
                            .build();

                    // 요청 만들기
                    Request request = new Request.Builder()
                            .url("http://172.30.1.41:5000/LoginSelect")
                            .post(requestBody)
                            .build();

                    OkHttpClient client = new OkHttpClient();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.e("로그인 응답 실패 : ", "로그인 응답 실패");
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            RbPreference pref = new RbPreference(getApplicationContext());
                            pref.put("Login_id", LoginID.getText().toString());
//                            Log.e("test : ", response.body().string());

                            try{
                                a = response.body().string();
                                Log.e("test2",a);

                                Message msg = handler.obtainMessage();
                                handler.sendMessage(msg);
                            } catch (Exception exception){
                                exception.printStackTrace();

                            }


                        }
                    });
                }
            }
        });
        btnjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),MemberTypeActivity.class);
                startActivity(intent);
            }
        });
    }
}