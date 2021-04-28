package school.system.student_attendance.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.system.student_attendance.models.Teachers;
import school.system.student_attendance.repositories.TeachersRepo;

@Service("TeachersService")
public class TeachersServiceImpl implements TeachersService{
    @Autowired
    TeachersRepo teachersRepo;

    @Override
    public Iterable<Teachers> findAll() {
        return teachersRepo.findAll();
    }
}
