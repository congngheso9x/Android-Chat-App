package bb.apps.firstapp;

public interface IBasicChatPanel
{
	public void addMessageHandler(IMessageHandler M);

	public void WipeLog();

	public void print(String s);

	public void println(String s);
}
