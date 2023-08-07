import com.publicapi.apimanage.ApiManageApplicationStarter;
import com.publicapi.facade.ApiFacade;
import com.publicapi.modal.ApiResourceDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = ApiManageApplicationStarter.class)
@RunWith(SpringRunner.class)
public class DubboTest {

    @Resource
    private ApiFacade apiFacade;
    @Test
    public void listApiTest(){
        List<ApiResourceDTO> apiResourceDTOS = apiFacade.listApi();
        apiResourceDTOS.forEach(System.out::println);
    }

}
