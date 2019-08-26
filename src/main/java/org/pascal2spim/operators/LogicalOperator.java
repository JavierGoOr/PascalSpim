package org.pascal2spim.operators;

import org.pascal2spim.types.BooleanType;
import org.pascal2spim.types.Type;

public class LogicalOperator extends Operator {
    public Type resultType(Type op1, Type op2) {
        Type result = null;
        if ((op1 instanceof BooleanType) && (op2 instanceof BooleanType))
            result = new BooleanType();
        return result;
    }

    public Type resultType(Type op1) {
        Type result = null;
        if (kind.compareToIgnoreCase("not") == 0)
            if (op1 instanceof BooleanType)
                result = op1;
        return result;
    }

    public String getAssemblyOp(boolean fpoint) {
        String result = "";
        if (kind.compareTo("not") == 0)
            result = "xor";
        else if (kind.compareTo("and") == 0)
            result = "and";
        else if (kind.compareTo("or") == 0)
            result = "or";
        return result;
    }
}