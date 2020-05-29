package scripts.Fremmy.api;
/**
 * Author: @Cass2186
 * <p>
 * NOTE: Some code is from FALSkills API (see premium scripter application)
 * others are from Fluffee's API (Availible online)
 */

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Keyboard;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import scripts.Bbuu20API.antiban.Antiban;
import scripts.Fremmy.Constants;
import scripts.Fremmy.FremennikTrials;
import scripts.dax_api.api_lib.DaxWalker;
import scripts.dax_api.walker.utils.AccurateMouse;
import scripts.dax_api.walker_engine.interaction_handling.NPCInteraction;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.function.BooleanSupplier;

public class Utilities {

    public static RSArea CLAN_WARS_TELE_AREA = new RSArea(new RSTile(3391, 3164, 0), new RSTile(3382, 3150, 0));
    public static RSArea INSIDE_CLAN_WARS = new RSArea(new RSTile(3324, 4755, 0), new RSTile(3333, 4747, 0));
    public static RSArea BEFORE_CLAN_WARS_PORTAL = new RSArea(new RSTile(3351, 3165, 0), new RSTile(3357, 3161, 0));
    public static RSArea GENERAL_CLAN_WARS = new RSArea(new RSTile(3392, 3149, 0), new RSTile(3348, 3178, 0));

    /**
     * QUEST COLOUR IDs
     */
    public static int NOT_STARTED_ID = 16711680;
    public static int IN_PROGRESS_ID = 16776960;
    public static int COMPLETED_ID = 901389;

    public static boolean NOT_STARTED;
    public static boolean IN_PROGRESS;
    public static boolean COMPLETED;


    public static int spaceKeyCode = 32;
    public static BooleanSupplier interfaceIsNull = () -> Interfaces.get(233, 3) == null;

    public static RSTile bobBarterTile = new RSTile(3156, 3481, 0);
    public static RSTile murkyMattTile = new RSTile(3173, 3481, 0);

    public static RSArea BOB_BARTER_AREA = new RSArea(new RSTile(3154, 3483, 0), new RSTile(3158, 3479, 0));
    public static RSArea murkyMattArea = new RSArea(new RSTile(3176, 3478, 0), new RSTile(3170, 3483, 0));

    public static RSNPC[] bobBarter = NPCs.find("Bob Barter (herbs)");

    public static final char VK_1 = KeyEvent.VK_1;
    public static final char VK_2 = KeyEvent.VK_2;
    public static final char VK_3 = KeyEvent.VK_3;
    public static final char VK_4 = KeyEvent.VK_4;
    public static char spaceKey = KeyEvent.VK_SPACE;

    public static int DURATION_MIN = General.random(45000, 60000); //30s to 60s
    public static int DURATION_MAX = General.random(120000, 300000); //2-5min
    public static int AFK_TIME = General.random(DURATION_MIN, DURATION_MAX);
    public static int MOUSE_OFF_SCREEN_CHANCE = 40; // expressed as a percentage


    public static void afk() { // afk for 2-5 min
        FremennikTrials.stage = "AFK'ing...";
        AFK_TIME = General.random(DURATION_MIN, DURATION_MAX);
        General.println("[Antiban]: AFK'ing for: " + AFK_TIME / 60000 + "min");
        if (General.random(0, 100) <= MOUSE_OFF_SCREEN_CHANCE) {
            Mouse.leaveGame();
            General.sleep(AFK_TIME);
            Mouse.pickupMouse();
        } else {
            General.sleep(AFK_TIME);
            Mouse.pickupMouse();
        }
    }

    public static void shortAfk() { // AFK for 30-90s
        FremennikTrials.stage = "Short AFK'ing...";
        AFK_TIME = General.random(30000, 90000);
        General.println("[Antiban]: AFK'ing for: " + AFK_TIME / 1000 + " seconds");
        if (General.random(0, 100) <= MOUSE_OFF_SCREEN_CHANCE) {
            Mouse.leaveGame();
            General.sleep(AFK_TIME);
            Mouse.pickupMouse();
        } else {
            General.sleep(AFK_TIME);
            Mouse.pickupMouse();
        }
    }

