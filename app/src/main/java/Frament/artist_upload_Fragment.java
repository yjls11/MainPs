package Frament;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mainps.R;
import com.example.mainps.RbPreference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class artist_upload_Fragment extends Fragment implements CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

    EditText artist_musuc_name; // 곡 제목 작성란
    Button btnUplord;
    Spinner sp_Genre; // 장르선택
    ImageButton imgMusic, imgCover;
    CheckBox artist_hs1, artist_hs2, artist_hs3, artist_hs4, artist_hs5, artist_hs6, artist_hs7, artist_hs8,artist_hs9,artist_hs10,artist_hs11
            ,artist_hs12,artist_hs13,artist_hs14,artist_hs15,artist_hs16,artist_hs17,artist_hs18, artist_hs19,artist_hs20,artist_hs21;
    File music, Cover;
    ArrayList<String> tags = new ArrayList<>();
    Uri data;
    String tag1, tag2, tag3;
    int cnt = 0;
    int cnt2 = 0;
    Bitmap bitmap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View fragment = inflater.inflate(R.layout.fragment_artist_upload_, container, false);



        artist_musuc_name = fragment.findViewById(R.id.artist_musuc_name);
        btnUplord = fragment.findViewById(R.id.btnUplord);
        imgMusic = fragment.findViewById(R.id.imgMusic);
        imgCover = fragment.findViewById(R.id.imgCover);
        sp_Genre = fragment.findViewById(R.id.sp_Genre);
        artist_hs1 = fragment.findViewById(R.id.artist_hs1);
        artist_hs2 = fragment.findViewById(R.id.artist_hs2);
        artist_hs3 = fragment.findViewById(R.id.artist_hs3);
        artist_hs4 = fragment.findViewById(R.id.artist_hs4);
        artist_hs5 = fragment.findViewById(R.id.artist_hs5);
        artist_hs6 = fragment.findViewById(R.id.artist_hs6);
        artist_hs7 = fragment.findViewById(R.id.artist_hs7);
        artist_hs8 = fragment.findViewById(R.id.artist_hs8);
        artist_hs9 = fragment.findViewById(R.id.artist_hs9);
        artist_hs10 = fragment.findViewById(R.id.artist_hs10);
        artist_hs11 = fragment.findViewById(R.id.artist_hs11);
        artist_hs12 = fragment.findViewById(R.id.artist_hs12);
        artist_hs13 = fragment.findViewById(R.id.artist_hs13);
        artist_hs14 = fragment.findViewById(R.id.artist_hs14);
        artist_hs15 = fragment.findViewById(R.id.artist_hs15);
        artist_hs16 = fragment.findViewById(R.id.artist_hs16);
        artist_hs17 = fragment.findViewById(R.id.artist_hs17);
        artist_hs18 = fragment.findViewById(R.id.artist_hs18);
        artist_hs19 = fragment.findViewById(R.id.artist_hs19);
        artist_hs20 = fragment.findViewById(R.id.artist_hs20);
        artist_hs21 = fragment.findViewById(R.id.artist_hs21);




        artist_hs1.setOnCheckedChangeListener(this);
        artist_hs2.setOnCheckedChangeListener(this);
        artist_hs3.setOnCheckedChangeListener(this);
        artist_hs4.setOnCheckedChangeListener(this);
        artist_hs5.setOnCheckedChangeListener(this);
        artist_hs6.setOnCheckedChangeListener(this);
        artist_hs7.setOnCheckedChangeListener(this);
        artist_hs8.setOnCheckedChangeListener(this);
        artist_hs9.setOnCheckedChangeListener(this);
        artist_hs10.setOnCheckedChangeListener(this);
        artist_hs11.setOnCheckedChangeListener(this);
        artist_hs12.setOnCheckedChangeListener(this);
        artist_hs13.setOnCheckedChangeListener(this);
        artist_hs14.setOnCheckedChangeListener(this);
        artist_hs15.setOnCheckedChangeListener(this);
        artist_hs16.setOnCheckedChangeListener(this);
        artist_hs17.setOnCheckedChangeListener(this);
        artist_hs18.setOnCheckedChangeListener(this);
        artist_hs19.setOnCheckedChangeListener(this);
        artist_hs20.setOnCheckedChangeListener(this);
        artist_hs21.setOnCheckedChangeListener(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.genre_array, R.layout.spinner_color);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_Genre.setAdapter(adapter);
        sp_Genre.setOnItemSelectedListener(this);
        String date = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date());

        SharedPreferences pref = getActivity().getPreferences(Context.MODE_PRIVATE);



        // 음악 업로드 버튼
        imgMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
                Log.e("음악음악음악음악", "" + data);
            }
        });

        // 커버사진
        imgCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
