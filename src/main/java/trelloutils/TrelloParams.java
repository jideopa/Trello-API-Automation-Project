package trelloutils;

import lombok.Getter;

@Getter
public enum TrelloParams {
    TRELLO_TOKEN("218b3f2389d91ad605cf128bddaaf2ea4640496baa1fbc6877364c23fdf65c47"),
    TRELLO_KEY("cdbe135808d892d02fb09d8755f9092b"),
    TRELLO_BOARD_ID("63a1def4bb4058009ea3113f"),
    TRELLO_BASE_URL("https://api.trello.com/1/boards/");
    final String value;
    TrelloParams(String credential) {
        this.value = credential;
    }
}
