package com.jahrud.kingdomdash;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication{

	AndroidAdViewer androidAdViewer;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
		androidAdViewer = new AndroidAdViewer(this);
		initialize(new GameEngine(androidAdViewer), cfg);
	}

	@Override
	public void onStart() {
		super.onStart();
		androidAdViewer.onStart();
	}

	@Override
	public void onResume() {
		super.onResume();
		androidAdViewer.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		androidAdViewer.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
		androidAdViewer.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		androidAdViewer.onDestroy();
	}

	@Override
	public void onBackPressed() {
		// If an interstitial is on screen, close it.
		if (androidAdViewer.onBackPressed())
			return;
		else
			super.onBackPressed();
	}
}
