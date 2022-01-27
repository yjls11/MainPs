package VO;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class BookmarkVO {

    private String bmmusicName;
    private String bmsingername;
    private String bmhashtag1;
    private String bmhashtag2;
    private String bmhashtag3;
    private Bitmap bmmusicImage;

    public BookmarkVO(String bmmusicName, String bmsingername, String bmhashtag1, String bmhashtag2, String bmhashtag3, Bitmap bmmusicImage) {
        this.bmmusicName = bmmusicName;
        this.bmsingername = bmsingername;
        this.bmhashtag1 = bmhashtag1;
        this.bmhashtag2 = bmhashtag2;
        this.bmhashtag3 = bmhashtag3;
        this.bmmusicImage = bmmusicImage;
    }
    public BookmarkVO(ArrayList<BookmarkVO> item){

    }

    public BookmarkVO() {

    }

    public String getBmmusicName() {
        return bmmusicName;
    }

    public void setBmmusicName(String bmmusicName) {
        this.bmmusicName = bmmusicName;
    }

    public String getBmsingername() {
        return bmsingername;
    }

    public void setBmsingername(String bmsingername) {
        this.bmsingername = bmsingername;
    }

    public String getBmhashtag1() {
        return bmhashtag1;
    }

    public void setBmhashtag1(String bmhashtag1) {
        this.bmhashtag1 = bmhashtag1;
    }

    public String getBmhashtag2() {
        return bmhashtag2;
    }

    public void setBmhashtag2(String bmhashtag2) {
        this.bmhashtag2 = bmhashtag2;
    }

    public String getBmhashtag3() {
        return bmhashtag3;
    }

    public void setBmhashtag3(String bmhashtag3) {
        this.bmhashtag3 = bmhashtag3;
    }

    public Bitmap getBmmusicImage() {
        return bmmusicImage;
    }

    public void setBmmusicImage(Bitmap bmmusicImage) {
        this.bmmusicImage = bmmusicImage;
    }
}
