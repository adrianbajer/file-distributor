package spring.rakscode;

import java.util.Map;

public class FormatsMap {

    private Map<String, String> formatsMap;

    public FormatsMap() {
        formatsMap.put("EG.+", " EG ");
        formatsMap.put("EG.-", " EG ");
        formatsMap.put("LEG+", "LEG");
        formatsMap.put("LEG-", "LEG");
        formatsMap.put("PEG+", "PLEG");
        formatsMap.put("PEG-", "PLEG");
        formatsMap.put("PEG2", "PLEG");
        formatsMap.put("LAM4", "LAM4");
        formatsMap.put("LAM6", "LAM6");
        formatsMap.put("LAM7", "LAM7");
        formatsMap.put("LAM9", "LAM9");
    }
}
