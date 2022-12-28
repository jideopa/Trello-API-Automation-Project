package steps.libraries;

import baseresponse.BaseResponse;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import trelloutils.TrelloDeleteBoardThread;
import trelloutils.DataGenerator;
import trelloutils.TrelloGetABoardThread;
import trelloutils.TrelloParams;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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


    public void deleteBoardWithThread(){
         TrelloDeleteBoardThread trelloDeleteBoardThread = new TrelloDeleteBoardThread();
         trelloDeleteBoardThread.run();

     }

     public void getABoardWithThread(){
         TrelloGetABoardThread trelloGetABoardThread = new TrelloGetABoardThread();
         trelloGetABoardThread.run();
     }

    public void storeBoardID(){
        Properties properties = new Properties();
            JsonPath jsonPath = response.jsonPath();
            String boardId = jsonPath.get("id");
            String name = jsonPath.getString("name");
        properties.setProperty("name", name);
            properties.setProperty("id", boardId);
        try {
            properties.store(new FileWriter("src/main/resources/boardlogger.properties"), "Trello");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
