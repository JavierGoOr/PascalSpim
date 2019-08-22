public abstract class FactorObject {
    protected Register register = null;

    abstract public Type getType();

    abstract public void generateCode();

    public Register getRegister() {
        return register;
    }
}