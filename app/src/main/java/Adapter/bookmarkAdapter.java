package Adapter;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainps.R;

import java.util.ArrayList;

import Frament.BookmarkFragment;
import VO.BookmarkVO;

public class bookmarkAdapter extends RecyclerView.Adapter<bookmarkAdapter.ViewHolder> {
    public class  ViewHolder extends RecyclerView.ViewHolder{
        ImageView bmmusicImg;
        TextView bmmusicName,bmsingerName,bmhashtag1,bmhashtag2,bmhashtag3;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bmmusicName=itemView.findViewById(R.id.bm_musciName);
            bmmusicName.setSingleLine(true);
            bmmusicName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            bmmusicName.setSelected(true);

            bmsingerName=itemView.findViewById(R.id.bm_singername);
            bmsingerName.setSingleLine(true);
            bmsingerName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            bmsingerName.setSelected(true);

            bmhashtag1=itemView.findViewById(R.id.bm_Hashtag1);
            bmhashtag2=itemView.findViewById(R.id.bm_Hashtag2);
            bmhashtag3=itemView.findViewById(R.id.bm_Hashtag3);
            bmmusicImg = itemView.findViewById(R.id.bm_musicImg);


        }


    }
    private  ArrayList<BookmarkVO> bookmarkList=null;


    public  bookmarkAdapter(ArrayList<BookmarkVO>bookmarkList){
        this.bookmarkList=bookmarkList;
    }

    @NonNull
    @Override
    public bookmarkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context =parent.getContext();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.bookmark_item,parent,false);
        bookmarkAdapter.ViewHolder viewHolder = new bookmarkAdapter.ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull bookmarkAdapter.ViewHolder holder, int position) {

        BookmarkVO bmVO=bookmarkList.get(position);

        holder.bmmusicName.setText(bmVO.getBmmusicName());
        holder.bmsingerName.setText(bmVO.getBmsingername());
        holder.bmhashtag1.setText(bmVO.getBmhashtag1());
        holder.bmhashtag2.setText(bmVO.getBmhashtag2());
        holder.bmhashtag3.setText(bmVO.getBmhashtag3());
        holder.bmmusicImg.setImageBitmap(bmVO.getBmmusicImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    @Override
    public int getItemCount() {
        return bookmarkList.size();
    }
}
