package com.skeldoor.coffeelover;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class CoffeeLoverPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(CoffeeLoverPlugin.class);
		RuneLite.main(args);
	}
}