public class StringType extends Type
{
	int resLength = 255; //reserved length
	
	public StringType() {}
	public StringType(int resLength)
	{
		this.resLength = resLength;
	}
	
	public int getResLength()
	{
		return resLength;
	}
	public void setResLength (int resLength)
	{
	   this.resLength = resLength;
	}
	public boolean isEqualTo(Type other)
	{
		boolean compatible = false;
		if(other instanceof StringType)
			compatible = true;
		return compatible;
	}
	public boolean isCompatibleWith(Type other)
	{
		boolean compatible = false;
		if((other instanceof CharType) || (other instanceof StringType))
			compatible = true;
		return compatible;
	}
	public boolean isIndexable()
	{
		return true;
	}
	public Type getElsType()
	{
		return new CharType();
	}
	public String toString()
	{
		return "string";
	}
}