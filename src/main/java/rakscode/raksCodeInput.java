package rakscode;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class raksCodeInput {


    private String raksCode;
    private UserName userName;
    private JobType jobType;

    public raksCodeInput() {
    }
}
