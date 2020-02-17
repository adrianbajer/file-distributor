package spring.cdrfiles;

import lombok.*;
import spring.rakscode.RaksCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CdrFiles")
@Data
@AllArgsConstructor
public class CdrFile {

    @ManyToMany(mappedBy = "cdrFileSet", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<RaksCode> raksCodeSet;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @Column(name = "ID")
    private int id;

    @Column(name = "Name")
    @NonNull
    private String name;

    @Column(name = "Place")
    @NonNull
    private String place;

    @Column(name = "Region")
    @NonNull
    private String region;

    @Column(name = "Type")
    @NonNull
    private String type;


    public void addRaksCode(RaksCode raksCode) {
        raksCodeSet.add(raksCode);
        raksCode.getCdrFileSet().add(this);
    }

    public void removeRaksCode(RaksCode raksCode) {
        raksCodeSet.remove(raksCode);
        raksCode.getCdrFileSet().remove(this);
    }

    public Set<RaksCode> getRaksCodeSet() {
        if (raksCodeSet == null) {
            raksCodeSet = new HashSet<>();
        }
        return raksCodeSet;
    }


    public CdrFile() {
    }

    public CdrFile(Set<RaksCode> raksCodeSet, @NonNull String name, @NonNull String place,
                   @NonNull String region, @NonNull String type) {
        this.raksCodeSet = raksCodeSet;
        this.name = name;
        this.place = place;
        this.region = region;
        this.type = type;
    }

    public CdrFile(int id, @NonNull String name, @NonNull String place,
                   @NonNull String region, @NonNull String type) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.region = region;
        this.type = type;
    }

}
