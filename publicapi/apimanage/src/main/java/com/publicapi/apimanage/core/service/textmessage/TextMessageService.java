package com.publicapi.apimanage.core.service.textmessage;

import com.publicapi.apimanage.core.enums.MessageEnum;
import com.publicapi.apimanage.core.template.TemplateParam;

public interface TextMessageService {
    public Boolean sendTextMsg(String phone, MessageEnum messageEnum, TemplateParam templateParam) throws Exception ;

}
