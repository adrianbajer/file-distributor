package spring.maps;

public class PolygonText {

    private String raksCodeName;
    private String componentFilesNames;

    public PolygonText() {
    }

    public PolygonText(String raksCodeName, String componentFilesNames) {
        this.raksCodeName = raksCodeName;
        this.componentFilesNames = componentFilesNames;
    }

    public String getRaksCodeName() {
        return raksCodeName;
    }

    public void setRaksCodeName(String raksCodeName) {
        this.raksCodeName = raksCodeName;
    }

    public String getComponentFilesNames() {
        return componentFilesNames;
    }

    public void setComponentFilesNames(String componentFilesNames) {
        this.componentFilesNames = componentFilesNames;
    }
}
