package VO;

import android.graphics.Bitmap;
import android.view.View;

import java.util.ArrayList;

public class rankingVO {

    private String rankmusicName, ranksingername;
    //    private Bitmap rankmusicImg;
    private Integer ranknum;
    private Bitmap rankmusicImg;

    public rankingVO(String rankmusicName, String ranksingername, Bitmap rankmusicImg, Integer ranknum) {
        this.rankmusicName = rankmusicName;
        this.ranksingername = ranksingername;
        this.rankmusicImg = rankmusicImg;
        this.ranknum = ranknum;
    }

    public rankingVO(ArrayList<rankingVO> items) {

    }

    public String getRankmusicName() {
        return rankmusicName;
    }

    public void setRankmusicName(String rankmusicName) {
        this.rankmusicName = rankmusicName;
    }

    public String getRanksingername() {
        return ranksingername;
    }

    public void setRanksingername(String ranksingername) {
        this.ranksingername = ranksingername;
    }

    public Integer getRanknum() {
        return ranknum;
    }

    public void setRanknum(Integer ranknum) {
        this.ranknum = ranknum;
    }

    public Bitmap getRankmusicImg() {
        return rankmusicImg;
    }

    public void setRankmusicImg(Bitmap rankmusicImg) {
        this.rankmusicImg = rankmusicImg;
    }

    public rankingVO() {

    }
}