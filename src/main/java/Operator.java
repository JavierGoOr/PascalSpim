public abstract class Operator {
    protected String kind;

    abstract public String getAssemblyOp(boolean fpoint);

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Type resultType(Type op1, Type op2) {
        return null;
    }

    public Type resultType(Type op1) {
        return null;
    }
}
