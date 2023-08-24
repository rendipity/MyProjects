package com.publicapi.modal;

import lombok.Data;

@Data
public class UserAuth {

    private Integer userId;
    private String username;
    private String appKey;
    private String secretKey;

}
