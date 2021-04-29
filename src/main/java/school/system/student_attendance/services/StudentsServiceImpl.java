package school.system.student_attendance.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.system.student_attendance.models.Classes;
import school.system.student_attendance.models.Students;
import school.system.student_attendance.repositories.StudentsRepo;

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
}
