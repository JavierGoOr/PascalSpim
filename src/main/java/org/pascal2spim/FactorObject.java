package org.pascal2spim;

import org.pascal2spim.types.Type;

public abstract class FactorObject {
    protected Register register = null;

    abstract public Type getType();

    abstract public void generateCode(GeneratedAssembly generatedAssembly, RegisterManager registerManager);

    public Register getRegister() {
        return register;
    }
}