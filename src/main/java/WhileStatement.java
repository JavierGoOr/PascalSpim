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

    public void generateCode() {
        Code code = Code.getInstance();
        RegisterManager rm = RegisterManager.getInstance();
        Register[] state;
        int n = code.getConsecutive();
        int ordering;
        String labelIni = "__while" + n;
        String labelEnd = "__whileEnd" + n;
        code.addLabel(labelIni);
        state = rm.saveStateOfRegisters();
        ordering = rm.giveCurrentNumber();
        condition.generateCode();
        condition.getRegister().checkAndLiberate();
        code.addSentence("beqz " + condition.getRegister().getName() + ", " + labelEnd);
        block.generateCode();
        rm.storeLoadedVars(ordering);
        rm.recoverStateOfRegisters(state);
        code.addSentence("b " + labelIni);
        code.addLabel(labelEnd);
    }
}