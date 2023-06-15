package ReentrantReadWriteLock.service.impl;

import ReentrantReadWriteLock.domain.Student;
import ReentrantReadWriteLock.mapper.StudentMapper;
import ReentrantReadWriteLock.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
* @author chenxinyu
* @description 针对表【Student】的数据库操作Service实现
* @createDate 2023-06-11 23:55:17
*/
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
    implements StudentService {

}




