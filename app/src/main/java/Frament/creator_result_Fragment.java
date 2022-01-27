package Frament;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainps.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import Adapter.musiclistAdapter;
import VO.coverlistVO;
import VO.musiclistVO;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class creator_result_Fragment extends Fragment {

    LinearLayoutManager linearLayoutManager;
    musiclistAdapter adapter;
    ArrayList<musiclistVO> items = new ArrayList<>();
    RecyclerView recyclerView, Recyclerview;
    ArrayList<coverlistVO> data2;
    Bitmap bitmap_cover; // 커버
    String name, artist, tag1, tag2, tag3, tag4, video;
    TextView tvtag1, tvtag2, tvtag3, tvtag4;
    VideoView video_result;
    String mu_seq;
    ImageView playimg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        postponeEnterTransition(250, TimeUnit.MILLISECONDS);


        View fragment = inflater.inflate(R.layout.fragment_creator_result, container, false);
        FrameLayout frame_home = fragment.findViewById(R.id.frame1);
        SharedPreferences sp = getActivity().getPreferences((Context.MODE_PRIVATE));

        recyclerView = fragment.findViewById(R.id.recycleview);
        tvtag1 = fragment.findViewById(R.id.tvtag1);
        tvtag2 = fragment.findViewById(R.id.tvtag2);
        tvtag3 = fragment.findViewById(R.id.tvtag3);
        tvtag4 = fragment.findViewById(R.id.tvtag4);
        video_result = fragment.findViewById(R.id.video_result);
//        playimg = fragment.findViewById(R.id.playimg);
        items = new ArrayList<>();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();

//        Handler handlerplay = new Handler(){
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                playimg.setOnClickListener(new View.OnClickListener() {
//                    @SuppressLint("HandlerLeak")
//                    @Override
//                    public void onClick(View view) {
////                        mediaPlayer.release();

//                        mediaPlayer = MediaPlayer.create(getActivity().getApplicationContext().this,uri);
////                        mediaPlayer = MediaPlayer.create()
//                        mediaPlayer.start();
//                    }
//                });
//            }
//        };

        Uri n = Uri.fromFile(new File("/sdcard/Download/videoss.mp4"));
        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                adapter = new musiclistAdapter(items);
                GridLayoutManager gridmgr = new GridLayoutManager(getActivity(), 4);
                gridmgr.setOrientation(RecyclerView.HORIZONTAL);
                recyclerView.setLayoutManager(gridmgr);
                recyclerView.setAdapter(adapter);
                tvtag1.setText("#"+tag1);
                tvtag2.setText("#"+tag2);
                tvtag3.setText("#"+tag3);
                tvtag4.setText("#"+tag4);
                video_result.setVideoURI(n);
                video_result.start();
                startPostponedEnterTransition();
            }
        };

        // Post
        String a = "a";
        RequestBody requestBody = new FormBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("asdf",a)
                .build();

        // 요청 만들기
        Request request = new Request.Builder()
                .url("http://172.30.1.41:5000/ModelList")
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
            public void onResponse(Call call, Response response) throws IOException {
//                Log.e("test : ", response.body().string());

                try{
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray jsonArray_img = jsonObject.getJSONArray("img");
                    JSONArray jsonArray_from = jsonObject.getJSONArray("from");

                    tag1 = jsonObject.getString("tag1");
                    tag2 = jsonObject.getString("tag2");
                    tag3 = jsonObject.getString("tag3");
                    tag4 = jsonObject.getString("tag4");



                    Random ran = new Random();
                    for (int v = 0; v < 12; v++) {
                        JSONObject jbform = jsonArray_from.getJSONObject(v);
                        int random = ran.nextInt(jbform.length());
                        byte[] encodeByte = Base64.decode(jsonArray_img.get(random).toString(), Base64.DEFAULT);
                        Log.e("바이트확인",""+encodeByte);
                        bitmap_cover = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

                        String id = jbform.getString("m_id");
                        String song = jbform.getString("mu_name");
                        mu_seq = jbform.getString("mu_seq");
                        Log.e("seq 보내주기 확인",""+mu_seq);

                        addItem(bitmap_cover,song,id, mu_seq);
                    }
                        Message msg = handler.obtainMessage();
                        handler.sendMessage(msg);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);

//
//        GridLayoutManager gridmgr = new GridLayoutManager(getActivity(), 4);
//        gridmgr.setOrientation(RecyclerView.VERTICAL);
//        recyclerView.setLayoutManager(gridmgr);
//

        return fragment;

    }

    private void addItem(Bitmap Cover, String m_name, String m_art, String museq) {

        musiclistVO mvo = new musiclistVO();

        mvo.setCover(Cover);
        mvo.setM_art(m_art);
        mvo.setM_name(m_name);
        mvo.setMu_seq(museq);

        items.add(mvo);


    }
}