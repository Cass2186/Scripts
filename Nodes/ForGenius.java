package scripts.Fremmy.Nodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSTile;
import scripts.Fremmy.FremennikTrials;
import scripts.Fremmy.api.Utilities;
import scripts.dax_api.walker_engine.interaction_handling.NPCInteraction;

public class ForGenius {
    int SEER_DOOR_ENTRANCE = 4165;
    int CLOSED_CUPBOARD = 4177;
    int OPEN_CUPBOARD = 4178;
    int CLOSED_CHEST = 4170;
    int OPEN_CHEST = 4171; //??? not positive about this one, it's the chest with hte jug in it
    int TAP = 4176;
    int RANGE = 4172;
    int CLOSED_TRAPDOOR = 4174;
    int OPEN_TRAPDOOR = 4173;
    int MURAL = 4179;
    int FROZEN_TABLE = 4169;
    int UNICORN_HEAD = 4181;
    int BULLS_HEAD = 4182;
    int BOOKCASE = 4171;
    int FULL_BUCKET = 3722;
    int TWO_FIFTHS_BUCKET = 3725;
    int FULL_JUG = 3729;
    int TWO_THIRDS_JUG = 3730;
    int FOUR_LITRE_BUCKET = 3723;
    int RED_GLOOP = 3746;
    int GLOOP_DISK = 3743;
    int VASE_LID = 3737;
    int SEALED_VASE = 3740;
    int FROZEN_KEY = 3741;
    int KEY = 3745;
    int VASE = 3734;
    int DRAIN = 4175;

    int oldRedDish = 9947;
    int woodenDisk = 3744;
    int emptyJug = 3732;
    int redHerring = 3742;
    int herring = 347;
    int emptyBucket = 3727;


    RSItem[] invRedDish = Inventory.find(oldRedDish);
    RSItem[] invRedHerring = Inventory.find(redHerring);
    RSItem[] invWoodenDisk = Inventory.find(woodenDisk);
    RSItem[] invEmptyBucket = Inventory.find(emptyBucket);
    RSItem[] invEmptyJug = Inventory.find(emptyJug);
    RSItem[] vase = Inventory.find(3734);

    RSItem[] invFullBucket = Inventory.find(3722);
    RSItem[] invtwofifthsBucket = Inventory.find(3725);
    RSItem[] invFullJug = Inventory.find(3729);
    RSItem[] twoThirdsJug = Inventory.find(3730);
    RSItem[] fourLitreBucket = Inventory.find(3723);
    RSItem[] invHerring = Inventory.find(herring);
    RSItem[] redGloop = Inventory.find(3746);
    RSItem[] gloopDisk = Inventory.find(3743);
    RSItem[] vaseLid = Inventory.find(3737);
    RSItem[] sealedVase = Inventory.find(3738);
    RSItem[] invFrozenKey = Inventory.find(3741);
    RSItem[] key = Inventory.find(3745);


    public RSArea PEER_UPSTAIRS_AREA = new RSArea(new RSTile(2629, 3665, 2), new RSTile(2638, 3660, 2));
    public RSArea BOTTOM_LEFT_ROOM = new RSArea(new RSTile(2629, 3666, 0), new RSTile(2633, 3659, 0));
    public RSArea BOTTOM_RIGHT_ROOM = new RSArea(new RSTile(2638, 3659, 0), new RSTile(2634, 3666, 0));


    public void updateItems() {
        invRedDish = Inventory.find(oldRedDish);
        invRedHerring = Inventory.find(redHerring);
        invWoodenDisk = Inventory.find(woodenDisk);
        invEmptyBucket = Inventory.find(emptyBucket);
        invEmptyJug = Inventory.find(emptyJug);
        invFullBucket = Inventory.find(FULL_BUCKET);
        invtwofifthsBucket = Inventory.find(TWO_FIFTHS_BUCKET);
        invFullJug = Inventory.find(FULL_JUG);
        twoThirdsJug = Inventory.find(TWO_THIRDS_JUG);
        fourLitreBucket = Inventory.find(FOUR_LITRE_BUCKET);
        invHerring = Inventory.find(herring);
        redGloop = Inventory.find(RED_GLOOP);
        gloopDisk = Inventory.find(GLOOP_DISK);
        vaseLid = Inventory.find(VASE_LID);
        sealedVase = Inventory.find(SEALED_VASE);
        invFrozenKey = Inventory.find(FROZEN_KEY);
        key = Inventory.find(KEY);
        vase = Inventory.find(VASE);
    }

