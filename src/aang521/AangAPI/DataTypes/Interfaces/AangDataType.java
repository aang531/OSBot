package aang521.AangAPI.DataTypes.Interfaces;

import aang521.AangAPI.AangScript;

public abstract class AangDataType {
    protected AangScript script;

    public AangDataType(AangScript script ){
        this.script = script;
    }
}
