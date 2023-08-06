package com.publicapi.apimanage.core.service.message.impl;

import cn.hutool.json.JSONUtil;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.teaopenapi.models.Config;
import com.publicapi.apimanage.core.enums.MessageEnum;
import com.publicapi.apimanage.core.properties.MessageProperties;
import com.publicapi.apimanage.core.service.message.MessageService;
import com.publicapi.apimanage.core.template.TemplateParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class MessageServiceImpl implements MessageService {

    // 从环境变量中获取RAM用户的访问密钥（AccessKeyId和AccessKeySecret）
    public  String accessKeyId = System.getenv("OSS_ACCESS_KEY_ID");
    public  String accessKeySecret = System.getenv("OSS_ACCESS_KEY_SECRET");
    @Resource
    MessageProperties messageProperties;

    @Override
    public Boolean sendMsg(String phone, MessageEnum messageEnum, TemplateParam templateParam) throws Exception {
        Client client = new Client(new Config().setAccessKeyId(accessKeyId).setAccessKeySecret(accessKeySecret).setEndpoint(messageProperties.endpoint));
        SendSmsRequest smsRequest = new SendSmsRequest()
                .setPhoneNumbers(phone)
                .setSignName(messageEnum.getSignName())
                .setTemplateCode(messageEnum.getTemplateCode())
                .setTemplateParam(JSONUtil.toJsonStr(templateParam));
        SendSmsResponse response = client.sendSms(smsRequest);
        if (response.getBody().getCode().equals("OK")){
            return true;
        }
        System.out.println(response.getBody());
        return false;
    }
}
