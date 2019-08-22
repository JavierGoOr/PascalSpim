public class RegisterManager
{
	private Register [] registers;
	private Register [] fregisters;
	private Register fResult = null, iResult = null;
	private static RegisterManager instance;
	int n = 0; //number used to identify the order of loaded variables
	
	private RegisterManager()
	{
		registers = new Register[20];
		fregisters = new Register[15];
		/*for(int i = 0; i < 2; i++)
		{
			registers[i] = new Register("$v" + i, false);
		}*/
		//for(int i = 2; i < 6; i++)
		for(int i = 0; i < 2; i++)
		{
			registers[i] = new Register("$a" + (i + 2), false);
		}
		for(int i = 2; i < 12; i++)
		{
			registers[i] = new Register("$t" + (i - 2), false);
		}
		for(int i = 12; i < 20; i++)
		{
			registers[i] = new Register("$s" + (i - 12), false);
		}
		for(int i = 0; i < fregisters.length; i++)
		{
			fregisters[i] = new Register("$f" + (i * 2), true);
		}
		iResult = new Register("$v0", false);
		fResult = new Register("$f30", true);
	}
	
	public static RegisterManager getInstance()
	{
		if(instance == null)
			instance = new RegisterManager();
		return instance;
	}
	
	public int giveNumber()
	{
		int result = n;
		n++;
		return result;
	}
	public int giveCurrentNumber()
	{
		return n;
	}
	public Register getFreeRegister()
	{
		boolean found = false;
		Register result = null;
		int i = 0;
		while(i < registers.length && !found)
		{
			if(registers[i].getFree())
			{
				found = true;
				result = registers[i];
				registers[i].reserve();
			}
			else
				 i++;
		}
		if(!found)
		{
			i = 0;
			while(i < registers.length && !found)
			{
				if((registers[i].getVariable() != null) && (!registers[i].getNotStore()))
				{
					found = true;
					result = registers[i];
					registers[i].liberate();
					registers[i].reserve();
				}
				else
					 i++;
			}
		}
		return result;
	}
	public Register getFreeFloatRegister()
	{
		boolean found = false;
		Register result = null;
		int i = 0;
		while(i < fregisters.length && !found) //$f30 is excluded
		{
			if(fregisters[i].getFree())
			{
				found = true;
				result = fregisters[i];
				fregisters[i].reserve();
			}
			else
				 i++;
		}
		if(!found)
		{
			i = 0;
			while(i < fregisters.length && !found)
			{
				if((fregisters[i].getVariable() != null) && (!fregisters[i].getNotStore()))
				{
					found = true;
					result = fregisters[i];
					fregisters[i].liberate();
					fregisters[i].reserve();
				}
				else
					 i++;
			}
		}
		return result;
	}
	public Register getRegisterOfVar(VariableApparition var)
	{
		Register result = null;
		boolean found = false;
		int i = 0;
		if(!(var instanceof IndexedVariable))
		{
			while(i < registers.length && !found)
			{
				if(registers[i].getVariable() != null)
				{
					if(registers[i].getVariable().isEqualTo(var))
					{
						found = true;
						result = registers[i];
					}
				}
				i++;
			}
			if(!found)
			{
				i = 0;
				while(i < fregisters.length && !found)
				{
					if(fregisters[i].getVariable() != null)
					{
						if(fregisters[i].getVariable().isEqualTo(var))
						{
							found = true;
							result = fregisters[i];
						}
					}
					i++;
				}
			}
		}
		return result;
	}
	public void storeLoadedVars(int startNumber)
	{
		for(int i = 0; i < registers.length; i++)
		{
			if(registers[i].getVariable() != null)
				if(registers[i].getOrdering() >= startNumber)
					registers[i].liberate();
		}
		for(int i = 0; i < fregisters.length; i++)
		{
			if(fregisters[i].getVariable() != null)
				if(fregisters[i].getOrdering() >= startNumber)
					fregisters[i].liberate();
		}
		n = startNumber;
	}
	public Register[] saveStateOfRegisters()
	{
		Register[] result = new Register[registers.length + fregisters.length];
		for(int i = 0; i < registers.length; i++)
		{
			result[i] = registers[i].getState();
		}
		for(int i = 0; i < fregisters.length; i++)
		{
			result[registers.length + i] = fregisters[i].getState();
		}
		return result;
	}
	public void recoverStateOfRegisters(Register[] state)
	{
		Register rg;
		Code code = Code.getInstance();
		Variable aux = null;
		for(int i = 0; i < registers.length; i++)
		{
			if(state[i].getVariable() != null)
			{
				if((registers[i].getVariable() == null) || (!registers[i].getVariable().isEqualTo(state[i].getVariable())) )
				{
					rg = getRegisterOfVar(state[i].getVariable());
					if(rg != null)
						rg.liberate();
					registers[i].liberate();
					registers[i].reserve();
					registers[i].setVariable(state[i].getVariable(), state[i].getOrdering());
					aux = (Variable) state[i].getVariable().getDescription().getObject();
					if(aux.getIsLocal())
						code.addSentence("lw " + registers[i].getName() + ", " + (aux.getDisplacement() * -1) + "($fp)");
					else
					{
						code.addSentence("lw " + registers[i].getName() + ", _" + registers[i].getVariable().getDescription().getName());
					}
				}
			}
		}
		for(int i = 0; i < fregisters.length; i++)
		{
			if(state[registers.length + i].getVariable() != null)
			{
				if((fregisters[i].getVariable() == null) || (!fregisters[i].getVariable().isEqualTo(state[registers.length + i].getVariable())) )
				{
					rg = getRegisterOfVar(state[registers.length + i].getVariable());
					if(rg != null)
						rg.liberate();
					fregisters[i].liberate();
					fregisters[i].reserve();
					fregisters[i].setVariable(state[registers.length + i].getVariable(), state[registers.length + i].getOrdering());
					aux = (Variable) state[registers.length + i].getVariable().getDescription().getObject();
					if(aux.getIsLocal())
						code.addSentence("l.d " + fregisters[i].getName() + ", " + (aux.getDisplacement() * -1) + "($fp)");
					else
					{
						code.addSentence("l.d " + fregisters[i].getName() + ", _" + fregisters[i].getVariable().getDescription().getName());
					}
				}
			}
		}
	}
	public void saveVariables()
	{
		for(int i = 0; i < registers.length; i++)
		{
			registers[i].liberate();
		}
		for(int i = 0; i < fregisters.length; i++)
		{
			fregisters[i].liberate();
		}
	}
	public void saveRegisters()
	{
		for(int i = 0; i < registers.length; i++)
		{
			registers[i].save();
		}
		for(int i = 0; i < fregisters.length; i++)
		{
			fregisters[i].save();
		}
	}
	public void saveGlobalVariables()
	{
		for(int i = 0; i < registers.length; i++)
		{
			registers[i].liberateIfGlobalVariable();
		}
		for(int i = 0; i < fregisters.length; i++)
		{
			fregisters[i].liberateIfGlobalVariable();
		}
	}
	public void reloadGlobalVariables()
	{
		for(int i = 0; i < registers.length; i++)
		{
			registers[i].reloadIfGlobalVariable();
		}
		for(int i = 0; i < fregisters.length; i++)
		{
			fregisters[i].reloadIfGlobalVariable();
		}
	}
	public Register[] storeInStack()
	{
		Code code = Code.getInstance();
		Register[] result = saveStateOfRegisters();
		for(int i = 0; i < registers.length; i++)
		{
			if(!registers[i].getFree())
			{	
				code.addSentence("subu $sp, $sp, 4");
				code.addSentence("sw " + registers[i].getName() + ", ($sp)");
				registers[i].changeState(null, null, -1, true, false);
			}
		}
		for(int i = 0; i < fregisters.length; i++)
		{
			if(!fregisters[i].getFree())
			{	
				code.addSentence("subu $sp, $sp, 8");
				code.addSentence("s.d " + fregisters[i].getName() + ", ($sp)");
				fregisters[i].changeState(null, null, -1, true, false);
			}
		}	
		return result;
	}
	public void recoverFromStack(Register[] state)
	{
		Code code = Code.getInstance();
		for(int i = (fregisters.length - 1); i >= 0; i--)
		{
			if(!state[registers.length + i].getFree())
			{
				code.addSentence("l.d " + fregisters[i].getName() + ", ($sp)");	
				code.addSentence("addu $sp, $sp, 8");
				fregisters[i].changeState(state[registers.length + i].getVariable(), state[registers.length + i].getIndex(),
												 state[registers.length + i].getOrdering(), state[registers.length + i].getFree(), state[registers.length + i].getNotStore());
			}
		}
		for(int i = (registers.length - 1); i >= 0; i--)
		{
			if(!state[i].getFree())
			{	
				code.addSentence("lw " + registers[i].getName() + ", ($sp)");
				code.addSentence("addu $sp, $sp, 4");
				registers[i].changeState(state[i].getVariable(), state[i].getIndex(), state[i].getOrdering(), state[i].getFree(), state[i].getNotStore());
			}
		}
			
	}
	public Register getFloatResult()
	{
		return fResult;
	}
	public Register getIntResult()
	{
		return iResult;
	}
}