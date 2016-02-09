package aang521.AangAPI.Function;

import aang521.AangAPI.AangScript;
import aang521.AangAPI.AangUtil;

public class VarpbitsFunc extends AangUtil {
    public VarpbitsFunc(AangScript script) {
        super(script);
    }

    public int get(int varpbit){
        return client.getConfigs1()[varpbit];
    }
}
