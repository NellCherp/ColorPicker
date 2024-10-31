package com.nellecodes.colorpicker;

public class Choice {

    private String colorHue;
    private String colorTxt;
    private String colorPicked;
    private String time;
    private String correct;

    public Choice() {}
    public Choice(String colorHue, String colorPicked, String colorTxt, String time, String correct) {
        this.colorPicked = colorPicked;
        this.colorHue = colorHue;
        this.colorTxt = colorTxt;
        this.time = time;
        this.correct = correct;

    }
    public String getColorHue() {
        return colorHue;
    }

    public void setColorHue(String colorHue) {
        this.colorHue = colorHue;
    }
    public String getColorTxt() {
        return colorTxt;
    }
    public String getCorrect() {
        return correct;
    }
    public void setCorrect(String correct) {
        this.correct = correct;
    }
    public void setColorTxt(String colorTxt) {
        this.colorTxt = colorTxt;
    }
    public String getColorPicked() {
        return colorPicked;
    }

    public void setColorPicked(String colorPicked) {
        this.colorPicked = colorPicked;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
