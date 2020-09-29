package scripts.Utility;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterfaceComponent;

import java.awt.*;

public class BankHandler {

    static int PARENT = 12;
    static int ITEM_WINDOW_CHILD = 12;
    static int SCROLLBAR_CHILD = 13;


    /**
     * Gets Y coordinate for the item in args in bank
     *
     * @param itemId to search for
     * @return absolute Y coordinate of interface containing item id; -1 if bank not open or interface is not substantiated
     */
    private static int getBankItemYCoordinate(int itemId) {

        if (Banking.isBankScreenOpen()) {
            if (Interfaces.isInterfaceSubstantiated(PARENT, ITEM_WINDOW_CHILD)) {
                RSInterfaceComponent[] components = Interfaces.get(PARENT, ITEM_WINDOW_CHILD).getChildren();
                for (RSInterfaceComponent i : components) {

                    if (i != null)
                        if (i.getComponentItem() == itemId)
                            return (int) i.getAbsolutePosition().getY(); // gets relative Y

                }
            }
        }
        return -1;
    }

    /**
     * Gets Y coordinate for the scroll bar
     *
     * @return Absolute Y coordinate of scroll bar (actual bar that moves); -1 if bank not open or interface is not substantiated
     */
    private static int getBankScrollBarYCoordinate() {
        if (Banking.isBankScreenOpen()) {
            if (Interfaces.isInterfaceSubstantiated(PARENT, SCROLLBAR_CHILD))
                return (int) Interfaces.get(PARENT, SCROLLBAR_CHILD, 1).getAbsolutePosition().getY();

        }
        return -1;
    }

    /**
     * This method will scroll until the item is on the screen
     * @param itemId to search for
     * @return
     */
    public static boolean scrollToBankItem(int itemId) {
        if (!Banking.isBankScreenOpen()) {
            //open bank method here
        }
        if (Banking.isBankScreenOpen()) {
            if (Banking.find(itemId).length > 0) { // make sure we have the item

                if (Interfaces.isInterfaceSubstantiated(PARENT, ITEM_WINDOW_CHILD)) {
                    Rectangle rec = Interfaces.get(PARENT, ITEM_WINDOW_CHILD).getAbsoluteBounds();
                    int buffer = (int) (Interfaces.get(PARENT, ITEM_WINDOW_CHILD).getHeight()/1.5);  // buffer is the size of the banking item area/1.5 (can increase to 2 if issues)
                    
                    for (int i = 0; i < 100; i++) { // limits to 100 scrolls of the mouse wheel
                       General.sleep(General.random(50,150));
    
                        if (!rec.contains(Mouse.getPos()))
                            Mouse.moveBox(rec);

                        /**
                         *  Y coordinate of item is constantly changing so you need to call the method each time to check it
                         *  Mouse speed should be played with, it's purposefully slow right now
                         */
                        General.println( getBankScrollBarYCoordinate());
                        if (getBankItemYCoordinate(itemId) > getBankScrollBarYCoordinate()) { // item is lower (higher #) than scroll bar Y
                            Mouse.scroll(false); //scroll down
                            General.sleep(General.randomSD(50, 750, 200, 125));
                        } else if (getBankItemYCoordinate(itemId) < getBankScrollBarYCoordinate() ) {
                            Mouse.scroll(true);
                            General.sleep(General.randomSD(50, 750, 250, 150));

                        }
                        if ((getBankItemYCoordinate(itemId) > getBankScrollBarYCoordinate() - buffer)
                                && (getBankItemYCoordinate(itemId) < getBankScrollBarYCoordinate() + buffer)) {
                            /**
                             *  return item withdraw here if desired
                              */
                            General.println("true");
                            return true;
                        }
                        if (i == 100) {
                            General.println("false");
                            return false;
                        }
                    }
                }
            }

        }
        return false;
    }

}
