import com.publicapi.GatewayApplicationStarter;
import com.publicapi.dubboclient.ApiClient;
import com.publicapi.modal.api.ApiResourceDTO;
import com.publicapi.util.ResultUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = GatewayApplicationStarter.class)
@RunWith(SpringRunner.class)
public class DubboTest {

    @Resource
    private ApiClient client;
    @Test
    public void listApiTest(){
        List<ApiResourceDTO> apiResourceDTOS = ResultUtil.isSuccess(client.listApiResource());
        apiResourceDTOS.forEach(System.out::println);
    }
}
