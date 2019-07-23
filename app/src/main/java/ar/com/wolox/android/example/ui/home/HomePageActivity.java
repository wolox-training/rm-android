package ar.com.wolox.android.example.ui.home;

import android.os.Bundle;
import android.os.PersistableBundle;

import ar.com.wolox.android.R;
import ar.com.wolox.wolmo.core.activity.WolmoActivity;

/**
 *
 */
public class HomePageActivity extends WolmoActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        init();
    }

    @Override
    protected int layout() {
        return R.layout.activity_base;
    }

    @Override
    protected void init() {
    }
}

