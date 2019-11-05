package org.pascal2spim.language.statements;

import org.pascal2spim.language.expressions.Expression;
import org.pascal2spim.mips32.GeneratedAssembly;
import org.pascal2spim.mips32.Register;
import org.pascal2spim.mips32.RegisterManager;

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

    public void generateCode(GeneratedAssembly generatedAssembly, RegisterManager registerManager) {
        Register[] state;
        int n = generatedAssembly.giveSequenceValue();
        int ordering;
        String labelIni = "__while" + n;
        String labelEnd = "__whileEnd" + n;
        generatedAssembly.addLabel(labelIni);
        state = registerManager.saveStateOfRegisters();
        ordering = registerManager.giveCurrentNumber();
        condition.generateCode(generatedAssembly, registerManager);
        condition.getRegister().checkAndLiberate(generatedAssembly);
        generatedAssembly.addCodeLine("beqz " + condition.getRegister().getName() + ", " + labelEnd);
        block.generateCode(generatedAssembly, registerManager);
        registerManager.storeLoadedVars(ordering, generatedAssembly);
        registerManager.recoverStateOfRegisters(state, generatedAssembly);
        generatedAssembly.addCodeLine("b " + labelIni);
        generatedAssembly.addLabel(labelEnd);
    }
}