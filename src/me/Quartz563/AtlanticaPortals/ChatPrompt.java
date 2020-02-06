package me.Quartz563.AtlanticaPortals;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;


import static me.Quartz563.AtlanticaPortals.Main.locationName;


public class ChatPrompt extends StringPrompt {
    @Override
    public String getPromptText(ConversationContext conversationContext) {
        return ChatColor.BLUE + "[Atlantica Portals] Please enter the name of the custom location";
    }

    @Override
    public Prompt acceptInput(ConversationContext conversationContext, String answer) {
        if(answer.length() >= 16){
            conversationContext.getForWhom().sendRawMessage(ChatColor.BLUE + "[Atlantica Portals] Invalid name. Please keep names under 16 characters.");
        } else {
            conversationContext.getForWhom().sendRawMessage(ChatColor.BLUE + "[Atlantica Portals] " + answer + "saved successfully.");
            locationName = answer;
        }
        return null;
    }
}