package com.publicapi.apimanage.core.service.message;

import com.publicapi.apimanage.core.enums.MessageEnum;
import com.publicapi.apimanage.core.template.TemplateParam;

public interface MessageService {
    public Boolean sendMsg(String phone, MessageEnum messageEnum, TemplateParam templateParam) throws Exception ;
}
