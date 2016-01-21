package AangAPI;

public class VarpbitsFunc extends AangUtil {
    private static VarpbitsFunc ourInstance = new VarpbitsFunc();

    public static VarpbitsFunc getInstance() {
        return ourInstance;
    }

    private VarpbitsFunc() {
    }

    public int get(int varpbit){
        return script.getConfigs().get(varpbit);
    }
}