    public static void cutScene() {
        while (Game.getSetting(1021) == 16576) {
            General.println("[Debug]: Cutscene Idle");
            General.sleep(50);
            NPCInteraction.handleConversation();
        }
    }

    public static void decantStamina(int doseNumber) {
        General.println("[Utilities]: Decanting Staminas to " + doseNumber + " doses");
        BankManager.open(true);
        BankManager.depositAll(true);
        if (Banking.find(Constants.STAMINA_POTION[1]).length > 0 || Banking.find(Constants.STAMINA_POTION[2]).length > 0 || Banking.find(Constants.STAMINA_POTION[3]).length > 0) {
            BankManager.turnNotesOn();
            BankManager.withdraw(0, true, Constants.STAMINA_POTION[1]);
            BankManager.withdraw(0, true, Constants.STAMINA_POTION[2]);
            BankManager.withdraw(0, true, Constants.STAMINA_POTION[3]);
            BankManager.close(true);
            Utilities.walkToArea(BOB_BARTER_AREA);
            bobBarter = NPCs.find("Bob Barter (herbs)");
            if (bobBarter.length > 0) {
                if (!bobBarter[0].isClickable()) {
                    bobBarter[0].adjustCameraTo();
                }
                if (DynamicClicking.clickRSNPC(bobBarter[0], "Decant")) {
                    NPCInteraction.waitForConversationWindow();
                    General.sleep(General.random(150, 600));
                    if (Interfaces.isInterfaceSubstantiated(582, 4)) {
                        Interfaces.get(582, (doseNumber + 2)).click();
                        scripts.Constants.idle(300, 1200);
                    }
                }
            }
            BankManager.open(true);
            BankManager.depositAll(true);
            BankManager.close(true);
        }
    }

    public static void continuingChat(int index, int child) {
        while (Interfaces.get(index, child) != null) {
            Keyboard.holdKey(spaceKey, spaceKeyCode, interfaceIsNull);
            General.println("Sleeping");
            General.sleep(1000);
        }
    }

    public static void continuingChat() { // another method for continuing chat when Dax's NPCInteraction doesn't work
        if (Interfaces.isInterfaceSubstantiated(233, 3)) {
            Keyboard.holdKey(spaceKey, spaceKeyCode, interfaceIsNull);
        }
        if (Interfaces.isInterfaceSubstantiated(11, 4)) {
            Keyboard.holdKey(spaceKey, spaceKeyCode, interfaceIsNull);
        }
        if (Interfaces.isInterfaceSubstantiated(229, 2)) {
            Keyboard.holdKey(spaceKey, spaceKeyCode, interfaceIsNull);
        }
    }

    public static boolean clickUseItem(int itemID) {
        RSItem[] invItem = Inventory.find(itemID);
        if (invItem.length > 0) {
            if (isItemSelected(itemID)) {
                return true;
            }
            if (AccurateMouse.click(invItem[0], "Use")) {
                return true;
            }
        }
        return false;
    }

    public static boolean closeQuestCompletionWindow() {
        if (Interfaces.isInterfaceSubstantiated(225, 14)) {
            Interfaces.get(225, 14).click();
        } else if (Interfaces.isInterfaceSubstantiated(277, 15)) {
            Interfaces.get(277, 15).click();
        }
        if (Interfaces.isInterfaceSubstantiated(11, 4)) {
            continuingChat(11, 4); // continues through chat after closing window
        }
        continuingChat(233, 3); // continues through chat if you gained levels.
        return false;
    }

