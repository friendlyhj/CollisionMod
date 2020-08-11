package youyihj.collision.config;

import java.util.HashMap;

public class NucleusDataReader {
    public static HashMap<String, NucleusDataEntry> nucleusDataEntries = new HashMap<>();

    public static int readNucleusData() {
        for (String singleInfo : Configuration.generalConfig.nuclei) {
            String[] singleInfos = singleInfo.split(",");
            if (singleInfos.length == 3) {
                nucleusDataEntries.put(singleInfos[0], new NucleusDataEntry(singleInfos[0], singleInfos[1], Integer.valueOf(singleInfos[2])));
            } else {
                throw new IllegalArgumentException(singleInfo + " is invalid!");
            }
        }
        return nucleusDataEntries.size();
    }
}
