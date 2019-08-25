package org.pascal2spim;

public class IfStatement extends Statement {
    private Expression condition;
    private Statement block;
    private Statement blockElse;

    public IfStatement(Expression condition, Statement block) {
        this.condition = condition;
        this.block = block;
        this.blockElse = null;
    }

    public IfStatement(Expression condition, Statement block, Statement blockElse) {
        this.condition = condition;
        this.block = block;
        this.blockElse = blockElse;
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

    public Statement getBlockElse() {
        return blockElse;
    }

    public void setBlockElse(Statement blockElse) {
        this.blockElse = blockElse;
    }

    public void generateCode() {
        Code code = Code.getInstance();
        RegisterManager rm = RegisterManager.getInstance();
        Register[] state;
        int n = code.getConsecutive();
        int ordering;
        String labelElse = "__else" + n;
        String labelEnd = "__ifend" + n;
        condition.generateCode();
        condition.getRegister().checkAndLiberate();
        ordering = rm.giveCurrentNumber();
        state = rm.saveStateOfRegisters();
        if (blockElse != null) {
            code.addSentence("beqz " + condition.getRegister().getName() + ", " + labelElse);
            block.generateCode();
            rm.storeLoadedVars(ordering);
            rm.recoverStateOfRegisters(state);
            code.addSentence("b " + labelEnd);
            code.addLabel(labelElse);
            blockElse.generateCode();
            rm.storeLoadedVars(ordering);
            rm.recoverStateOfRegisters(state);
            code.addLabel(labelEnd);
        } else {
            code.addSentence("beqz " + condition.getRegister().getName() + ", " + labelEnd);
            block.generateCode();
            rm.storeLoadedVars(ordering);
            rm.recoverStateOfRegisters(state);
            code.addLabel(labelEnd);
        }
    }
}