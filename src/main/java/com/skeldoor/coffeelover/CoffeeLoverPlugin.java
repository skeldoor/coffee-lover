package com.skeldoor.coffeelover;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.events.BeforeRender;
import net.runelite.api.events.OverheadTextChanged;
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
		if (overheadTextChanged.getActor() == localPlayer && overheadTextChanged.getOverheadText().contains("nice cupppa tea!")){
			Random random = new Random();
			if (random.nextInt(4) == 0){
				localPlayer.setOverheadText("Coffee is perfect, I'm glad you didn't offer tea!");
			} else {
				localPlayer.setOverheadText("Aaah, nothing like a nice cupppa coffee!");
			}
		}
	}

	@Subscribe
	public void onBeforeRender(BeforeRender beforeRender){
		if (Objects.requireNonNull(client.getWidget(WidgetInfo.DIALOG_NPC_TEXT)).getText().contains("Would you like a cup of tea before you go?")){
			Objects.requireNonNull(client.getWidget(WidgetInfo.DIALOG_NPC_TEXT)).setText("Thank you so much! Would you like a cup of coffee before you go?");
		}
	}
}
