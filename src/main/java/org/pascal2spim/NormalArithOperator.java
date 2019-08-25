package org.pascal2spim;

import org.pascal2spim.types.IntegerType;
import org.pascal2spim.types.RealType;
import org.pascal2spim.types.Type;

public class NormalArithOperator extends Operator {
    public Type resultType(Type op1, Type op2) {
        Type result = null;
        if ((op1 instanceof IntegerType) || (op1 instanceof RealType))
            if ((op2 instanceof IntegerType) || (op2 instanceof RealType)) {
                if (op1.isCompatibleWith(op2))
                    result = op1;
                else if (op2.isCompatibleWith(op1))
                    result = op2;
            }
        return result;
    }

    public Type resultType(Type op1) {
        Type result = null;
        if ((kind.compareTo("+") == 0) || (kind.compareTo("-") == 0))
            if ((op1 instanceof IntegerType) || (op1 instanceof RealType))
                result = op1;
        return result;
    }

    public String getAssemblyOp(boolean fpoint) {
        String result = "";
        if (fpoint) {
            if (kind.compareTo("+") == 0)
                result = "add.d";
            else if (kind.compareTo("-") == 0)
                result = "sub.d";
            else if (kind.compareTo("*") == 0)
                result = "mul.d";
        } else {
            if (kind.compareTo("+") == 0)
                result = "add";
            else if (kind.compareTo("-") == 0)
                result = "sub";
            else if (kind.compareTo("*") == 0)
                result = "mul";
        }
        return result;
    }
}