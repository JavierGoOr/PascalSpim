package org.pascal2spim.types;

public class CharType extends Type {
    public boolean isEqualTo(Type other) {
        return this.isCompatibleWith(other);
    }

    public boolean isCompatibleWith(Type other) {
        boolean compatible = false;
        if (other instanceof CharType)
            compatible = true;
        return compatible;
    }

    public String toString() {
        return "char";
    }
}