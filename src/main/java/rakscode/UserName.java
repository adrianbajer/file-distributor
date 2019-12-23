package rakscode;

public enum UserName {
    ADRIAN("Adrian"),
    BOLEK("Bolek"),
    LOLEK("Lolek");

    private String name;

    UserName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
