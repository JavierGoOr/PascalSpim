package org.pascal2spim.statements;

import org.pascal2spim.Expression;
import org.pascal2spim.GeneratedAssembly;
import org.pascal2spim.Register;
import org.pascal2spim.RegisterManager;

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

    public void generateCode(GeneratedAssembly generatedAssembly, RegisterManager registerManager) {
        Register[] state;
        int n = generatedAssembly.giveSequenceValue();
        int ordering;
        String labelElse = "__else" + n;
        String labelEnd = "__ifend" + n;
        condition.generateCode(generatedAssembly, registerManager);
        condition.getRegister().checkAndLiberate(generatedAssembly);
        ordering = registerManager.giveCurrentNumber();
        state = registerManager.saveStateOfRegisters();
        if (blockElse != null) {
            generatedAssembly.addCodeLine("beqz " + condition.getRegister().getName() + ", " + labelElse);
            block.generateCode(generatedAssembly, registerManager);
            registerManager.storeLoadedVars(ordering, generatedAssembly);
            registerManager.recoverStateOfRegisters(state, generatedAssembly);
            generatedAssembly.addCodeLine("b " + labelEnd);
            generatedAssembly.addLabel(labelElse);
            blockElse.generateCode(generatedAssembly, registerManager);
            registerManager.storeLoadedVars(ordering, generatedAssembly);
            registerManager.recoverStateOfRegisters(state, generatedAssembly);
            generatedAssembly.addLabel(labelEnd);
        } else {
            generatedAssembly.addCodeLine("beqz " + condition.getRegister().getName() + ", " + labelEnd);
            block.generateCode(generatedAssembly, registerManager);
            registerManager.storeLoadedVars(ordering, generatedAssembly);
            registerManager.recoverStateOfRegisters(state, generatedAssembly);
            generatedAssembly.addLabel(labelEnd);
        }
    }
}