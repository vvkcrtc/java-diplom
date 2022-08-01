package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;


public class TextGraphicsConverterImpl implements TextGraphicsConverter {
    protected int maxTxtWidth;
    protected int maxTxtHeight;
    protected int newWidth;
    protected int newHeight;

    protected int srcWidth;
    protected int srcHeight;
    protected double maxRatio;
    protected TextColorSchema schema;


    public TextGraphicsConverterImpl(TextColorSchema schema) {
        setTextColorSchema(schema);
    }

    @Override
    public void setMaxWidth(int width) {
        maxTxtWidth = width;
    }

    @Override
    public void setMaxHeight(int height) {
        maxTxtHeight = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    /*
     * Определение разницы сторон между графической и текстовой картинкой
     *
     * */
    double getRatio(int firstParam, int secondParam) {
        double ratio = 1.0d;
        if (firstParam > secondParam) {
            ratio = (double) firstParam / secondParam;
        } else if (secondParam > firstParam) {
            ratio = (double) secondParam / firstParam * -1;
        }
        return ratio;
    }
    /*
     * Вычисление размеров картинки [ширина,высота]
     * */
    int[] newSize(int picWidth, int picHeight) {

        int[] size = new int[]{picWidth, picHeight};
        double widthRatio = getRatio(picWidth, maxTxtWidth);
        double heightRatio = getRatio(picHeight, maxTxtHeight);
        double totalRatio;
        if (widthRatio > 0) {
            totalRatio = Math.max(widthRatio, heightRatio);
            size[0] = (int) (picWidth / totalRatio);
            size[1] = (int) (picHeight / totalRatio);
        } else {
            totalRatio = Math.max(widthRatio, heightRatio);
            size[0] = (int) (picHeight * Math.abs(totalRatio));
            size[1] = (int) (picWidth * Math.abs(totalRatio));
        }

        return size;
    }


    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        BufferedImage img = ImageIO.read(new URL(url));
        srcWidth = img.getWidth();
        srcHeight = img.getHeight();
        double srcRatio = Math.abs(getRatio(srcWidth, srcHeight));
        if (srcRatio > maxRatio) {
            throw new BadImageSizeException(srcRatio, maxRatio);
        }
        int[] size = newSize(srcWidth, srcHeight);
        newWidth = size[0];
        newHeight = size[1];

        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = bwImg.createGraphics();
        graphics.drawImage(scaledImage, 0, 0, null);
        WritableRaster bwRaster = bwImg.getRaster();

        String rezultStr = "";

        for (int h = 0; h < newHeight; h++) {
            for (int w = 0; w < newWidth; w++) {
                int color = bwRaster.getPixel(w, h, new int[3])[0];
                char c = schema.convert(color);
                rezultStr += c; //запоминаем символ c, например, в двумерном массиве или как-то ещё на ваше усмотрение
            }
            rezultStr += '\n';
        }
        return rezultStr;
    }

    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }

    @Override
    public String toString() {
        return "Размер изображения : " + newWidth + "x" + newHeight + " , исходный размер картинки : " + srcWidth + "x" + srcHeight;
    }
}
