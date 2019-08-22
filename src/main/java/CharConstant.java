public class CharConstant extends Constant
{
	private char value;
	
	public CharConstant()
	{
		super(new CharType());
	}
	public char getValue()
	{
		return value;
	}
	public void setValue(char val)
	{
		value = val;
	}
	public void storePascalValue(int line, int column, String value)
	{
		this.value = value.charAt(1);
	}
	public String toString()
	{
		return "" + value;
	}
	public void generateCode()
	{
		RegisterManager rm = RegisterManager.getInstance();
		Code code = Code.getInstance();
		register = rm.getFreeRegister();
		code.addSentence("li " + register.getName() + ", '" + value + "'");
	}
}