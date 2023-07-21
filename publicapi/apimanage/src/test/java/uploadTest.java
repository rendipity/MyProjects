import com.aliyun.oss.model.PutObjectRequest;
import com.publicapi.apimanage.biz.service.impl.CommonServiceImpl;
import org.junit.Test;

import java.io.File;

public class uploadTest {

    @Test
    public void uploadTest(){
        CommonServiceImpl commonService = new CommonServiceImpl();
        commonService.upload();
    }

    @Test
    public void createBucketTest(){
        CommonServiceImpl commonService = new CommonServiceImpl();
        commonService.createBucket();
    }

    @Test
    public void deleteObjectTest(){
        CommonServiceImpl commonService = new CommonServiceImpl();
        commonService.deleteObject();
    }
}
