package com.example.mainps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class joinActivity extends AppCompatActivity {
    public ImageView pick;
    public static final int CAMERA_REQUEST = 100;
    public static final int STORAGE_REQUEST = 101;
    String cameraPermission[];
    String storagePermission[];
    Button btnMbJoin;
    EditText editID, editPW, editName, editPwC, editUrl;
    //    CircleImageView Mimg;
    RadioButton rbtnM, rbtnF;
    RadioGroup radiogroup;
    String sex;
    File files;
    int a = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        cameraPermission = new String[]{
                Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        btnMbJoin = findViewById(R.id.btnMbJoin);
        editID = findViewById(R.id.editID);
        editPW = findViewById(R.id.editPW);
        editName = findViewById(R.id.editName);
        editPwC = findViewById(R.id.editNick);
        editUrl = findViewById(R.id.editUrl);
        rbtnM = findViewById(R.id.rbtnM);
        rbtnF = findViewById(R.id.rbtnF);

        Intent intent = getIntent();
        a = intent.getIntExtra("URL",0);
        if (a == 1){
            editUrl.setVisibility(View.INVISIBLE);
        }


        // radio 버튼부
        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rbtnM) {
                    sex = "남";
                } else if (i == R.id.rbtnF) {
                    sex = "여";
                }
            }
        });

            btnMbJoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (editID.length() == 0 || editPW.length() == 0 || editName.length() == 0 || editPwC.length() == 0) {
                        Toast.makeText(getApplicationContext(), "회원 가입 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                        Log.e("회원가입 클릭", "" + btnMbJoin);

                    } else {


//                 get방식 파라미터 추가
//                HttpUrl.Builder urlBuilder = HttpUrl.parse("http://172.30.1.41:5000/MBJoin").newBuilder();
////                    urlBuilder.addQueryParameter("asdf", "1.0"); // 예시
//                String url = urlBuilder.build().toString();
//                    Log.e("url", "" + url);

                        Drawable d = pick.getDrawable();
                        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();

                        try {
                            String date = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date());
                            files = new File(Environment.getExternalStorageDirectory(), date + "_img.jpeg");

                            Log.e("경로체크", "" + files);
                            OutputStream out = new FileOutputStream(files);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        String date = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date());
                        // POST 파라미터 추가
                        RequestBody requestBody = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("id", editID.getText().toString())
                                .addFormDataPart("pw", editPW.getText().toString())
                                .addFormDataPart("pwc", editPwC.getText().toString())
                                .addFormDataPart("name", editName.getText().toString())
                                .addFormDataPart("date", date)
                                .addFormDataPart("type", String.valueOf(a))
                                .addFormDataPart("sex", sex)
                                .addFormDataPart("url", editUrl.getText().toString())
                                .addFormDataPart("imgFile", files.getName(), RequestBody.create(MultipartBody.FORM, files))
                                .build();

                        // 요청 만들기
                        Request request = new Request.Builder()
                                .url("http://172.30.1.41:5000/MBJoin")
                                .post(requestBody)
                                .build();

                        OkHttpClient client = new OkHttpClient();
                        client.newCall(request).enqueue(new Callback() {
                                                            @Override
                                                            public void onFailure(Call call, IOException e) {
                                                                Log.e("응답 실패 : ", "응답 실패");
                                                                e.printStackTrace();
                                                            }

                                                            @Override
                                                            public void onResponse(Call call, final Response response) throws IOException {
                                                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                                                startActivity(intent);
                                                            }
                                                        }
                        );
                    }
                }
            });

        pick = (ImageView) findViewById(R.id.profile_img);
        pick.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
                int picd = 0;
                if (picd == 0) {
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else {
                        pickFromGallery();
                    }
                } else if (picd == 1) {
                    if (!checkStoragePermission()) {
                        requestStoragePermission();
                    } else {
                        pickFromGallery();

                    }
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission() {
        requestPermissions(storagePermission, STORAGE_REQUEST);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;

    }

    private void pickFromGallery() {
        CropImage.activity().start(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        requestPermissions(cameraPermission, CAMERA_REQUEST);
    }

    private boolean checkCameraPermission() {
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result1 && result2;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Picasso.with(this).load(resultUri).into(pick);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST: {
                if (grantResults.length > 0) {
                    boolean camera_accepted = grantResults[0] == (PackageManager.PERMISSION_GRANTED);
                    boolean storage_accepted = grantResults[1] == (PackageManager.PERMISSION_GRANTED);
                    if (camera_accepted && storage_accepted) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(this, "카메라 권한 허용을 해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST: {
                if (grantResults.length > 0) {
                    boolean storage_accepted = grantResults[0] == (PackageManager.PERMISSION_GRANTED);
                    if (storage_accepted) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(this, "저장 권한을 허용해 주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
    }
}