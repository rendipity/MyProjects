import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.publicapi.apimanage.common.utils.RedisCodeUtil;
import org.junit.Test;

import java.util.Date;


public class HutoolUtilTest {

    @Test
    public void dateFormatTest(){
        String date = DateUtil.format(DateUtil.date(), "yyyy/MM/dd");
        System.out.println(date);
    }
    @Test
    public void dateOffsetTest(){
        Date date = DateUtil.parse("2023-8-2");
        RedisCodeUtil.getWeekRedisCode("aaa",date);
    }
    
}
