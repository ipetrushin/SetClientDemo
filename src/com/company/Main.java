package com.company;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

class Card {
    int count, fill, shape, color;
    // TODO: добавить функцию toString()
    // TODO: реализовать функцию equals()
}

// 0) описать класс Card с необходимыми полями и функциями (toString(), equals(), getThird())
// 1) дополнить классы необходимыми полями, чтобы они подходили для любого запроса и ответа
class Request {
    String action, nickname;
    // предусмотреть конструкторы для каждого типа запросов (3 шт)
    public Request(String action, String nickname, int token) {
        this.action = action;
        this.nickname = nickname;
        this.token = token;
    }
    public static Request register(String nickname) {
        return new Request("register", nickname, -1);
    }
    public static Request fetch_cards(int token) {
        return new Request("fetch_cards", "", token);
    }

    int token;

}

class Response {
    String status;
    int token;
    ArrayList<Card> cards;
}

public class Main {
    public static Response serverRequest(Request req) {

        // 2) доработать функцию и обработать исключения
        // измените номер порта на тот, что у Вашего сервера
        String set_server_url = "http://194.176.114.21:8053";
        Gson gson = new Gson();
        try {
            URL url = new URL(set_server_url);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true); // setting POST method

            OutputStream out = urlConnection.getOutputStream();
            out.write(gson.toJson(req).getBytes());
            InputStream stream = urlConnection.getInputStream();
            Response response = gson.fromJson(new InputStreamReader(stream), Response.class);
            return response;
        } catch (IOException e ) { return null; }
    }

    public static void main(String[] args) throws IOException {

        Request req = new Request("register", "Petya3", -1);
        req = Request.register("Petya");
        Response resp = serverRequest(req);
        System.out.println(resp.token);

        int token = 3337221;
        req = new Request("fetch_cards", "", token);
        req = Request.fetch_cards(token);
        resp = serverRequest(req);
        System.out.println(resp.cards.size());
        // 3)
        // зарегистрировться на сервере и получить токен
        // получить список карт с сервера
        // найти среди 12 карт 3 составляющие сет
        // отправить запрос с 3 картами (сетом) на сервер
        // вывести ответ сервера (число очков)
        // можно повторять запросы, пока есть сеты
    }

    // если не получается сделать самим, можно посмотреть в шпаргалку
    // https://gist.github.com/ipetrushin/08ceffe7a11019656718c1716bc91a85
}
