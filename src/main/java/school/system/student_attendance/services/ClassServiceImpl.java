package school.system.student_attendance.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.system.student_attendance.models.Classes;
import school.system.student_attendance.models.Courses;
import school.system.student_attendance.repositories.ClassRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("ClassService")
public class ClassServiceImpl implements ClassService {

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

    @Override
    public List<Classes> getAllClassesNotInCourse(Courses course) {
        List<Classes> allClasses = getAllClasses();
        List<Classes> classesNotInCourse = new ArrayList<>();
        List<Integer> usedIds = new ArrayList<>();
        Boolean used = false;

        for (Classes c: course.getClasses()) {
            usedIds.add(c.getId());
        }

        for (Classes c: allClasses) {
            for (Integer id: usedIds) {
                if(c.getId() == id) {
                    used = true;
                }
            }

            if(used != true) {
                classesNotInCourse.add(c);
                usedIds.add(c.getId());
            }
            used = false;
        }


        return classesNotInCourse;
    }

}
