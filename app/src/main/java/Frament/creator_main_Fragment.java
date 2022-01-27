package Frament;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.mainps.LoadingActivity;
import com.example.mainps.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class creator_main_Fragment extends Fragment {

    ImageButton  btn_Album;
    private boolean recording = false;
    File file, videofile;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragment = inflater.inflate(R.layout.fragment_creator_main, container, false);

        btn_Album = fragment.findViewById(R.id.btn_artist);


        SharedPreferences pref = getActivity().getPreferences(Context.MODE_PRIVATE);


        btn_Album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
//                Intent intent1 = new Intent(getActivity(), LoadingActivity.class);
//                startActivity(intent1);
            }
        });
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Uri dataUri = data.getData();
            InputStream in = getActivity().getContentResolver().openInputStream(dataUri);
            String date = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date());
            file = new File(Environment.getExternalStorageDirectory(), date + "_file.mp4");
//            videofile = new File(Environment.getExternalStorageDirectory(),"file");
//            RbPreference pref = new RbPreference(getActivity().getApplicationContext());
//            pref.put("Login_id", file);
            Log.e("경로", "" + file);
            FileOutputStream out = new FileOutputStream(file);
//            FileOutputStream out2 = new FileOutputStream(videofile);
            Log.e("output", "" + out);
//            Log.e("output2", "" + out2);
            Log.e("outputfile",""+file);
//            Log.e("outputfile2",""+videofile);

            try {

                int read;
                byte[] bytes = new byte[1024]; // 1024

                while ((read = in.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            in.close();
            out.close();

        } catch (Exception ioe) {
            ioe.printStackTrace();
        }

        // okhttp3
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), RequestBody.create(MultipartBody.FORM, file))
                .build();
        Request request = new Request.Builder()
                .url("http://172.30.1.41:5000/upload")
                // Server URL 은 본인 IP를 입력
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.d("Test : ", response.body().string());
            }
        });

//        btnImageSend.setEnabled(true);
        Intent intent1 = new Intent(getActivity(), LoadingActivity.class);
        startActivity(intent1);
    }
}
