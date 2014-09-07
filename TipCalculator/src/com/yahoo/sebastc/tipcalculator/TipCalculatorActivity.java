package com.yahoo.sebastc.tipcalculator;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class TipCalculatorActivity extends Activity {

	private static final String CONFIG_KEY_TIP_RATE1 = "tipRate1";
	private static final String CONFIG_KEY_TIP_RATE2 = "tipRate2";
	private static final String CONFIG_KEY_TIP_RATE3 = "tipRate3";
	private static final String CONFIG_FILE_NAME = "config.txt";
	private EditText etAmount;
	private EditText etTipRate1;
	private EditText etTipRate2;
	private EditText etTipRate3;
	private Button bTipRate1;
	private Button bTipRate2;
	private Button bTipRate3;
	private ImageButton bEditRates;
	private TextView tvTip;
	private RelativeLayout rlTipRateButtons;
	private SeekBar sbTipRate;
	private RatingBar rbTipRate;

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

		loadConfig();
		currentTipRate = tipRate2;
		refreshUI();

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
				saveConfig();
				refreshUI();
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
				saveConfig();
				refreshUI();
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
				saveConfig();
				refreshUI();
			}
		});

		sbTipRate = (SeekBar) findViewById(R.id.sbTipRate);
		sbTipRate
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
					}

					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
					}

					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						updateTip(progress);

					}
				});

		rbTipRate = (RatingBar) findViewById(R.id.rbTipRate);
		rbTipRate
				.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

					@Override
					public void onRatingChanged(RatingBar ratingBar,
							float rating, boolean fromUser) {
						updateTip(Math.round(rating * 10));
					}
				});

		rlTipRateButtons = (RelativeLayout) findViewById(R.id.rlTipRateButtons);

		((RadioButton) findViewById(R.id.rbButtons))
		.setOnCheckedChangeListener(new VisibilityOnCheckedChangeListener(rlTipRateButtons));

		((RadioButton) findViewById(R.id.rbRating))
		.setOnCheckedChangeListener(new VisibilityOnCheckedChangeListener(rbTipRate));

		((RadioButton) findViewById(R.id.rbSeekBar))
		.setOnCheckedChangeListener(new VisibilityOnCheckedChangeListener(sbTipRate));

		updateTip();
	}

	private void refreshUI() {
		bTipRate1.setText(getString(R.string.tip_button_format, tipRate1));
		bTipRate2.setText(getString(R.string.tip_button_format, tipRate2));
		bTipRate3.setText(getString(R.string.tip_button_format, tipRate3));
	}

	private void loadConfig() {
		tipRate1 = 15;
		tipRate2 = 20;
		tipRate3 = 25;
		try {
			File configFile = new File(getFilesDir(), CONFIG_FILE_NAME);
			if (configFile.exists()) {
				String configStr = FileUtils.readFileToString(configFile);
				JSONObject object = (JSONObject) new JSONTokener(configStr)
						.nextValue();
				tipRate1 = object.getInt(CONFIG_KEY_TIP_RATE1);
				tipRate2 = object.getInt(CONFIG_KEY_TIP_RATE2);
				tipRate3 = object.getInt(CONFIG_KEY_TIP_RATE3);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void saveConfig() {
		try {
			File configFile = new File(getFilesDir(), CONFIG_FILE_NAME);
			JSONObject config = new JSONObject();
			config.put(CONFIG_KEY_TIP_RATE1, tipRate1);
			config.put(CONFIG_KEY_TIP_RATE2, tipRate2);
			config.put(CONFIG_KEY_TIP_RATE3, tipRate3);
			FileUtils.writeStringToFile(configFile, config.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
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
			etTipRate1.requestFocus();
		} else {
			bEditRates.setImageResource(R.drawable.ic_edit);
			bTipRate1.setVisibility(View.VISIBLE);
			bTipRate2.setVisibility(View.VISIBLE);
			bTipRate3.setVisibility(View.VISIBLE);
			etTipRate1.setVisibility(View.INVISIBLE);
			etTipRate2.setVisibility(View.INVISIBLE);
			etTipRate3.setVisibility(View.INVISIBLE);
			etAmount.requestFocus();
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
		sbTipRate.setProgress(currentTipRate);
		rbTipRate.setRating(currentTipRate / 10f);
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
