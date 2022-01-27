package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainps.R;

import java.util.ArrayList;

import VO.coverlistVO;

public class coverlistAdapter extends RecyclerView.Adapter<coverlistAdapter.ViewHolder> {


    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image_cover;
        TextView mu_name_art, mu_hashtag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image_cover = (ImageView) itemView.findViewById(R.id.image_cover);
            mu_name_art = (TextView) itemView.findViewById(R.id.mu_name_art);
            mu_hashtag = (TextView) itemView.findViewById(R.id.mu_hashtag);
        }
    }
    private ArrayList<coverlistVO> clist = null;

    public coverlistAdapter(ArrayList<coverlistVO> clist){
        this.clist = clist;
    }

    //vo 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.coverlist,parent,false);
        coverlistAdapter.ViewHolder cv = new coverlistAdapter.ViewHolder(view);
        return cv;
    }
    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull coverlistAdapter.ViewHolder holder, int position) {
        coverlistVO vo = clist.get(position);

        holder.image_cover.setImageResource(R.drawable.sokodomo);
        holder.mu_name_art.setText(vo.getMu_name_art());
        holder.mu_hashtag.setText(vo.getMu_hashtag());

    }

    @Override
    public int getItemCount() {
        return clist.size();
    }
}

