package school.system.student_attendance.models;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ipranges", schema = "student_attendance", catalog = "")
public class Ipranges {
    private int id;
    private int ipFromPart1;
    private int ipFromPart2;
    private int ipFromPart3;
    private int ipFromPart4;
    private int ipToPart1;
    private int ipToPart2;
    private int ipToPart3;
    private int ipToPart4;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ipfrompart1")
    public int getIpFromPart1() {
        return ipFromPart1;
    }

    public void setIpFromPart1(int ipFromPart1) {
        this.ipFromPart1 = ipFromPart1;
    }

    @Basic
    @Column(name = "ipfrompart2")
    public int getIpFromPart2() {
        return ipFromPart2;
    }

    public void setIpFromPart2(int ipFromPart2) {
        this.ipFromPart2 = ipFromPart2;
    }

    @Basic
    @Column(name = "ipfrompart3")
    public int getIpFromPart3() {
        return ipFromPart3;
    }

    public void setIpFromPart3(int ipFromPart3) {
        this.ipFromPart3 = ipFromPart3;
    }

    @Basic
    @Column(name = "ipfrompart4")
    public int getIpFromPart4() {
        return ipFromPart4;
    }

    public void setIpFromPart4(int ipFromPart4) {
        this.ipFromPart4 = ipFromPart4;
    }

    @Basic
    @Column(name = "iptopart1")
    public int getIpToPart1() {
        return ipToPart1;
    }

    public void setIpToPart1(int ipToPart1) {
        this.ipToPart1 = ipToPart1;
    }

    @Basic
    @Column(name = "iptopart2")
    public int getIpToPart2() {
        return ipToPart2;
    }

    public void setIpToPart2(int ipToPart2) {
        this.ipToPart2 = ipToPart2;
    }

    @Basic
    @Column(name = "iptopart3")
    public int getIpToPart3() {
        return ipToPart3;
    }

    public void setIpToPart3(int ipToPart3) {
        this.ipToPart3 = ipToPart3;
    }

    @Basic
    @Column(name = "iptopart4")
    public int getIpToPart4() {
        return ipToPart4;
    }

    public void setIpToPart4(int ipToPart4) {
        this.ipToPart4 = ipToPart4;
    }

    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ipranges that = (Ipranges) o;
        return id == that.id && ipFromPart1 == that.ipFromPart1 && ipFromPart2 == that.ipFromPart2 && ipFromPart3 == that.ipFromPart3 && ipFromPart4 == that.ipFromPart4 && ipToPart1 == that.ipToPart1 && ipToPart2 == that.ipToPart2 && ipToPart3 == that.ipToPart3 && ipToPart4 == that.ipToPart4;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ipFromPart1, ipFromPart2, ipFromPart3, ipFromPart4, ipToPart1, ipToPart2, ipToPart3, ipToPart4);
    }

     */
}
