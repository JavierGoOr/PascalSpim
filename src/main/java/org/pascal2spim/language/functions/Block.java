package org.pascal2spim.language.functions;

import org.pascal2spim.language.statements.Statement;
import org.pascal2spim.mips32.GeneratedAssembly;
import org.pascal2spim.mips32.RegisterManager;
import org.pascal2spim.symboltable.SymbolTableEntry;

import java.util.ArrayList;
import java.util.List;

public class Block {
    private List<SymbolTableEntry> functsProcs = new ArrayList<>();
    private Statement statements;
    private String fname = null;

    public Block() {
    }

    public Block(String fname) {
        this.fname = fname;
    }


    public Statement getStatements() {
        return statements;
    }

    public void setStatements(Statement statements) {
        this.statements = statements;
    }

    public void addFunctOrProc(SymbolTableEntry functProc) {
        functsProcs.add(functProc);
    }

    public void generateCode(GeneratedAssembly generatedAssembly, RegisterManager registerManager) {
        FunctOrProc fp;
        statements.generateCode(generatedAssembly, registerManager);
        registerManager.saveVariables(generatedAssembly);
        if ((fname != null) && (fname.compareTo("1") == 0)) {
            generatedAssembly.addCodeLine("li $v0, 10");
            generatedAssembly.addCodeLine("syscall");
        }
        for (SymbolTableEntry ste : functsProcs) {
            fp = (FunctOrProc) ste.getObject();
            fp.generateCode(ste.getName(), generatedAssembly, registerManager);
        }
        if ((fname != null) && (fname.compareTo("1") == 0)) {
            generatedAssembly.addDataDefinition("\tw_ln:\t.asciiz \"\\n\"");
            generatedAssembly.addDataDefinition("\tw_char:\t.asciiz \"\\n\"");
            generatedAssembly.addDataDefinition("\tw_true:\t.asciiz \"true\"");
            generatedAssembly.addDataDefinition("\tw_false:\t.asciiz \"false\"");
        }
    }
}