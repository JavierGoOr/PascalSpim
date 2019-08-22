class BooleanConstant extends Constant
{
	private boolean value;
	
	public BooleanConstant()
	{
		super(new BooleanType());
	}
	public boolean getValue()
	{
		return value;
	}
	public void setValue(boolean val)
	{
		value = val;
	}
	public void storePascalValue(int line, int column, String value)
	{
		if(value.compareToIgnoreCase("true") == 0)
			this.value = true;
		else
			this.value = false;
	}
	public String toString()
	{
		if(value)
			return "true";
		else
			return "false";
	}
	public void generateCode()
	{
		int boolValue;
		RegisterManager rm = RegisterManager.getInstance();
		Code code = Code.getInstance();
		register = rm.getFreeRegister();
		if(value)
			boolValue = 1;
		else
			boolValue = 0;
		code.addSentence("li " + register.getName() + ", " + boolValue);
	}
}