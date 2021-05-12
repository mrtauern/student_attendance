package school.system.student_attendance.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import school.system.student_attendance.models.Ipranges;

@Repository("IprangesRepo")
public interface IprangesRepo extends CrudRepository<Ipranges, Integer> {
}
