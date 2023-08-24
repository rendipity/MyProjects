package com.publicapi.modal.mq;

import lombok.Data;

import java.io.Serializable;

@Data
public class MQMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private String sendTime;

    private String messageType;

    private Object content;
}
