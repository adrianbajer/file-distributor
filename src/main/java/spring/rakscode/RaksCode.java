package spring.rakscode;

import lombok.*;
import spring.cdrfiles.CdrFile;

import javax.persistence.*
        ;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "RaksCodes")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class RaksCode {

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "RaksCodes_CdrFiles",
            joinColumns = {@JoinColumn(name = "raksCodeId", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "cdrFileId", referencedColumnName = "ID")})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<CdrFile> cdrFileSet;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @Column(name = "ID")
    private int id;

    @Column(name = "RaksCode")
    @NonNull
    private String raksCode;

    @Column(name = "UserName")
//    @NonNull
    private UserName userName;

    @Column(name = "JobType")
//    @NonNull
    private JobType jobType;


    public void addCdrFile(CdrFile cdrFile) {
        cdrFileSet.add(cdrFile);
        cdrFile.getRaksCodeSet().add(this);
    }

    public void removeCdrFile(CdrFile cdrFile) {
        cdrFileSet.remove(cdrFile);
        cdrFile.getRaksCodeSet().remove(this);
    }

    public Set<CdrFile> getCdrFileSet() {
        if (cdrFileSet == null) {
            cdrFileSet = new HashSet<>();
        }
        return cdrFileSet;
    }

    public RaksCode() {
    }

    public RaksCode(Set<CdrFile> cdrFileSet, @NonNull String raksCode, UserName userName,
                    JobType jobType) {
//    public RaksCode(Set<CdrFile> cdrFileSet, @NonNull String raksCode, @NonNull UserName userName,
//                @NonNull JobType jobType) {
        this.cdrFileSet = cdrFileSet;
        this.raksCode = raksCode;
        this.userName = userName;
        this.jobType = jobType;
    }

    public RaksCode(int id, @NonNull String raksCode, UserName userName,
                    JobType jobType) {
//     public RaksCode(int id, @NonNull String raksCode, @NonNull UserName userName,
//        @NonNull JobType jobType) {
        this.id = id;
        this.raksCode = raksCode;
        this.userName = userName;
        this.jobType = jobType;
    }
}
