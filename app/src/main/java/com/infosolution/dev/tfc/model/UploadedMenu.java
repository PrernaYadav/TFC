package com.infosolution.dev.tfc.model;

/**
 * Created by amit on 1/19/2018.
 */

public class UploadedMenu {
    String pronameupld;
    String timinguplad;
    String qtyupld;
    String priceupld;
    String imgupld;

    public int getImgveg() {
        return imgveg;
    }

    public void setImgveg(int imgveg) {
        this.imgveg = imgveg;
    }

    int imgveg;

    public UploadedMenu() {
        this.pronameupld = pronameupld;
        this.timinguplad = timinguplad;
        this.qtyupld = qtyupld;
        this.priceupld = priceupld;
        this.imgupld = imgupld;
    }

    public String getPronameupld() {
        return pronameupld;
    }

    public void setPronameupld(String pronameupld) {
        this.pronameupld = pronameupld;
    }

    public String getTiminguplad() {
        return timinguplad;
    }

    public void setTiminguplad(String timinguplad) {
        this.timinguplad = timinguplad;
    }

    public String getQtyupld() {
        return qtyupld;
    }

    public void setQtyupld(String qtyupld) {
        this.qtyupld = qtyupld;
    }

    public String getPriceupld() {
        return priceupld;
    }

    public void setPriceupld(String priceupld) {
        this.priceupld = priceupld;
    }

    public String getImgupld() {
        return imgupld;
    }

    public void setImgupld(String imgupld) {
        this.imgupld = imgupld;
    }
}
