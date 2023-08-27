package com.publicapi.modal;

import lombok.Data;
import org.springframework.util.MultiValueMap;

@Data
public class GatewayContext {

    public static final String CACHE_GATEWAY_CONTEXT = "cacheGatewayContext";
    /**
     * cache json body
     */
    private String cacheBody;
    /**
     * cache formdata
     */
    private MultiValueMap<String, String> formData;
}
