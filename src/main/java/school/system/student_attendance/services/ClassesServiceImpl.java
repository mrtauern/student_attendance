package school.system.student_attendance.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.system.student_attendance.models.Classes;
import school.system.student_attendance.repositories.ClassRepository;
import school.system.student_attendance.repositories.ClassesRepo;

import java.util.List;
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


    @Autowired
    ClassRepository classRepository;


    @Override
    public List<Classes> getAllClasses() {
        return (List<Classes>) classRepository.findAll();
    }

    @Override
    public Classes saveClass(Classes classes) {
        return classRepository.save(classes);
    }

    @Override
    public Classes getClassById(int id) {
        Optional<Classes> optional = classRepository.findById(id);
        Classes classes = null;
        if (optional.isPresent()){
            classes = optional.get();
        }else {
            throw new RuntimeException("Class is not found for id ::" + id);
        }
        return classes;
    }


    @Override
    public void deleteClassById(int Id) {
        this.classRepository.deleteById(Id);
    }
}
