package com.bigtheta.astronomypal.MenuContent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cevans on 10/19/13.
 */
public class PersonalSettingsContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<PersonalSettingsMenuItem> ITEMS = new ArrayList<PersonalSettingsMenuItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, PersonalSettingsMenuItem> ITEM_MAP = new HashMap<String, PersonalSettingsMenuItem>();

    static {
        // Add 4 sample items.
        addItem(new PersonalSettingsMenuItem("1", "Telescope"));
        addItem(new PersonalSettingsMenuItem("2", "Location"));
    }

    private static void addItem(PersonalSettingsMenuItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class PersonalSettingsMenuItem {
        public String id;
        public String content;

        public PersonalSettingsMenuItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
