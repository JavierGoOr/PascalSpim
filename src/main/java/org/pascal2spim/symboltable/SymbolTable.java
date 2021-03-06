package org.pascal2spim.symboltable;

import org.pascal2spim.mips32.GeneratedAssembly;

import java.util.List;

public class SymbolTable {
    private static SymbolTable instance;
    private boolean error;
    private Namespace namespace;

    private SymbolTable() {
        init();
    }

    public static SymbolTable getInstance() {
        if (instance == null)
            instance = new SymbolTable();
        return instance;
    }

    public void init() {
        error = false;
        namespace = new Namespace("1");
    }

    public void dump() {
        namespace.dump();
    }

    public void addSymbol(SymbolTableEntry entry) {
        namespace.addSymbol(entry);
    }

    public SymbolTableEntry getSymbol(String name, String scope) {
        return namespace.getSymbol(name, scope);
    }

    public boolean canBeOveridden(String name, String scope) {
        return namespace.canBeOveridden(name, scope);
    }

    public void generateCode(GeneratedAssembly generatedAssembly) {
        namespace.generateCode(generatedAssembly);
    }

    public boolean getError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<SymbolTableEntry> getLocalVariables(String scope) {
        return namespace.getLocalVariables(scope);
    }
}