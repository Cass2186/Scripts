package scripts.Fremmy.Nodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.*;
import org.tribot.api2007.types.*;
import scripts.Fremmy.Constants;
import scripts.Fremmy.FremennikTrials;
import scripts.Fremmy.api.BankManager;
import scripts.Fremmy.api.Utilities;
import scripts.GrandExchange.GEManager;
import scripts.Slayer.api.api.Node;
import scripts.dax_api.walker.utils.AccurateMouse;
import scripts.dax_api.walker_engine.interaction_handling.NPCInteraction;

public class NatureSpirit extends Node {

    int SICKLE = 2961;
    int GHOSTSPEAK_AMULET = 552;
    int ADAMANT_SCIMITAR = 1331;
    int LOBSTER = 379;
    int DRUIDIC_SPELL = 2968;
    int MUSHROOM = 2970;
    int WASHING_BOWL = 2964;
    int MIRROR = 2966;
    int USED_SPELL = 2969;
    int SILVER_SICKLE_B = 2963;
    int DRUIDIC_POUCH = 2957;
    int FILLED_DRUIDIC_POUCH = 2958;
    int RUNE_SCIMITAR = 1333;
    int APPLE_PIE = 2323;
    int MEAT_PIE = 2327;
    int INVISIBLE_GHAST = 945;
    int VISIBLE_GHAST = 946;
    int ROTTEN_FOOD = 2959;
    int PIE_DISH = 2313;
    int GROTO_ENTER_ID = 3516;


    RSArea DREZEL_UNDERGROUND = new RSArea(new RSTile(3432, 9892, 0), new RSTile(3443, 9902, 0));
    RSArea BEFORE_BRIDGE = new RSArea(new RSTile(3437, 3328, 0), new RSTile(3443, 3325, 0));
    RSArea INSIDE_TREE = new RSArea(new RSTile(3447, 9733, 0), new RSTile(3435, 9744, 0));
    RSArea ROTTEN_LOG2_AREA = new RSArea(new RSTile(3425, 3333, 0), new RSTile(3427, 3331, 0));
    RSArea GROTTO_TREE_AREA = new RSArea(new RSTile(3446, 3330, 0), new RSTile(3434, 3345, 0));

    RSTile LIGHT_BROWN_STONE_TILE = new RSTile(3439, 3336, 0);
    RSTile ORANGE_STONE_TILE = new RSTile(3440, 3335, 0);
    RSTile ROTTEN_LOG_TILE = new RSTile(3435, 3449, 0);
    RSTile ROTTEN_LOG_TILE2 = new RSTile(3426, 3331, 0);

    private void checkRequirements() {
        if (Skills.getActualLevel(Skills.SKILLS.ATTACK) < 30) {
            General.println("[Debug]: Missing Attack Level Requirement (30+)");
            FremennikTrials.NATURE_SPIRIT = false;
            FremennikTrials.checkScriptStatus(FremennikTrials.natureSpirit);
        }
        if (Skills.getActualLevel(Skills.SKILLS.PRAYER) < 30) {
            General.println("[Debug]: Missing Prayer Level Requirement (30+)");
            FremennikTrials.NATURE_SPIRIT = false;
            FremennikTrials.checkScriptStatus(FremennikTrials.natureSpirit);
        }
        if (Game.getSetting(302) < 60) {
            General.println("[Debug]: Missing Priest in Peril Requirement");
            FremennikTrials.NATURE_SPIRIT = false;
            FremennikTrials.checkScriptStatus(FremennikTrials.natureSpirit);
        }
    }

