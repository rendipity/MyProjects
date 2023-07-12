package com.publicapi.apimanage.biz.statemachine;


import org.springframework.stereotype.Component;

import static com.publicapi.apimanage.biz.statemachine.ApiEventEnum.DISABLE;
import static com.publicapi.apimanage.biz.statemachine.ApiEventEnum.ENABLE;
import static com.publicapi.apimanage.biz.statemachine.ApiStateEnum.DISABLEING;
import static com.publicapi.apimanage.biz.statemachine.ApiStateEnum.ENABLEING;

@Component
public class ApiStateMachine {

    public String transfer(String state, String event){
        if (ENABLEING.getCode().equals(state) && DISABLE.getCode().equals(event))
            return DISABLEING.getCode();
        if (DISABLEING.getCode().equals(state) && ENABLE.getCode().equals(event))
            return ENABLEING.getCode();
        return null;
    }
}
