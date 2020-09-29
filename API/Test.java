package scripts.Utility;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.types.RSInterfaceComponent;

import java.awt.*;

public class Test {

    static int PARENT = 12;
    static int ITEM_WINDOW_CHILD = 12;
    static int SCROLLBAR_CHILD = 13;


    /**
     * Gets Y coordinate for the item in args in bank
     *
     * @param itemId to search for
     * @return absolute Y coordinate of interface containing item id
     */
    public static int getBankItemYCoordinate(int itemId) {

        if (Banking.isBankScreenOpen()) {
            if (Interfaces.isInterfaceSubstantiated(PARENT, ITEM_WINDOW_CHILD)) {
                RSInterfaceComponent[] components = Interfaces.get(PARENT, ITEM_WINDOW_CHILD).getChildren();
                for (RSInterfaceComponent i : components) {

                    if (i != null)
                        if (i.getComponentItem() == itemId) {
                            //General.println("[BankHandler]: Bank item interface component: 12, 12, " + i.getComponentIndex());
                            return (int) i.getAbsolutePosition().getY(); // gets relative Y
                        }


                }
            }
        }
        return -1;
    }

    /**
     * Gets Y coordinate for the scroll bar
     *
     * @return Absolute Y coordinate of scroll bar (actual bar that moves)
     */
    public static int getBankScrollBarYCoordinate() {
        if (Banking.isBankScreenOpen()) {
            if (Interfaces.isInterfaceSubstantiated(PARENT, SCROLLBAR_CHILD))
                return (int) Interfaces.get(PARENT, SCROLLBAR_CHILD, 1).getAbsolutePosition().getY();

        }
        return -1;
    }


    public static boolean scrollToBankItem(int itemId) {
        if (!Banking.isBankScreenOpen()) {
            //open bank method here
        }
            if (Banking.isBankScreenOpen()) {
                if (Interfaces.isInterfaceSubstantiated(PARENT, ITEM_WINDOW_CHILD)) {
                    Rectangle rec = Interfaces.get(PARENT, ITEM_WINDOW_CHILD).getAbsoluteBounds();

                    for (int i = 0; i < 50; i++) { // limits to 50 scrolls of the mouse wheel
                        if (!rec.contains(Mouse.getPos()))
                            Mouse.moveBox(rec);

                        /**
                         *  Y coordinate of item is constantly changing so you need to call the method each time to check it
                         */
                        if (getBankItemYCoordinate(itemId) > getBankScrollBarYCoordinate()) { // item is lower (higher #) than scroll bar Y + 5 lines worth
                            Mouse.scroll(false); //scroll down
                            General.sleep(General.randomSD(50, 750, 200, 120));
                        } else if (getBankItemYCoordinate(itemId) < getBankScrollBarYCoordinate() - 180) {
                            Mouse.scroll(true);
                            General.sleep(General.randomSD(50, 750, 250, 150));

                        } else if ((getBankItemYCoordinate(itemId) > getBankScrollBarYCoordinate() - 180) && (getBankItemYCoordinate(itemId) < getBankScrollBarYCoordinate() + 180)) {
                            // can add item click here if desired
                            return true;
                        }
                        if (i == 49) {
                            return false;
                        }
                    }
                }

            }
        return false;
    }

}
