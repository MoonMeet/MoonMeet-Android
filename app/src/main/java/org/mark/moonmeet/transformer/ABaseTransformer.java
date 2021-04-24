package org.mark.moonmeet.transformer;

import androidx.viewpager.widget.ViewPager.PageTransformer;
import android.view.View;

public abstract class ABaseTransformer implements PageTransformer {

	
	protected abstract void onTransform(View page, float position);

	
	@Override
	public void transformPage(View page, float position) {
		float clampedPosition = clampPosition(position);

		onPreTransform(page, clampedPosition);
		onTransform(page, clampedPosition);
		onPostTransform(page, clampedPosition);
	}

	
	private float clampPosition(float position) {
		if (position < -1f) {
			return -1f;
		} else if (position > 1f) {
			return 1f;
		}
		return position;
	}

	
	protected boolean hideOffscreenPages() {
		return true;
	}

	
	protected boolean isPagingEnabled() {
		return false;
	}

	
	protected void onPreTransform(View page, float position) {
		final float width = page.getWidth();

		page.setRotationX(0);
		page.setRotationY(0);
		page.setRotation(0);
		page.setScaleX(1);
		page.setScaleY(1);
		page.setPivotX(0);
		page.setPivotY(0);
		page.setTranslationY(0);
		page.setTranslationX(isPagingEnabled() ? 0f : -width * position);

		if (hideOffscreenPages()) {
			page.setAlpha(position <= -1f || position >= 1f ? 0f : 1f);
			page.setEnabled(false);
		} else {
			page.setEnabled(true);
			page.setAlpha(1f);
		}
	}

	
	protected void onPostTransform(View page, float position) {
	}

	
	protected static final float min(float val, float min) {
		return val < min ? min : val;
	}

}
