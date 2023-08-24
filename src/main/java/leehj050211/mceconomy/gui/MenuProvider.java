package leehj050211.mceconomy.gui;

import com.samjakob.spigui.SpiGUI;
import leehj050211.mceconomy.MCEconomy;

public class MenuProvider {

    private static MenuProvider instance;
    public static MenuProvider getInstance() {
        if (instance == null) {
            instance = new MenuProvider();
        }
        return instance;
    }

    private static SpiGUI spiGUI;

    private MenuProvider() {
        spiGUI = new SpiGUI(MCEconomy.getInstance());
    }

    public static SpiGUI menuGui() {
        spiGUI.setEnableAutomaticPagination(false);
        return spiGUI;
    }

    public static SpiGUI pageableMenuGui() {
        spiGUI.setDefaultToolbarBuilder(new MenuToolbarProvider());
        return spiGUI;
    }

    public static SpiGUI inventoryGui() {
        spiGUI.setDefaultToolbarBuilder(new MenuToolbarProvider());
        spiGUI.setBlockDefaultInteractions(false);
        return spiGUI;
    }
}
