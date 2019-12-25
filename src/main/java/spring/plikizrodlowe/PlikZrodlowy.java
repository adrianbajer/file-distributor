package spring.plikizrodlowe;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlikZrodlowy {
    private String name;
    private String place;
    private String region;
    private String type;

    public PlikZrodlowy() {
    }

}
