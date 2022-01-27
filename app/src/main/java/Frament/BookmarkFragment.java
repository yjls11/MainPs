package Frament;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainps.R;
import com.example.mainps.RbPreference;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import Adapter.bookmarkAdapter;
import VO.BookmarkVO;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class BookmarkFragment extends Fragment {


    bookmarkAdapter bookmarkAdapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<BookmarkVO> item = new ArrayList<>();
    RecyclerView bm_recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View fragment = inflater.inflate(R.layout.fragment_bookmark, container, false);
        FrameLayout frameLayout= fragment.findViewById(R.id.frame_bookmark);
        Context context = fragment.getContext();
        SharedPreferences sp = getActivity().getPreferences((Context.MODE_PRIVATE));
        bm_recyclerView = fragment.findViewById(R.id.bm_recyclerView);

        item = new ArrayList<>();

        //프래그먼트 새로고침
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();

        RbPreference pref = new RbPreference();
        String id = pref.getValue("Login_id", null);

        Handler handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                bm_recyclerView.setAdapter(bookmarkAdapter);
            }
        };

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("id",id)
                .build();

        Request request = new Request.Builder()
                .url("http://172.30.1.41:5000/favoriatelist")
                .post(requestBody)
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("즐겨찾기 리스트 응답 실패 : ", "응답 실패");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                        Log.e("즐겨찾기 리스트 통신 확인",response.body().string());
                try{
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray jsonArray_img = jsonObject.getJSONArray("img");
                    JSONArray jsonArray_from = jsonObject.getJSONArray("from");

                    for (int i = 0; i < jsonArray_from.length(); i++){
                        JSONObject jbfrom = jsonArray_from.getJSONObject(i);

                        byte[] encodeByte = Base64.decode(jsonArray_img.get(i).toString(), Base64.DEFAULT);
                        Bitmap bitmap_cover = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

                        String id = jbfrom.getString("m_id");
                        String song = jbfrom.getString("mu_name");
                        String tag1 = jbfrom.getString("mu_tag1");
                        String tag2 = jbfrom.getString("mu_tag2");
                        String tag3 = jbfrom.getString("mu_tag3");

                        addbookmark(id,song,"#"+tag1,"#"+tag2,"#"+tag3, bitmap_cover);
                    }
                    Message msg = handler.obtainMessage();
                    handler.sendMessage(msg);


                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });



//        bookmarkAdapter.notifyDataSetChanged();
        linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        bm_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        bookmarkAdapter = new bookmarkAdapter(item);
//        bm_recyclerView.setAdapter(bookmarkAdapter);
        bm_recyclerView.setLayoutManager(new LinearLayoutManager(context));

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(bm_recyclerView);


        return fragment;
    }
    ItemTouchHelper.SimpleCallback callback =new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Snackbar snackbar = Snackbar.make(bm_recyclerView, "음악 리스트가 삭제 됩니다.", Snackbar.LENGTH_LONG);
            snackbar.show();

            item.remove(viewHolder.getAdapterPosition());
            bookmarkAdapter.notifyDataSetChanged();
        }
    };



    private  void  addbookmark(String bmmusicName ,String bmsingername,String bmhashtag1,String bmhashtag2,String bmhashtag3, Bitmap bmmusicImage){

        BookmarkVO bmVO =new BookmarkVO();
        bmVO.setBmmusicName(bmmusicName);
        bmVO.setBmsingername(bmsingername);
        bmVO.setBmhashtag1(bmhashtag1);
        bmVO.setBmhashtag2(bmhashtag2);
        bmVO.setBmhashtag3(bmhashtag3);
        bmVO.setBmmusicImage(bmmusicImage);

        item.add(bmVO);
    }
}




