package spring.rakscode;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RaksCode {


    private String raksCode;
    private UserName userName;
    private JobType jobType;

    public RaksCode() {
    }
}
