package Frament;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.example.mainps.R;
import com.example.mainps.RbPreference;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import Adapter.artistspaceAdapter;
import Adapter.bookmarkAdapter;
import VO.ArtistSpaceVO;
import VO.BookmarkVO;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Fragment_artist_Space extends Fragment {

    Adapter.artistspaceAdapter artistspaceAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<ArtistSpaceVO> Asitem;
    RecyclerView artist_recyclerView;
    Bitmap bitmap;
    String name, singer, tag1, tag2, tag3;
    File videofile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View fragment = inflater.inflate(R.layout.fragment_artist__space, container, false);
        Context context = fragment.getContext();
        FrameLayout frameLayout = fragment.findViewById(R.id.artist_space_frame);
        SharedPreferences sp = getActivity().getPreferences((Context.MODE_PRIVATE));
        artist_recyclerView = fragment.findViewById(R.id.artist_rectclerView);
        Asitem = new ArrayList<>();

        //프래그먼트 새로고침
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();

        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                artistspaceAdapter.notifyDataSetChanged();
                artist_recyclerView.setAdapter(artistspaceAdapter);
                artist_recyclerView.setLayoutManager(new LinearLayoutManager(context));
                Adapter.artistspaceAdapter artistspaceAdapter =new artistspaceAdapter(Asitem);


            }
        };

        
        RbPreference pref = new RbPreference();
        String id = pref.getValue("Login_id", null);
//        String id = "MAKKENZIE"; // 위 코드로 변환
        Log.e("로그인 아이디 확인", id);


        // okhttp 뷰
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id", id)
                .build();

        // 요청 만들기
        Request request = new Request.Builder()
                .url("http://172.30.1.41:5000/artistList")
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
                            //Log.e("test : ", response.body().string());

                try{
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray jsonArray_img = jsonObject.getJSONArray("img");
                    JSONArray jsonArray_from = jsonObject.getJSONArray("from");
                        for (int v = 0; v < jsonArray_from.length(); v++) {

                                JSONObject jbform = jsonArray_from.getJSONObject(v);
                                byte[] encodeByte = Base64.decode(jsonArray_img.get(v).toString(), Base64.DEFAULT);
                                bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                                name = jbform.getString("mu_name");
                                singer = jbform.getString("m_id");
                                tag1 = jbform.getString("mu_tag1");
                                tag2 = jbform.getString("mu_tag2");
                                tag3 = jbform.getString("mu_tag3");
                                addAs(name, singer, "#"+tag1, "#"+tag2, "#"+tag3, bitmap);
                                Message msg = handler.obtainMessage();
                                handler.sendMessage(msg);
//                            artistspaceAdapter.notifyDataSetChanged();

                        }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        artist_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        artistspaceAdapter = new artistspaceAdapter(Asitem);
        artist_recyclerView.setAdapter(artistspaceAdapter);
        artist_recyclerView.setLayoutManager(new LinearLayoutManager(context));

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(artist_recyclerView);

        return fragment;
    }
    ItemTouchHelper.SimpleCallback callback =new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Snackbar snackbar = Snackbar.make(artist_recyclerView, "음악 리스트가 삭제 됩니다.", Snackbar.LENGTH_LONG);
            snackbar.show();

            Asitem.remove(viewHolder.getAdapterPosition());
            artistspaceAdapter.notifyDataSetChanged();
        }
    };


    private  void  addAs(String musicName , String singername , String hashtag1, String hashtag2, String hashtag3 , Bitmap musicImage) {

        ArtistSpaceVO AsVO = new ArtistSpaceVO();
        AsVO.setMusicName(musicName);
        AsVO.setSingername(singername);
        AsVO.setHashtag1(hashtag1);
        AsVO.setHashtag2(hashtag2);
        AsVO.setHashtag3(hashtag3);
        AsVO.setMusicImage(musicImage);
        Asitem.add(AsVO);

    }


    }
