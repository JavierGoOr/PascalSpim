package org.pascal2spim;

import org.pascal2spim.types.Type;

public class Factor {
    private Operator prevOperator; //it can only be a NOT operator
    private FactorObject factor;

    public FactorObject getFactor() {
        return factor;
    }

    public void setFactor(FactorObject factor) {
        this.factor = factor;
    }

    public Operator getPrevOperator() {
        return prevOperator;
    }

    public void setPrevOperator(Operator prevOperator) {
        this.prevOperator = prevOperator;
    }

    public Type getType() {
        Type aux = factor.getType();
        if (prevOperator != null)
            aux = prevOperator.resultType(factor.getType());
        return aux;
    }

    public Register getRegister() {
        return factor.getRegister();
    }

    public void generateCode() {
        Code code = Code.getInstance();
        String assemblyOp = "";
        factor.generateCode();
        if (prevOperator != null) {
            assemblyOp = prevOperator.getAssemblyOp(false);
            code.addSentence(assemblyOp + " " + factor.getRegister().getName() + ", " + factor.getRegister().getName() + ", 0x00000001");
        }
    }
}