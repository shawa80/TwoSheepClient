package com.siliconandsynapse.ixcpp.util;

import java.lang.reflect.*;

public class DynLoad
{
	public DynLoad()
	{

	}

	@SuppressWarnings("rawtypes")
	public Object loadClass(String className, Object[] params) throws 
		ClassNotFoundException, InstantiationException, IllegalAccessException, 
		IllegalArgumentException, InvocationTargetException, LinkageError, 
		ExceptionInInitializerError, Exception
	{
		Class argTypes[];

		argTypes = new Class[params.length];

		for (int i = 0; i < argTypes.length; i++)
		{
			argTypes[i] = params[i].getClass();
		}

		return loadClass(className, argTypes, params);
	}

	@SuppressWarnings("rawtypes")
	private Object loadClass(String className, Class[] argTypes, Object[] params) throws 
		ClassNotFoundException, InstantiationException, IllegalAccessException, 
		IllegalArgumentException, InvocationTargetException, LinkageError, 
		ExceptionInInitializerError, Exception
	{
		Class myClass;		//the class we are going to load
		Constructor cons[];		//all of the constructors for our class
		Class classes[];		//the arguments for the constructors
		Class interfaces[];		//the possible interfaces for an object;

		Constructor myCon = null;	//the constructor we actually want
		Object myInstance = null;

		boolean defaultConstructor = false;

		try {
			//load the class
			myClass = Class.forName(className);

			//get all of the constructors fo the class
			cons = myClass.getConstructors();

			//find the constructor that matches the one we want
			for (int i = 0; i < cons.length; i++)
			{
				classes = cons[i].getParameterTypes();

				boolean gotOne = false;
				boolean error = false;
				if  (classes.length == argTypes.length)
				{

					//Check for the default constructor
					if (classes.length == 0)
					{
						defaultConstructor = true;
						gotOne = true;
						myCon = cons[i];  //we found our constructor
						break;
					}

					//check each arg
					for (int j = 0; j < classes.length; j++)
					{
						/************************
						Check the object plus its parents for a match
						************************/

						if (checkParent(argTypes[j], classes[j]))
						{
							gotOne = true;
						}


						/************************
						Check the interfaces of the object plus its 
						interface parents for a match
						************************/

						interfaces = argTypes[j].getInterfaces();
						for (int k = 0; k < interfaces.length; k++)
						{
							if (checkParent(interfaces[k], classes[j]))
							{
								gotOne = true;
								break;
							}
						}
						if (gotOne == false)
						{
							error = true;
							break;
						}
					}
				}
				else
				{
					error = true;
				}

				if (error == false)
				{
					myCon = cons[i];  //we found our constructor
					break;
				}

			}

			//if we found the constructor, create the class using that constructor
			if (myCon != null)
			{
				if (defaultConstructor == true)
					myInstance = myCon.newInstance();
				else
					myInstance = myCon.newInstance(params);
			}
			else
			{
				throw new IllegalArgumentException("The Arguments passed could not be matched toa constructor.");
			}

		} catch (Exception e) {
			throw e;
		}



		return myInstance;
	}


	@SuppressWarnings("rawtypes")
	private boolean checkParent(Class test, Class correct)
	{
		if (test == correct)		//if we have a match return ok
			return true;

		if (test == Object.class)	//we got to the bottom of the inheritence
			return false;		

		if (test == null)		//interfaces inherit from null
			return false;

		return checkParent(test.getSuperclass(), correct);
	}

}

