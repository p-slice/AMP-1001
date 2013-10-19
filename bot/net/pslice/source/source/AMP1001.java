package source;

import org.pircbotx.*;

public class AMP1001 {
	
	public static final PircBotX AMP = new PircBotX();
	
	public static void main(String[] args){
		
		AMP.getListenerManager().addListener(new data.MessageData());
		AMP.getListenerManager().addListener(new data.EventData());
		
		AMP.setName("AMP-1001");
		AMP.setLogin("AMP-1001");
		AMP.setVersion("Owned by p_slice");
		AMP.setVerbose(false);
		AMP.setAutoNickChange(false);
		AMP.setCapEnabled(false);
		
		try {
			AMP.connect("irc.seion.us");
			AMP.joinChannel("#p_slice");
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
	}
}