    public static boolean isItemSelected(int itemID) {
        RSItemDefinition itemDef = RSItemDefinition.get(itemID);
        String itemString = itemDef.getName();
        if (Game.getUptext().contains(itemString)) {
            return true;
        }
        return false;
    }

    public static void clanWarsReset() {
        if (Inventory.find(Constants.RING_OF_DUELING).length < 1 && Equipment.find(Constants.RING_OF_DUELING).length < 1) {
            FremennikTrials.stage = "Getting Ring of Dueling for Clan wars reset";
            General.println("[Debug]: " + FremennikTrials.stage);
            BankManager.open(true);
            BankManager.getTeleItem(Constants.RING_OF_DUELING[0], Constants.RING_OF_DUELING[1], Constants.RING_OF_DUELING[2], Constants.RING_OF_DUELING[3]);
            BankManager.close(true);
        }
        if (Inventory.find(Constants.RING_OF_DUELING).length > 0 || Equipment.find(Constants.RING_OF_DUELING).length > 0) {
            FremennikTrials.stage = "Going to Clan wars";
            General.println("[Debug]: " + FremennikTrials.stage);
            walkToArea(BEFORE_CLAN_WARS_PORTAL);
        }
        RSObject[] portal = Objects.findNearest(20, 26645);
        if (portal.length > 0) {
            if (clickObject(26646, "Enter"))
                Timing.waitCondition(() -> INSIDE_CLAN_WARS.contains(Player.getPosition()), 8000);
        }
    }

    public static boolean clickObject(int objectID, String action) {
        RSObject[] obj = Objects.findNearest(20, objectID);
        if (obj.length > 0) {
            if (!obj[0].isOnScreen()) {
                Walking.blindWalkTo(obj[0].getPosition());
                Timing.waitCondition(() -> obj[0].isClickable(), 8000);
                General.sleep(General.random(200, 600));
            }
            if (AccurateMouse.click(obj[0], action))
                return true;
        }
        return false;
    }

    public static boolean clickObject(int objectID, String action, int distance) { //allows you to specify object search distance
        RSObject[] obj = Objects.findNearest(distance, objectID);
        if (obj.length > 0) {
            if (!obj[0].isOnScreen()) {
                Walking.blindWalkTo(obj[0].getPosition());
                Timing.waitCondition(() -> obj[0].isClickable(), 8000);
                General.sleep(General.random(200, 600));
            }
            if (AccurateMouse.click(obj[0], action))
                return true;
        }
        return false;
    }


    public static boolean clickObject(String objectID, String action) {
        RSObject[] obj = Objects.findNearest(20, objectID);
        if (obj.length > 0) {
            if (!obj[0].isOnScreen()) {
                Walking.blindWalkTo(obj[0].getPosition());
                Timing.waitCondition(() -> obj[0].isClickable(), 8000);
                General.sleep(General.random(200, 600));
            }
            if (AccurateMouse.click(obj[0], action))
                return true;
        }
        return false;
    }


    public static boolean useItemOnItem(int itemID, int itemID2) {
        RSItem[] invItem = Inventory.find(itemID);
        RSItemDefinition itemDef = RSItemDefinition.get(itemID);
        String itemString = itemDef.getName();
        RSItem[] invItem2 = Inventory.find(itemID2);
        if (invItem.length > 0 && invItem2.length > 0) {
            General.println("[Debug]: Using: " + itemString);
            if (AccurateMouse.click(invItem[0], "Use")) {
                General.sleep(General.random(100, 500));
                if (AccurateMouse.click(invItem2[0], "Use " + itemString))
                    return true;
            }
        }
        return false;
    }

    public static boolean useItemOnObject(int itemID, int objectID) {
        RSItem[] invItem = Inventory.find(itemID);
        RSItemDefinition itemDef = RSItemDefinition.get(itemID);
        String itemString = itemDef.getName();
        RSObject[] invItem2 = Objects.findNearest(25, objectID);
        if (invItem.length > 0 && invItem2.length > 0) {
            General.println("[Debug]: Using: " + itemString);
            AccurateMouse.click(invItem[0], "Use");
            General.sleep(General.random(200, 500));
            if (!invItem2[0].isClickable()) {
                invItem2[0].adjustCameraTo();
            }
            if (AccurateMouse.click(invItem2[0], "Use " + itemString))
                return true;
        }
        return false;
    }

