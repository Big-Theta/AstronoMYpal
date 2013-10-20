package com.bigtheta.astronomypal.MainMenu;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bigtheta.astronomypal.R;

/**
 * Created by cevans on 10/19/13.
 */
public class MainMenuFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_menu_buttons, container, false);
    }

    public void fragmentTest() {
    }
}