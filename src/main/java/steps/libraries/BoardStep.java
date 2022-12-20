package steps.libraries;

import baseresponse.BaseResponse;
import io.restassured.http.ContentType;
import lombok.SneakyThrows;
import trelloutils.DataGenerator;
import trelloutils.TrelloParams;

import java.io.FileReader;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class BoardStep extends BaseResponse {
    public void getBoard() {
        response = given()
                .contentType(ContentType.JSON)
                .queryParam("key", TrelloParams.TRELLO_KEY.getValue())
                .queryParam("token", TrelloParams.TRELLO_TOKEN.getValue())
                .when()
                .get(TrelloParams.TRELLO_BASE_URL.getValue() + TrelloParams.TRELLO_BOARD_ID.getValue());
    }

    public void createABoard() {
        String boardName = DataGenerator.getInstance().faker.dog().breed();
        response = given()
                .contentType(ContentType.JSON)
                .queryParam("name", boardName)
                .queryParam("key", TrelloParams.TRELLO_KEY.getValue())
                .queryParam("token", TrelloParams.TRELLO_TOKEN.getValue())
                .when()
                .post(TrelloParams.TRELLO_BASE_URL.getValue());
    }


    public void statueCode(int statusCode) {
        response.then().statusCode(statusCode);
    }

     @SneakyThrows
    public void deleteBoard(){
        Properties properties = new Properties();
        FileReader fileReader = new FileReader("src/main/resources/boardlogger.properties");
        properties.load(fileReader);
        String id = properties.getProperty("id");
        System.out.println(id);
        response = given()
                .contentType(ContentType.JSON)
                .queryParam("key", TrelloParams.TRELLO_KEY.getValue())
                .queryParam("token", TrelloParams.TRELLO_TOKEN.getValue())
                .when()
                .delete(TrelloParams.TRELLO_BASE_URL.getValue() +id);
    }
}
