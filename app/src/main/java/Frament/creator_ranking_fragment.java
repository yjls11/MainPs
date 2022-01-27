package Frament;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainps.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import Adapter.rankingAdapter;
import VO.rankingVO;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class creator_ranking_fragment extends Fragment {

    LinearLayoutManager linearLayoutManager;
    rankingAdapter rankingAdapter;
    ArrayList<rankingVO> items = new ArrayList<>();
    RecyclerView recyclerview2;
    TextView rankmusicName, ranksingername;
    ImageView rankmusicImg;

    ImageView ranknum;
//    Button rankartistbtn;
    int[] drawables = new int[]{R.drawable.rk, R.drawable.rk2, R.drawable.rk3, R.drawable.rk4, R.drawable.rk5};

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View fragment = inflater.inflate(R.layout.fragment_creator_ranking, container, false);
        FrameLayout frameLayouts = fragment.findViewById(R.id.frame_bookmark);
        SharedPreferences sp = getActivity().getPreferences((Context.MODE_PRIVATE));

        recyclerview2 = fragment.findViewById(R.id.recyclerview2);
        rankmusicName = fragment.findViewById(R.id.ranksingername);
        ranksingername = fragment.findViewById(R.id.ranksingername);
        ranknum=fragment.findViewById(R.id.ranknum);

        items = new ArrayList<>();


        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                recyclerview2.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                rankingAdapter = new rankingAdapter(items);
                recyclerview2.setAdapter(rankingAdapter);
            }
        };


        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();


//        linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
//        recyclerview2.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
//        rankingAdapter = new rankingAdapter(items);
//        recyclerview2.setAdapter(rankingAdapter);


        RequestBody requestBody = new FormBody.Builder()
                .build();

        // 요청 만들기
        Request request = new Request.Builder()
                .url("http://172.30.1.41:5000/RankingMusicList")
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
                        Log.e("asdfasdfsad",jsonArray_from.length()+"");
                        JSONObject jbform = jsonArray_from.getJSONObject(v);
                        byte[] encodeByte = Base64.decode(jsonArray_img.get(v).toString(), Base64.DEFAULT);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

                        String name = jbform.getString("mu_name");
                        String singer = jbform.getString("m_id");
                        addItems(name, singer, bitmap, drawables[v]);
                        Message msg = handler.obtainMessage();
                        handler.sendMessage(msg);

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        return fragment;
    }

    private  void addItems(String rankmusicName, String ranksingername, Bitmap rankmusicImg, Integer ranknum) {

        rankingVO rankvo = new rankingVO();


            rankvo.setRankmusicImg(rankmusicImg);
            rankvo.setRanksingername(ranksingername);
            rankvo.setRankmusicName(rankmusicName);
            rankvo.setRanknum(ranknum);



        items.add(rankvo);

        }
}
