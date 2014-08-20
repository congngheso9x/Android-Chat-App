package bb.apps.firstapp;

import java.io.IOException;
import java.net.Socket;

import bb.apps.firstapp.command.ICommand;
import android.net.NetworkInfo;

public class AndroidMessageHandler extends BasicMessageHandler
{

	IChatActor			ICActor		= new IChatActor(){

										private String	name	= "Client";

										@Override
										public String getActorName()
										{

											return name;
										}

										@Override
										public void setActorName(String s)
										{

											name = s;
										}

										@Override
										public void disconnect()
										{

										}
									};

	Socket				socket;

	IOHandler			IRServer	= null;
	Thread				t;

	private NetworkInfo	netinf;

	public AndroidMessageHandler()
	{

		side = Side.CLIENT;
		localActor = ICActor;
		addCommand(bb.apps.firstapp.command.Connect.class);
		addCommand(bb.apps.firstapp.command.Whisper.class);
		addCommand(bb.apps.firstapp.command.Rename.class);
		addCommand(bb.apps.firstapp.command.Disconnect.class);
	}

	@Override
	public void recieveMessage(String s, IChatActor ica)
	{
		System.out.println("Recieved : " + s);

		if(s.equals("")) { return; }
		if(s.startsWith("/"))
		{
			String[] strA = s.split(" ");
			ICommand ic = getCommand(strA[0].replace("/", ""));
			if(ic != null)
			{
				ic.runCommandRecievedFromServer(s, this);
			}
		}
		else
		{
			println(s);
		}
	}

	@Override
	public void sendMessage(String text, IChatActor Send)
	{
		if(IRServer != null)
		{
			if(Target != ALL)
			{
				IRServer.getOut().println("/whisper " + Target.getActorName() + " " + text);
			}
			else
			{
				IRServer.getOut().println(text);
			}
			IRServer.getOut().flush();
		}
		else
		{
			println("You are not connected to a Server!\nPlease connect first!");
		}

	}

	@Override
	public void disconnect(IChatActor a)
	{
		try
		{
            workingRunnable.stop();
            workingRunnable = null;
            workingThread = null;
			socket.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		IRServer.removeMessageHandler(this);
	}

	@Override
	public void connect(String host, int port)
	{
		if(IRServer != null)
		{
			disconnect(IRServer);
			try
			{
				IRServer.finalize();
			}
			catch(Throwable e)
			{

				e.printStackTrace();
			}
			IRServer = null;
		}

		socket = new ConnectionEstablisher(host, port, this, netinf).getSocket();

		try
		{
			IRServer = new IOHandler(socket.getInputStream(), socket.getOutputStream(), this);
			t = new Thread(IRServer);
			t.start();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

	}

	public void setNetworkInfo(NetworkInfo networkInfo)
	{
		netinf = networkInfo;

	}

    public void whip(){
        for(IBasicChatPanel bcp : BCPList){
            bcp.WipeLog();
        }
    }

}
