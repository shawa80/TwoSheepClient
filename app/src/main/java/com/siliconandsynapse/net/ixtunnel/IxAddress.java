package com.siliconandsynapse.net.ixtunnel;


import androidx.annotation.NonNull;

public class IxAddress
{
	private final String name;
	private final IxAddress parent;
	private final boolean isRootElement;

	public static IxAddress parse(String addressStr) throws ParseError
	{
		IxAddress address;
		IxAddress parent;
		String nodes[];
		int i;

		nodes = addressStr.split("\\.");

		parent = null;
		address = null;
		for (i = 0; i < nodes.length; i++)
		{
			address = new IxAddress(parent, nodes[i]);
			parent = address;
		}
		if (address == null)
		{
			throw new ParseError("Empty name cannot be parsed.");
		}

		return address;
	}
	public static IxAddress createRoot(String name)
	{
		return new IxAddress(null, name);
	}

	public IxAddress append(String name)
	{
		return new IxAddress(this, name);
	}

	private IxAddress(IxAddress parent, String name)
	{

//		if (name == null)
//			throw new ParseError("Cannot parse a null");
//
//		if (name.contains("."))
//			throw new ParseError("Name cannot contain '.'");

		this.name = name;
		this.parent = parent;
		if (parent == null)
			this.isRootElement = true;
		else
			this.isRootElement = false;

	}


	public boolean equals(Object c)
	{
		IxAddress o;

		if (c == null)
			return false;

		if (!(c instanceof IxAddress))
			return false;

		o = (IxAddress)c;

		return toString().equals(o.toString());

	}
	@NonNull
	public String toString()
	{
		if (isRootElement)
		{
			return name;
		}
		return parent.toString() + "." + name;
	}
	public int hashCode()
	{
		return toString().hashCode();
	}


	public String getFullPath()
	{
		return toString();
	}

	public String name()
	{
		return name;
	}

	public IxAddress getParent()
	{
		if (isRootElement)
		{
			return null;
		}
		else
		{
			return parent;
		}
	}

	public boolean isRoot()
	{
		return isRootElement;
	}
}
