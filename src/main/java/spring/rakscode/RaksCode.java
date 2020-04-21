package spring.rakscode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class RaksCode {


    private String raksCode;
    private UserName userName;
    private JobType jobType = JobType.PUBLICATION;

    public RaksCode() {
    }

    public RaksCode(String raksCode) {
        this.raksCode = raksCode;
    }
}
