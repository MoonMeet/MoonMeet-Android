package org.mark.moonmeet.components;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import org.mark.moonmeet.utils.AndroidUtilities;

public class LineProgressView extends View {

    private static DecelerateInterpolator decelerateInterpolator;
    private static Paint progressPaint;
    private float animatedAlphaValue = 1.0f;
    private float animatedProgressValue;
    private float animationProgressStart;
    private int backColor;
    private float currentProgress;
    private long currentProgressTime;
    private long lastUpdateTime;
    private int progressColor;
    private RectF rect = new RectF();

    public LineProgressView(Context context) {
        super(context);
        if (decelerateInterpolator == null) {
            decelerateInterpolator = new DecelerateInterpolator();
            Paint paint = new Paint(1);
            progressPaint = paint;
            paint.setStrokeCap(Paint.Cap.ROUND);
            progressPaint.setStrokeWidth((float) AndroidUtilities.dp(2.0f));
        }
    }

    private void updateAnimation() {
        long newTime = System.currentTimeMillis();
        long dt = newTime - this.lastUpdateTime;
        this.lastUpdateTime = newTime;
        float f = this.animatedProgressValue;
        if (f != 1.0f) {
            float f2 = this.currentProgress;
            if (f != f2) {
                float f3 = this.animationProgressStart;
                float progressDiff = f2 - f3;
                if (progressDiff > 0.0f) {
                    long j = this.currentProgressTime + dt;
                    this.currentProgressTime = j;
                    if (j >= 300) {
                        this.animatedProgressValue = f2;
                        this.animationProgressStart = f2;
                        this.currentProgressTime = 0;
                    } else {
                        this.animatedProgressValue = f3 + (decelerateInterpolator.getInterpolation(((float) j) / 300.0f) * progressDiff);
                    }
                }
                invalidate();
            }
        }
        float f4 = this.animatedProgressValue;
        if (f4 >= 1.0f && f4 == 1.0f) {
            float f5 = this.animatedAlphaValue;
            if (f5 != 0.0f) {
                float f6 = f5 - (((float) dt) / 200.0f);
                this.animatedAlphaValue = f6;
                if (f6 <= 0.0f) {
                    this.animatedAlphaValue = 0.0f;
                }
                invalidate();
            }
        }
    }

    public void setProgressColor(int color) {
        this.progressColor = color;
    }

    public void setBackColor(int color) {
        this.backColor = color;
    }

    public void setProgress(float value, boolean animated) {
        if (!animated) {
            this.animatedProgressValue = value;
            this.animationProgressStart = value;
        } else {
            this.animationProgressStart = this.animatedProgressValue;
        }
        if (value != 1.0f) {
            this.animatedAlphaValue = 1.0f;
        }
        this.currentProgress = value;
        this.currentProgressTime = 0;
        this.lastUpdateTime = System.currentTimeMillis();
        invalidate();
    }

    public float getCurrentProgress() {
        return this.currentProgress;
    }

    public void onDraw(Canvas canvas) {
        int i = this.backColor;
        if (!(i == 0 || this.animatedProgressValue == 1.0f)) {
            progressPaint.setColor(i);
            progressPaint.setAlpha((int) (this.animatedAlphaValue * 255.0f));
            int width = (int) (((float) getWidth()) * this.animatedProgressValue);
            this.rect.set(0.0f, 0.0f, (float) getWidth(), (float) getHeight());
            canvas.drawRoundRect(this.rect, (float) (getHeight() / 2), (float) (getHeight() / 2), progressPaint);
        }
        progressPaint.setColor(this.progressColor);
        progressPaint.setAlpha((int) (this.animatedAlphaValue * 255.0f));
        this.rect.set(0.0f, 0.0f, ((float) getWidth()) * this.animatedProgressValue, (float) getHeight());
        canvas.drawRoundRect(this.rect, (float) (getHeight() / 2), (float) (getHeight() / 2), progressPaint);
        updateAnimation();
    }
}

