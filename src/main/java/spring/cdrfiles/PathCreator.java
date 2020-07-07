package spring.cdrfiles;

public interface PathCreator {

    String createPathForLocalUse(CdrFile cdrFile);

    String createPathToVDir(String latestVDirectory);

}
