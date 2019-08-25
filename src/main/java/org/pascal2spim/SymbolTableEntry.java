package org.pascal2spim;

public class SymbolTableEntry {
    private String name;
    private String scope;
    private SymbolTableObject object;

    public SymbolTableEntry(String name, String scope, SymbolTableObject object) {
        this.name = name;
        this.scope = scope;
        this.object = object;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public SymbolTableObject getObject() {
        return object;
    }

    public void setObject(SymbolTableObject object) {
        this.object = object;
    }


    public String toString() {
        return name;
    }
}