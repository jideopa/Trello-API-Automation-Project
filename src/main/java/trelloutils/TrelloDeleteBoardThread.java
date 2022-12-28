package trelloutils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class TrelloDeleteBoardThread extends Thread {


    @Override
    public void run() {
       delete();
    }

    public void delete(){
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("src/main/resources/boardlogger.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String id = properties.getProperty("id");
        System.out.println(id);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

      Response  response = given()
                .contentType(ContentType.JSON)
                .queryParam("key", TrelloParams.TRELLO_KEY.getValue())
                .queryParam("token", TrelloParams.TRELLO_TOKEN.getValue())
                .when()
                .delete(TrelloParams.TRELLO_BASE_URL.getValue() +id);
        response.then()
                .statusCode(200)
                .log().all();
    }
}
