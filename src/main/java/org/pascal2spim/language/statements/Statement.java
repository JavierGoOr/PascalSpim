package org.pascal2spim.language.statements;

import org.pascal2spim.mips32.GeneratedAssembly;
import org.pascal2spim.mips32.RegisterManager;

public abstract class Statement {
    protected int line;
    protected int column;

    public int getLine() {
        return line;
    }

    public void setLine(int l) {
        line = l;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int c) {
        column = c;
    }

    abstract public void generateCode(GeneratedAssembly generatedAssembly, RegisterManager registerManager);

    public Statement hasReturnStatement() {
        return null;
    }
}
