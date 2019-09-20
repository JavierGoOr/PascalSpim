package org.pascal2spim.statements;

import org.pascal2spim.Code;
import org.pascal2spim.FunctionCall;
import org.pascal2spim.RegisterManager;

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

    public void generateCode(Code code, RegisterManager registerManager) {
        fc.generateCode(code, registerManager);
    }
}