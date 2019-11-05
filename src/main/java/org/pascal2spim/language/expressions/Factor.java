package org.pascal2spim.language.expressions;

import org.pascal2spim.language.operators.Operator;
import org.pascal2spim.language.types.Type;
import org.pascal2spim.mips32.GeneratedAssembly;
import org.pascal2spim.mips32.Register;
import org.pascal2spim.mips32.RegisterManager;

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

    public void generateCode(GeneratedAssembly generatedAssembly, RegisterManager registerManager) {
        String assemblyOp = "";
        factor.generateCode(generatedAssembly, registerManager);
        if (prevOperator != null) {
            assemblyOp = prevOperator.getAssemblyOp(false);
            generatedAssembly.addCodeLine(assemblyOp + " " + factor.getRegister().getName() + ", " + factor.getRegister().getName() + ", 0x00000001");
        }
    }
}