//                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 2);
            }
        });
        sp_Genre.getOnItemSelectedListener().toString();

        // 완료버튼
        btnUplord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("sp ", sp_Genre + "");
                RbPreference perf = new RbPreference(getContext());
                String id = perf.getValue("Login_id", null);
                Log.e("아이디 확인", "" + id);


                Log.e("커버 경로 확인 ", "" + Cover);
                Log.e("name 확인", "" + artist_musuc_name.getText());
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("music", music.getName(), RequestBody.create(MultipartBody.FORM, music))
                        .addFormDataPart("cover", Cover.getName(), RequestBody.create(MultipartBody.FORM, Cover))
                        .addFormDataPart("name", artist_musuc_name.getText().toString())
                        .addFormDataPart("genre", sp_Genre.getSelectedItem().toString())
                        .addFormDataPart("tag1", tags.get(0))
                        .addFormDataPart("tag2", tags.get(1))
                        .addFormDataPart("tag3", tags.get(2))
                        .addFormDataPart("id", id)
                        .build();
                Request request = new Request.Builder()
                        .url("http://172.30.1.41:5000/music/upload")
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

                        Intent intent = new Intent(getActivity(),Fragment_artist_main_Activity.class);
                        intent.putExtra("musicupload",30);
                        startActivity(intent);
                    }
                });
            }
        });
        return fragment;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            try {
                Uri dataUri = data.getData();
                InputStream in = getActivity().getContentResolver().openInputStream(dataUri);
                String date = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date());
                music = new File(Environment.getExternalStorageDirectory(), date + "_Muisc.mp3");
                Log.e("music 경로", music + "");
                FileOutputStream out = new FileOutputStream(music);
                try {
                    int read;
                    byte[] bytes = new byte[1024];

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
        }
        if (requestCode == 2){
            try {
                Uri dataUri = data.getData();
                InputStream in = getActivity().getContentResolver().openInputStream(dataUri);
                String date = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date());
                bitmap = BitmapFactory.decodeStream(in);
                Cover = new File(Environment.getExternalStorageDirectory(), date + "_Cover.jpeg");

                OutputStream out = new FileOutputStream(Cover);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 0, out);
                Log.e("music 경로", Cover + "");

                in.close();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if(cnt == 2) {
            Toast.makeText(getActivity(), "최대 3개까지 선택 가능합니다", Toast.LENGTH_SHORT).show();

            if (!artist_hs1.isChecked()) {
                artist_hs1.setEnabled(false);
            }
            if (!artist_hs2.isChecked()) {
                artist_hs2.setEnabled(false);
            }
            if (!artist_hs3.isChecked()) {
                artist_hs3.setEnabled(false);
            }
            if (!artist_hs4.isChecked()) {
                artist_hs4.setEnabled(false);
            }
            if (!artist_hs5.isChecked()) {
                artist_hs5.setEnabled(false);
            }
            if (!artist_hs6.isChecked()) {
                artist_hs6.setEnabled(false);
            }
            if (!artist_hs7.isChecked()) {
                artist_hs7.setEnabled(false);
            }
            if (!artist_hs8.isChecked()) {
                artist_hs8.setEnabled(false);
            }
            if (!artist_hs9.isChecked()) {
                artist_hs9.setEnabled(false);
            }
            if (!artist_hs10.isChecked()) {
                artist_hs10.setEnabled(false);
            }
            if (!artist_hs11.isChecked()) {
                artist_hs11.setEnabled(false);
            }
            if (!artist_hs12.isChecked()) {
                artist_hs12.setEnabled(false);
            }
            if (!artist_hs13.isChecked()) {
                artist_hs13.setEnabled(false);
            }
            if (!artist_hs14.isChecked()) {
                artist_hs14.setEnabled(false);
            }
            if (!artist_hs15.isChecked()) {
                artist_hs15.setEnabled(false);
            }
            if (!artist_hs16.isChecked()) {
                artist_hs16.setEnabled(false);
            }
            if (!artist_hs17.isChecked()) {
                artist_hs17.setEnabled(false);
            }
            if (!artist_hs18.isChecked()) {
                artist_hs18.setEnabled(false);
            }
            if (!artist_hs19.isChecked()) {
                artist_hs19.setEnabled(false);
            }
            if (!artist_hs20.isChecked()) {
                artist_hs20.setEnabled(false);
            }
            if (!artist_hs21.isChecked()) {
                artist_hs21.setEnabled(false);
            }


        }


        artist_hs1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (artist_hs1.isChecked()) {
                    tags.add(artist_hs1.getText().toString());
                    cnt++;
                    cnt2++;
                    Log.e("1번 증가 cnt",cnt+"");
                    Log.e("1번 증가 size()",tags.size()+"");
                    Log.e("1번 증가",artist_hs1.getText().toString());
                } else {
                    tags.remove(artist_hs1.getText().toString());
                    cnt--;
                    cnt2--;
                    Log.e("1번 삭제 cnt",cnt+"");
                    Log.e("1번 삭제 size()",tags.size()+"");
                    Log.e("1번 삭제",artist_hs1.getText().toString());
                }
                Log.e("1","1");
            }
        });
        artist_hs2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (artist_hs2.isChecked()) {
                    tags.add(artist_hs2.getText().toString());
                    cnt++;
                    cnt2++;
                    Log.e("2번 증가 cnt",cnt+"");
                    Log.e("2번 증가 size()",tags.size()+"");
                    Log.e("2번 증가",artist_hs2.getText().toString());
                } else {
                    tags.remove(artist_hs2.getText().toString());
                    cnt--;
                    cnt2--;
                    Log.e("2번 삭제 cnt",cnt+"");
                    Log.e("2번 삭제 size()",tags.size()+"");
                    Log.e("2번 삭제",artist_hs2.getText().toString());
                }
                Log.e("2","2");
            }
        });

        artist_hs3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (artist_hs3.isChecked()) {
                    tags.add(artist_hs3.getText().toString());
                    cnt++;
                    cnt2++;
                    Log.e("3번 증가 cnt",cnt+"");
                    Log.e("3번 증가 size()",tags.size()+"");
                    Log.e("3번 증가",artist_hs3.getText().toString());
                } else  {
                    tags.remove(artist_hs3.getText().toString());
                    cnt--;
                    cnt2--;
                    Log.e("3번 삭제 cnt",cnt+"");
                    Log.e("3번 삭제 size()",tags.size()+"");
                    Log.e("3번 삭제",artist_hs3.getText().toString());
                }
                Log.e("3","3");
            }
        });
        artist_hs4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (artist_hs4.isChecked()) {
                    tags.add(artist_hs4.getText().toString());
                    cnt++;
                    Log.e("4번 증가 cnt",cnt+"");
                    Log.e("4번 증가 size()",tags.size()+"");
                    Log.e("4번 증가",artist_hs4.getText().toString());
                } else {
                    tags.remove(artist_hs4.getText().toString());
                    cnt--;
                    Log.e("4번 삭제 cnt",cnt+"");
                    Log.e("4번 삭제 size()",tags.size()+"");
                    Log.e("4번 삭제",artist_hs4.getText().toString());
                }
                Log.e("4","4");
            }
        });

        artist_hs5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (artist_hs5.isChecked()) {
                    tags.add(artist_hs5.getText().toString());
                    cnt++;
                    Log.e("5번 증가 cnt",cnt+"");
                    Log.e("5번 증가 size()",tags.size()+"");
                    Log.e("5번 증가",artist_hs5.getText().toString());
                } else {
                    tags.remove(artist_hs5.getText().toString());
                    cnt--;
                    Log.e("5번 삭제 cnt",cnt+"");
                    Log.e("5번 삭제 size()",tags.size()+"");
                    Log.e("5번 삭제",artist_hs5.getText().toString());
                }
                Log.e("5","5");
            }
        });

        artist_hs6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (artist_hs6.isChecked()) {
                    tags.add(artist_hs6.getText().toString());
                    cnt++;
                    Log.e("6번 증가 cnt",cnt+"");
                    Log.e("6번 증가 size()",tags.size()+"");
                    Log.e("6번 증가",artist_hs6.getText().toString());
                } else {
                    tags.remove(artist_hs6.getText().toString());
                    cnt--;
                    Log.e("6번 삭제 cnt",cnt+"");
                    Log.e("6번 삭제 size()",tags.size()+"");
                    Log.e("6번 삭제",artist_hs6.getText().toString());
                }
                Log.e("6","6");
            }
        });

        artist_hs7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (artist_hs7.isChecked()) {
                    tags.add(artist_hs7.getText().toString());
                    cnt++;
                    Log.e("7번 증가 cnt",cnt+"");
                    Log.e("7번 증가 size()",tags.size()+"");
                    Log.e("7번 증가",artist_hs7.getText().toString());
                } else {
                    tags.remove(artist_hs7.getText().toString());
                    cnt--;
                    Log.e("7번 삭제 cnt",cnt+"");
                    Log.e("7번 삭제 size()",tags.size()+"");
                    Log.e("7번 삭제",artist_hs7.getText().toString());
                }
                Log.e("7","7");
            }
        });

        artist_hs8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (artist_hs8.isChecked()) {
                    tags.add(artist_hs8.getText().toString());
                    cnt++;
                    Log.e("8번 증가 cnt",cnt+"");
                    Log.e("8번 증가 size()",tags.size()+"");
                    Log.e("8번 증가",artist_hs8.getText().toString());
                } else {
                    tags.remove(artist_hs8.getText().toString());
                    cnt--;
                    Log.e("8번 삭제 cnt",cnt+"");
                    Log.e("8번 삭제 size()",tags.size()+"");
                    Log.e("8번 삭제",artist_hs8.getText().toString());
                }
                Log.e("8","8");
            }
        });

        artist_hs9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (artist_hs9.isChecked()) {
                    tags.add(artist_hs9.getText().toString());
                    cnt++;
                    Log.e("9번 증가 cnt",cnt+"");
                    Log.e("9번 증가 size()",tags.size()+"");
                    Log.e("9번 증가",artist_hs9.getText().toString());
                } else {
                    tags.remove(artist_hs9.getText().toString());
                    cnt--;
                    Log.e("9번 삭제 cnt",cnt+"");
                    Log.e("9번 삭제 size()",tags.size()+"");
                    Log.e("9번 삭제",artist_hs9.getText().toString());
                }
                Log.e("9","9");
            }
        });
        artist_hs10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (artist_hs10.isChecked()) {
                    tags.add(artist_hs10.getText().toString());
                    cnt++;
                    Log.e("10번 증가 cnt",cnt+"");
                    Log.e("10번 증가 size()",tags.size()+"");
                    Log.e("10번 증가",artist_hs10.getText().toString());
                } else {
                    tags.remove(artist_hs10.getText().toString());
                    cnt--;
                    Log.e("10번 삭제 cnt",cnt+"");
                    Log.e("10번 삭제 size()",tags.size()+"");
                    Log.e("10번 삭제",artist_hs10.getText().toString());
                }
                Log.e("10","10");
            }
        });
        artist_hs11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (artist_hs11.isChecked()) {
                    tags.add(artist_hs11.getText().toString());
                    cnt++;
                    Log.e("11번 증가 cnt",cnt+"");
                    Log.e("11번 증가 size()",tags.size()+"");
                    Log.e("11번 증가",artist_hs11.getText().toString());
                } else {
                    tags.remove(artist_hs11.getText().toString());
                    cnt--;
                    Log.e("11번 삭제 cnt",cnt+"");
                    Log.e("11번 삭제 size()",tags.size()+"");
                    Log.e("11번 삭제",artist_hs11.getText().toString());
                }
                Log.e("11","11");
            }
        });
        artist_hs12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (artist_hs12.isChecked()) {
                    tags.add(artist_hs12.getText().toString());
                    cnt++;
                    Log.e("12번 증가 cnt",cnt+"");
                    Log.e("12번 증가 size()",tags.size()+"");
                    Log.e("12번 증가",artist_hs12.getText().toString());
                } else {
                    tags.remove(artist_hs12.getText().toString());
                    cnt--;
                    Log.e("12번 삭제 cnt",cnt+"");
                    Log.e("12번 삭제 size()",tags.size()+"");
                    Log.e("12번 삭제",artist_hs12.getText().toString());
                }
                Log.e("12","12");
            }
        });
        artist_hs13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (artist_hs13.isChecked()) {
                    tags.add(artist_hs13.getText().toString());
                    cnt++;
                    Log.e("13번 증가 cnt",cnt+"");
                    Log.e("13번 증가 size()",tags.size()+"");
                    Log.e("13번 증가",artist_hs13.getText().toString());
                } else {
                    tags.remove(artist_hs13.getText().toString());
                    cnt--;
                    Log.e("13번 삭제 cnt",cnt+"");
                    Log.e("13번 삭제 size()",tags.size()+"");
                    Log.e("13번 삭제",artist_hs13.getText().toString());
                }
                Log.e("13","13");
            }
        });
        artist_hs14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (artist_hs14.isChecked()) {
                    tags.add(artist_hs14.getText().toString());
                    cnt++;
                    Log.e("14번 증가 cnt",cnt+"");
                    Log.e("14번 증가 size()",tags.size()+"");
                    Log.e("14번 증가",artist_hs14.getText().toString());
                } else {
                    tags.remove(artist_hs14.getText().toString());
                    cnt--;
                    Log.e("14번 삭제 cnt",cnt+"");
                    Log.e("14번 삭제 size()",tags.size()+"");
                    Log.e("14번 삭제",artist_hs14.getText().toString());
                }
                Log.e("14","14");
            }
        });
        artist_hs15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (artist_hs15.isChecked()) {
                    tags.add(artist_hs15.getText().toString());
                    cnt++;
                    Log.e("15번 증가 cnt",cnt+"");
                    Log.e("15번 증가 size()",tags.size()+"");
                    Log.e("15번 증가",artist_hs8.getText().toString());
                } else {
                    tags.remove(artist_hs15.getText().toString());
                    cnt--;
                    Log.e("15번 삭제 cnt",cnt+"");
                    Log.e("15번 삭제 size()",tags.size()+"");
                    Log.e("15번 삭제",artist_hs15.getText().toString());
                }
                Log.e("15","15");
            }
        });
        artist_hs16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (artist_hs16.isChecked()) {
                    tags.add(artist_hs16.getText().toString());
                    cnt++;
                    Log.e("16번 증가 cnt",cnt+"");
                    Log.e("16번 증가 size()",tags.size()+"");
                    Log.e("16번 증가",artist_hs16.getText().toString());
                } else {
                    tags.remove(artist_hs16.getText().toString());
                    cnt--;
                    Log.e("16번 삭제 cnt",cnt+"");
                    Log.e("16번 삭제 size()",tags.size()+"");
                    Log.e("16번 삭제",artist_hs16.getText().toString());
                }
                Log.e("16","16");
            }
        });
        artist_hs17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (artist_hs17.isChecked()) {
                    tags.add(artist_hs17.getText().toString());
                    cnt++;
                    Log.e("17번 증가 cnt",cnt+"");
                    Log.e("17번 증가 size()",tags.size()+"");
                    Log.e("17번 증가",artist_hs17.getText().toString());
                } else {
                    tags.remove(artist_hs17.getText().toString());
                    cnt--;
                    Log.e("17번 삭제 cnt",cnt+"");
                    Log.e("17번 삭제 size()",tags.size()+"");
                    Log.e("17번 삭제",artist_hs17.getText().toString());
                }
                Log.e("17","17");
            }
        });
        artist_hs18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (artist_hs18.isChecked()) {
                    tags.add(artist_hs18.getText().toString());
                    cnt++;
                    Log.e("18번 증가 cnt",cnt+"");
                    Log.e("18번 증가 size()",tags.size()+"");
                    Log.e("18번 증가",artist_hs18.getText().toString());
                } else {
                    tags.remove(artist_hs18.getText().toString());
                    cnt--;
                    Log.e("18번 삭제 cnt",cnt+"");
                    Log.e("18번 삭제 size()",tags.size()+"");
                    Log.e("18번 삭제",artist_hs18.getText().toString());
                }
                Log.e("18","18");
            }
        });
        artist_hs19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (artist_hs19.isChecked()) {
                    tags.add(artist_hs19.getText().toString());
                    cnt++;
                    Log.e("19번 증가 cnt",cnt+"");
                    Log.e("19번 증가 size()",tags.size()+"");
                    Log.e("19번 증가",artist_hs19.getText().toString());
                } else {
                    tags.remove(artist_hs19.getText().toString());
                    cnt--;
                    Log.e("19번 삭제 cnt",cnt+"");
                    Log.e("19번 삭제 size()",tags.size()+"");
                    Log.e("19번 삭제",artist_hs19.getText().toString());
                }
                Log.e("19","19");
            }
        });
        artist_hs20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (artist_hs20.isChecked()) {
                    tags.add(artist_hs20.getText().toString());
                    cnt++;
                    Log.e("20번 증가 cnt",cnt+"");
                    Log.e("20번 증가 size()",tags.size()+"");
                    Log.e("20번 증가",artist_hs20.getText().toString());
                } else {
                    tags.remove(artist_hs20.getText().toString());
                    cnt--;
                    Log.e("20번 삭제 cnt",cnt+"");
                    Log.e("20번 삭제 size()",tags.size()+"");
                    Log.e("20번 삭제",artist_hs20.getText().toString());
                }
                Log.e("20","20");
            }
        });
        artist_hs21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (artist_hs21.isChecked()) {
                    tags.add(artist_hs21.getText().toString());
                    cnt++;
                    Log.e("21번 증가 cnt",cnt+"");
                    Log.e("21번 증가 size()",tags.size()+"");
                    Log.e("21번 증가",artist_hs21.getText().toString());
                } else {
                    tags.remove(artist_hs21.getText().toString());
                    cnt--;
                    Log.e("21번 삭제 cnt",cnt+"");
                    Log.e("21번 삭제 size()",tags.size()+"");
                    Log.e("21번 삭제",artist_hs21.getText().toString());
                }
                Log.e("21","21");
            }
        });


        if (cnt2 == 0) {
            artist_hs1.setEnabled(true);
            artist_hs2.setEnabled(true);
            artist_hs3.setEnabled(true);
            artist_hs4.setEnabled(true);
            artist_hs5.setEnabled(true);
            artist_hs6.setEnabled(true);
            artist_hs7.setEnabled(true);
            artist_hs8.setEnabled(true);
            artist_hs9.setEnabled(true);
            artist_hs10.setEnabled(true);
            artist_hs11.setEnabled(true);
            artist_hs12.setEnabled(true);
            artist_hs13.setEnabled(true);
            artist_hs14.setEnabled(true);
            artist_hs15.setEnabled(true);
            artist_hs16.setEnabled(true);
            artist_hs17.setEnabled(true);
            artist_hs18.setEnabled(true);
            artist_hs19.setEnabled(true);
            artist_hs20.setEnabled(true);
            artist_hs21.setEnabled(true);



        }


};

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String choice = parent.getItemAtPosition(position).toString();
        Toast.makeText(getActivity(), choice, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


