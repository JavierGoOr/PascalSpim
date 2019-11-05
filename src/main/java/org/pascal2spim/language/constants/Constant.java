package org.pascal2spim.language.constants;

import org.pascal2spim.language.expressions.FactorObject;
import org.pascal2spim.language.types.Type;
import org.pascal2spim.symboltable.SymbolTableObject;

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