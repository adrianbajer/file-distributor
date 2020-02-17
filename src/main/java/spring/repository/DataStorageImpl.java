package spring.repository;

import spring.cdrfiles.CdrFile;
import spring.cdrfiles.UpdatingArea;
import spring.rakscode.RaksCode;

import java.util.*;

public class DataStorageImpl implements DataStorage{

//    Map<String, List<UpdatingArea>> raksCodeMap;
//    Map<UpdatingArea, CdrFile> updatingAreaMap;
//
//    public DataStorageImpl() {
//        CdrFile cdrFile1 = new CdrFile("France 300","archive","France 300","2 road maps");
//        CdrFile cdrFile2 = new CdrFile("Bordeaux 15","archive","Bordeaux","1 city maps");
//        CdrFile cdrFile3 = new CdrFile("Bordeaux Center 10","archive","Bordeaux","1 city maps");
//        CdrFile cdrFile4 = new CdrFile("Albania 300","Adrian","Albania","2 road maps");
//
//        UpdatingArea updatingArea1 = new UpdatingArea("Aquitaine S 300");
//        UpdatingArea updatingArea2 = new UpdatingArea("Aquitaine SW 300");
//        UpdatingArea updatingArea3 = new UpdatingArea("Bordeaux 15");
//        UpdatingArea updatingArea4 = new UpdatingArea("Bordeaux Center 10");
//        UpdatingArea updatingArea5 = new UpdatingArea("Albania 300");
//
//        List<UpdatingArea> aquitaineList = new ArrayList<>();
//        List<UpdatingArea> albaniaList = new ArrayList<>();
//
//        aquitaineList.add(updatingArea1);
//        aquitaineList.add(updatingArea2);
//        aquitaineList.add(updatingArea3);
//        aquitaineList.add(updatingArea4);
//        albaniaList.add(updatingArea5);
//
//
//        raksCodeMap = new HashMap<>();
//        raksCodeMap.put("Aquitaine._map_LAM9_FR", aquitaineList);
//        raksCodeMap.put("Albania..._map_LAM9_EN", albaniaList);
//
//        updatingAreaMap = new HashMap<>();
//        updatingAreaMap.put(updatingArea1, cdrFile1);
//        updatingAreaMap.put(updatingArea2, cdrFile1);
//        updatingAreaMap.put(updatingArea3, cdrFile2);
//        updatingAreaMap.put(updatingArea4, cdrFile3);
//        updatingAreaMap.put(updatingArea5, cdrFile4);
//
//    }
//
    public Set<CdrFile> getSetOfCdrFiles(RaksCode raksCode){
//
//        DataStorageImpl dataStorage = new DataStorageImpl();
//        Set<CdrFile> cdrFileSet = new HashSet<>();
//
//        if (raksCodeMap.get(raksCode.getRaksCode()) != null) {
//            List<UpdatingArea> updatingAreaList = raksCodeMap.get(raksCode.getRaksCode());
//
//            for (UpdatingArea updatingArea : updatingAreaList) {
//                cdrFileSet.add(updatingAreaMap.get(updatingArea));
//            }
//        }
//
//        return cdrFileSet;
        return null;
    }

    //        System.out.println(raksCode);
//        System.out.println(cdrFileSet);
//        System.out.println(cdrFileSet.size());
//        System.out.println(dataStorage.updatingAreaMap);
//        System.out.println(dataStorage.raksCodeMap);
//        System.out.println(updatingAreaList);

}
