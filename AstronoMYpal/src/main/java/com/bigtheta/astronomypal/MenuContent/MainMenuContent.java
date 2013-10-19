package com.bigtheta.astronomypal.MenuContent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cevans on 10/19/13.
 */
public class MainMenuContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<MainMenuItem> ITEMS = new ArrayList<MainMenuItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, MainMenuItem> ITEM_MAP = new HashMap<String, MainMenuItem>();

    static {
        // Add 4 sample items.
        addItem(new MainMenuItem("1", "Start a New Session"));
        addItem(new MainMenuItem("2", "View Past Session"));
        addItem(new MainMenuItem("3", "Personal Settings"));
        addItem(new MainMenuItem("4", "Achievements"));
    }

    private static void addItem(MainMenuItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class MainMenuItem {
        public String id;
        public String content;

        public MainMenuItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
