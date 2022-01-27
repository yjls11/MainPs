package VO;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class musiclistVO {

    private Bitmap Cover;
    private String m_name, img2, m_art;
    private String mu_seq;

    public musiclistVO() {
    }

    public Bitmap getCover() {
        return Cover;
    }

    public void setCover(Bitmap cover) {
        Cover = cover;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getM_art() {
        return m_art;
    }

    public void setM_art(String m_art) {
        this.m_art = m_art;
    }

    public String getMu_seq() {
        return mu_seq;
    }

    public void setMu_seq(String mu_seq) {
        this.mu_seq = mu_seq;
    }

    public musiclistVO(Bitmap cover, String m_name, String m_art, String mu_seq) {
        this.Cover = cover;
        this.m_name = m_name;
        this.m_art = m_art;
        this.mu_seq = mu_seq;
    }

}