package org.pascal2spim.statements;

import org.pascal2spim.*;

import java.util.Vector;

public class CaseStatement extends Statement {
    private Expression caseExpr;
    private Vector caseStats = new Vector();
    private Statement elseBlock;

    public CaseStatement(Expression caseExpr) {
        this.caseExpr = caseExpr;
    }

    public Expression getCaseExpr() {
        return caseExpr;
    }

    public void setCaseExpr(Expression caseExpr) {
        this.caseExpr = caseExpr;
    }

    public void addCaseBlock(Constant caseConst, Statement block) {
        if (caseConst == null)
            elseBlock = block;
        else
            caseStats.add(new CaseSequence(caseConst, block));
    }

    public void generateCode() {
        Code code = Code.getInstance();
        RegisterManager rm = RegisterManager.getInstance();
        Register[] state;
        CaseSequence cs;
        String value = null;
        int n = code.getConsecutive();
        int ordering;
        caseExpr.generateCode();
        caseExpr.getRegister().checkAndLiberate();
        ordering = rm.giveCurrentNumber();
        state = rm.saveStateOfRegisters();
        for (int i = 0; i < caseStats.size(); i++) {
            cs = (CaseSequence) caseStats.elementAt(i);
            if (cs.getCaseConst() instanceof BooleanConstant) {
                if (cs.getCaseConst().toString().compareTo("true") == 0)
                    value = "1";
                else
                    value = "0";
            } else if (cs.getCaseConst() instanceof IntegerConstant) {
                value = cs.getCaseConst().toString();
            } else if (cs.getCaseConst() instanceof CharConstant) {
                value = "'" + cs.getCaseConst().toString() + "'";
            }
            code.addSentence("beq " + caseExpr.getRegister().getName() + ", " + value + ", case" + n + "_b" + i);
        }
        if (elseBlock != null)
            code.addSentence("b case" + n + "_else");
        else
            code.addSentence("b case" + n + "_end");
        for (int i = 0; i < caseStats.size(); i++) {
            code.addLabel("case" + n + "_b" + i);
            cs = (CaseSequence) caseStats.elementAt(i);
            cs.getBlock().generateCode();
            rm.storeLoadedVars(ordering);
            rm.recoverStateOfRegisters(state);
            code.addSentence("b case" + n + "_end");
        }
        if (elseBlock != null) {
            code.addLabel("case" + n + "_else");
            elseBlock.generateCode();
            rm.storeLoadedVars(ordering);
            rm.recoverStateOfRegisters(state);
        }
        code.addLabel("case" + n + "_end");
    }

    public boolean isRepeated(Constant caseConst) {
        int i = 0;
        boolean found = false;
        Constant cte;
        while (i < caseStats.size() & !found) {
            cte = ((CaseSequence) caseStats.elementAt(i)).getCaseConst();
            if (caseConst.isEqualTo(cte))
                found = true;
            i++;
        }
        return found;
    }
}