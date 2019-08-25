package org.pascal2spim;

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

    public void generateCode() {
        fc.generateCode();
    }
}