public class IntegerType extends Type {
    public boolean isEqualTo(Type other) {
        return this.isCompatibleWith(other);
    }

    public boolean isCompatibleWith(Type other) {
        boolean compatible = false;
        if (other instanceof IntegerType)
            compatible = true;
        return compatible;
    }

    public String toString() {
        return "integer";
    }
}