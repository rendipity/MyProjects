package ReentrantReadWriteLock;

import ReentrantReadWriteLock.domain.Student;
import ReentrantReadWriteLock.service.StudentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class Cache {

    @Resource
    StudentService service;

    ReentrantReadWriteLock readwriteLock = new ReentrantReadWriteLock();
    ConcurrentHashMap<Integer, Student> studentHashMap = new ConcurrentHashMap<>();

    public Student queryByIdFromCache(Integer id){
        Student student = null;
        try {
            readwriteLock.readLock().lock();
            student = studentHashMap.get(id);
            if (student != null) {
                return student;
            }
        }finally {
            readwriteLock.readLock().unlock();
        }
        try {
           readwriteLock.writeLock().lock();
            if (student != null) {
                return student;
            }
            student = service.getById(id);
            studentHashMap.put(id,student);
            return student;
        }finally {
            readwriteLock.writeLock().unlock();
        }
    }

    public Student queryByIdFromDb(Integer id){
           return service.getById(id);
    }
}
