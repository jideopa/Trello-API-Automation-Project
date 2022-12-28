package trelloutils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class TrelloGetABoardThread extends Thread{
    @Override
    public void run() {
       getABoard();
    }

    public void getABoard() {

        Properties properties = new Properties();
        try {
            properties.load(new FileReader("src/main/resources/boardlogger.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String id = properties.getProperty("id");
        System.out.println(id);
      Response response = given()
                .contentType(ContentType.JSON)
                .queryParam("key", TrelloParams.TRELLO_KEY.getValue())
                .queryParam("token", TrelloParams.TRELLO_TOKEN.getValue())
                .when()
                .get(TrelloParams.TRELLO_BASE_URL.getValue() + id);
      response.then().statusCode(200).log().all();
    }
}