    public static void printStatus(String status) {
        FremennikTrials.stage = status;
        General.println("[Debug]: " + FremennikTrials.stage);
    }

    public static boolean useItemOnNPC(int itemID, String NPCName) {
        RSItem[] invItem = Inventory.find(itemID);
        RSItemDefinition itemDef = RSItemDefinition.get(itemID);
        String itemString = itemDef.getName();
        RSNPC[] NPC = NPCs.findNearest(NPCName);
        if (invItem.length > 0) {
            General.println("[Debug]: Using: " + itemString);
            AccurateMouse.click(invItem[0], "Use");
            General.sleep(General.random(200, 500));
            if (NPC.length > 0) {
                if (!NPC[0].isClickable()) {
                    NPC[0].adjustCameraTo();
                }
                if (AccurateMouse.click(NPC[0], "Use"))
                    return true;
            }
        }
        return false;
    }

    public static boolean useItemOnNPC(String itemName, String NPCName) {
        RSItem[] invItem = Inventory.find(itemName);
        RSNPC[] NPC = NPCs.findNearest(NPCName);
        if (invItem.length > 0) {
            General.println("[Debug]: Using: " + itemName);
            AccurateMouse.click(invItem[0], "Use");
            General.sleep(General.random(200, 500));
            if (NPC.length > 0) {
                if (!NPC[0].isClickable()) {
                    NPC[0].adjustCameraTo();
                }
                if (AccurateMouse.click(NPC[0], "Use"))
                    return true;
            }
        }
        return false;
    }

    public static void muteSound() {
        Mixer.Info[] infos = AudioSystem.getMixerInfo();
        for (Mixer.Info info : infos) {
            Mixer mixer = AudioSystem.getMixer(info);
            for (Line line : mixer.getSourceLines()) {
                BooleanControl bc = (BooleanControl) line.getControl(BooleanControl.Type.MUTE);
                if (bc != null) {
                    bc.setValue(true);
                }
            }
        }
    }

    public static JFrame findTRiBotFrame() {
        Frame[] frames = JFrame.getFrames();
        for (Frame tempFrame : frames) {
            if (tempFrame.getTitle().contains("TRiBot Old-School - The Desktop Botting Solution")) {
                return (JFrame) tempFrame;
            }
            General.sleep(100);
        }
        General.println("Error, could not find TRiBot Frame.");
        return null;
    }

    public static void minimizeClient() {
        findTRiBotFrame().setState(Frame.ICONIFIED);
    }

    public static void maximizeClient() {
        findTRiBotFrame().setState(Frame.NORMAL);
    }


    public static boolean useItemOnNPC(int itemID, int npcId) {
        RSItem[] invItem = Inventory.find(itemID);
        RSItemDefinition itemDef = RSItemDefinition.get(itemID);
        String itemString = itemDef.getName();
        RSNPC[] npc = NPCs.findNearest(npcId);
        if (invItem.length > 0) {
            General.println("[Debug]: Using: " + itemString);
            AccurateMouse.click(invItem[0], "Use");
            General.sleep(General.random(200, 500));
            if (npc.length > 0) {
                if (!npc[0].isClickable()) {
                    npc[0].adjustCameraTo();
                }
                if (AccurateMouse.click(npc[0], "Use"))
                    return true;
            }
        }
        return false;
    }

