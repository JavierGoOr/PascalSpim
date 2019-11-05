package org.pascal2spim.language.statements;

import org.pascal2spim.language.functions.FunctionCall;
import org.pascal2spim.mips32.GeneratedAssembly;
import org.pascal2spim.mips32.RegisterManager;

public class FunctionCallStatement extends Statement {
    private FunctionCall fc;

    public FunctionCallStatement(FunctionCall fc) {
        this.fc = fc;
    }

    public FunctionCall getFunctionCall() {
        return fc;
    }

    public void setFunctionCall(FunctionCall fc) {
        this.fc = fc;
    }

    public void generateCode(GeneratedAssembly generatedAssembly, RegisterManager registerManager) {
        fc.generateCode(generatedAssembly, registerManager);
    }
}