package school.system.student_attendance.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.system.student_attendance.models.Classes;
import school.system.student_attendance.repositories.ClassesRepo;

import java.util.Optional;

@Service("ClassesService")
public class ClassesServiceImpl  implements  ClassesService{
    @Autowired
    ClassesRepo classesRepo;

    @Override
    public Iterable<Classes> findAll() {
        return classesRepo.findAll();
    }

    @Override
    public void save(Classes classname) {
        classesRepo.save(classname);
    }

    @Override
    public Optional<Classes> findById(int id) {
        Long longId = Long.valueOf(id);
        return classesRepo.findById(longId);
    }
}
