import com.publicapi.apimanage.ApiManageApplicationStarter;
import com.publicapi.apimanage.biz.service.UserService;
import com.publicapi.apimanage.core.enums.MessageEnum;
import com.publicapi.apimanage.core.service.textmessage.impl.TextMessageServiceImpl;
import com.publicapi.apimanage.core.service.textmessage.template.AuthCodeTemplateParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = ApiManageApplicationStarter.class)
@RunWith(SpringRunner.class)
public class TextMQMessageTest {

    @Resource
    UserService userService;
    @Test
    public void sendMsgTest(){
        TextMessageServiceImpl messageService = new TextMessageServiceImpl();
        try {
            messageService.sendTextMsg("17782072752", MessageEnum.Test, new AuthCodeTemplateParam("4261"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("发送失败！");
        }
    }
    @Test
    public void sendRegisterCode(){
        Boolean result = userService.sendRegisterAuthCode("17782072752","127.0.0.1");
        if (result){
            System.out.println("发送成功");
        }else{
            System.out.println("发送失败");
        }
    }

    @Test
    public void sendSensitiveCode(){
        Boolean result = userService.sendSensitiveAuthCode();
        if (result){
            System.out.println("发送成功");
        }else{
            System.out.println("发送失败");
        }

    }
}
