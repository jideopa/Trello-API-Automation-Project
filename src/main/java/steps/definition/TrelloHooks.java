package steps.definition;

import baseresponse.BaseResponse;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import steps.libraries.BoardStep;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.config.ConnectionConfig.connectionConfig;

public class TrelloHooks extends BaseResponse {
    static Properties properties = new Properties();

   @After(order = 1)
    public void logResponse() {
      response.then().log().all();
    }

   // @After(order = 1)
    public void storeBoardID(Scenario scenario) throws IOException {

        String i = scenario.getName();

        if (i.equals("Create a Board")) {
            JsonPath jsonPath = response.jsonPath();
            String boardId = jsonPath.get("id");
            String name = jsonPath.getString("name");
            FileReader fileReader = new FileReader("src/main/resources/boardlogger.properties");
            properties.load(fileReader);

            System.out.println(scenario.getName());
            properties.setProperty("name", name);
            properties.setProperty("id", boardId);
            properties.store(new FileWriter("src/main/resources/boardlogger.properties"), "Trello");
            fileReader.close();
        }
        else {
            System.out.println("Invalid scenario");
        }
    }
    public static void closeConnection() {
        RestAssured.config = RestAssured.config().connectionConfig(connectionConfig().closeIdleConnectionsAfterEachResponse());
    }
}
