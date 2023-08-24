package com.publicapi.apimanage.common.qto;

import lombok.Data;

@Data
public class ApiPageQuery {

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    private String groupCode;
}
