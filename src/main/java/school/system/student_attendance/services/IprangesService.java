package school.system.student_attendance.services;

import org.springframework.stereotype.Service;
import school.system.student_attendance.models.Ipranges;
import school.system.student_attendance.models.Students;

import java.util.List;

@Service("IprangesService")
public interface IprangesService {

    Iterable<Ipranges> getAllIpranges();
    Ipranges save(Ipranges iprange);
    void deleteIprangeById(int id);
    Ipranges getIprangeById(int id);
    Boolean isIpAllowed(String ipAddress);
}
