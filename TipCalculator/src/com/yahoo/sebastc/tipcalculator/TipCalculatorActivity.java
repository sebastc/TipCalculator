package com.yahoo.sebastc.tipcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class TipCalculatorActivity extends Activity {

	private EditText etAmount;
	private EditText etTipRate1;
	private EditText etTipRate2;
	private EditText etTipRate3;
	private Button bTipRate1;
	private Button bTipRate2;
	private Button bTipRate3;
	private ImageButton bEditRates;
	private TextView tvTip;

	private int currentTipRate;
	private int tipRate1;
	private int tipRate2;
	private int tipRate3;

	private boolean editMode = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tip_calculator);
		bTipRate1 = (Button) findViewById(R.id.bTipRate1);
		bTipRate2 = (Button) findViewById(R.id.bTipRate2);
		bTipRate3 = (Button) findViewById(R.id.bTipRate3);
		bEditRates = (ImageButton) findViewById(R.id.bEditRates);
		tvTip = (TextView) findViewById(R.id.tvTip);

		tipRate1 = 15;
		tipRate2 = 20;
		tipRate3 = 25;
		currentTipRate = tipRate2;
		
		etAmount = (EditText) findViewById(R.id.etAmount);
		etAmount.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
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
		etTipRate1 = (EditText) findViewById(R.id.etTipRate1);
		etTipRate1.setText(Integer.toString(tipRate1));
		etTipRate1.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				tipRate1 = parseInt(s.toString(), tipRate1);
				bTipRate1.setText(getString(R.string.tip_button_format, tipRate1));
			}
		});
		etTipRate2 = (EditText) findViewById(R.id.etTipRate2);
		etTipRate2.setText(Integer.toString(tipRate2));
		etTipRate2.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				tipRate2 = parseInt(s.toString(), tipRate2);
				bTipRate2.setText(getString(R.string.tip_button_format, tipRate2));
			}
		});
		etTipRate3 = (EditText) findViewById(R.id.etTipRate3);
		etTipRate3.setText(Integer.toString(tipRate3));
		etTipRate3.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				tipRate3 = parseInt(s.toString(), tipRate3);
				bTipRate3.setText(getString(R.string.tip_button_format, tipRate3));
			}
		});
		updateTip();
	}

	public void onTipRate1Click(View v) {
		updateTip(tipRate1);
	}

	public void onTipRate2Click(View v) {
		updateTip(tipRate2);
	}

	public void onTipRate3Click(View v) {
		updateTip(tipRate3);
	}

	public void onClickBEditRates(View v) {
		editMode = !editMode;
		if (editMode) {
			bEditRates.setImageResource(R.drawable.ic_validate);
			bTipRate1.setVisibility(View.INVISIBLE);
			bTipRate2.setVisibility(View.INVISIBLE);
			bTipRate3.setVisibility(View.INVISIBLE);
			etTipRate1.setVisibility(View.VISIBLE);
			etTipRate2.setVisibility(View.VISIBLE);
			etTipRate3.setVisibility(View.VISIBLE);
		} else {
			bEditRates.setImageResource(R.drawable.ic_edit);
			bTipRate1.setVisibility(View.VISIBLE);
			bTipRate2.setVisibility(View.VISIBLE);
			bTipRate3.setVisibility(View.VISIBLE);
			etTipRate1.setVisibility(View.INVISIBLE);
			etTipRate2.setVisibility(View.INVISIBLE);
			etTipRate3.setVisibility(View.INVISIBLE);
		}
	}

	private void updateTip(int pct) {
		currentTipRate = pct;
		updateTip();
	}

	private void updateTip() {
		String amountStr = etAmount.getText().toString();
		double amount = parseInt(amountStr, 0);
		double tip = amount * currentTipRate / 100;
		tvTip.setText(getResources().getString(R.string.tip_output_format, tip,
				currentTipRate));
	}

	private int parseInt(String str, int defaultValue) {
		int value = defaultValue;
		if (!str.trim().isEmpty()) {
			try {
				value = Integer.parseInt(str);
			} catch (NumberFormatException e) {
			}
		}
		return value;
	}
}
