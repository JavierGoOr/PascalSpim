public class VariableApparition extends FactorObject
{
	protected SymbolTableEntry description;
	
	public VariableApparition(){}
	public VariableApparition(SymbolTableEntry description)
	{
		this.description = description;
	}
	
	public SymbolTableEntry getDescription()
	{
		return description;
	}
	public void setDescription(SymbolTableEntry description)
	{
		this.description = description;
	}
	public Type getType()
	{
		Variable v = (Variable) description.getObject();
		return v.getType();
	}
	public boolean isIndexable()
	{
		return (this.getType()).isIndexable();
	}
	public boolean isCorrect()
	{
		return true;
	}
	public int getIndexLevel()
	{
		return 0;
	}
	protected void setRegister(Register reg)
	{
		reg.setVariable(this);
		register = reg;
	}
	public void generateCode()
	{
		RegisterManager rm = RegisterManager.getInstance();
		register = rm.getRegisterOfVar(this);
		if(register == null)
		{
			Code code = Code.getInstance();
			if(this.getType() instanceof RealType)
			{
				register = rm.getFreeFloatRegister();
				register.setVariable(this);
				if(((Variable)description.getObject()).getIsLocal())
					code.addSentence("l.d " + register.getName() + ", " + (((Variable)description.getObject()).getDisplacement() * -1) + "($fp)");
				else
				{
					code.addSentence("l.d " + register.getName() + ", _" + description.getName());
				}
			}
			else /*if(this.getType() instanceof ArrayType)
			{
				//It has already a register, because of IndexedVariable
				register = rm.getFreeRegister();
				register.setVariable(this);
				code.addSentence("la " + register.getName() + ", " + description.getName());
			}
			else*/
			{
				register = rm.getFreeRegister();
				register.setVariable(this);
				if(((Variable)description.getObject()).getIsLocal())
					code.addSentence("lw " + register.getName() + ", " + (((Variable)description.getObject()).getDisplacement() * -1) + "($fp)");
				else if(this.getType() instanceof ArrayType)
				{
					code.addSentence("la " + register.getName() + ", _" + description.getName());
				}
				else
				{
					code.addSentence("lw " + register.getName() + ", _" + description.getName());
				}
			}
		}
	}
	public boolean isEqualTo(VariableApparition var)
	{
		return var.getDescription() == description;
	}
}