    public static boolean useItemOnNPC(int[] itemID, int NPCID) {
        RSItem[] invItem = Inventory.find(itemID);
        RSNPC[] NPC = NPCs.findNearest(NPCID);
        if (invItem.length > 0) {
            AccurateMouse.click(invItem[0], "Use");
            General.sleep(General.random(200, 500));
            if (NPC.length > 0 && isItemSelected(itemID[0])) {
                if (!NPC[0].isClickable()) {
                    NPC[0].adjustCameraTo();
                }
                if (AccurateMouse.click(NPC[0], "Use")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkP2PQuestStatus(String questName) {
        for (int i = 0; i < 123; i++) {
            if (Interfaces.get(399, 7, i) != null) {
                if (Interfaces.get(399, 7, i).getText().contains(questName)) {
                    if (Interfaces.get(399, 7, i).getTextColour() == NOT_STARTED_ID) {
                        General.println("[Debug]: " + questName + " is not started");
                        return NOT_STARTED;
                    }
                    if (Interfaces.get(399, 7, i).getTextColour() == IN_PROGRESS_ID) {
                        General.println("[Debug]: " + questName + " is in progress");
                        return IN_PROGRESS;
                    }
                    if (Interfaces.get(399, 7, i).getTextColour() == COMPLETED_ID) {
                        General.println("[Debug]: " + questName + " is complete");
                        return COMPLETED;
                    }
                }
            }
        }
        return false;
    }

    public static boolean useItem(int itemID) {
        RSItem[] invItem = Inventory.find(itemID);
        RSItemDefinition itemDef = RSItemDefinition.get(itemID);
        String itemString = itemDef.getName();
        if (invItem.length > 0 && !isUsing(itemString)) {
            General.println("[Debug]: Using: " + itemString);
            if (AccurateMouse.click(invItem[0], "Use")) {
                return true;
            }
        } else if (invItem.length > 0 && isUsing(itemString)) {
            return true;
        }
        return false;
    }

    public static boolean isUsing(String itemName) {
        return Game.isUptext("Use " + itemName + "->");
    }

    public static boolean attackNPC(int npcID) {
        if (!Combat.isUnderAttack() && Combat.getTargetEntity() == null) {
            RSNPC[] target = NPCs.find(npcID);
            if (target.length > 0) {
                if (!target[0].isInCombat() && !target[0].isInteractingWithMe()) {
                    if (!target[0].isOnScreen()) {
                        DaxWalker.getInstance().walkTo(target[0].getPosition());
                        Timing.waitCondition(() -> target[0].isOnScreen(), General.random(8000, 12000));
                    }
                    if (AccurateMouse.click(NPCs.find(npcID)[0], "Attack"))
                        return Timing.waitCondition(() -> Combat.isUnderAttack(), General.random(8000, 12000));
                }
            }
        }
        return false;
    }

    public static boolean clickGroundItem(int itemID) {
        if (GroundItems.find(itemID).length > 0) {
            RSItem[] invItems = Inventory.find(itemID);
            if (AccurateMouse.click(GroundItems.find(itemID)[0], "Take")) {
               return Timing.waitCondition(() -> Inventory.find(itemID).length > invItems.length, General.random(5000, 7000));
            }
        }
        return false;
    }

    public static void clickNPC(int npcId, String action) {
        if (NPCs.find(npcId).length > 0) {
            if (!NPCs.findNearest(npcId)[0].isOnScreen()) {
                walkToTile(NPCs.findNearest(npcId)[0].getPosition());
                NPCs.findNearest(npcId)[0].adjustCameraTo();
            }
            if (AccurateMouse.click(NPCs.findNearest(npcId)[0], action))
                Constants.idle(150, 500);
        }
    }

    public static void clickNPC(String npcString, String action) {
        if (NPCs.find(npcString).length > 0) {
            if (!NPCs.findNearest(npcString)[0].isOnScreen()) {
                walkToTile(NPCs.findNearest(npcString)[0].getPosition());
                NPCs.findNearest(npcString)[0].adjustCameraTo();
            }
            if (AccurateMouse.click(NPCs.findNearest(npcString)[0], action))
                Constants.idle(150, 500);
        }
    }

    /**
     * reachableNPC() & getPlayerCount() are from @Elon
     * (thank you!)
     */

    public static RSNPC reachableNpc(String name) {
        RSNPC[] targets = NPCs.findNearest(name);
        if (targets != null && targets.length > 0) {
            for (RSNPC target : targets) {
                if (!target.isInCombat() && (PathFinding.canReach(target.getPosition(), false))) {
                    return target;
                }
            }
            return null;
        } else {
            return null;
        }
    }


    public static Integer getPlayerCount() {
        RSPlayer[] players = Players.getAll();
        int playersAmount = players.length - 1;
        return playersAmount;
    }


    public static boolean attackNPC(String npcName) {
        if (!Combat.isUnderAttack() && Combat.getTargetEntity() == null) {
            RSNPC[] target = NPCs.find(npcName);
            if (target.length > 0) {
                if (!target[0].isInCombat() && !target[0].isInteractingWithMe()) {
                    if (!target[0].isOnScreen()) {
                        DaxWalker.getInstance().walkTo(target[0].getPosition());
                        Timing.waitCondition(() -> target[0].isOnScreen(), General.random(8000, 12000));
                    }
                    if (AccurateMouse.click(NPCs.find(npcName)[0], "Attack"))
                        return Timing.waitCondition(() -> Combat.isUnderAttack(), General.random(8000, 12000));
                }
            }
        }
        return false;
    }


    public static boolean eqiuipItem(int weaponID) {
        RSItem[] invItem1 = Inventory.find(weaponID);
        if (invItem1.length > 0) {
            if (AccurateMouse.click(invItem1[0], "Wield")) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


    public static boolean eqiuipArmour(int itemID) {
        RSItem[] invItem1 = Inventory.find(itemID);
        if (invItem1.length > 0) {
            AccurateMouse.click(invItem1[0], "Wear");
            Timing.waitCondition(() -> Equipment.find(itemID).length > 0, General.random(3000, 5000));
            if (Equipment.find(itemID).length < 1) {
                AccurateMouse.click(invItem1[0], "Wear");
                return Timing.waitCondition(() -> Equipment.find(itemID).length > 0, General.random(3000, 5000));
            } else {
                return true;
            }
        }
        return false;
    }

    public static void dropItem(int item) {
        int itemNumber = Inventory.find(item).length;
        if (Inventory.find(item).length > 0) {
            Inventory.drop(item);
            Timing.waitCondition(() -> Inventory.find(item).length < itemNumber || Inventory.find(item).length == 0, 5000);
        }
    }

    public static boolean hasInterface(int m) {
        return Interfaces.isInterfaceValid(m);
    }

    public static boolean hasInterfaces(int... m) {
        for (int i : m) {
            if (hasInterface(i)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasInterface(int m, int c) {
        return Interfaces.get(m, c) != null;
    }

    public static RSInterface getInterface(int m) {
        RSInterface i = Interfaces.get(m);
        return i != null ? i : null;
    }

    public static RSInterface getInterface(int m, int c) {
        RSInterface i = Interfaces.get(m, c);
        return i != null ? i : null;
    }

    public static RSInterface getInterface(int m, int c, int d) {
        RSInterface i = Interfaces.get(m, c, d);
        return i != null ? i : null;
    }

    public static boolean areUnwantedInterfacesOpen() {
        return hasInterfaces(345, 193, 229, 233, 84, 464, 465, 102, 214, 400, 402, 382, 310, 553, 451);
    }

    public static boolean closeUnwantedInterfaces() {
        RSInterface close;
        if (hasInterface(345)) {
            close = getInterface(345, 1);
            if (close == null || close.getChildren() == null) {
                close = getInterface(345, 2);
            }
            if (close != null) {
                close = close.getChild(11);
                return close != null && close.click();
            }
        } else if (hasInterface(12)) {
            close = getInterface(12, 2, 11);
            return close != null && close.click();
        } else if (hasInterface(193)) {
            close = getInterface(193, 2);
            return close != null && close.click();
        } else if (hasInterface(229)) {
            close = getInterface(229, 1);
            return close != null && close.click();
        } else if (hasInterface(233)) {
            close = getInterface(233, 2);
            return close != null && close.click();
        } else if (hasInterface(84)) {
            close = getInterface(84, 4);
            return close != null && close.click();
        } else if (hasInterface(464)) {
            close = getInterface(464, 1);
            if (close != null) {
                close = close.getChild(3);
                return close != null && close.click();
            }
        } else if (hasInterface(465)) {
            close = getInterface(465, 2, 11);
            return close != null && close.click();
        } else if (hasInterface(102)) {
            close = getInterface(102, 7);
            return close != null && close.click();
        } else if (hasInterface(214)) {
            close = getInterface(214, 25);
            return close != null && close.click();
        } else if (hasInterface(400)) {
            close = getInterface(400, 2);
            if (close != null) {
                close = close.getChild(3);
                return close != null && close.click();
            }
        } else if (hasInterface(402)) {
            close = getInterface(400, 2);
            if (close != null) {
                close = close.getChild(11);
                return close != null && close.click();
            }
        } else if (hasInterface(382)) {
            close = getInterface(382, 18);
            return close != null && close.click();
        } else if (hasInterface(310)) {
            close = getInterface(310, 1);
            if (close == null || close.getChildren() == null) {
                close = getInterface(310, 2);
            }
            if (close != null) {
                close = close.getChild(11);
                return close != null && close.click();
            }
        } else if (hasInterface(553)) {
            close = getInterface(553, 1);
            if (close != null) {
                close = close.getChild(11);
                return close != null && close.click();
            }
        } else if (hasInterface(451)) {
            close = getInterface(451, 1);
            if (close != null) {
                close = close.getChild(11);
                return close != null && close.click();
            }
        }
        return false;
    }

    public static boolean talkToNPC(String npcName) {
        RSNPC[] targetNPC = NPCs.findNearest(npcName);
        if (targetNPC.length > 0) {
            if (!targetNPC[0].isOnScreen()) {
                if (DaxWalker.getInstance().walkTo(targetNPC[0].getPosition()))
                    Timing.waitCondition(() -> targetNPC[0].isClickable(), 8000);
            }
            if (!targetNPC[0].isClickable()) {
                targetNPC[0].adjustCameraTo();
            }
            if (AccurateMouse.click(targetNPC[0], "Talk-to")) {
                NPCInteraction.waitForConversationWindow();
                General.sleep(General.random(1500, 2500));
            }
            if (!NPCInteraction.isConversationWindowUp()) {
                General.println("[Debug]: Seemingly missed clicked, trying again.");
                if (AccurateMouse.click(targetNPC[0], "Talk-to"))
                    return true;
            }
        }
        return false;
    }

    public static boolean talkToNPC(int npcName) {
        RSNPC[] targetNPC = NPCs.findNearest(npcName);
        if (targetNPC.length > 0) {
            if (!targetNPC[0].isOnScreen()) {
                if (DaxWalker.getInstance().walkTo(targetNPC[0].getPosition()))
                    Timing.waitCondition(() -> targetNPC[0].isClickable(), 8000);
            }
            if (!targetNPC[0].isClickable()) {
                targetNPC[0].adjustCameraTo();
            }
            if (AccurateMouse.click(targetNPC[0], "Talk-to")) {
                NPCInteraction.waitForConversationWindow();
                General.sleep(General.random(1500, 2500));
            }
            if (!NPCInteraction.isConversationWindowUp()) {
                General.println("[Debug]: Seemingly missed clicked, trying again.");
                if (AccurateMouse.click(targetNPC[0], "Talk-to"))
                    return true;
            }
        }
        return false;
    }

    public static void walkToArea(RSArea area) {
        if (!area.contains(Player.getPosition())) {
            Antiban.divider = 3;
            if (DaxWalker.getInstance().walkTo(area.getRandomTile())) {
                Antiban.startTime = System.currentTimeMillis();
                Timing.waitCondition(() -> area.contains(Player.getPosition()), General.random(8000, 12000));
                Antiban.get().generateAndSleep2(Antiban.startTime);
            }
        }
    }

    public static void walkToTile(RSTile area) {
        Antiban.divider = 3;
        DaxWalker.getInstance().walkTo(area);
        Antiban.startTime = System.currentTimeMillis();
        Timing.waitCondition(() -> area.getPosition().distanceTo(Player.getPosition()) < 3, General.random(8000, 12000));
        Antiban.get().generateAndSleep2(Antiban.startTime);
    }

    public static void blindWalkToArea(RSArea area) {
        if (!area.contains(Player.getPosition())) {
            Antiban.divider = 3;
            Walking.blindWalkTo(area.getRandomTile());
            Antiban.startTime = System.currentTimeMillis();
            Timing.waitCondition(() -> area.contains(Player.getPosition()), General.random(8000, 12000));
            Antiban.get().generateAndSleep2(Antiban.startTime);
        }
    }

    public static void blindWalkToTile(RSTile tile) {
        Antiban.divider = 3;
        Walking.blindWalkTo(tile);
        Antiban.startTime = System.currentTimeMillis();
        Timing.waitCondition(() -> tile.getPosition().distanceTo(Player.getPosition()) < 2 && !Player.isMoving(), General.random(8000, 12000));
        Antiban.get().generateAndSleep2(Antiban.startTime);
    }

    public static void clickScreenWalk(RSArea area) {
        RSTile tile = area.getRandomTile();
        if (!area.contains(Player.getPosition())) {
            if (!tile.isClickable()) {
                Walking.blindWalkTo(tile);
                Timing.waitCondition(tile::isOnScreen, General.random(4000, 6000));
                General.sleep(General.random(300, 1200));
            }
            if (tile.isClickable()) {
                Walking.clickTileMS(tile, "Walk here");
                Timing.waitCondition(() -> Player.getPosition().equals(tile), General.random(4000, 6000));
            }
        }
    }

    public static void clickScreenWalk(RSTile tile) {
        if (!Player.getPosition().equals(tile)) {
            if (!tile.isClickable()) {
                Walking.blindWalkTo(tile);
                Timing.waitCondition(tile::isOnScreen, General.random(6000, 9000));
                General.sleep(General.random(300, 1200));
            }
            if (tile.isClickable()) {
                if (Walking.clickTileMS(tile, "Walk here"))
                    Timing.waitCondition(() -> Player.getPosition().equals(tile), General.random(6000, 9000));
            }
        }
    }

    public static void pickupItem(int itemID) {
        RSItem[] stock = Inventory.find(itemID);
        if (GroundItems.find(itemID).length > 0) {
            if (AccurateMouse.click(GroundItems.find(itemID)[0], "Take"))
                Timing.waitCondition(() -> Inventory.find(itemID).length == stock.length + 1, 9000);
        }
    }

    public static void adjustZoom(int zoom) { // so zoom of 1 is furthest out.
        GameTab.open(GameTab.TABS.OPTIONS);
        General.sleep(General.random(250, 750));
        if (Interfaces.get(261, 1, 0) != null) {
            if (Interfaces.get(261, 1, 0).getTextureID() == 761) { // the screen tab is NOT selected
                Interfaces.get(261, 1, 0).click();
                General.sleep(General.random(200, 600));
            }
        }
        if (Interfaces.get(261, 8) != null) {
            Interfaces.get(261, zoom + 8).click();
        }
    }

}
