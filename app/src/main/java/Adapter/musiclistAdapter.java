package Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainps.PlayerActivity;
import com.example.mainps.R;
import com.example.mainps.RbPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import Frament.FragmentMainActivity;
import VO.musiclistVO;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class musiclistAdapter  extends RecyclerView.Adapter<musiclistAdapter.ViewHolder> {

    boolean isCheck = false;
    String id;
    BufferedInputStream reader;
    ImageView urlbtn;
    Uri uri;
    File file;
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView Cover,img1,img2;
        TextView m_art, m_name,mu_seq;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Cover = (ImageView) itemView.findViewById(R.id.Cover);
//            img1 = (ImageView) itemView.findViewById(R.id.img1);
            img2 = (ImageView) itemView.findViewById(R.id.img2);
            urlbtn =itemView.findViewById(R.id.urlbtn);

            m_art=itemView.findViewById(R.id.m_art);
            m_art.setSingleLine(true);
            m_art.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            m_art.setSelected(true);

            m_name=itemView.findViewById(R.id.m_name);
            m_name.setSingleLine(true);
            m_name.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            m_name.setSelected(true);


            mu_seq = (TextView) itemView.findViewById(R.id.mu_seq);
//            m_time = (TextView) itemView.findViewById(R.id.m_time);

            RbPreference rbPreference = new RbPreference();
            id = rbPreference.getValue("Login_id", null);
            Log.e("id 세션 확인",""+id);


            /*img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 즐겨찾기 성공
                    if(isCheck == false){
                        img2.setImageResource(R.drawable.star_1);
                        isCheck = true;

                        RequestBody requestBody = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("id",id)
                                .build();

                        Request request = new Request.Builder()
                                .url("http://172.30.1.41:5000/")
                                .post(requestBody)
                                .build();

                        OkHttpClient client = new OkHttpClient();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                Log.e("즐겨찾기 실패","즐겨찾기 *선택* 실패!");
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                Log.e("응답 성공","즐겨찾기 성공");
                                try{


                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });
                        // 즐겨찾기 해제
                    }else{
                        img2.setImageResource(R.drawable.star_2);
                        isCheck = false;

                        RequestBody requestBody = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                .addFormDataPart("id",id)
                                .build();

                        Request request = new Request.Builder()
                                .url("http://172.30.1.41:5000/")
                                .post(requestBody)
                                .build();

                        OkHttpClient client = new OkHttpClient();
                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                                Log.e("즐겨찾기 실패","즐겨찾기 *해제통신* 실패!");
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                                Log.e("응답 성공","즐겨찾기 해제 성공");

                                try{

                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                }
            });*/

        }
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            isCheck = true;
        }
    };

    private ArrayList<musiclistVO> mlist = null;

    public musiclistAdapter(ArrayList<musiclistVO> mlist){
        this.mlist = mlist;
    }

    //vo 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public musiclistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.musiclist,parent,false);
        musiclistAdapter.ViewHolder mv = new musiclistAdapter.ViewHolder(view);
        return mv;
    }
    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull musiclistAdapter.ViewHolder holder, int position) {

        musiclistVO mvo = mlist.get(position);

        holder.Cover.setImageBitmap(mvo.getCover());
//        holder.img1.setImageResource(R.drawable.sokodomo);
        holder.img2.setImageResource(R.drawable.star_1);
        holder.m_art.setText(mvo.getM_art());
        holder.m_name.setText(mvo.getM_name());
        holder.mu_seq.setText(mvo.getMu_seq());

        urlbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("art", String.valueOf(mvo.getM_art()))
                        .build();

                Request request = new Request.Builder()
                        .url("http://172.30.1.41:5000/Rink")
                        .post(requestBody)
                        .build();

                OkHttpClient client = new OkHttpClient();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.e("즐겨찾기 실패", "즐겨찾기 *선택* 실패!");
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                        Log.e("응답 성공", ""+response.body().string());


                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            JSONArray art = jsonObject.getJSONArray("art");

                            JSONObject url = art.getJSONObject(0);
                            String urlgo = url.getString("m_url");

                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse((urlgo)));
                            ((FragmentMainActivity)view.getContext()).startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(art));
//                ((FragmentMainActivity)view.getContext()).startActivity(intent);


            }
        });


//        Log.e("seq확인확인용", mu_seq);
//        holder.m_time.setText(mvo.getM_time());

        holder.m_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("노래 재생 클릭", "이거클릭함 ->> "+mvo.getMu_seq());
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("seq", String.valueOf(mvo.getMu_seq()))
                        .build();

                Request request = new Request.Builder()
                        .url("http://172.30.1.41:5000/Musicplay")
                        .post(requestBody)
                        .build();

                OkHttpClient client = new OkHttpClient();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.e("즐겨찾기 실패", "즐겨찾기 *선택* 실패!");
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        Log.e("응답 성공", "응답성공");
                        try {
                            int count;
                            InputStream in = response.body().byteStream();
                            reader = new BufferedInputStream(in);
                            file = new File(Environment.getExternalStorageDirectory() , "_file.mp4");
                            OutputStream out = new FileOutputStream(file);

                            byte[] data = new byte[1024];
                            long total = 0;
                            while ((count = in.read(data)) != -1) {
                                total += count;
                                out.write(data, 0, count);
                            }
                            out.flush();
                            out.close();
                            in.close();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        uri = Uri.fromFile(file);
                        Intent intent = new Intent(view.getContext(), PlayerActivity.class);
                        intent.putExtra("music",uri);
                        intent.putExtra("name",mvo.getM_name());
                        intent.putExtra("cover",mvo.getCover());
                        ((FragmentMainActivity)view.getContext()).startActivity(intent);

//                        intent.putExtra("seqNum",mvo.getMu_seq());

                    }
                });
            }
        });



        holder.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 즐겨찾기 성공
                if(isCheck == false) {
                    holder.img2.setImageResource(R.drawable.star_2);


                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("id", id)
                            .addFormDataPart("seq", String.valueOf(mvo.getMu_seq()))
                            .build();

                    Request request = new Request.Builder()
                            .url("http://172.30.1.41:5000/favoriate")
                            .post(requestBody)
                            .build();

                    OkHttpClient client = new OkHttpClient();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            Log.e("즐겨찾기 실패", "즐겨찾기 *선택* 실패!");
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            Log.e("응답 성공", ""+response.body().string());
                        }
                    });

                    Message msg = handler.obtainMessage();
                    handler.sendMessage(msg);
                    // 즐겨찾기 해제
                }else if (isCheck == true){
                    holder.img2.setImageResource(R.drawable.star_1);

                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("id",id)
                            .addFormDataPart("seq", String.valueOf(mvo.getMu_seq()))
                            .build();

                    Request request = new Request.Builder()
                            .url("http://172.30.1.41:5000/favoriateX")
                            .post(requestBody)
                            .build();

                    OkHttpClient client = new OkHttpClient();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            Log.e("즐겨찾기 실패","즐겨찾기 *해제통신* 실패!");
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            Log.e("응답 성공",""+response.body().string());

                            try{

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                    });
                    isCheck = false;
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
}
