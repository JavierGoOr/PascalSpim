package org.pascal2spim;

import org.pascal2spim.types.ArrayType;
import org.pascal2spim.types.RealType;
import org.pascal2spim.types.Type;

public class VariableApparition extends FactorObject {
    protected SymbolTableEntry description;

    public VariableApparition() {
    }

    public VariableApparition(SymbolTableEntry description) {
        this.description = description;
    }

    public SymbolTableEntry getDescription() {
        return description;
    }

    public void setDescription(SymbolTableEntry description) {
        this.description = description;
    }

    public Type getType() {
        Variable v = (Variable) description.getObject();
        return v.getType();
    }

    public boolean isIndexable() {
        return (this.getType()).isIndexable();
    }

    public boolean isCorrect() {
        return true;
    }

    public int getIndexLevel() {
        return 0;
    }

    protected void setRegister(Register reg, RegisterManager registerManager) {
        reg.setVariable(this, registerManager);
        register = reg;
    }

    public void generateCode(GeneratedAssembly generatedAssembly, RegisterManager registerManager) {
        register = registerManager.getRegisterOfVar(this);
        if (register == null) {
            if (this.getType() instanceof RealType) {
                register = registerManager.getFreeFloatRegister(generatedAssembly);
                register.setVariable(this, registerManager);
                if (((Variable) description.getObject()).getIsLocal())
                    generatedAssembly.addCodeLine("l.d " + register.getName() + ", " + (((Variable) description.getObject()).getDisplacement() * -1) + "($fp)");
                else {
                    generatedAssembly.addCodeLine("l.d " + register.getName() + ", _" + description.getName());
                }
            } else /*if(this.getType() instanceof ArrayType)
			{
				//It has already a register, because of IndexedVariable
				register = rm.getFreeRegister();
				register.setVariable(this);
				code.addSentence("la " + register.getName() + ", " + description.getName());
			}
			else*/ {
                register = registerManager.getFreeRegister(generatedAssembly);
                register.setVariable(this, registerManager);
                if (((Variable) description.getObject()).getIsLocal())
                    generatedAssembly.addCodeLine("lw " + register.getName() + ", " + (((Variable) description.getObject()).getDisplacement() * -1) + "($fp)");
                else if (this.getType() instanceof ArrayType) {
                    generatedAssembly.addCodeLine("la " + register.getName() + ", _" + description.getName());
                } else {
                    generatedAssembly.addCodeLine("lw " + register.getName() + ", _" + description.getName());
                }
            }
        }
    }

    public boolean isEqualTo(VariableApparition var) {
        return var.getDescription() == description;
    }
}