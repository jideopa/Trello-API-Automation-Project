package steps.libraries;

import baseresponse.BaseResponse;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j2;
import trelloutils.DataGenerator;
import trelloutils.TrelloParams;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

@Log4j2
public class BoardStep extends BaseResponse {
    public void getBoard() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Properties properties = new Properties();
                FileReader fileReader = null;
                try {
                  fileReader = new FileReader(TrelloParams.BOARDLOGGER_PATH.getValue());
                    properties.load(fileReader);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String id = properties.getProperty("id");

                try {
                    assert fileReader!=null;
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    log.info("File Reader not close properly");
                }

                Response response = given()
                        .contentType(ContentType.JSON)
                        .queryParam("key", TrelloParams.TRELLO_KEY.getValue())
                        .queryParam("token", TrelloParams.TRELLO_TOKEN.getValue())
                        .when()
                        .get(TrelloParams.TRELLO_BASE_URL.getValue() + id);
                response.then().statusCode(200);

            }

        };
        runnable.run();
        log.info("Get board method is send");
    }

    public void createABoard() {
        log.info("Generating a board name");
        String boardName = DataGenerator.getInstance().faker.dog().breed();
        log.info("Board name \n" + boardName);
        response = given()
                .contentType(ContentType.JSON)
                .queryParam("name", boardName)
                .queryParam("key", TrelloParams.TRELLO_KEY.getValue())
                .queryParam("token", TrelloParams.TRELLO_TOKEN.getValue())
                .when()
                .post(TrelloParams.TRELLO_BASE_URL.getValue());
        log.info("Post method is send to create a board");
        JsonPath jsonPath = response.jsonPath();
        String boardId = jsonPath.get("id");
        log.info("Board id \n" + boardId);
    }

    public void statueCode(int statusCode) {
        response.then().statusCode(statusCode);
    }


    public void deleteBoard() {
        log.info("Creating a runnable thread to delete board");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                log.info("Reading.. properties to get board id ");
                Properties properties = new Properties();
                FileReader fileReader = null;
                try {
                    Thread.sleep(5000);
                    fileReader = new FileReader(TrelloParams.BOARDLOGGER_PATH.getValue());
                    properties.load(fileReader);
                } catch (InterruptedException | IOException exception) {
                    exception.printStackTrace();
                }
                try {
                    assert fileReader != null;
                    fileReader.close();
                } catch (IOException e) {
                   log.info("File not close properly");
                }

                String id = properties.getProperty("id");


                log.info("board id properties /n " + id);
                Response response = given()
                        .contentType(ContentType.JSON)
                        .queryParam("key", TrelloParams.TRELLO_KEY.getValue())
                        .queryParam("token", TrelloParams.TRELLO_TOKEN.getValue())
                        .when()
                        .delete(TrelloParams.TRELLO_BASE_URL.getValue() + id);
                response.then()
                        .statusCode(200);
                log.info("Delete method is sent to the server");

            }
        };
        runnable.run();

        log.info("Board is Deleted");
    }

    public void storeBoardID() {
        log.info("storing board id");
        Properties properties = new Properties();
        JsonPath jsonPath = response.jsonPath();
        String boardId = jsonPath.get("id");
        String name = jsonPath.getString("name");
        properties.setProperty("name", name);
        properties.setProperty("id", boardId);
          FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(TrelloParams.BOARDLOGGER_PATH.getValue());
            properties.store(fileWriter, "Trello");
        } catch (IOException e) {
            e.printStackTrace();
            log.info("Unable to store properties to file");
        }
        try {
            assert fileWriter!=null;
            fileWriter.close();
        } catch (IOException e) {
           e.printStackTrace();
        }

        log.info("Board id is stored");
    }
}
