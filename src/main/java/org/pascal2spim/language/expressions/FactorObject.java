package org.pascal2spim.language.expressions;

import org.pascal2spim.language.types.Type;
import org.pascal2spim.mips32.GeneratedAssembly;
import org.pascal2spim.mips32.Register;
import org.pascal2spim.mips32.RegisterManager;

public abstract class FactorObject {
    protected Register register = null;

    abstract public Type getType();

    abstract public void generateCode(GeneratedAssembly generatedAssembly, RegisterManager registerManager);

    public Register getRegister() {
        return register;
    }
}