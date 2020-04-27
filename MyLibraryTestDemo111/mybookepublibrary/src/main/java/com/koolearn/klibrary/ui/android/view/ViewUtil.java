package com.koolearn.klibrary.ui.android.view;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

public abstract class ViewUtil {
	public static void setColorLevel(Paint paint, Integer level) {
		if (level != null) {
			paint.setColorFilter(new PorterDuffColorFilter(
				Color.rgb(level, level, level), PorterDuff.Mode.MULTIPLY
			));
		} else {
			paint.setColorFilter(null);
		}
	}
}