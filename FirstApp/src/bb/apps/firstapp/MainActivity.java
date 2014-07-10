package bb.apps.firstapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements IBasicChatPanel
{
	AndroidMessageHandler		IMH				= new AndroidMessageHandler();

	public final static String	EXTRA_MESSAGE	= "bb.apps.firstapp.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		super.onCreate(savedInstanceState);
		IMH.setNetworkInfo(networkInfo);
		addMessageHandler(IMH);
		IMH.addBasicChatPanel(this);
		setContentView(R.layout.activity_main);

		if(savedInstanceState == null)
		{
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(id == R.id.action_settings) { return true; }
		switch(item.getItemId()){
			case R.id.action_search :
				// openSearch();
				return true;

			case R.id.action_settings :
				// openSettings();
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment
	{

		public PlaceholderFragment()
		{}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootView = inflater.inflate(R.layout.fragment_main, container, false);
			return rootView;
		}
	}

	public void sendMessage(View view)
	{
		EditText editText = (EditText) findViewById(R.id.edit_message);
		String message = editText.getText().toString();
		IMH.Message(message);

	}

	private List<IMessageHandler>	IMHList	= new ArrayList<IMessageHandler>();

	@Override
	public void addMessageHandler(IMessageHandler M)
	{
		IMHList.add(M);
	}

	@Override
	public void WipeLog()
	{
		TextView textViwe = (TextView) findViewById(R.id.textView);
		textViwe.setText("");
	}

	@Override
	public void print(String s)
	{
		TextView textViwe = (TextView) findViewById(R.id.textView);
		textViwe.append(s);
	}

	@Override
	public void println(String s)
	{
		TextView textViwe = (TextView) findViewById(R.id.textView);
		textViwe.append(s + "\n");
	}

}
