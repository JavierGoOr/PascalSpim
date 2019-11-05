package org.pascal2spim.language.functions;

import org.pascal2spim.language.types.Type;

public class Function extends FunctOrProc {
    private Type returnType;

    public Type getReturnType() {
        return returnType;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }
}