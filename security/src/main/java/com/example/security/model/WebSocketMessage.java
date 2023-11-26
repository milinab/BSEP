package com.example.security.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebSocketMessage {

    private String channel;
    private String message;


}
