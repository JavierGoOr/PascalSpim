package org.pascal2spim.language.operators;

import org.pascal2spim.language.types.IntegerType;
import org.pascal2spim.language.types.RealType;
import org.pascal2spim.language.types.Type;

public class RealDivisionOperator extends Operator {
    public Type resultType(Type op1, Type op2) {
        Type result = null;
        if ((op1 instanceof IntegerType) || (op1 instanceof RealType))
            if ((op2 instanceof IntegerType) || (op2 instanceof RealType))
                result = new RealType();
        return result;
    }

    public Type resultType(Type op1) {
        return null;
    }

    public String getAssemblyOp(boolean fpoint) {
        return "div.d";
    }
}