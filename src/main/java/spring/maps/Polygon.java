package spring.maps;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import spring.rakscode.RaksCode;

import javax.persistence.*;

@Entity
@Table (name = "Polygons")
@Data
@AllArgsConstructor
public class Polygon {

    @OneToOne (mappedBy = "polygon")
    @EqualsAndHashCode.Exclude
    private RaksCode raksCode;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @Column(name = "ID")
    private int id;

    @Column(name = "LeftUpperLat")
    @NonNull
    private double leftUpperLat;

    @Column(name = "LeftUpperLon")
    @NonNull
    private double leftUpperLon;

    @Column(name = "RightLowerLat")
    @NonNull
    private double rightLowerLat;

    @Column(name = "RightLowerLon")
    @NonNull
    private double rightLowerLon;


    public Polygon() {
    }


}
