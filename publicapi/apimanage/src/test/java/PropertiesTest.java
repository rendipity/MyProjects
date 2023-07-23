import com.publicapi.apimanage.ApplicationStarter;
import com.publicapi.apimanage.core.service.OssService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = ApplicationStarter.class)
@RunWith(SpringRunner.class)
public class PropertiesTest {

    @Resource
    OssService ossService;

    @Test
    public void OssClientTest(){
        ossService.putFile("hello1",null);
    }
}
