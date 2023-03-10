package steps.definition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import steps.libraries.BoardStep;

public class BoardStepDefinition {
    BoardStep boardStep = new BoardStep();
    @Given("a valid Get Method request is send to server")
    public void aValidGetRequestIsSendToServer() {
        boardStep.createABoard();
        boardStep.storeBoardID();
        boardStep.getBoard();
        boardStep.deleteBoard();
    }
    @Then("a response body is received with {int} status")
    public void aResponseIsReceivedWithStatus(int statusCode) {
      boardStep.statueCode(statusCode);
    }

    @Given("a valid Post Method request is send to server")
    public void aValidPostMethodRequestIsSendToServer(){
        boardStep.createABoard();
        boardStep.storeBoardID();
        boardStep.deleteBoard();

    }

    @Given("a valid Delete Method is send to sever")
    public void aValidDeleteMethodIsSendToSever(){
        aValidPostMethodRequestIsSendToServer();
    }
}
