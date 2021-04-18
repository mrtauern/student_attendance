package school.system.student_attendance.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.system.student_attendance.models.Students;
import school.system.student_attendance.repositories.StudentsRepo;

@Service("StudentsService")
public class StudentsServiceImpl implements StudentsService {

    @Autowired
    StudentsRepo studentsRepo;

    @Override
    public Iterable<Students> findAll() {
        return studentsRepo.findAll();
    }
}
