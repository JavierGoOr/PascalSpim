package org.pascal2spim.statements.switchcase;

import org.pascal2spim.Expression;
import org.pascal2spim.GeneratedAssembly;
import org.pascal2spim.Register;
import org.pascal2spim.RegisterManager;
import org.pascal2spim.constants.BooleanConstant;
import org.pascal2spim.constants.CharConstant;
import org.pascal2spim.constants.Constant;
import org.pascal2spim.constants.IntegerConstant;
import org.pascal2spim.statements.Statement;

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

    public void generateCode(GeneratedAssembly generatedAssembly, RegisterManager registerManager) {
        Register[] state;
        CaseSequence cs;
        String value = null;
        int n = generatedAssembly.giveSequenceValue();
        int ordering;
        caseExpr.generateCode(generatedAssembly, registerManager);
        caseExpr.getRegister().checkAndLiberate(generatedAssembly);
        ordering = registerManager.giveCurrentNumber();
        state = registerManager.saveStateOfRegisters();
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
            generatedAssembly.addCodeLine("beq " + caseExpr.getRegister().getName() + ", " + value + ", case" + n + "_b" + i);
        }
        if (elseBlock != null)
            generatedAssembly.addCodeLine("b case" + n + "_else");
        else
            generatedAssembly.addCodeLine("b case" + n + "_end");
        for (int i = 0; i < caseStats.size(); i++) {
            generatedAssembly.addLabel("case" + n + "_b" + i);
            cs = (CaseSequence) caseStats.elementAt(i);
            cs.getBlock().generateCode(generatedAssembly, registerManager);
            registerManager.storeLoadedVars(ordering, generatedAssembly);
            registerManager.recoverStateOfRegisters(state, generatedAssembly);
            generatedAssembly.addCodeLine("b case" + n + "_end");
        }
        if (elseBlock != null) {
            generatedAssembly.addLabel("case" + n + "_else");
            elseBlock.generateCode(generatedAssembly, registerManager);
            registerManager.storeLoadedVars(ordering, generatedAssembly);
            registerManager.recoverStateOfRegisters(state, generatedAssembly);
        }
        generatedAssembly.addLabel("case" + n + "_end");
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