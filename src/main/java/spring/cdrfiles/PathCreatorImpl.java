package spring.cdrfiles;

public class PathCreatorImpl implements PathCreator{


    @Override
    public String createPathForLocalUse(CdrFile cdrFile) {

        StringBuilder sb = new StringBuilder();

        sb.append("C:\\fd");
        sb.append(cdrFile.getPath().substring(1));

        return sb.toString();
    }

    @Override
    public String createPathToVDir(String latestVDirectory) {
        return null;
    }


}
