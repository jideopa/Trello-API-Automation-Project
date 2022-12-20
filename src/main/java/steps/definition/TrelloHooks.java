package steps.definition;

import baseresponse.BaseResponse;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.core.Serenity;
import trelloutils.TrelloSessionVariable;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.config.ConnectionConfig.connectionConfig;

public class TrelloHooks extends BaseResponse {
    static Properties properties = new Properties();

    @After(order = 2)
    public void logResponse() {
        response.then().log().all();
    }

    @After(order = 1)
    public void storeBoardID(Scenario scenario) throws IOException {
        JsonPath jsonPath = response.jsonPath();
        String boardId = jsonPath.get("id");
        String name = jsonPath.getString("name");
        FileReader fileReader = new FileReader("src/main/resources/boardlogger.properties");
        properties.load(fileReader);

        if (scenario.isFailed()) {
            System.out.println("test failed so board id is store");

        } else {
            System.out.println(scenario.getName());
            properties.setProperty("name", name);
            properties.setProperty("id", boardId);
            properties.store(new FileWriter("src/main/resources/boardlogger.properties"), "Trello");
            String id = properties.getProperty("id");
            Serenity.setSessionVariable(TrelloSessionVariable.ID.getId()).to(id);
            fileReader.close();
        }
    }

    public static void closeConnection() {
        RestAssured.config = RestAssured.config().connectionConfig(connectionConfig().closeIdleConnectionsAfterEachResponse());
    }
}
