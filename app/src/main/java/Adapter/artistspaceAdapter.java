package Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import VO.ArtistSpaceVO;
import com.example.mainps.R;

import java.util.ArrayList;

public class artistspaceAdapter extends  RecyclerView.Adapter<artistspaceAdapter.ViewHolder>{
    public class  ViewHolder extends RecyclerView.ViewHolder{
        ImageView musicImg;
        TextView musicName,singerName,hashtag1,hashtag2,hashtag3;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            musicName=itemView.findViewById(R.id.musciName);
            musicName.setSingleLine(true);
            musicName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            musicName.setSelected(true);
            singerName=itemView.findViewById(R.id.singername);
            singerName.setSingleLine(true);
            singerName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            singerName.setSelected(true);

            hashtag1=itemView.findViewById(R.id.Hashtag1);
            hashtag2=itemView.findViewById(R.id.Hashtag2);
            hashtag3=itemView.findViewById(R.id.Hashtag3);
            musicImg = itemView.findViewById(R.id.musicImg);
        }

    }
    private  ArrayList<ArtistSpaceVO>artistspaceList=null;


    public  artistspaceAdapter(ArrayList<ArtistSpaceVO>artistspaceList){
        this.artistspaceList=artistspaceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context =parent.getContext();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.music_item_list,parent,false);
        artistspaceAdapter.ViewHolder viewHolder =new artistspaceAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull artistspaceAdapter.ViewHolder holder, int position) {

        ArtistSpaceVO AsVO=artistspaceList.get(position);

        holder.musicName.setText(AsVO.getMusicName());
        holder.singerName.setText(AsVO.getSingername());
        holder.hashtag1.setText(AsVO.getHashtag1());
        holder.hashtag2.setText(AsVO.getHashtag2());
        holder.hashtag3.setText(AsVO.getHashtag3());
        holder.musicImg.setImageBitmap(AsVO.getMusicImage());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return artistspaceList.size();
    }


}




