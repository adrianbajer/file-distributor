package spring.cdrfiles;

public class PathCreatorImpl implements PathCreator{


    @Override
    public String createPath(CdrFile cdrFile) {

        StringBuilder sb = new StringBuilder();

        sb.append("src\\main\\resources\\example file repository\\");
        sb.append(cdrFile.getType() + "\\");
        sb.append(cdrFile.getRegion() + "\\");
        sb.append(cdrFile.getName() + ".txt");

        return sb.toString();
    }


}
