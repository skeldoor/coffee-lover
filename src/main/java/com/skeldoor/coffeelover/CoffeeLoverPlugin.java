package com.skeldoor.coffeelover;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Player;
import net.runelite.api.events.BeforeRender;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.OverheadTextChanged;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import java.util.Objects;
import java.util.Random;

@Slf4j
@PluginDescriptor(
	name = "Coffee Lover"
)
public class CoffeeLoverPlugin extends Plugin
{
	@Inject
	private Client client;

	@Subscribe
	public void onOverheadTextChanged(OverheadTextChanged overheadTextChanged){
		Player localPlayer = client.getLocalPlayer();
		if (overheadTextChanged.getActor() == localPlayer && overheadTextChanged.getOverheadText().contains("nice cuppa tea!")){
			Random random = new Random();
			if (random.nextInt(4) == 0){
				localPlayer.setOverheadText("Coffee is perfect, I'm glad you didn't offer tea!");
			} else {
				localPlayer.setOverheadText("Aaah, nothing like a nice cuppa coffee!");
			}
		}
	}

	@Subscribe
	public void onChatMessage(ChatMessage chatMessage){
		if (Objects.equals(chatMessage.getSender(), client.getLocalPlayer().getName()) &&
				chatMessage.getMessage().contains("Aaah, nothing like a nice cuppa tea!")){
			chatMessage.setMessage("Aaah, nothing like a nice cuppa coffee!");
		}
	}

	@Subscribe
	public void onBeforeRender(BeforeRender beforeRender){
		if (client.getGameState() != GameState.LOGGED_IN) return;
		if (client.getWidget(WidgetInfo.DIALOG_NPC_TEXT) != null &&
			client.getWidget(WidgetInfo.DIALOG_NPC_TEXT).getText().contains("Would you like a cup of tea")){
			Objects.requireNonNull(client.getWidget(WidgetInfo.DIALOG_NPC_TEXT)).setText("Thank you so much! Would you like a cup of coffee before you go?");
		}
		if (client.getWidget(WidgetInfo.DIALOG_OPTION_OPTIONS) != null &&
			client.getWidget(WidgetInfo.DIALOG_OPTION_OPTIONS).getChildren().length > 0 &&
			client.getWidget(WidgetInfo.DIALOG_OPTION_OPTIONS).getChildren()[0].getText().contains("Take tea?")) {
			client.getWidget(WidgetInfo.DIALOG_OPTION_OPTIONS).getChildren()[0].setText("Take coffee?");
		}
		if (client.getWidget(WidgetInfo.CHATBOX_MESSAGE_LINES) != null &&
			client.getWidget(WidgetInfo.CHATBOX_MESSAGE_LINES).getChildren() != null &&
			client.getWidget(WidgetInfo.CHATBOX_MESSAGE_LINES).getChildren().length > 0){
			for (Widget child : client.getWidget(WidgetInfo.CHATBOX_MESSAGE_LINES).getChildren()){
				if (child.getText().contains("You drink the tea, you feel energized!")){
					child.setText("You drink the coffee, you feel energized!");
				}
			}
		}
	}
}
