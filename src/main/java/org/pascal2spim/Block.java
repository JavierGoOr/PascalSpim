package org.pascal2spim;

import org.pascal2spim.statements.Statement;

import java.util.Vector;

public class Block {
    private Vector functsProcs = new Vector();
    private Statement statements;
    private String fname = null;

    public Block() {
    }

    /*public Block(Statement statements)
    {
        this.statements = statements;
    }*/
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

    public void generateCode(Code code, RegisterManager registerManager) {
        SymbolTableEntry ste;
        FunctOrProc fp;
        statements.generateCode(code, registerManager);
        registerManager.saveVariables(code);
        if ((fname != null) && (fname.compareTo("1") == 0)) {
            code.addSentence("li $v0, 10");
            code.addSentence("syscall");
        }
        for (int i = 0; i < functsProcs.size(); i++) {
            ste = (SymbolTableEntry) functsProcs.elementAt(i);
            fp = (FunctOrProc) ste.getObject();
            fp.generateCode(ste.getName(), code, registerManager);
        }
        if ((fname != null) && (fname.compareTo("1") == 0)) {
            code.addDataSentence("\tw_ln:\t.asciiz \"\\n\"");
            code.addDataSentence("\tw_char:\t.asciiz \"\\n\"");
            code.addDataSentence("\tw_true:\t.asciiz \"true\"");
            code.addDataSentence("\tw_false:\t.asciiz \"false\"");
        }
    }
}