package org.pascal2spim.statements;

import org.pascal2spim.Code;
import org.pascal2spim.Expression;
import org.pascal2spim.Register;
import org.pascal2spim.RegisterManager;

public class WhileStatement extends Statement {
    private Expression condition;
    private Statement block;

    public WhileStatement(Expression condition, Statement block) {
        this.condition = condition;
        this.block = block;
    }

    public Expression getCondition() {
        return condition;
    }

    public void setCondition(Expression condition) {
        this.condition = condition;
    }

    public Statement getBlock() {
        return block;
    }

    public void setBlock(Statement block) {
        this.block = block;
    }

    public void generateCode(Code code, RegisterManager registerManager) {
        Register[] state;
        int n = code.getConsecutive();
        int ordering;
        String labelIni = "__while" + n;
        String labelEnd = "__whileEnd" + n;
        code.addLabel(labelIni);
        state = registerManager.saveStateOfRegisters();
        ordering = registerManager.giveCurrentNumber();
        condition.generateCode(code, registerManager);
        condition.getRegister().checkAndLiberate(code);
        code.addSentence("beqz " + condition.getRegister().getName() + ", " + labelEnd);
        block.generateCode(code, registerManager);
        registerManager.storeLoadedVars(ordering, code);
        registerManager.recoverStateOfRegisters(state, code);
        code.addSentence("b " + labelIni);
        code.addLabel(labelEnd);
    }
}