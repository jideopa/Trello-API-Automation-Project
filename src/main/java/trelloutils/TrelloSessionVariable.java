package trelloutils;

import lombok.Getter;

@Getter
public enum TrelloSessionVariable {
    ID("id");
    private String id;
    TrelloSessionVariable(String id) {
        this.id = id;
    }


}
