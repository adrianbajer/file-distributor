package spring.rakscode;

public enum JobType {
    PUBLISHING("publishing"),
    UPDATING("updating");

    private String type;

    JobType(String name) {
        this.type = name;
    }

    public String getType() {
        return type;
    }
}
