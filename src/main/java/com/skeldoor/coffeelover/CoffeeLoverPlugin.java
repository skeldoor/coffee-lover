package com.skeldoor.coffeelover;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Player;
import net.runelite.api.events.BeforeRender;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.OverheadTextChanged;
import net.runelite.api.widgets.ComponentID;
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
		Widget dialogNPCText = client.getWidget(ComponentID.DIALOG_NPC_TEXT);
		if (dialogNPCText != null &&
			dialogNPCText.getText().contains("Would you like a cup of tea")){
			Objects.requireNonNull(client.getWidget(WidgetInfo.DIALOG_NPC_TEXT)).setText("Thank you so much! Would you like a cup of coffee before you go?");
		}
		Widget dialogOptionOptions = client.getWidget(ComponentID.DIALOG_OPTION_OPTIONS);
		if (dialogOptionOptions != null &&
			dialogOptionOptions.getChildren() != null &&
			dialogOptionOptions.getChildren().length > 0 &&
			dialogOptionOptions.getChildren()[0].getText().contains("Take tea?")) {
			dialogOptionOptions.getChildren()[0].setText("Take coffee?");
		}
		Widget chatboxMessageLines = client.getWidget(ComponentID.CHATBOX_MESSAGE_LINES);
		if (chatboxMessageLines != null &&
			chatboxMessageLines.getChildren() != null &&
			chatboxMessageLines.getChildren().length > 0){
			for (Widget child : chatboxMessageLines.getChildren()){
				if (child.getText().contains("You drink the tea, you feel energized!")){
					child.setText("You drink the coffee, you feel energized!");
				}
			}
		}
	}
}
