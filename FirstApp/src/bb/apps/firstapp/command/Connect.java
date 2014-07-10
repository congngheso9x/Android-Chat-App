package bb.apps.firstapp.command;

import bb.apps.firstapp.IChatActor;
import bb.apps.firstapp.IMessageHandler;

/**
 * @author BB20101997
 */
public class Connect extends ICommand
{

	/**
	 * Used Client-Side to Connect to a Server
	 */

	@Override
	public String getName()
	{

		return "connect";
	}

	@Override
	public boolean runCommandServer(String d, IMessageHandler a, IChatActor sender)
	{

		return false;
	}

	@Override
	public boolean runCommandClient(String d, IMessageHandler a)
	{

		String[] strA = d.split(" ");
		if(strA.length >= 3)
		{
			a.connect(strA[1], Integer.valueOf(strA[2]));
			return true;
		}
		else if(strA.length >= 2)
		{
			a.connect(strA[1], 256);
			return true;
		}
		else
		{
			a.connect("192.168.178.21", 256);

		}
		return false;
	}
}
