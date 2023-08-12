import com.publicapi.apimanage.ApiManageApplicationStarter;
import com.publicapi.apimanage.core.service.mq.MqService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = ApiManageApplicationStarter.class)
@RunWith(SpringRunner.class)
public class MqTest {

    @Resource
    MqService mqService;
    @Test
    public void mqTest(){
        mqService.sendMqMessage("test","hello world from apiManage");
    }
}
