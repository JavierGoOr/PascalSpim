public class RealType extends Type {
    public boolean isEqualTo(Type other) {
        boolean compatible = false;
        if (other instanceof RealType)
            compatible = true;
        return compatible;
    }

    public boolean isCompatibleWith(Type other) {
        boolean compatible = false;
        if ((other instanceof IntegerType) || (other instanceof RealType))
            compatible = true;
        return compatible;
    }

    public String toString() {
        return "real";
    }
}