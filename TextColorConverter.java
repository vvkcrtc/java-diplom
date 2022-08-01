package ru.netology.graphics.image;

public class  TextColorConverter implements TextColorSchema {
    protected char symbolMap [] = { '▇', '●', '◉', '◍', '◎', '○', '☉', '◌', '-'};
    final int MAX_COLOR_VALUE = 255;
    final int TEXT_COLOR_INTERVAL = MAX_COLOR_VALUE / symbolMap.length;
    @Override
    public char convert(int color) {
        int symbolMapPos = color / TEXT_COLOR_INTERVAL;
        if (symbolMapPos < 0) {
            return symbolMap[0];
        } else if (symbolMapPos >= symbolMap.length) {
            return symbolMap[symbolMap.length-1];
        } else {
            return symbolMap[symbolMapPos];
        }
    }
}
