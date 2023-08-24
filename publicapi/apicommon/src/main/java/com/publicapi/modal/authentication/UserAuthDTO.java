package com.publicapi.modal.authentication;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAuthDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer userId;
    private String username;
    private String appKey;
    private String secretKey;

}
