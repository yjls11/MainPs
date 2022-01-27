package VO;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class ArtistSpaceVO {

    private String musicName;
    private String singername;
    private String hashtag1;
    private String hashtag2;
    private String hashtag3;
    private Bitmap musicImage;

    public ArtistSpaceVO(String musicName, String singername, String hashtag1, String hashtag2, String hashtag3, Bitmap musicImage) {
        this.musicName = musicName;
        this.singername = singername;
        this.hashtag1 = hashtag1;
        this.hashtag2 = hashtag2;
        this.hashtag3 = hashtag3;
        this.musicImage = musicImage;
    }
    public ArtistSpaceVO(ArrayList<ArtistSpaceVO> As_item){

    }
    public ArtistSpaceVO() {

    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getSingername() {
        return singername;
    }

    public void setSingername(String singername) {
        this.singername = singername;
    }

    public String getHashtag1() {
        return hashtag1;
    }

    public void setHashtag1(String hashtag1) {
        this.hashtag1 = hashtag1;
    }

    public String getHashtag2() {
        return hashtag2;
    }

    public void setHashtag2(String hashtag2) {
        this.hashtag2 = hashtag2;
    }

    public String getHashtag3() {
        return hashtag3;
    }

    public void setHashtag3(String hashtag3) {
        this.hashtag3 = hashtag3;
    }

    public Bitmap getMusicImage() {
        return musicImage;
    }

    public void setMusicImage(Bitmap musicImage) {
        this.musicImage = musicImage;
    }
}