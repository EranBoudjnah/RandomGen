package com.mitteloupe.randomgenexample.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mitteloupe.randomgenexample.R;
import com.mitteloupe.randomgenexample.data.planet.Material;
import com.mitteloupe.randomgenexample.data.planet.Planet;
import com.mitteloupe.randomgenexample.data.planet.PlanetarySystem;

import java.util.List;

/**
 * Created by Eran Boudjnah on 19/08/2018.
 */
public class PlanetarySystemView extends FrameLayout {
	private static final long ANIMATION_DELAY_MILLIS = 40;
	private static final long PLANET_DATA_DELAY_MILLIS = 3000;

	private static final float ASPECT_RATIO = 0.7f;
	private static final float RING_ASPECT_RATIO = 0.5f;
	private static final float RING_TO_STAR_RATIO = 1.4f;

	private PlanetarySystem mPlanetarySystem;
	private PlanetAnimation[] mPlanetAnimations = new PlanetAnimation[0];
	private int mPlanetsCount;

	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

	private RectF mVisualRect = new RectF();
	private float mVisualSize;
	private PointF mVisualCenter = new PointF();
	private float mOrbitRingSpacing;

	private PointF mPlanetCenter = new PointF();
	private float mPlanetSize;

	private RectF mPlanetRingRect = new RectF();

	private View mRenderFrame;
	private TextView starAgeTextView;
	private TextView starDiameterTextView;
	private TextView starMassTextView;
	private TextView starPlanetsCountTextView;

	private TextView starPlanetNameTextView;
	private TextView starPlanetDiameterTextView;
	private TextView starPlanetMassTextView;
	private TextView starPlanetOrbitalPeriodTextView;
	private TextView starRotationPeriodTextView;
	private TextView starMoonsTextView;
	private TextView starRingsTextView;
	private TextView starAtmosphereTextView;

	private TimeHandler mAnimationHandler;
	private TimeHandler mPlanetHandler;

	private int mPlanetDataIndex;

	public PlanetarySystemView(@NonNull Context context) {
		this(context, null);
	}

	public PlanetarySystemView(@NonNull Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public PlanetarySystemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		inflate(context, R.layout.view_planetary_system, this);

		setWillNotDraw(false);
		setUpBackground();

		mRenderFrame = findViewById(R.id.render_frame);

		initStarTextViews();

		initPlanetTextViews();

		initTimers();
	}

