package AangAPI.Function;

import AangAPI.AangUtil;

public class VarpbitsFunc extends AangUtil {
    private static VarpbitsFunc ourInstance = new VarpbitsFunc();

    public static VarpbitsFunc getInstance() {
        return ourInstance;
    }

    private VarpbitsFunc() {
    }

    public int get(int varpbit){
        return client.getConfigs1()[varpbit];
    }
}
