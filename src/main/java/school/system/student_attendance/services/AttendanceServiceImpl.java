package school.system.student_attendance.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.system.student_attendance.models.Attendance;
import school.system.student_attendance.repositories.AttendanceRepo;

import java.util.List;

@Service("AttendanceService")
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    AttendanceRepo attendanceRepo;

    @Override
    public List<Attendance> findAll() {
        return attendanceRepo.findAll();
    }

    @Override
    public Attendance findById(int id) {
        return attendanceRepo.findById(id).get();
    }

    @Override
    public Attendance save(Attendance attendance) {
        return attendanceRepo.save(attendance);
    }

    @Override
    public void deleteById(int id) {
        attendanceRepo.deleteById(id);
    }

    @Override
    public void delete(Attendance attendance) {
        attendanceRepo.delete(attendance);
    }
}
