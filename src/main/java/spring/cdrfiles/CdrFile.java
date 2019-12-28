package spring.cdrfiles;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CdrFile {
    private String name;
    private String place;
    private String region;
    private String type;

    public CdrFile() {
    }

}
