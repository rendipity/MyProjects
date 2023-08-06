package com.publicapi.apimanage.core.template;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthCodeTemplateParam implements TemplateParam {
    private String code;
}
