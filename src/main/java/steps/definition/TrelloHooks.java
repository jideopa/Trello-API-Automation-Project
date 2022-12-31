package steps.definition;

import baseresponse.BaseResponse;
import io.cucumber.java.After;

public class TrelloHooks extends BaseResponse {
   @After(order = 1)
    public void logResponse() {
      response.then();
    }

}
