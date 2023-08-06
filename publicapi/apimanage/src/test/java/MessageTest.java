import com.publicapi.apimanage.ApplicationStarter;
import com.publicapi.apimanage.biz.service.UserService;
import com.publicapi.apimanage.core.enums.MessageEnum;
import com.publicapi.apimanage.core.service.message.MessageService;
import com.publicapi.apimanage.core.service.message.impl.MessageServiceImpl;
import com.publicapi.apimanage.core.template.AuthCodeTemplateParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


@SpringBootTest(classes = ApplicationStarter.class)
@RunWith(SpringRunner.class)
public class MessageTest {

    @Resource
    UserService userService;
    @Test
    public void sendMsgTest(){
        MessageServiceImpl messageService = new MessageServiceImpl();
        try {
            messageService.sendMsg("17782072752", MessageEnum.Test, new AuthCodeTemplateParam("4261"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("发送失败！");
        }
    }
    @Test
    public void sendRegisterCode(){
        Boolean result = userService.sendRegisterAuthCode("17782072752");
        if (result){
            System.out.println("发送成功");
        }else{
            System.out.println("发送失败");
        }
    }

    @Test
    public void sendSensitiveCode(){
        Boolean result = userService.sendSensitiveAuthCode("17782072752");
        if (result){
            System.out.println("发送成功");
        }else{
            System.out.println("发送失败");
        }

    }
}
