package org.pascal2spim.constants;

import org.pascal2spim.FactorObject;
import org.pascal2spim.SymbolTableObject;
import org.pascal2spim.types.Type;

public abstract class Constant extends FactorObject implements SymbolTableObject {
    protected Type type;

    public Constant(Type type) {
        this.type = type;
    }

    abstract public void storePascalValue(int line, int column, String value);

    public Type getType() {
        return type;
    }

    public boolean isEqualTo(Constant other) {
        boolean result = false;
        if (this.getType().isEqualTo(other.getType()))
            if (this.toString().compareTo(other.toString()) == 0)
                result = true;
        return result;
    }
}