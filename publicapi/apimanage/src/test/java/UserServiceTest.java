import com.publicapi.apimanage.ApiManageApplicationStarter;
import com.publicapi.apimanage.biz.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.swing.*;

@SpringBootTest(classes = ApiManageApplicationStarter.class)
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Resource
    private UserService userService;
    @Test
    public void sendRegisterAuthCodeTest(){
        String phone ="17782072752";
        String ip = "127.0.0.1";
        // 手机号+ip
        Boolean sendAuthCodeResult1 = userService.sendRegisterAuthCode(phone, ip);
        System.out.println(sendAuthCodeResult1);
        phone ="17782072751";
        ip = "127.0.0.1";
        // ip
        Boolean sendAuthCodeResult2 = userService.sendRegisterAuthCode(phone, ip);
        System.out.println(sendAuthCodeResult2);
        phone ="17782072752";
        ip = "127.0.0.2";
        // phone
        Boolean sendAuthCodeResult3 = userService.sendRegisterAuthCode(phone, ip);
        System.out.println(sendAuthCodeResult3);
    }
}
