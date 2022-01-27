package Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainps.PlayerActivity;
import com.example.mainps.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import Frament.FragmentMainActivity;
import VO.rankingVO;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class rankingAdapter extends RecyclerView.Adapter<rankingAdapter.ViewHolder> {

    String id;
    BufferedInputStream reader;
    Uri uri;
    File afile;

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView rankmusicName, ranksingername;
        ImageView ranknum,rankartistbtn,rankmusicImg;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rankmusicName =  itemView.findViewById(R.id.rankmusciName);
            rankmusicName.setSingleLine(true);
            rankmusicName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            rankmusicName.setSelected(true);

            ranksingername = itemView.findViewById(R.id.ranksingername);
            ranksingername.setSingleLine(true);
            ranksingername.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            ranksingername.setSelected(true);


            rankmusicImg = itemView.findViewById(R.id.rankmusicImg);
            ranknum=itemView.findViewById(R.id.ranknum);
            rankartistbtn=itemView.findViewById(R.id.rankartistbtn);


        }
    }
    private ArrayList<rankingVO> ranklist = null;

    public rankingAdapter(ArrayList<rankingVO>ranklist) {this.ranklist = ranklist;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.music_rankinglist,parent,false);
        ViewHolder rank= new ViewHolder(view);
        return rank;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        rankingVO rankvo = ranklist.get(position);

        holder.rankmusicImg.setImageBitmap(rankvo.getRankmusicImg());
        holder.ranksingername.setText(rankvo.getRanksingername());
        holder.rankmusicName.setText(rankvo.getRankmusicName());
        holder.ranknum.setImageResource(rankvo.getRanknum());

        holder.rankartistbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.jamendo.com/artist/434337/anny-sky"));
                ((FragmentMainActivity)view.getContext()).startActivity(intent);

            }
        });

        holder.rankmusicImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("노래 재생 클릭", "이거클릭함 ->> "+rankvo.getRankmusicImg());
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("name", String.valueOf(rankvo.getRankmusicName()))
                        .build();

                Request request = new Request.Builder()
                        .url("http://172.30.1.41:5000/RankingMusicplay")
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
                            afile = new File(Environment.getExternalStorageDirectory() , "11_file.mp4");
                            OutputStream out = new FileOutputStream(afile);

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
                        Uri uri = Uri.fromFile(afile);
                        Intent intent = new Intent(view.getContext(), PlayerActivity.class);
                        intent.putExtra("music",uri);
                        intent.putExtra("name",rankvo.getRanksingername());
                        intent.putExtra("cover",rankvo.getRankmusicImg());
                        ((FragmentMainActivity)view.getContext()).startActivity(intent);

//                        intent.putExtra("seqNum",mvo.getMu_seq());

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {

        return ranklist.size();
    }
}
