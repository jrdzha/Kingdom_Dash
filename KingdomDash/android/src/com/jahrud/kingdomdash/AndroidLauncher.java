package com.jahrud.kingdomdash;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import com.google.ads.*;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class AndroidLauncher extends AndroidApplication implements OSLauncher{

	protected AdView adView;

	@Override public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Create the layout
		RelativeLayout layout = new RelativeLayout(this);

		// Do the stuff that initialize() would do for you
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		// Create the libgdx View
		View gameView = initializeForView(new GameEngine(this), config);

		// Create and setup the AdMob view
		adView = new AdView(this, AdSize.BANNER, "ca-app-pub-4350977853893984/4915739550");
		adView.loadAd(new AdRequest());

		// Add the libgdx view
		layout.addView(gameView);

		// Add the AdMob view
		RelativeLayout.LayoutParams adParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

		layout.addView(adView, adParams);

		// Hook it all up
		setContentView(layout);
	}

	@Override
	public void showInterstitialAd() {

	}

	@Override
	public void showBannerAd() {
		adView.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideBannerAd() {
		adView.setVisibility(View.GONE);
	}
}
