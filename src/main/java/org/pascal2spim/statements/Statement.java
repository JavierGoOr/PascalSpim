package org.pascal2spim.statements;

import org.pascal2spim.GeneratedAssembly;
import org.pascal2spim.RegisterManager;

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
