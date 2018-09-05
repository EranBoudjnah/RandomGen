package com.mitteloupe.randomgenexample.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mitteloupe.randomgenexample.R;
import com.mitteloupe.randomgenexample.data.person.Gender;
import com.mitteloupe.randomgenexample.data.person.Person;

/**
 * Created by Eran Boudjnah on 18/08/2018.
 */
public class PersonView extends FrameLayout {
	private View mIconFemaleView;
	private View mIconMaleView;
	private TextView mNameView;
	private TextView mAgeView;
	private TextView mOccupationView;
	private TextView mPhoneNumberView;

	public PersonView(@NonNull Context context) {
		this(context, null);
	}

	public PersonView(@NonNull Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PersonView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		inflate(context, R.layout.view_person, this);

		mIconFemaleView = findViewById(R.id.icon_female);
		mIconMaleView = findViewById(R.id.icon_male);
		mNameView = findViewById(R.id.text_name_value);
		mAgeView = findViewById(R.id.text_age_value);
		mOccupationView = findViewById(R.id.text_occupation_value);
		mPhoneNumberView = findViewById(R.id.text_phone_value);
	}

	public void setPerson(Person pPerson) {
		mIconFemaleView.setVisibility(pPerson.getGender() == Gender.FEMALE ? View.VISIBLE : View.GONE);
		mIconMaleView.setVisibility(pPerson.getGender() == Gender.MALE ? View.VISIBLE : View.GONE);

		mNameView.setText(pPerson.getName());
		mAgeView.setText(String.valueOf(pPerson.getAge()));
		mOccupationView.setText(formatOccupation(pPerson.getOccupation().toString()));
		mPhoneNumberView.setText(pPerson.getPhoneNumber());
	}

	private String formatOccupation(String pString) {
		String[] words = pString.split("_");
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < words.length; ++i) {
			String word = words[i];
			stringBuilder.append(word.substring(0, 1).toUpperCase())
				.append(word.substring(1).toLowerCase());
			if (i < words.length - 1) {
				stringBuilder.append(" ");
			}
		}
		return stringBuilder.toString();
	}
}
