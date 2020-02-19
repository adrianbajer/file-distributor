package spring.rakscode;

public enum UserName {
    ADRIAN("Adrian"),
    JANE("Jane"),
    GEORGE("George");

    private String name;

    UserName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
