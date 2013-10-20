package com.bigtheta.astronomypal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;

import com.bigtheta.astronomypal.MainMenu.MainMenuFragment;

import com.bigtheta.astronomypal.MainMenu.MainMenuItemDetailFragment;

import java.util.ArrayList;


/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ItemListFragment} and the item details
 * (if present) is a {@link com.bigtheta.astronomypal.MainMenu.MainMenuItemDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link ItemListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class MainActivity extends FragmentActivity
        implements ItemListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_twopane);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            //((ItemListFragment) getSupportFragmentManager()
            //        .findFragmentById(R.id.main_menu_item_list))
            //        .setActivateOnItemClick(true);
            //Fragment mainMenuFragment = getSupportFragmentManager()
            //        .findFragmentById(R.id.main_menu_item_list);
            //MainMenuFragment mainMenu = new MainMenuFragment();
        }
        ArrayList<String> listItems = new ArrayList<String>();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);
        ListView lv = (ListView) findViewById(R.id.messier_list);
        lv.setAdapter(adapter);
        //lv.setFilterEnabled(true);
        //put items in the listItems
        adapter.notifyDataSetChanged();


        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link ItemListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(MainMenuItemDetailFragment.ARG_ITEM_ID, id);
            MainMenuItemDetailFragment fragment = new MainMenuItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, ItemDetailActivity.class);
            detailIntent.putExtra(MainMenuItemDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
    public void search(View view) {
        String query = ((EditText) findViewById(R.id.search_box)).getText().toString();
        ((TextView) findViewById(R.id.LoganHackTextView)).setText(query);
    }
    public void LoganHackTime(View view) {
        ((TextView) findViewById(R.id.LoganHackTextView)).setText("And God killed another puppy.");
    }
}
