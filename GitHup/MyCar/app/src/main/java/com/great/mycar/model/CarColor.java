package com.great.mycar.model;

public class CarColor {
    private String white="0",red="0",black="0",blue,green="0",yellow="0",orange="0",gray="0";

    public CarColor(String white, String red, String black, String blue, String green, String yellow, String orange, String gray) {
        this.white = white;
        this.red = red;
        this.black = black;
        this.blue = blue;
        this.green = green;
        this.yellow = yellow;
        this.orange = orange;
        this.gray = gray;
    }

    public CarColor() {
    }

    public String getWhite() {
        return white;
    }

    public void setWhite(String white) {
        this.white = white;
    }

    public String getRed() {
        return red;
    }

    public void setRed(String red) {
        this.red = red;
    }

    public String getBlack() {
        return black;
    }

    public void setBlack(String black) {
        this.black = black;
    }

    public String getBlue() {
        return blue;
    }

    public void setBlue(String blue) {
        this.blue = blue;
    }

    public String getGreen() {
        return green;
    }

    public void setGreen(String green) {
        this.green = green;
    }

    public String getYellow() {
        return yellow;
    }

    public void setYellow(String yellow) {
        this.yellow = yellow;
    }

    public String getOrange() {
        return orange;
    }

    public void setOrange(String orange) {
        this.orange = orange;
    }

    public String getGray() {
        return gray;
    }

    public void setGray(String gray) {
        this.gray = gray;
    }
}
