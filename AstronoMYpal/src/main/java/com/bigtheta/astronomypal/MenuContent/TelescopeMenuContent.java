package com.bigtheta.astronomypal.MenuContent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cevans on 10/19/13.
 */
public class TelescopeMenuContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<TelescopeMenuItem> ITEMS = new ArrayList<TelescopeMenuItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, TelescopeMenuItem> ITEM_MAP = new HashMap<String, TelescopeMenuItem>();

    static {
        // Add 4 sample items.
        addItem(new TelescopeMenuItem("1", "Type"));
        addItem(new TelescopeMenuItem("2", "Aperture"));
        addItem(new TelescopeMenuItem("3", "Manufacturer"));
        addItem(new TelescopeMenuItem("4", "Name"));
    }

    private static void addItem(TelescopeMenuItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class TelescopeMenuItem {
        public String id;
        public String content;

        public TelescopeMenuItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
