package spring.cdrfiles;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class CdrFile {
    private String name;
    private String version;
    private String date;
    private String place;
    private String region;
    private String type;
    private String path;

    public CdrFile() {
    }

    public CdrFile(String name) {
        this.name = name;
    }
}
