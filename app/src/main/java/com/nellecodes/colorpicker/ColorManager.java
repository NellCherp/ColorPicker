package com.nellecodes.colorpicker;

import java.util.Random;

public class ColorManager {

    Color color, prevColor;
    Color colorTxt;

    public ColorManager() {
        setRandomColor();
        prevColor = Color.YELLOW;
    }


    public Color getColor() {
        return color;
    }
    public Color getColorTxt() {
        return colorTxt;
    }

    public void setRandomColor() {
        Color[] colors = Color.values();
        int randomIndex = new Random().nextInt(colors.length);
        if (prevColor != colors[randomIndex]) {
            color = colors[randomIndex];
            setRandomColorTxt(color);
            prevColor = color;
        } else {
            setRandomColor();
        }

    }
    public void setRandomColorTxt(Color color) {
        Color[] colors = Color.values();
        int randomIndex = new Random().nextInt(colors.length);
        if (colors[randomIndex] == color) {
           setRandomColorTxt(color);
        } else {
            colorTxt = colors[randomIndex];
        }

    }

}
