package school.system.student_attendance.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.system.student_attendance.controllers.HomeController;
import school.system.student_attendance.models.Classes;
import school.system.student_attendance.models.Ipranges;
import school.system.student_attendance.repositories.IprangesRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service("IprangesService")
public class IprangesServiceImpl implements IprangesService{

    @Autowired
    IprangesRepo iprangesRepo;

    Logger log = Logger.getLogger(IprangesServiceImpl.class.getName());

    @Override
    public Iterable<Ipranges> getAllIpranges() {
        return iprangesRepo.findAll();
    }

    @Override
    public Ipranges save(Ipranges iprange) {
        return iprangesRepo.save(iprange);
    }

    @Override
    public void deleteIprangeById(int id) {
        iprangesRepo.deleteById(id);
    }

    @Override
    public Ipranges getIprangeById(int id) {
        Optional<Ipranges> optional = iprangesRepo.findById(id);
        Ipranges ipranges = null;
        if (optional.isPresent()){
            ipranges = optional.get();
        }else {
            throw new RuntimeException("Class is not found for id ::" + id);
        }
        return ipranges;
    }

    public Boolean isIpAllowed(String ipAddress) {
        boolean allowed = false;

        int[] ipArr = splitIpStringReturnInt(ipAddress);
        Iterable<Ipranges> ipranges = iprangesRepo.findAll();

        for (Ipranges ip: ipranges) {
            for (int i = 0; i<4; i++) {
                switch (i) {
                    case 0:
                        allowed = isInRange(ip.getIpFromPart1(), ip.getIpToPart1(), ipArr[i]);
                        if(!allowed) {
                            return false;
                        }
                    break;

                    case 1:
                        allowed = isInRange(ip.getIpFromPart2(), ip.getIpToPart2(), ipArr[i]);
                        if(!allowed) {
                            return false;
                        }
                    break;

                    case 2:
                        allowed = isInRange(ip.getIpFromPart3(), ip.getIpToPart3(), ipArr[i]);
                        if(!allowed) {
                            return false;
                        }
                    break;

                    case 3:
                        allowed = isInRange(ip.getIpFromPart4(), ip.getIpToPart4(), ipArr[i]);
                        if(!allowed) {
                            return false;
                        }
                    break;

                    default:
                }
            }
        }

        return allowed;
    }

    private int[] splitIpStringReturnInt(String stringToSplit) {
        String[] split = stringToSplit.split("\\.",4);
        int[] intArray = new int[4];

        for (int i = 0; i<4;i++) {
            intArray[i] = Integer.parseInt(split[i]);
        }

        return intArray;
    }

    private boolean isInRange(int from, int to, int ipPart) {
        boolean debuggerEnabled = false;

        if (debuggerEnabled) {
            log.info("----------comparing ip part----------");
            log.info("ip from = " + from + " ip to = " + to + " ip part = " + ipPart);
        }

        if((ipPart>from && ipPart<to) || (ipPart==from || ipPart==to)) {
            return true;
        }
        else {
            return false;
        }
    }
}
