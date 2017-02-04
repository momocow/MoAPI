package me.momocow.mobasic.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;

public abstract class MoCommand extends CommandBase
{
	private String unlocalizedName;
	
	public String getUnlocalizedName()
	{
		if(!this.unlocalizedName.isEmpty()) return "commands." + this.unlocalizedName;
		
		String clazz = this.getClass().getName();
		
		if(clazz.indexOf("Command") == 0) clazz = clazz.substring(7);
		
		return "commands." + clazz;
	}
	
	public void setUnlocalizedName(String name)
	{
		this.unlocalizedName = name;
	}
	
	@Override
	public int compareTo(ICommand arg0) {
		return 0;
	}
	
	@Override
	public String getCommandUsage(ICommandSender sender) 
	{
		return this.getUnlocalizedName() + ".usage";
	}
}
