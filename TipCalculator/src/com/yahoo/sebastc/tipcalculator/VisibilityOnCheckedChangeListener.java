package com.yahoo.sebastc.tipcalculator;

import android.view.View;
import android.widget.CompoundButton;

public class VisibilityOnCheckedChangeListener implements
		CompoundButton.OnCheckedChangeListener {

	private final View controledView;

	public VisibilityOnCheckedChangeListener(View controledView) {
		this.controledView = controledView;
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			controledView.setVisibility(View.VISIBLE);
		} else {
			controledView.setVisibility(View.INVISIBLE);
		}
	}

}