    public void buyItems() {
        FremennikTrials.stage = "Buying Items";
        General.println("[Debug]: Buying Items");
        GEManager.getCoins();
        GEManager.buyItem(SICKLE, 1500, 1);
        if (Skills.getCurrentLevel(Skills.SKILLS.ATTACK) >= 40) {
            GEManager.buyItem(RUNE_SCIMITAR, 20, 1);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.ATTACK) >= 30) {
            GEManager.buyItem(ADAMANT_SCIMITAR, 100, 1);
        }
        GEManager.buyItem(LOBSTER, 30, 16);
        GEManager.buyItem(Constants.STAMINA_POTION[0], 10, 2);
        GEManager.buyItem(Constants.PRAYER_POTION[0], 15, 2);
        GEManager.buyItem(Constants.RING_OF_DUELING[0], 25, 1);
        GEManager.collectItems();
        GEManager.closeGE();
    }

    public void getItems1() {
        FremennikTrials.stage = "Getting Items";
        General.println("[Debug]: " + FremennikTrials.stage);
        BankManager.open(true);
        BankManager.checkEquippedGlory();
        BankManager.depositAll(true);
        BankManager.withdraw(14, true, LOBSTER);
        BankManager.withdraw(2, true, Constants.STAMINA_POTION[0]);
        BankManager.withdraw(1, true, SICKLE);
        BankManager.withdraw(2, true, Constants.PRAYER_POTION[0]);
        BankManager.withdraw(1, true, GHOSTSPEAK_AMULET);
        BankManager.withdraw(1, true, Constants.RING_OF_DUELING[0]);
        if (Skills.getCurrentLevel(Skills.SKILLS.ATTACK) >= 40) {
            BankManager.withdraw(1, true, RUNE_SCIMITAR);
            Utilities.eqiuipItem(RUNE_SCIMITAR);
        } else if (Skills.getCurrentLevel(Skills.SKILLS.ATTACK) >= 30) {
            BankManager.withdraw(1, true, ADAMANT_SCIMITAR);
            Utilities.eqiuipItem(ADAMANT_SCIMITAR);
        }
        BankManager.close(true);
        Utilities.eqiuipArmour(GHOSTSPEAK_AMULET);
    }

    public void startQuest() {
        FremennikTrials.stage = "Going to Start Quest";
        Utilities.walkToArea(DREZEL_UNDERGROUND);
        if (DREZEL_UNDERGROUND.contains(Player.getPosition())) {
            Utilities.talkToNPC("Drezel");
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Is there anything else interesting to do around here?");
            NPCInteraction.handleConversation("Well, what is it, I may be able to help?");
            NPCInteraction.handleConversation("Yes, I'll go and look for him.");
            NPCInteraction.handleConversation("Yes, I'm sure.");
            NPCInteraction.handleConversation();
            if (Interfaces.get(11, 4) != null) {
                Interfaces.get(11, 4).click();
            }
        }
    }

    public void goToGrotto() { // this method is used to go to the Grotto tree and Dax won't go all the way across the bridge
        FremennikTrials.stage = "Going to Grotto tree";
        General.println("[Debug]: " + FremennikTrials.stage);
        if (!GROTTO_TREE_AREA.contains(Player.getPosition())) {
            if (Equipment.find(GHOSTSPEAK_AMULET).length < 1 && Inventory.find(GHOSTSPEAK_AMULET).length < 1) {
                General.println("[Debug]: We are missing our Ghostspeak amulet, banking");
                getItems1();
            }
            Utilities.clickObject("Holy barrier", "Pass-through"); // only will execute if we are near the holy barrier
            Timing.waitCondition(() -> NPCs.find("Drezel").length < 1, 8000); // anchors to drezel to know we have left his area
            Utilities.walkToArea(BEFORE_BRIDGE);
        }
        if (BEFORE_BRIDGE.contains(Player.getPosition())) {
            Utilities.clickObject("Bridge", "Jump");
            Timing.waitCondition(() -> GROTTO_TREE_AREA.contains(Player.getPosition()) && !Player.isMoving(), 8000);
            Constants.idle(500, 3000);
        }

    }

    public void getAnotherScroll() { // called if for some reason our first spell doesn't work and we need another
        if (Inventory.find(DRUIDIC_SPELL).length < 1) {
            FremennikTrials.stage = "Getting another Spell.";
            General.println("[Debug]: " + FremennikTrials.stage);
            goToGrotto();
            dropRottenFood(); // makes inventory space
            Utilities.clickObject(GROTO_ENTER_ID, "Enter");
            Timing.waitCondition(() -> NPCs.find("Filliman Tarlock").length > 0, 5000);
            if (NPCs.find("Filliman Tarlock").length > 0) {
                Utilities.talkToNPC("Filliman Tarlock");
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.handleConversation("Could I have another bloom scroll please?");
                NPCInteraction.handleConversation();
            }
        }
    }


    public void dropRottenFood() { // used to make room in inventory for quest items
        if (Inventory.isFull()) {
            Inventory.drop(ROTTEN_FOOD);
            Inventory.drop(PIE_DISH);
            Inventory.drop(WASHING_BOWL);
        }
    }

    public void step2() {
        if (!GROTTO_TREE_AREA.contains(Player.getPosition())) {
            goToGrotto();
        }
        dropRottenFood();
        if (GROTTO_TREE_AREA.contains(Player.getPosition())) {
            RSGroundItem[] washingBowlGround = GroundItems.find(WASHING_BOWL);
            if (washingBowlGround.length > 0 && Inventory.find(MIRROR).length < 1) {
                if (Utilities.clickGroundItem(WASHING_BOWL)) {
                    Timing.waitCondition(() -> Inventory.find(WASHING_BOWL).length > 0, 5000);
                }
                if (Utilities.clickGroundItem(MIRROR)) {
                    Timing.waitCondition(() -> Inventory.find("Mirror").length > 0, 5000);
                    Constants.idle(500, 2500);
                }
            }
            if (Inventory.find(MIRROR).length > 0) {
                if (Utilities.clickObject(GROTO_ENTER_ID, "Enter")) {
                    Timing.waitCondition(() -> NPCs.find("Filliman Tarlock").length > 0, 5000);
                    Constants.idle(500, 2500);
                }
            }
            if (NPCs.find("Filliman Tarlock").length > 0 && Inventory.find(MIRROR).length > 0) {
                if (Utilities.talkToNPC("Filliman Tarlock")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("What's it like being a ghost?");
                    NPCInteraction.handleConversation();
                }
                if (Utilities.useItemOnNPC(MIRROR, "Filliman Tarlock")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                }
            }
        }
    }


    public void step2b() {
        Utilities.clickObject(GROTO_ENTER_ID, "Enter");
        Timing.waitCondition(() -> NPCs.find("Filliman Tarlock").length > 0, 5000);
        if (NPCs.find("Filliman Tarlock").length > 0) {
            if (Utilities.talkToNPC("Filliman Tarlock")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
        if (Inventory.find("Journal").length < 1) {
            if (Utilities.clickObject("Grotto tree", "Search")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Timing.waitCondition(() -> Inventory.find("Journal").length > 0, 5000);
            }
        }
        if (NPCs.find("Filliman Tarlock").length > 0) {
            if (Inventory.find("Journal").length > 0) {
                if (Utilities.useItemOnNPC("Journal", "Filliman Tarlock")) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation("How can I help?");
                    NPCInteraction.handleConversation();
                }
            }
        }
    }

    public void step3() {
        FremennikTrials.stage = "Going to Drezel";
        if (GROTTO_TREE_AREA.contains(Player.getPosition())) {
            if (Utilities.clickObject("Bridge", "Jump")) {
                Timing.waitCondition(() -> BEFORE_BRIDGE.contains(Player.getPosition()), 8000);
            }
        }
        if (!DREZEL_UNDERGROUND.contains(Player.getPosition())) {
            Utilities.walkToArea(DREZEL_UNDERGROUND);
        }
        if (DREZEL_UNDERGROUND.contains(Player.getPosition())) {
            FremennikTrials.stage = "Getting blessed";
            if (Utilities.talkToNPC("Drezel")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                General.sleep(General.random(3000, 5000)); // sleep while he blesses you
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step4() {
        FremennikTrials.stage = "Getting Mushroom";
        General.println("[Debug]: " + FremennikTrials.stage);
        if (Inventory.find(DRUIDIC_SPELL).length < 1) {
            getAnotherScroll();
        }
        if (Inventory.find(MUSHROOM).length < 1) {
            Utilities.walkToTile(ROTTEN_LOG_TILE);
            RSObject[] rottenLog = Objects.findNearest(10, "Rotting log");
            if (rottenLog.length > 0) {
                if (rottenLog[0].getPosition().distanceTo(Player.getPosition()) > 4) {
                    Utilities.walkToTile(rottenLog[0].getPosition());
                }

                if (AccurateMouse.click(Inventory.find(DRUIDIC_SPELL)[0], "Cast")) {
                    Timing.waitCondition(() -> Objects.find(20, 3509).length > 0, 9000);
                    dropRottenFood();
                }
                if (Inventory.isFull()) { // if inventory is still full after dropping junk we'll drop pies to make room for mushrooms
                    Inventory.drop(MEAT_PIE);
                    Inventory.drop(APPLE_PIE);
                }
                RSObject[] groundMushroom = Objects.find(20, 3509);
                if (Objects.find(20, 3509).length > 0) {
                    if (AccurateMouse.click(groundMushroom[0], "Pick")) {
                        Timing.waitCondition(() -> Inventory.find(MUSHROOM).length > 0, 5000);
                    }
                }
            }
        }
    }


    public void step5() {
        if (Inventory.find(MUSHROOM).length > 0) {
            goToGrotto();
            if (Utilities.useItemOnObject(MUSHROOM, 3527))
                Timing.waitCondition(() -> Inventory.find(MUSHROOM).length < 1, 6000);
            RSObject[] grottoTree = Objects.findNearest(20, GROTO_ENTER_ID);
            if (grottoTree.length > 0) {
                AccurateMouse.click(grottoTree[0], "Enter");
                Timing.waitCondition(() -> NPCs.find("Filliman Tarlock").length > 0, 5000);
            }
        }
        if (NPCs.find("Filliman Tarlock").length > 0) {
            step6();
        }
    }

    public void step6() {
        FremennikTrials.stage = "Nature Spirit: Solving Quiz";
        General.println("[Debug]: " + FremennikTrials.stage);
        if (Inventory.find(USED_SPELL).length > 0) {
            if (Utilities.useItemOnObject(USED_SPELL, 3529)) {
                Timing.waitCondition(() -> Inventory.find(USED_SPELL).length < 1, 8000);
                General.sleep(General.random(500, 3000));
            }
        }
        RSObject[] grottoTree = Objects.findNearest(20, "Grotto");
        if (grottoTree.length > 0) {
            AccurateMouse.click(grottoTree[0], "Enter");
            Timing.waitCondition(() -> NPCs.find("Filliman Tarlock").length > 0, 5000);
            General.sleep(General.random(1000, 3500));
        }
        Walking.clickTileMS(ORANGE_STONE_TILE, "Walk here");
        General.sleep(General.random(2400, 4500));
        if (NPCs.find("Filliman Tarlock").length > 0) {
            if (Utilities.talkToNPC("Filliman Tarlock")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation("I think I've solved the puzzle!");
                General.sleep(General.random(2000, 3500));
                NPCInteraction.handleConversation();
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step7() {
        FremennikTrials.stage = "Entering Grotto";
        General.println("[Debug]: " + FremennikTrials.stage);
        RSObject[] grottoTree = Objects.findNearest(20, "Grotto");
        if (grottoTree.length > 0) {
            AccurateMouse.click(grottoTree[0], "Enter");
            Timing.waitCondition(() -> !GROTTO_TREE_AREA.contains(Player.getPosition()), 5000);
            Constants.idle(500, 1500);
        }
    }

    public void step8() {
        if (!INSIDE_TREE.contains(Player.getPosition())) {
            step7();
        }
        if (INSIDE_TREE.contains(Player.getPosition())) {
            FremennikTrials.stage = "Searching Grotto";
            General.println("[Debug]: " + FremennikTrials.stage);
            if (Utilities.clickObject(3520, "Search")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
            }
        }
    }

    public void step9() { // kill 3 ghasts
        FremennikTrials.stage = "Going to Kill Ghasts";
        General.println("[Debug]: " + FremennikTrials.stage);
        if (INSIDE_TREE.contains(Player.getPosition())) {
            if (Utilities.clickObject(3525, "Exit")) {
                Timing.waitCondition(() -> GROTTO_TREE_AREA.contains(Player.getPosition()), 7000);
                Constants.idle(400, 1200);
            }
        }
        if (GROTTO_TREE_AREA.contains(Player.getPosition())) {
            if (Utilities.clickObject("Bridge", "Jump")) {
                Timing.waitCondition(() -> BEFORE_BRIDGE.contains(Player.getPosition()), 8000);
                General.sleep(General.random(800, 2400));
            }
        }
        if (!ROTTEN_LOG2_AREA.contains(Player.getRSPlayer())) {
            Utilities.walkToTile(ROTTEN_LOG_TILE2);
        }
        if (ROTTEN_LOG2_AREA.contains(Player.getPosition())) {
            while (Inventory.find(MUSHROOM).length < 4) {
                FremennikTrials.stage = "Getting fungi";
                General.sleep(150);
                RSItem[] invFungi = Inventory.find(MUSHROOM);
                if (AccurateMouse.click(Inventory.find(SILVER_SICKLE_B)[0], "Cast Bloom")) {
                    Timing.waitCondition(() -> Objects.find(20, 3509).length > 0, 6000);
                    Constants.idle(400, 1200);
                }
                dropRottenFood();
                if (Inventory.isFull()) {
                    Inventory.drop(MEAT_PIE);
                    Inventory.drop(APPLE_PIE);
                }
                if (Objects.find(20, 3509).length > 0) {
                    if (AccurateMouse.click(Objects.find(20, 3509)[0], "Pick")) {
                        Timing.waitCondition(() -> Inventory.find(MUSHROOM).length > invFungi.length, 6000);
                        Constants.idle(400, 1200);
                    }
                }
                if (Inventory.find(MUSHROOM).length > 2) {
                    break;
                }
            }
            if (Inventory.find(MUSHROOM).length > 2 && Inventory.find(DRUIDIC_POUCH).length > 0) {
                if (AccurateMouse.click(Inventory.find(DRUIDIC_POUCH)[0], "Fill"))
                    Timing.waitCondition(() -> NPCs.find("Ghast").length > 0, 25000);
            }
            while (Inventory.find(DRUIDIC_POUCH).length < 1) { // returns true if we have a pouc with food in it (difernet ID)
                General.sleep(150);
                if (!Combat.isUnderAttack() && NPCs.find(INVISIBLE_GHAST).length > 0) {
                    FremennikTrials.stage = "Attacking";
                    if (AccurateMouse.click(NPCs.find(INVISIBLE_GHAST)[0], "Attack")) {
                        Timing.waitCondition(() -> Combat.isUnderAttack(), 15000);
                        Constants.idle(400, 1200);
                    }
                }
                if (Combat.isUnderAttack() && NPCs.find("Ghast").length > 0) {
                    if (Combat.getHPRatio() < General.random(40, 65) && Inventory.find(LOBSTER).length > 0) {
                        FremennikTrials.stage = "Eating";
                        if (AccurateMouse.click(Inventory.find(LOBSTER)[0], "Eat"))
                            General.sleep(General.random(500, 1000));
                    }
                } else {
                    FremennikTrials.stage = "Idling...";
                    General.sleep(General.random(1000, 3000));
                }
            }
        }
    }

    public void getFungi() {
        Utilities.walkToTile(ROTTEN_LOG_TILE2);
        if (ROTTEN_LOG2_AREA.contains(Player.getPosition())) {
            if (Prayer.getPrayerPoints() < 5) {
                if (Inventory.find(Constants.PRAYER_POTION).length > 0) {
                    FremennikTrials.stage = "Drinking Prayer Potion";
                    General.println("[Debug]: " + FremennikTrials.stage);
                    if (AccurateMouse.click(Inventory.find(Constants.PRAYER_POTION)[0]))
                        General.sleep(General.random(300, 1200));
                }
            }
            if (Prayer.getPrayerPoints() > 5) {
                FremennikTrials.stage = "Getting fungi";
                General.println("[Debug]: " + FremennikTrials.stage);
                if (Inventory.find(SILVER_SICKLE_B).length > 0) {
                    if (AccurateMouse.click(Inventory.find(SILVER_SICKLE_B)[0], "Cast Bloom")) {
                        Timing.waitCondition(() -> Objects.find(20, 3509).length > 0, 6000);
                        General.sleep(General.random(300, 1200));
                        dropRottenFood();
                    }
                }
                if (Objects.find(20, 3509).length > 0) {
                    RSItem[] invFungi = Inventory.find(MUSHROOM);
                    if (AccurateMouse.click(Objects.find(20, 3509)[0], "Pick"))
                        Timing.waitCondition(() -> Inventory.find(MUSHROOM).length > invFungi.length, 6000);
                    General.sleep(General.random(300, 1200));
                }
            }
        }
    }


    public void fightGhast() {
        if (Inventory.find(MUSHROOM).length > 2) {
            FremennikTrials.stage = "Nature Spirit: Filling pouch";
            if (Inventory.find(DRUIDIC_POUCH).length > 0) {
                if (AccurateMouse.click(Inventory.find(DRUIDIC_POUCH)[0], "Fill"))
                    Timing.waitCondition(() -> NPCs.find("Ghast").length > 0, 25000);
            } else if (Inventory.find(FILLED_DRUIDIC_POUCH).length > 0) {
                if (AccurateMouse.click(Inventory.find(FILLED_DRUIDIC_POUCH)[0], "Fill"))
                    Timing.waitCondition(() -> NPCs.find("Ghast").length > 0, 25000); //waits to be attacked
            }
        }
        while (Inventory.find(FILLED_DRUIDIC_POUCH).length > 0 || Combat.isUnderAttack()) {
            General.sleep(150);
            if (!Combat.isUnderAttack() && NPCs.find(INVISIBLE_GHAST).length > 0) {
                FremennikTrials.stage = "Attacking Ghast";
                AccurateMouse.click(Inventory.find(FILLED_DRUIDIC_POUCH)[0], "Use");
                AccurateMouse.click(NPCs.find(INVISIBLE_GHAST)[0], "Use");
                Timing.waitCondition(() -> Combat.isUnderAttack(), 12000);
            }
            if (!Combat.isUnderAttack() && NPCs.find(VISIBLE_GHAST).length > 0) {
                if (AccurateMouse.click(NPCs.find(VISIBLE_GHAST)[0], "Use"))
                    Timing.waitCondition(() -> Combat.isUnderAttack(), 12000);
            }
            if (Combat.getHPRatio() < General.random(40, 65) && Inventory.find(LOBSTER).length > 0) {
                FremennikTrials.stage = "Eating";
                General.println("[Debug]: " + FremennikTrials.stage);
                AccurateMouse.click(Inventory.find(LOBSTER)[0], "Eat");
                General.sleep(General.random(500, 1000));
            } else {
                FremennikTrials.stage = "Idling...";
                General.sleep(General.random(1000, 3000));
            }
        }

    }

    public void finishQuest() {
        if (!GROTTO_TREE_AREA.contains(Player.getPosition())) {
            goToGrotto();
        }
        RSObject[] grottoTree = Objects.findNearest(20, "Grotto");
        if (grottoTree.length > 0) {
            FremennikTrials.stage = "Nature Spirit: Finishing Quest";
            General.println("[Debug]: " + FremennikTrials.stage);
            if (!grottoTree[0].isClickable()) {
                grottoTree[0].adjustCameraTo();
            }
            AccurateMouse.click(grottoTree[0], "Enter");
            Timing.waitCondition(() -> !GROTTO_TREE_AREA.contains(Player.getPosition()), 5000);
        }
        Utilities.clickObject(3520, "Search"); // clicks the grotto once inside the tree
        NPCInteraction.waitForConversationWindow();
        NPCInteraction.handleConversation();
        NPCInteraction.waitForConversationWindow();
        NPCInteraction.handleConversation();

    }

    @Override
    public void execute() {
        checkRequirements();
        while (FremennikTrials.NATURE_SPIRIT) {
            FremennikTrials.stage = "Nature Spirit";
            General.sleep(100);
            if (Game.getSetting(307) == 0) {
                buyItems();
                getItems1();
                if (Prayer.getPrayerPoints() < 30) {
                    Utilities.clanWarsReset();
                }
                startQuest();
            }
            if (Game.getSetting(307) == 5 || Game.getSetting(307) == 10 || Game.getSetting(307) == 15) {
                step2();
            }
            if (Game.getSetting(307) == 25 || Game.getSetting(307) == 30) {
                step2b();
            }
            if (Game.getSetting(307) == 35) {
                step3();
            }
            if (Game.getSetting(307) == 40 || Game.getSetting(307) == 45) {
                step4();
            }
            if (Game.getSetting(307) == 50) {
                step5();
            }
            if (Game.getSetting(307) == 55) {
                step5();
                step6();
            }
            if (Game.getSetting(307) == 60) {
                step7();
            }
            if (Game.getSetting(307) == 65) {
                step8();
            }
            if (Game.getSetting(307) == 70) {
                step8();
            }
            if (Game.getSetting(307) == 75) {
                step9();
            }
            if (Game.getSetting(307) == 80) {
                getFungi();
            }
            if (Game.getSetting(307) == 85) {
                getFungi();
                fightGhast();
            }
            if (Game.getSetting(307) == 90) {
                fightGhast();
            }
            if (Game.getSetting(307) == 95) {
                fightGhast();
            }
            if (Game.getSetting(307) == 100) {
                fightGhast();
            }
            if (Game.getSetting(307) == 105) {
                finishQuest();
            }
            if (Game.getSetting(307) == 110) {
                Utilities.closeQuestCompletionWindow();
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                Utilities.continuingChat();
                FremennikTrials.NATURE_SPIRIT = false;
                FremennikTrials.checkScriptStatus(FremennikTrials.natureSpirit);
            }

        }
    }

    @Override
    public boolean validate() {
        return FremennikTrials.NATURE_SPIRIT;
    }
}
