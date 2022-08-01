package ru.netology.graphics;

import ru.netology.graphics.image.TextColorConverter;
import ru.netology.graphics.image.TextColorSchema;
import ru.netology.graphics.image.TextGraphicsConverter;
import ru.netology.graphics.image.TextGraphicsConverterImpl;
import ru.netology.graphics.server.GServer;

import java.io.File;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) throws Exception {
        TextGraphicsConverter converter = new TextGraphicsConverterImpl(new TextColorConverter()); // Создайте тут объект вашего класса конвертера

        GServer server = new GServer(converter); // Создаём объект сервера
        server.start(); // Запускаем
//        converter.setMaxWidth(150);
//        converter.setMaxHeight(150);
//        converter.setMaxRatio(1.2);


        // Или то же, но с выводом на экран:
        //String url = "https://raw.githubusercontent.com/netology-code/java-diplom/main/pics/simple-test.png";
        //String url = "https://i.ibb.co/6DYM05G/edu0.jpg";
        //String url = "https://avatars.mds.yandex.net/i?id=91cdcc28f1775bbebdc9541fe4b0b64c-5877516-images-thumbs&n=13";
        //String url = "https://avatars.mds.yandex.net/i?id=cded116867bd0b66634652ac5f2a2296-4483167-images-thumbs&n=13";
        //String imgTxt = converter.convert(url);
        //System.out.println(converter.toString());
        //System.out.println(imgTxt);
    }
}
