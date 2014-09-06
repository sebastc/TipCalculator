package com.yahoo.sebastc.tipcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TipCalculatorActivity extends Activity {

	private EditText etAmount;
	private Button bPct15;
	private Button bPct20;
	private Button bPct25;
	private TextView tvTip;
	private double currentTipRate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip_calculator);
		bPct15 = (Button) findViewById(R.id.bPct15);
		bPct20 = (Button) findViewById(R.id.bPct20);
		bPct25 = (Button) findViewById(R.id.bPct25);
		tvTip = (TextView) findViewById(R.id.tvTip);
		etAmount = (EditText) findViewById(R.id.etAmount);
		etAmount.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				updateTip();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		currentTipRate = .20;
		updateTip();
	}

	public void onPct15Click(View v) {
		updateTip(.15);
	}

	public void onPct20Click(View v) {
		updateTip(.20);
	}

	public void onPct25Click(View v) {
		updateTip(.25);
	}

	private void updateTip(double pct) {
		currentTipRate = pct;
		updateTip();
	}

	private void updateTip() {
		String amountStr = etAmount.getText().toString();
		double amount = 0;
		if (!amountStr.trim().isEmpty()) {
			try {
				amount = Double.parseDouble(amountStr);
			} catch (NumberFormatException e) {
			}
		}
		double tip = amount * currentTipRate;
		tvTip.setText(getResources().getString(R.string.tip_format, tip, currentTipRate*100));
	}
}