	private void setUpBackground() {
		Drawable drawable = getResources().getDrawable(android.R.drawable.dialog_holo_light_frame);
		drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY));
		setBackgroundDrawable(drawable);
	}

	private void initTimers() {
		mAnimationHandler = new TimeHandler(ANIMATION_DELAY_MILLIS, new Runnable() {
			@Override
			public void run() {
				invalidate();
			}
		});
		mAnimationHandler.start();

		mPlanetHandler = new TimeHandler(PLANET_DATA_DELAY_MILLIS, new Runnable() {
			@Override
			public void run() {
				mPlanetDataIndex = (mPlanetDataIndex + 1) % mPlanetarySystem.getPlanets().length;
				populatePlanetTextViews(mPlanetarySystem.getPlanets()[mPlanetDataIndex], mPlanetDataIndex + 1);
			}
		});
	}

	private void initStarTextViews() {
		starAgeTextView = findViewById(R.id.text_star_age_value);
		starDiameterTextView = findViewById(R.id.text_star_diameter_value);
		starMassTextView = findViewById(R.id.text_star_mass_value);
		starPlanetsCountTextView = findViewById(R.id.text_planets_count_value);
	}

	private void initPlanetTextViews() {
		starPlanetNameTextView = findViewById(R.id.text_planet_name_value);
		starPlanetDiameterTextView = findViewById(R.id.text_planet_diameter_value);
		starPlanetMassTextView = findViewById(R.id.text_planet_mass_value);
		starPlanetOrbitalPeriodTextView = findViewById(R.id.text_planet_orbital_period_value);
		starRotationPeriodTextView = findViewById(R.id.text_planet_rotation_period_value);
		starMoonsTextView = findViewById(R.id.text_planet_moons_value);
		starRingsTextView = findViewById(R.id.text_planet_rings_value);
		starAtmosphereTextView = findViewById(R.id.text_planet_atmosphere_value);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		mVisualRect.set(mRenderFrame.getLeft(), mRenderFrame.getTop(), mRenderFrame.getRight(), mRenderFrame.getBottom());

		float widthX = mVisualRect.right - mVisualRect.left;
		float heightX = mVisualRect.bottom - mVisualRect.top;
		mVisualSize = Math.max(widthX, heightX);
		mVisualCenter.set(
			(mVisualRect.left + mVisualRect.right) / 2f,
			(mVisualRect.top + mVisualRect.bottom) / 2f
		);
	}

	public void setPlanetarySystem(PlanetarySystem pPlanetarySystem) {
		mPlanetarySystem = pPlanetarySystem;
		Planet[] planets = mPlanetarySystem.getPlanets();
		mPlanetsCount = planets.length;
		mOrbitRingSpacing = 0.35f / (float)mPlanetsCount;

		setPlanetAnimations(planets);

		populateStarTextViews(pPlanetarySystem);

		mPlanetHandler.stop();

		if (pPlanetarySystem.getPlanets().length != 0) {
			showPlanetTextViews();
			mPlanetDataIndex = -1;
			mPlanetHandler.start();

		} else {
			hidePlanetTextViews();
		}

		invalidate();
	}

	private void setPlanetAnimations(Planet[] pPlanets) {
		mPlanetAnimations = new PlanetAnimation[mPlanetsCount];

		for (int i = 0; i < mPlanetsCount; ++i) {
			mPlanetAnimations[i] = getPlanetAnimation(pPlanets[i]);
		}
	}

	@NonNull
	private PlanetAnimation getPlanetAnimation(Planet pPlanet) {
		PlanetAnimation planetAnimation = new PlanetAnimation();
		planetAnimation.mAngle = (float)Math.random() * 360f;
		planetAnimation.mVelocity = 25f / pPlanet.getOrbitalPeriodYears();

		// We need some fixed rule to determine the direction of rotation. This will do for a demo.
		if (pPlanet.getMoons() % 2 == 0) planetAnimation.mVelocity = -planetAnimation.mVelocity;
		return planetAnimation;
	}

	private void populateStarTextViews(PlanetarySystem pPlanetarySystem) {
		Resources resources = getResources();

		starAgeTextView.setText(resources.getString(R.string.star_age_value, pPlanetarySystem.getStarAgeBillionYears()));
		starDiameterTextView.setText(resources.getString(R.string.star_diameter_value, pPlanetarySystem.getStarDiameterSunRadii()));
		starMassTextView.setText(resources.getString(R.string.star_mass_value, pPlanetarySystem.getStarSolarMass()));
		starPlanetsCountTextView.setText(String.valueOf(pPlanetarySystem.getPlanets().length));
	}

	private void hidePlanetTextViews() {
		starPlanetNameTextView.setVisibility(INVISIBLE);
		starPlanetDiameterTextView.setVisibility(INVISIBLE);
		starPlanetMassTextView.setVisibility(INVISIBLE);
		starPlanetOrbitalPeriodTextView.setVisibility(INVISIBLE);
		starRotationPeriodTextView.setVisibility(INVISIBLE);
		starMoonsTextView.setVisibility(INVISIBLE);
		starRingsTextView.setVisibility(INVISIBLE);
		starAtmosphereTextView.setVisibility(INVISIBLE);
	}

	private void showPlanetTextViews() {
		starPlanetNameTextView.setVisibility(VISIBLE);
		starPlanetDiameterTextView.setVisibility(VISIBLE);
		starPlanetMassTextView.setVisibility(VISIBLE);
		starPlanetOrbitalPeriodTextView.setVisibility(VISIBLE);
		starRotationPeriodTextView.setVisibility(VISIBLE);
		starMoonsTextView.setVisibility(VISIBLE);
		starRingsTextView.setVisibility(VISIBLE);
		starAtmosphereTextView.setVisibility(VISIBLE);
	}

	private void populatePlanetTextViews(Planet pPlanet, int pPosition) {
		Resources resources = getResources();

		starPlanetNameTextView.setText(resources.getString(R.string.planet_name, pPosition));
		starPlanetDiameterTextView.setText(resources.getString(R.string.planet_diameter_value, pPlanet.getDiameterEarthRatio()));
		starPlanetMassTextView.setText(resources.getString(R.string.planet_mass_value, pPlanet.getSolarMass()));
		starPlanetOrbitalPeriodTextView.setText(resources.getString(R.string.planet_orbital_period_value, pPlanet.getOrbitalPeriodYears()));
		starRotationPeriodTextView.setText(resources.getString(R.string.planet_rotation_period_value, pPlanet.getRotationPeriodDays()));
		starMoonsTextView.setText(String.valueOf(pPlanet.getMoons()));
		starRingsTextView.setText(pPlanet.hasRings() ? resources.getString(R.string.planet_has_rings) : resources.getString(R.string.planet_no_rings));
		starAtmosphereTextView.setText(Html.fromHtml(getMaterialsFormatted(pPlanet.getAtmosphere())));
	}

	private String getMaterialsFormatted(List<Material> pMaterials) {
		if (pMaterials.isEmpty()) {
			return "N/A";
		}

		StringBuilder stringBuilder = new StringBuilder();
		for (Material material : pMaterials) {
			stringBuilder
				.append(getMaterialFormatted(material))
				.append("<br/>");
		}
		return stringBuilder.toString();
	}

	private String getMaterialFormatted(Material material) {
		StringBuilder stringBuilder = new StringBuilder();
		for (Pair<String, Integer> elementCompound : material.getCompound()) {
			stringBuilder
				.append(elementCompound.first);
			if (elementCompound.second != 1) {
				stringBuilder
					.append("<small><sub>")
					.append(elementCompound.second)
					.append("</sub></small>");
			}
		}
		return stringBuilder.toString();
	}

	@Override
	protected void onDraw(Canvas pCanvas) {
		super.onDraw(pCanvas);

		if (mPlanetarySystem == null) return;

		drawOrbitRings(pCanvas);

		drawStar(pCanvas);

		advanceAllPlanetAnimations();

		drawPlanets(pCanvas);
	}

	private void drawStar(Canvas pCanvas) {
		drawCircle(pCanvas, mVisualCenter, (float)mPlanetarySystem.getStarDiameterSunRadii() / 10f, 0xFFFFFFFF);
	}

	private void drawPlanets(Canvas pCanvas) {
		for (int i = 0; i < mPlanetsCount; ++i) {
			drawPlanetAtIndex(pCanvas, i);
		}
	}

	private void drawOrbitRings(Canvas pCanvas) {
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(0f);
		mPaint.setColor(0x80FFFFFF);

		for (int i = 0; i < mPlanetsCount; ++i) {
			drawOrbitRing(pCanvas, i);
		}
	}

	private void drawOrbitRing(Canvas pCanvas, int pPosition) {
		float relativeRadius = 0.1f + mOrbitRingSpacing * pPosition;
		float orbitRingWidth = mVisualSize * relativeRadius;
		float orbitRingHeight = orbitRingWidth * ASPECT_RATIO;
		mPlanetRingRect.set(
			mVisualCenter.x - orbitRingWidth, mVisualCenter.y - orbitRingHeight,
			mVisualCenter.x + orbitRingWidth, mVisualCenter.y + orbitRingHeight
		);
		pCanvas.drawArc(mPlanetRingRect, 0, 360, false, mPaint);
	}

	private void drawPlanetAtIndex(Canvas canvas, int pIndex) {
		updatePlanetCenter(pIndex);

		Planet planet = mPlanetarySystem.getPlanets()[pIndex];

		mPlanetSize = planet.getDiameterEarthRatio() * 1.5f;

		drawCircle(canvas, mPlanetCenter, mPlanetSize, pIndex == mPlanetDataIndex ? 0xFFFFFFFF : 0xFFA0B0FF);

		if (planet.hasRings()) {
			drawPlanetRing(canvas);
		}
	}

	private void drawCircle(Canvas canvas, PointF mCenter, float pSize, int pColor) {
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setColor(pColor);

		canvas.drawCircle(mCenter.x, mCenter.y, pSize, mPaint);
	}

	private void updatePlanetCenter(int pPosition) {
		PlanetAnimation planetAnimation = mPlanetAnimations[pPosition];
		float relativeRadius = 0.1f + mOrbitRingSpacing * pPosition;
		float actualRadius = mVisualSize * relativeRadius;
		mPlanetCenter.set(
			(float)Math.sin(planetAnimation.mAngle / 180f * 3.14f) * actualRadius,
			(float)-Math.cos(planetAnimation.mAngle / 180f * 3.14f) * actualRadius * ASPECT_RATIO
		);
		mPlanetCenter.offset(mVisualCenter.x, mVisualCenter.y);
	}

	private void advanceAllPlanetAnimations() {
		for (int i = 0; i < mPlanetsCount; ++i) {
			advancePlanetAnimation(i);
		}
	}

	private void advancePlanetAnimation(int pPosition) {
		PlanetAnimation planetAnimation = mPlanetAnimations[pPosition];
		planetAnimation.mAngle += planetAnimation.mVelocity;
		while (planetAnimation.mAngle >= 360) planetAnimation.mAngle -= 360;
		while (planetAnimation.mAngle < 0) planetAnimation.mAngle += 360;
	}

	private void drawPlanetRing(Canvas canvas) {
		float ringWidth = mPlanetSize * RING_TO_STAR_RATIO;
		float ringHeight = ringWidth * RING_ASPECT_RATIO;

		mPlanetRingRect.set(
			mPlanetCenter.x - ringWidth, mPlanetCenter.y - ringHeight,
			mPlanetCenter.x + ringWidth, mPlanetCenter.y + ringHeight
		);

		mPaint.setStrokeWidth(mPlanetSize * 0.25f);
		mPaint.setStyle(Paint.Style.STROKE);

		canvas.drawArc(mPlanetRingRect, 0, 360, false, mPaint);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();

		mAnimationHandler.start();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();

		mAnimationHandler.stop();
	}

	private static class TimeHandler extends Handler {
		private final Runnable mRunnable;
		private final long mDelayMilliseconds;

		private Message mMessage;

		TimeHandler(long pDelayMilliseconds, Runnable pRunnable) {
			mDelayMilliseconds = pDelayMilliseconds;
			mRunnable = pRunnable;
		}

		@Override
		public void handleMessage(Message msg) {
			mMessage = Message.obtain();
			sendMessageDelayed(mMessage, mDelayMilliseconds);
			mRunnable.run();
		}

		void start() {
			if (mMessage != null) return;

			mMessage = Message.obtain();
			sendMessage(mMessage);
		}

		void stop() {
			if (mMessage == null) return;

			removeMessages(mMessage.what);
			mMessage = null;
		}
	}

	private class PlanetAnimation {
		float mVelocity;
		float mAngle;
	}
}
