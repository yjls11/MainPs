//package com.example.mainps;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.FrameLayout;
//
//import java.util.ArrayList;
//
//import Adapter.coverlistAdapter;
//import Adapter.musiclistAdapter;
//import VO.coverlistVO;
//import VO.musiclistVO;
//
//public class creator_result_Activity extends Fragment {
//
//
//    LinearLayoutManager linearLayoutManager;
//    musiclistAdapter adapter;
//    ArrayList<musiclistVO> items = new ArrayList<>();
//    RecyclerView recyclerView, Recyclerview;
//    ArrayList<coverlistVO> data2;
//    Adapter.coverlistAdapter coverlistAdapter;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        View fragment = inflater.inflate(R.layout.fragment_creator_result, container, false);
//        FrameLayout frame_home = fragment.findViewById(R.id.frame1);
//        SharedPreferences sp = getActivity().getPreferences((Context.MODE_PRIVATE));
//
//        items = new ArrayList<>();
//
//        Recyclerview = fragment.findViewById(R.id.Recycleview);
//        recyclerView = fragment.findViewById(R.id.recycleView);
//        //음악 리스트 recycle view
//        for (int i = 0; i < 9; i++) {
//            addItem("cover", "img", "img1", "3:20", "노래제목",
//                    "노래제목", "가수이름");
//
//        }
//        linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
//
//        adapter = new musiclistAdapter(items);
//        //Recyclerview.setLayoutManager(linearLayoutManager);
//        GridLayoutManager gridmgr = new GridLayoutManager(getActivity(), 3);
//        gridmgr.setOrientation(RecyclerView.HORIZONTAL);
//        Recyclerview.setLayoutManager(gridmgr);
//        Recyclerview.setAdapter(adapter);
//
//        return fragment;
//    }
//    private void addItem(String Cover, String img1, String img2, String m_time, String m_name1, String m_name, String m_art) {
//        musiclistVO mvo = new musiclistVO();
//        mvo.setCover(Cover);
//        mvo.setImg1(img1);
//        mvo.setImg2(img2);
//        mvo.setM_art(m_art);
//        mvo.setM_name(m_name);
//        mvo.setM_name1(m_name1);
//        mvo.setM_time(m_time);
//        items.add(mvo);
//
//        //----------------------------------구분선------------------------------------
//
//        //커버리스트 recycle view
//        data2 = new ArrayList<>();
//
//        for(int i=0; i<5; i++){
//            addItem1("iconName", "박경원/박경원","#겨울#여룸#가을#봄");
//        }
//        coverlistAdapter =new coverlistAdapter(data2);
//        recyclerView.setAdapter(coverlistAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
//
//    }
//
//    private void addItem1(String image_cover, String mu_name_art, String mu_hashtag) {
//
//        coverlistVO vo = new coverlistVO();
//        vo.setImage_cover(image_cover);
//        vo.setMu_name_art(mu_name_art);
//        vo.setMu_hashtag(mu_hashtag);
//        data2.add(vo);
//
//    }
//
//}