    public void gotoSeer() {
        General.println("[Debug]: Going to Peer the Seer");
        // put a method to walk to the start here
}

    public void startSeerTrial() {
        FremennikTrials.stage = "Staring Seer Trial";
        gotoSeer();
        if (Utilities.talkToNPC("Peer the Seer")) {
            NPCInteraction.waitForConversationWindow();
            NPCInteraction.handleConversation("Yes");
            NPCInteraction.handleConversation("Yes");
            NPCInteraction.handleConversation();
        }
    }

    public void seerDoor() {
        FremennikTrials.stage = "Fremmy: Seer Trial";
        gotoSeer();
        if (!Interfaces.isInterfaceSubstantiated(Interfaces.get(298, 43))) {
            if (Utilities.clickObject(4165, "Open")) {
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                NPCChat.selectOption("Read the riddle", true);
            }
        }
    }
    
        public void solveDoor() {
        FremennikTrials.stage = "Solving Door";
        General.sleep(500);
        if (Interfaces.get(229, 1) != null) {
            String answer;
            String seerText = Interfaces.get(229, 1).getText();
            if (seerText.contains("My first is in fish, but not in the sea.")) {
                answer = "FIRE";
                General.println("[Debug]: Answer is: " + answer);
                NPCInteraction.handleConversation();
                Timing.waitCondition(() -> Interfaces.isInterfaceSubstantiated(298, 47), 3000);
                General.sleep(General.random(300, 1000));
                if (Interfaces.isInterfaceSubstantiated(298, 47)) {
                    while (!Interfaces.get(298, 43).getText().contains("F")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 48).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 44).getText().contains("I")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 50).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 45).getText().contains("R")) {
                        General.sleep(General.random(150, 300));
                        Interfaces.get(298, 52).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 46).getText().contains("E")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 54).click();
                        General.sleep(General.random(500, 800));
                    }
                    if (Interfaces.isInterfaceSubstantiated(298, 56)) {
                        Interfaces.get(298, 56).click(); // confirm button
                        Timing.waitCondition(() -> !Interfaces.isInterfaceSubstantiated(298, 56), 3500);
                    }
                }
                Utilities.clickObject(SEER_DOOR_ENTRANCE, "Open"); //4165 is the door ID that you need to open to enter Seer's building
                General.sleep(General.random(2000, 4000));
            }
            if (seerText.contains("My first is in the well, but not at sea.")) {
                answer = "LIFE";
                General.println("[Debug]: Answer is: " + answer);
                NPCInteraction.handleConversation();
                Timing.waitCondition(() -> Interfaces.isInterfaceSubstantiated(298, 47), 3000);
                General.sleep(General.random(300, 1000));
                if (Interfaces.get(298, 47) != null) {
                    while (!Interfaces.get(298, 43).getText().contains("L")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 48).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 44).getText().contains("I")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 50).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 45).getText().contains("F")) {
                        General.sleep(General.random(150, 300));
                        Interfaces.get(298, 52).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 46).getText().contains("E")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 54).click();
                        General.sleep(General.random(500, 800));
                    }
                    if (Interfaces.isInterfaceSubstantiated(298, 56)) {
                        Interfaces.get(298, 56).click(); // confirm button
                        Timing.waitCondition(() -> !Interfaces.isInterfaceSubstantiated(298, 56), 3500);
                    }
                }
                Utilities.clickObject(SEER_DOOR_ENTRANCE, "Open"); //4165 is the door ID that you need to open to enter Seer's building
                General.sleep(General.random(2000, 4000));
            }
            if (seerText.contains("My first is in mage, but not in wizard.")) {
                answer = "MIND";
                General.println("[Debug]: Answer is: " + answer);
                NPCInteraction.handleConversation();
                Timing.waitCondition(() -> Interfaces.isInterfaceSubstantiated(298, 47), 3000);
                General.sleep(General.random(300, 1000));
                if (Interfaces.get(298, 47) != null) {
                    while (!Interfaces.get(298, 43).getText().contains("M")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 48).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 44).getText().contains("I")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 50).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 45).getText().contains("N")) {
                        General.sleep(General.random(150, 300));
                        Interfaces.get(298, 52).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 46).getText().contains("D")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 54).click();
                        General.sleep(General.random(500, 800));
                    }
                    if (Interfaces.isInterfaceSubstantiated(298, 56)) {
                        Interfaces.get(298, 56).click(); // confirm button
                        Timing.waitCondition(() -> !Interfaces.isInterfaceSubstantiated(298, 56), 3500);
                    }
                }
                Utilities.clickObject(SEER_DOOR_ENTRANCE, "Open"); //4165 is the door ID that you need to open to enter Seer's building
                General.sleep(General.random(2000, 4000));
            }
            if (seerText.contains("My first is in tar, but not in a swamp.")) {
                answer = "TREE";
                General.println("[Debug]: Answer is: " + answer);
                NPCInteraction.handleConversation();
                Timing.waitCondition(() -> Interfaces.isInterfaceSubstantiated(298, 47), 3000);
                General.sleep(General.random(300, 1000));
                if (Interfaces.get(298, 47) != null) {
                    while (!Interfaces.get(298, 43).getText().contains("T")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 48).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 44).getText().contains("R")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 50).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 45).getText().contains("E")) {
                        General.sleep(General.random(150, 300));
                        Interfaces.get(298, 52).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 46).getText().contains("E")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 54).click();
                        General.sleep(General.random(500, 800));
                    }
                    if (Interfaces.isInterfaceSubstantiated(298, 56)) {
                        Interfaces.get(298, 56).click(); // confirm button
                        Timing.waitCondition(() -> !Interfaces.isInterfaceSubstantiated(298, 56), 3500);
                    }
                }
                Utilities.clickObject(SEER_DOOR_ENTRANCE, "Open"); //4165 is the door ID that you need to open to enter Seer's building
                General.sleep(General.random(2000, 4000));
            }
            if (seerText.contains("My first is in wizard, but not in a mage.")) {
                answer = "WIND";
                General.println("[Debug]: Answer is: " + answer);
                NPCInteraction.handleConversation();
                Timing.waitCondition(() -> Interfaces.isInterfaceSubstantiated(298, 47), 3000);
                General.sleep(General.random(300, 1000));
                if (Interfaces.get(298, 47) != null) {
                    while (!Interfaces.get(298, 43).getText().contains("W")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 48).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 44).getText().contains("I")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 50).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 45).getText().contains("N")) {
                        General.sleep(General.random(150, 300));
                        Interfaces.get(298, 52).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 46).getText().contains("D")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 54).click();
                        General.sleep(General.random(500, 800));
                    }
                    if (Interfaces.isInterfaceSubstantiated(298, 56)) {
                        Interfaces.get(298, 56).click(); // confirm button
                        Timing.waitCondition(() -> !Interfaces.isInterfaceSubstantiated(298, 56), 3500);
                    }
                }
                Utilities.clickObject(SEER_DOOR_ENTRANCE, "Open"); //4165 is the door ID that you need to open to enter Seer's building
                General.sleep(General.random(2000, 4000));
            }
            if (seerText.contains("My first is in water, and also in tea.")) {
                answer = "TIME";
                General.println("[Debug]: Answer is: " + answer);
                NPCInteraction.handleConversation();
                Timing.waitCondition(() -> Interfaces.isInterfaceSubstantiated(298, 47), 3000);
                General.sleep(General.random(300, 1000));
                if (Interfaces.get(298, 47) != null) {
                    while (!Interfaces.get(298, 43).getText().contains("T")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 48).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 44).getText().contains("I")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 50).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 45).getText().contains("M")) {
                        General.sleep(General.random(150, 300));
                        Interfaces.get(298, 52).click();
                        General.sleep(General.random(500, 800));
                    }
                    while (!Interfaces.get(298, 46).getText().contains("E")) {
                        General.sleep(General.random(50, 300));
                        Interfaces.get(298, 54).click();
                        General.sleep(General.random(500, 800));
                    }
                    if (Interfaces.isInterfaceSubstantiated(298, 56)) {
                        Interfaces.get(298, 56).click(); // confirm button
                        Timing.waitCondition(() -> !Interfaces.isInterfaceSubstantiated(298, 56), 3500);
                    }
                }
                Utilities.clickObject(SEER_DOOR_ENTRANCE, "Open"); //4165 is the door ID that you need to open to enter Seer's building
                General.sleep(General.random(2000, 4000));
            }
        }
    }

    public void seerUpStairsItems() {
        if (BOTTOM_RIGHT_ROOM.contains(Player.getPosition())) {
            FremennikTrials.stage = "Going upstairs";
            Utilities.clickObject("Ladder", "Climb-up");
            Timing.waitCondition(() -> PEER_UPSTAIRS_AREA.contains(Player.getPosition()), 5000);
            General.sleep(General.random(400, 1200));
        }
        if (PEER_UPSTAIRS_AREA.contains(Player.getPosition())) {
            updateItems();
            if (fourLitreBucket.length > 0 || invHerring.length > 0 || vase.length > 0) {
                /**
                 * this is in case it loops and calls this method again we don't need it do continue if ...
                 *  this is what our inventory has.
                 */
                return;
            } else if (invEmptyJug.length < 1 || invEmptyBucket.length < 1 || invRedDish.length < 1 || invRedHerring.length < 1 || invWoodenDisk.length < 1) {
                if (invEmptyBucket.length < 1) {
                    FremennikTrials.stage = "Getting empty bucket";
                    Utilities.clickObject(CLOSED_CUPBOARD, "Open");
                    Timing.waitCondition(() -> Objects.find(20, OPEN_CUPBOARD).length > 0, 5000);
                    if (Utilities.clickObject(OPEN_CUPBOARD, "Search"))
                        Timing.waitCondition(() -> Inventory.find(emptyBucket).length > 0, 5000);
                    updateItems();
                }
                if (invRedDish.length < 1) {
                    FremennikTrials.stage = "Getting red dish";
                    if (Utilities.clickObject(UNICORN_HEAD, "Study")) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation();
                        Timing.waitCondition(() -> Inventory.find(oldRedDish).length > 0, 3000);
                        updateItems();
                    }
                }
                if (invWoodenDisk.length < 1) {
                    FremennikTrials.stage = "Getting wooden dish";
                    if (Utilities.clickObject(BULLS_HEAD, "Study")) {
                        NPCInteraction.waitForConversationWindow();
                        NPCInteraction.handleConversation();
                        updateItems();
                    }
                }
                if (invEmptyJug.length < 1) {
                    FremennikTrials.stage = "Getting empty jug ";
                    Utilities.clickObject(4167, "Open", 5);
                    Timing.waitCondition(() -> Objects.findNearest(2, 4168).length > 0, 5000);
                    if (Utilities.clickObject(4168, "Search")) {
                        Timing.waitCondition(() -> Inventory.find(emptyJug).length > 0, 7000);
                        updateItems();
                    }
                }
                if (invRedHerring.length < 1) {
                    FremennikTrials.stage = "Getting red herring";
                    if (Utilities.clickObject(BOOKCASE, "Search")) {
                        Timing.waitCondition(() -> Inventory.find(redHerring).length > 0, 7000);
                        General.sleep(General.random(300, 1200));
                        updateItems();
                    }
                }
            }
        }
    }

    public void doWaterPuzzle() {
        FremennikTrials.stage = "Doing puzzle";
        updateItems();
        General.println("[Debug]: Doing Puzzle.");
        while (Inventory.find(KEY).length < 1) {
            updateItems();
            General.sleep(100);
            if (invFullBucket.length < 1 && invEmptyBucket.length > 0 && twoThirdsJug.length < 1 && sealedVase.length < 1) {
                FremennikTrials.stage = "Filling empty bucket";
                if (Utilities.useItemOnObject(emptyBucket, TAP)) {
                    Timing.waitCondition(() -> Inventory.find(FULL_BUCKET).length > 0, 8000);
                    updateItems();
                }
            }
            if (key.length < 1 && invtwofifthsBucket.length < 1 && invEmptyJug.length > 0 && fourLitreBucket.length < 1 && vase.length < 1 && sealedVase.length < 1 && invHerring.length < 1) {
                FremennikTrials.stage = "Filling jug";
                Utilities.useItemOnItem(FULL_BUCKET, emptyJug);
                General.sleep(General.random(500, 2500));
                updateItems();

            }
            if (key.length < 1 && invEmptyJug.length < 1 && invFullJug.length > 0 && vase.length < 1 && fourLitreBucket.length < 1 && vase.length < 1) {
                FremennikTrials.stage = "Emptying jug";
                Utilities.useItemOnObject(FULL_JUG, DRAIN);
                Timing.waitCondition(() -> Inventory.find(emptyJug).length > 0, 8000);
                General.sleep(General.random(300, 2000));
                updateItems();
            }
            if (key.length < 1 && invtwofifthsBucket.length > 0 && invEmptyJug.length > 0 && fourLitreBucket.length < 1 && vase.length < 1) {
                FremennikTrials.stage = "Getting 2/5 Jug";
                Utilities.useItemOnItem(TWO_FIFTHS_BUCKET, emptyJug);
                updateItems();
            }
            if (key.length < 1 && twoThirdsJug.length > 0 && invEmptyBucket.length > 0 && vase.length < 1 && fourLitreBucket.length < 1) {
                FremennikTrials.stage = "Refilling bucket";
                Utilities.useItemOnObject(emptyBucket, TAP);
                Timing.waitCondition(() -> Inventory.find(FULL_BUCKET).length > 0, 8000);
                General.sleep(General.random(300, 2000));
                updateItems();
            }
            if (key.length < 1 && invFullBucket.length > 0 && twoThirdsJug.length > 0 && vase.length < 1) {
                FremennikTrials.stage = "Getting 4L Bucket";
                if (Utilities.useItemOnItem(FULL_BUCKET, TWO_THIRDS_JUG)) {
                    General.sleep(General.random(300, 2000));
                    updateItems();
                }
            }
            if (key.length < 1 && fourLitreBucket.length > 0 && vase.length < 1) {
                FremennikTrials.stage = "Using 4L bucket on chest";
                if (Utilities.useItemOnObject(FOUR_LITRE_BUCKET, CLOSED_CHEST)) {
                    Timing.waitCondition(() -> Inventory.find(VASE).length > 0, 8000);
                    General.sleep(General.random(300, 2000));
                    updateItems();
                }
            }
            if (key.length < 1 && vase.length > 0 && redGloop.length < 1 && gloopDisk.length < 1 && vaseLid.length < 1) {
                FremennikTrials.stage = "Getting Red Gloop";
                if (Utilities.useItemOnObject(redHerring, RANGE)) {
                    NPCInteraction.waitForConversationWindow();
                    NPCInteraction.handleConversation();
                    General.sleep(General.random(300, 2000));
                    updateItems();
                }
            }
            if (key.length < 1 && redGloop.length > 0 && gloopDisk.length < 1) {
                FremennikTrials.stage = "Using gloop on disk";
                if (Utilities.useItemOnItem(RED_GLOOP, woodenDisk)) {
                    Timing.waitCondition(() -> Inventory.find(GLOOP_DISK).length > 0, 3000);
                    General.sleep(General.random(300, 2000));
                    updateItems();
                }
            }
            if (key.length < 1 && redGloop.length < 1 && gloopDisk.length > 0 && !BOTTOM_LEFT_ROOM.contains(Player.getPosition())) {
                FremennikTrials.stage = "Going downstairs";
                Utilities.clickScreenWalk(new RSTile(2636, 3662,2)); //if you don't walk to the trap door it goes down the wrong one
                if (Utilities.clickObject(CLOSED_TRAPDOOR, "Open"))
                    Timing.waitCondition(() -> Objects.findNearest(20, OPEN_TRAPDOOR).length > 0, 8000);
                if (Utilities.clickObject(OPEN_TRAPDOOR, "Climb-down"))
                    Timing.waitCondition(() -> BOTTOM_LEFT_ROOM.contains(Player.getPosition()), 8000);
                General.sleep(General.random(1500, 3000));
            }
            if (key.length < 1 && gloopDisk.length > 0 && BOTTOM_LEFT_ROOM.contains(Player.getPosition())) {
                FremennikTrials.stage = "Using Disks on Mural";
                if (Utilities.useItemOnObject(oldRedDish, MURAL)) {
                    Timing.waitCondition(() -> Inventory.find(oldRedDish).length < 1, 6000);
                    General.sleep(General.random(300, 1000));
                }
                if (Utilities.useItemOnObject(GLOOP_DISK, MURAL)) {
                    Timing.waitCondition(() -> Inventory.find(GLOOP_DISK).length < 1, 6000);
                    General.sleep(General.random(300, 1000));
                }
                updateItems();
            }
            if (key.length < 1 && vaseLid.length > 0) {
                FremennikTrials.stage = "Filling vase";
                if (invFullJug.length > 0) {
                    Utilities.useItemOnItem(FULL_JUG, VASE);
                    Timing.waitCondition(() -> Inventory.find(VASE).length > 0, 4000);
                    General.sleep(General.random(300, 2000));

                }
                if (Inventory.find(VASE).length > 0) {
                    Utilities.useItemOnItem(VASE_LID, VASE);
                    Timing.waitCondition(() -> Inventory.find(SEALED_VASE).length > 0, 4000);
                    General.sleep(General.random(300, 2000));
                }
                updateItems();
            }
            if (sealedVase.length > 0) {
                FremennikTrials.stage = "Getting frozen Key";
                sealedVase = Inventory.find(3740);
                Utilities.clickObject("Ladder", "Climb-up");
                Timing.waitCondition(() -> PEER_UPSTAIRS_AREA.contains(Player.getPosition()), 6000);
                if (PEER_UPSTAIRS_AREA.contains(Player.getPosition())) {
                    if (Utilities.useItemOnObject(SEALED_VASE, FROZEN_TABLE)) {
                        Timing.waitCondition(() -> Inventory.find(FROZEN_KEY).length > 0, 8000);
                        General.sleep(General.random(300, 2000));
                    }
                }
                updateItems();
            }
            if (invFrozenKey.length > 0) {
                FremennikTrials.stage = "Coking key";
                Utilities.useItemOnObject(FROZEN_KEY, RANGE);
                Timing.waitCondition(() -> Inventory.find(KEY).length > 0, 8000);
                General.sleep(General.random(300, 3000));
                updateItems();
            }
            if (key.length > 0) {
                if (PEER_UPSTAIRS_AREA.contains(Player.getPosition())) {
                    FremennikTrials.stage = "Going downstairs";
                    Utilities.clickScreenWalk(new RSTile(2636, 3662, 2)); //if you don't walk to the trap door it goes down the wrong one
                    if (Utilities.clickObject(CLOSED_TRAPDOOR, "Open"))
                        Timing.waitCondition(() -> Objects.findNearest(20, OPEN_TRAPDOOR).length > 0, 8000);
                    if (Utilities.clickObject(OPEN_TRAPDOOR, "Climb-down"))
                        Timing.waitCondition(() -> BOTTOM_LEFT_ROOM.contains(Player.getPosition()), 8000);
                    General.sleep(General.random(500, 3000));
                }
                Utilities.useItemOnObject(KEY, 4166);
                General.sleep(General.random(1500, 3000));
                NPCInteraction.waitForConversationWindow();
                NPCInteraction.handleConversation();
                updateItems();
                if (!BOTTOM_RIGHT_ROOM.contains(Player.getPosition()) && !BOTTOM_LEFT_ROOM.contains(Player.getPosition())
                        && !PEER_UPSTAIRS_AREA.contains(Player.getPosition())
                        && Inventory.find(KEY).length < 1) {
                    FremennikTrials.stage = "Home teleport";
                    if (GameTab.open(GameTab.TABS.MAGIC)) {
                        if (Interfaces.isInterfaceSubstantiated(218, 4)) {
                            Interfaces.get(218, 4).click();
                            General.sleep(General.random(10000, 15000));
                            break;
                        }
                    }

                }
            }
        }
    }

}
