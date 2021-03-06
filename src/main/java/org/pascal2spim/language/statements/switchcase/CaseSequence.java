package org.pascal2spim.language.statements.switchcase;

import org.pascal2spim.language.constants.Constant;
import org.pascal2spim.language.statements.Statement;

public class CaseSequence {
    private Constant caseConst;
    private Statement block;

    public CaseSequence(Constant caseConst, Statement block) {
        this.caseConst = caseConst;
        this.block = block;
    }

    public Constant getCaseConst() {
        return caseConst;
    }

    public void setCaseConst(Constant caseConst) {
        this.caseConst = caseConst;
    }

    public Statement getBlock() {
        return block;
    }

    public void setBlock(Statement block) {
        this.block = block;
    }
}