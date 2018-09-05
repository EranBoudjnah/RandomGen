package com.mitteloupe.randomgenexample.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.mitteloupe.randomgenexample.R;
import com.mitteloupe.randomgenexample.data.flat.DivisionType;
import com.mitteloupe.randomgenexample.data.flat.Flat;
import com.mitteloupe.randomgenexample.data.flat.Room;

/**
 * Created by Eran Boudjnah on 26/08/2018.
 */
public class FlatView extends FrameLayout {
	private Flat mFlat;

	private Paint mPaint = new Paint();
	private RectF mBoundingWalls = new RectF();

	public FlatView(@NonNull Context context) {
		this(context, null);
	}

	public FlatView(@NonNull Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FlatView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		setWillNotDraw(false);
		setUpBackground();
	}

	private void setUpBackground() {
		Drawable drawable = getResources().getDrawable(android.R.drawable.dialog_holo_light_frame);
		drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY));
		setBackgroundDrawable(drawable);
	}

	public void setFlat(Flat pFlat) {
		mFlat = pFlat;

		invalidate();
	}

	@Override
	protected void onDraw(Canvas pCanvas) {
		super.onDraw(pCanvas);

		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setColor(0xFFFFFFFF);
		mPaint.setTextAlign(Paint.Align.CENTER);
		mPaint.setTextSize(24f);

		float flatWidth = getWidth() - 100;
		float flatHeight = getHeight() - 100;
		mBoundingWalls.set(50, 50, flatWidth + 50, flatHeight + 50);
		pCanvas.drawRect(mBoundingWalls, mPaint);

		drawWallOrRoomType(pCanvas, mFlat.getRooms(), mBoundingWalls);
	}

	private void drawWallOrRoomType(Canvas pCanvas, Room pRoom, RectF boundingWalls) {
		if (!isRoomDivided(pRoom)) {
			pCanvas.drawText(formatRoomType(pRoom.getRoomType().toString()), boundingWalls.centerX(), boundingWalls.centerY(), mPaint);
			return;
		}

		RectF innerWalls = new RectF();
		if (pRoom.getDivisionType() == DivisionType.HORIZONTAL) {
			float totalHeight = boundingWalls.height();
			innerWalls.left = boundingWalls.left;
			innerWalls.right = boundingWalls.right;
			innerWalls.top = boundingWalls.top;
			innerWalls.bottom = boundingWalls.top + totalHeight * pRoom.getDivisionRatio();
			drawWallOrRoomType(pCanvas, pRoom.getFirstRoom(), innerWalls);
			innerWalls.top = innerWalls.bottom;
			innerWalls.bottom = boundingWalls.bottom;
			drawWallOrRoomType(pCanvas, pRoom.getSecondRoomBranch(), innerWalls);
			pCanvas.drawLine(innerWalls.left, innerWalls.top, innerWalls.right, innerWalls.top, mPaint);

		} else {
			float totalWidth = boundingWalls.width();
			innerWalls.top = boundingWalls.top;
			innerWalls.bottom = boundingWalls.bottom;
			innerWalls.left = boundingWalls.left;
			innerWalls.right = boundingWalls.left + totalWidth * pRoom.getDivisionRatio();
			drawWallOrRoomType(pCanvas, pRoom.getFirstRoom(), innerWalls);
			innerWalls.left = innerWalls.right;
			innerWalls.right = boundingWalls.right;
			drawWallOrRoomType(pCanvas, pRoom.getSecondRoomBranch(), innerWalls);
			pCanvas.drawLine(innerWalls.left, innerWalls.top, innerWalls.left, innerWalls.bottom, mPaint);
		}
	}

	private boolean isRoomDivided(Room pRoom) {
		return pRoom.getFirstRoom() != null && pRoom.getSecondRoomBranch() != null;
	}

	private String formatRoomType(String pString) {
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
