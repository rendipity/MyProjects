package com.publicapi.apimanage.core.service.textmessage.template;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthCodeTemplateParam implements TemplateParam {
    private String code;
}
