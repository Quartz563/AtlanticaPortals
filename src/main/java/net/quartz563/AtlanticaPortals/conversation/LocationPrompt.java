package net.quartz563.AtlanticaPortals.conversation;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mineacademy.fo.conversation.SimplePrompt;



public class LocationPrompt extends SimplePrompt {

	//todo get prefix

	@Override
	protected String getPrompt(ConversationContext context) {

		return "Please enter the name of the custom location";
	}

	@Nullable
	@Override
	protected Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull String input) {
		if(input.length() >= 16){
			context.getForWhom().sendRawMessage(ChatColor.BLUE + "[Atlantica Portals] Invalid name. Please keep names under 16 characters.");
		} else {
			context.getForWhom().sendRawMessage(ChatColor.BLUE + "[Atlantica Portals] " + input + "saved successfully.");
			//locationName = input;
		}
		return Prompt.END_OF_CONVERSATION;
	}
}
