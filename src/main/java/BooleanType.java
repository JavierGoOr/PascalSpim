public class BooleanType extends Type
{
	public boolean isEqualTo(Type other)
	{
		return this.isCompatibleWith(other);
	}
	public boolean isCompatibleWith(Type other)
	{
		boolean compatible = false;
		if(other instanceof BooleanType)
			compatible = true;
		return compatible;
	}
	public String toString()
	{
		return "boolean";
	}
}