package AangJewel;

import AangAPI.AangScript;
import org.osbot.rs07.script.ScriptManifest;

@ScriptManifest(name = "AangJewel", author = "Aang521", version = 1.0, info = "", logo = "")
public class AangJewel extends AangScript{

    @Override
    public void onStart(){
    }

    @Override
    public int onLoop() {
        //random(123);
        //TODO research: getClient().accessor

        //TODO set mousespeed on 0 for hopping maybe?
        return 10;
    }
}
