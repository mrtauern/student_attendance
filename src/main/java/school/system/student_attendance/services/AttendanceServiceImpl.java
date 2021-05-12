package school.system.student_attendance.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.system.student_attendance.models.*;
import school.system.student_attendance.repositories.AttendanceRepo;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service("AttendanceService")
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    AttendanceRepo attendanceRepo;

    @Autowired
    SessionsService sessionsService;

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

    @Override
    public List<AttentionList> attendanceList(int id) {

        Sessions session = sessionsService.findById(id);

        List<Attendance> attendance = findAll();

        List<AttentionList> attentionList = new ArrayList<>();

        attentionList.add(new AttentionList("", new ArrayList<SessionAttendance>()));

        int listNumber = 0;

        for (Students s: session.getCourse().getStudents()) {
            int attended = 0;
            int networkVerified = 0;
            for (Attendance a: attendance){
                if(a.getSession().getId() == id && a.getStudent().getId() == s.getId()) {
                    if (a.getStatus() == 1) {
                        attended = 1;
                        networkVerified = a.getNetworkVerified();
                    }
                }
            }
            SessionAttendance sessionAttendance = new SessionAttendance(s.getId(), s.getFirstname(), s.getLastname(), s.getEmail(), attended, networkVerified);
            attentionList.get(0).getList().add(sessionAttendance);
        }

        for (Classes c: session.getCourse().getClasses()){
            listNumber++;

            attentionList.add(new AttentionList(c.getClassname(), new ArrayList<SessionAttendance>()));

            for (Students s: c.getStudents()) {
                int attended = 0;
                int networkVerified = 0;
                for (Attendance a: attendance){
                    if(a.getSession().getId() == id && a.getStudent().getId() == s.getId()) {
                        if (a.getStatus() == 1) {
                            attended = 1;
                            networkVerified = a.getNetworkVerified();
                        }
                    }
                }
                SessionAttendance sessionAttendance = new SessionAttendance(s.getId(), s.getFirstname(), s.getLastname(), s.getEmail(), attended, networkVerified);
                attentionList.get(listNumber).getList().add(sessionAttendance);
            }
        }

        return attentionList;
    }

    public boolean checkIfActive(Timestamp timestamp){
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(timestamp);
        //calendar1.add(Calendar.DATE, 1);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(timestamp);
        calendar2.add(Calendar.MINUTE, 45);
        //calendar2.add(Calendar.DATE, 1);

        Timestamp ts = Timestamp.from(Instant.now());
        Calendar calendar3 = Calendar.getInstance();
        calendar3.setTime(ts);
        //calendar3.add(Calendar.DATE, 1);

        Date x = calendar3.getTime();
        if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
            //checkes whether the current time is between 14:49:00 and 20:11:13.
            //System.out.println(true);
            return true;
        }

        return false;
    }
}
