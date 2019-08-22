public class IntegerArithOperator extends Operator {
    public Type resultType(Type op1, Type op2) {
        Type result = null;
        if ((op1 instanceof IntegerType) && (op2 instanceof IntegerType))
            result = new IntegerType();
        return result;
    }

    public Type resultType(Type op1) {
        return null;
    }

    public String getAssemblyOp(boolean fpoint) {
        String result = "";
        if (kind.compareTo("div") == 0)
            result = "div";
        else if (kind.compareTo("mod") == 0)
            result = "rem";
        return result;
    }
}