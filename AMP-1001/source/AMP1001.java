package source;

import org.pircbotx.*;

public class AMP1001 {
	
	public static final PircBotX AMP1001 = new PircBotX();
	
	public static void main(String[] args){
		
		AMP1001.getListenerManager().addListener(new data.MessageData());
		AMP1001.getListenerManager().addListener(new data.EventData());
		
		AMP1001.setName("AMP-1001");
		AMP1001.setLogin("AMP-1001");
		AMP1001.setVerbose(false);
		AMP1001.setAutoNickChange(false);
		AMP1001.setCapEnabled(false);
		
		try {
			AMP1001.connect("irc.esper.net");
			AMP1001.joinChannel("#p_slice");
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
	}
}
