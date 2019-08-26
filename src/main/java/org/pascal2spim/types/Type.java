package org.pascal2spim.types;

import org.pascal2spim.SymbolTableObject;

public abstract class Type implements SymbolTableObject {
    abstract public boolean isCompatibleWith(Type other);

    abstract public boolean isEqualTo(Type other);

    public boolean isIndexable() {
        return false;
    }

    public Type getElsType() {
        return null;
    }
}