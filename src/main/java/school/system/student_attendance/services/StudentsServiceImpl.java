package school.system.student_attendance.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.system.student_attendance.models.Classes;
import school.system.student_attendance.models.Students;
import school.system.student_attendance.repositories.StudentsRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("StudentsService")
public class StudentsServiceImpl implements StudentsService {

    @Autowired
    StudentsRepo studentsRepo;

    @Override
    public Iterable<Students> findAll() {
        return studentsRepo.findAll();
    }

    @Override
    public List<Students> getAllStudents() {
        return (List<Students>) studentsRepo.findAll();
    }

    @Override
    public Students saveStudent(Students students) {
        return studentsRepo.save(students);
    }

    @Override
    public Students getStudentById(int id) {
        Optional<Students> optional = studentsRepo.findById(id);
        Students students = null;
        if (optional.isPresent()){
            students = optional.get();
        }else {
            throw new RuntimeException("Student is not found for id ::" + id);
        }
        return students;
    }

    @Override
    public void deleteStudentById(int id) {
        this.studentsRepo.deleteById(id);
    }

    @Override
    public List<Students> getAllStudentsNotInClass(Classes classes) {
        List<Students> allStudents = getAllStudents();
        List<Students> studentNotInClass = new ArrayList<>();
        List<Integer> usedIds = new ArrayList<>();
        Boolean used = false;

        for (Students s: classes.getStudents()) {
            usedIds.add(s.getId());
        }

        for (Students s: allStudents) {
            for (Integer id: usedIds) {
                if(s.getId() == id) {
                    used = true;
                }
            }

            if(used != true) {
                studentNotInClass.add(s);
                usedIds.add(s.getId());
            }
            used = false;
        }


        return studentNotInClass;
    }


}
