package spring.cdrfiles;

public interface PathCreator {

    String createPath(CdrFile cdrFile);

    String createPathToVDir(String latestVDirectory);

}
