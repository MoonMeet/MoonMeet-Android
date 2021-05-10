package org.mark.moonmeet.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.util.StateSet;

import androidx.core.view.ViewCompat;

import org.mark.moonmeet.R;
import org.mark.moonmeet.components.CombinedDrawable;
import org.mark.moonmeet.utils.AndroidUtilities;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

public class Theme {

	public static Drawable chat_composeShadowDrawable;
	public static Paint chat_composeBackgroundPaint = new Paint();

	public static Paint dividerPaint;
	public static Paint checkboxSquare_eraserPaint;
	public static Paint checkboxSquare_checkPaint;
	public static Paint checkboxSquare_backgroundPaint;

	public static final int ACTION_BAR_COLOR = 0xff527da3;
	public static final int ACTION_BAR_PHOTO_VIEWER_COLOR = 0x7f000000;
	public static final int ACTION_BAR_MEDIA_PICKER_COLOR = 0xff333333;
	public static final int ACTION_BAR_SUBTITLE_COLOR = 0xffd5e8f7;
	public static final int ACTION_BAR_SELECTOR_COLOR = 0xff406d94;

	public static final String key_dialogButton = "dialogButton";
	public static final String key_dialogIcon = "dialogIcon";
	public static final int ACTION_BAR_PICKER_SELECTOR_COLOR = 0xff3d3d3d;
	public static final int ACTION_BAR_WHITE_SELECTOR_COLOR = 0x40ffffff;
	public static final int ACTION_BAR_AUDIO_SELECTOR_COLOR = 0x2f000000;
	public static final int ACTION_BAR_MODE_SELECTOR_COLOR = 0x000000;

	public static final String key_progressCircle = "progressCircle";
	public static final String key_emptyListPlaceholder = "emptyListPlaceholder";
	public static String key_windowBackgroundWhiteRedText = "windowBackgroundWhiteRedText";

	private static HashMap<String, Integer> defaultColors = new HashMap<>();

	private static final Paint maskPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	public static Paint chat_statusPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


	public static Drawable moveUpDrawable;

	public static Drawable createBarSelectorDrawable(int color) {
		return createBarSelectorDrawable(color, true);
	}

	public static void setDrawableColor(Drawable drawable, int color) {
		if (drawable == null) {
			return;
		}
		if (drawable instanceof ShapeDrawable) {
			((ShapeDrawable) drawable).getPaint().setColor(color);
		} else {
			drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
		}
	}

	//color keys
	public static final String key_actionBarDefault = "actionBarDefault";
	public static final String key_actionBarDefaultSelector = "actionBarDefaultSelector";
	public static final String key_actionBarWhiteSelector = "actionBarWhiteSelector";
	public static final String key_actionBarDefaultIcon = "actionBarDefaultIcon";
	public static final String key_actionBarTipBackground = "actionBarTipBackground";
	public static final String key_actionBarActionModeDefault = "actionBarActionModeDefault";
	public static final String key_actionBarActionModeDefaultTop = "actionBarActionModeDefaultTop";
	public static final String key_actionBarActionModeDefaultIcon = "actionBarActionModeDefaultIcon";
	public static final String key_actionBarActionModeDefaultSelector = "actionBarActionModeDefaultSelector";
	public static final String key_actionBarDefaultTitle = "actionBarDefaultTitle";
	public static final String key_actionBarDefaultSubtitle = "actionBarDefaultSubtitle";
	public static final String key_actionBarDefaultSearch = "actionBarDefaultSearch";
	public static final String key_actionBarDefaultSearchPlaceholder = "actionBarDefaultSearchPlaceholder";
	public static final String key_actionBarDefaultSubmenuItem = "actionBarDefaultSubmenuItem";
	public static final String key_actionBarDefaultSubmenuItemIcon = "actionBarDefaultSubmenuItemIcon";
	public static final String key_actionBarDefaultSubmenuBackground = "actionBarDefaultSubmenuBackground";
	public static final String key_actionBarTabActiveText = "actionBarTabActiveText";
	public static final String key_actionBarTabUnactiveText = "actionBarTabUnactiveText";
	public static final String key_actionBarTabLine = "actionBarTabLine";
	public static final String key_actionBarTabSelector = "actionBarTabSelector";
	public static final String key_actionBarDefaultArchived = "actionBarDefaultArchived";
	public static final String key_actionBarDefaultArchivedSelector = "actionBarDefaultArchivedSelector";
	public static final String key_actionBarDefaultArchivedIcon = "actionBarDefaultArchivedIcon";
	public static final String key_actionBarDefaultArchivedTitle = "actionBarDefaultArchivedTitle";
	public static final String key_actionBarDefaultArchivedSearch = "actionBarDefaultArchivedSearch";
	public static final String key_actionBarDefaultArchivedSearchPlaceholder = "actionBarDefaultSearchArchivedPlaceholder";
	public static final String key_listSelector = "listSelectorSDK21";

	public static final String key_windowBackgroundWhite = "windowBackgroundWhite";
	public static final String key_windowBackgroundGray = "windowBackgroundGray";
	public static final String key_windowBackgroundWhiteBlackText = "windowBackgroundWhiteBlackText";
	public static final String key_windowBackgroundWhiteGrayText2 = "windowBackgroundWhiteGrayText2";

	public static final String key_groupcreate_hintText = "groupcreate_hintText";
	public static final String key_groupcreate_cursor = "groupcreate_cursor";
	public static final String key_groupcreate_sectionShadow = "groupcreate_sectionShadow";
	public static final String key_groupcreate_sectionText = "groupcreate_sectionText";
	public static final String key_groupcreate_spanText = "groupcreate_spanText";
	public static final String key_groupcreate_spanBackground = "groupcreate_spanBackground";
	public static final String key_groupcreate_spanDelete = "groupcreate_spanDelete";

	public static final String key_fastScrollActive = "fastScrollActive";
	public static final String key_fastScrollInactive = "fastScrollInactive";
	public static final String key_fastScrollText = "fastScrollText";

	public static final String key_profile_title = "profile_title";
	public static final String key_profile_actionIcon = "profile_actionIcon";
	public static final String key_profile_actionBackground = "profile_actionBackground";
	public static final String key_profile_actionPressedBackground = "profile_actionPressedBackground";

	public static final String key_player_actionBarTitle = "player_actionBarTitle";
	public static final String key_player_actionBar = "player_actionBar";
	public static final String key_player_actionBarSelector = "player_actionBarSelector";
	public static final String key_player_actionBarTop = "player_actionBarTop";
	public static final String key_player_actionBarSubtitle = "player_actionBarSubtitle";
	public static final String key_player_actionBarItems = "player_actionBarItems";

	public static final String key_avatar_subtitleInProfileBlue = "avatar_subtitleInProfileBlue";
	public static final String key_avatar_actionBarSelectorBlue = "avatar_actionBarSelectorBlue";
	public static final String key_avatar_actionBarIconBlue = "avatar_actionBarIconBlue";
	public static final String key_avatar_backgroundActionBarBlue = "avatar_backgroundActionBarBlue";
	public static final String key_profile_status = "profile_status";

	public static final String key_avatar_backgroundBlue = "avatar_backgroundBlue";

	public static final String key_divider = "divider";

	public static final String key_checkboxCheck = "checkboxCheck";

	public static final String key_dialogBackground = "dialogBackground";

	public static final String key_chat_attachPhotoBackground = "chat_attachPhotoBackground";

	public static final String key_checkbox = "checkbox";
	public static final String key_checkboxDisabled = "checkboxDisabled";

	public static final String key_dialogCheckboxSquareBackground = "dialogCheckboxSquareBackground";
	public static final String key_dialogCheckboxSquareCheck = "dialogCheckboxSquareCheck";
	public static final String key_dialogCheckboxSquareUnchecked = "dialogCheckboxSquareUnchecked";
	public static final String key_dialogCheckboxSquareDisabled = "dialogCheckboxSquareDisabled";

	public static final String key_checkboxSquareBackground = "checkboxSquareBackground";
	public static final String key_checkboxSquareCheck = "checkboxSquareCheck";
	public static final String key_checkboxSquareUnchecked = "checkboxSquareUnchecked";
	public static final String key_checkboxSquareDisabled = "checkboxSquareDisabled";

	public static final String key_dialogTextBlack = "dialogTextBlack";
	public static final String key_dialogTextLink = "dialogTextLink";

	public static final String key_windowBackgroundWhiteLinkText = "windowBackgroundWhiteLinkText";

	public static final String key_dialogTextBlue = "dialogTextBlue";
	public static final String key_dialogButtonSelector = "dialogButtonSelector";
	public static final String key_windowBackgroundWhiteValueText = "windowBackgroundWhiteValueText";

	public static final String key_dialogRadioBackground = "dialogRadioBackground";
	public static final String key_dialogRadioBackgroundChecked = "dialogRadioBackgroundChecked";

	public static final String key_radioBackground = "radioBackground";
	public static final String key_radioBackgroundChecked = "radioBackgroundChecked";

	public static final String key_dialogTextGray2 = "dialogTextGray2";
	public static final String key_dialogTextGray3 = "dialogTextGray3";
	public static final String key_dialogTextGray4 = "dialogTextGray4";


	static{
		defaultColors.put(key_dialogTextGray2, 0xff757575);
		defaultColors.put(key_dialogTextGray3, 0xff999999);
		defaultColors.put(key_dialogTextGray4, 0xffb3b3b3);
		defaultColors.put(key_dialogButton, 0xff4991cc);
		defaultColors.put(key_radioBackground, 0xffb3b3b3);
		defaultColors.put(key_radioBackgroundChecked, 0xff37a9f0);
		defaultColors.put(key_dialogRadioBackground, 0xffb3b3b3);
		defaultColors.put(key_dialogRadioBackgroundChecked, 0xff37a9f0);

		defaultColors.put(key_windowBackgroundWhiteValueText, 0xff3a95d5);
		defaultColors.put(key_dialogTextBlue, 0xff2f8cc9);

		defaultColors.put(key_windowBackgroundWhiteLinkText, 0xff2678b6);

		defaultColors.put(key_dialogTextBlack, 0xff222222);
		defaultColors.put(key_dialogTextLink, 0xff2678b6);

		defaultColors.put(key_checkboxSquareBackground, 0xff43a0df);
		defaultColors.put(key_checkboxSquareCheck, 0xffffffff);
		defaultColors.put(key_checkboxSquareUnchecked, 0xff737373);
		defaultColors.put(key_checkboxSquareDisabled, 0xffb0b0b0);

		defaultColors.put(key_dialogCheckboxSquareBackground, 0xff43a0df);
		defaultColors.put(key_dialogCheckboxSquareCheck, 0xffffffff);
		defaultColors.put(key_dialogCheckboxSquareUnchecked, 0xff737373);
		defaultColors.put(key_dialogCheckboxSquareDisabled, 0xffb0b0b0);

		defaultColors.put(key_chat_attachPhotoBackground, 0x0c000000);

		defaultColors.put(key_dialogBackground, 0xffffffff);
		defaultColors.put(key_dialogButtonSelector, 0x0f000000);
		defaultColors.put(key_checkbox, 0xff5ec245);
		defaultColors.put(key_checkboxCheck, 0xffffffff);
		defaultColors.put(key_checkboxDisabled, 0xffb0b9c2);

		defaultColors.put(key_profile_status, 0xffd7eafa);

		defaultColors.put(key_actionBarDefault, 0xff527da3);
		defaultColors.put(key_actionBarDefaultIcon, 0xffffffff);
		defaultColors.put(key_actionBarActionModeDefault, 0xffffffff);
		defaultColors.put(key_actionBarActionModeDefaultTop, 0x10000000);
		defaultColors.put(key_actionBarActionModeDefaultIcon, 0xff676a6f);
		defaultColors.put(key_actionBarDefaultTitle, 0xffffffff);
		defaultColors.put(key_actionBarDefaultSubtitle, 0xffd5e8f7);
		defaultColors.put(key_actionBarDefaultSelector, 0xff406d94);
		defaultColors.put(key_actionBarWhiteSelector, 0x1d000000);
		defaultColors.put(key_actionBarDefaultSearch, 0xffffffff);
		defaultColors.put(key_actionBarDefaultSearchPlaceholder, 0x88ffffff);
		defaultColors.put(key_actionBarDefaultSubmenuItem, 0xff222222);
		defaultColors.put(key_actionBarDefaultSubmenuItemIcon, 0xff676b70);
		defaultColors.put(key_actionBarDefaultSubmenuBackground, 0xffffffff);
		defaultColors.put(key_actionBarActionModeDefaultSelector, 0xffe2e2e2);
		defaultColors.put(key_actionBarTabActiveText, 0xffffffff);
		defaultColors.put(key_actionBarTabUnactiveText, 0xffd5e8f7);
		defaultColors.put(key_actionBarTabLine, 0xffffffff);
		defaultColors.put(key_actionBarTabSelector, 0xff406d94);

		// defaultColors.put(key_actionBarBrowser, 0xffffffff);
		defaultColors.put(key_groupcreate_hintText, 0xffa1aab3);
		defaultColors.put(key_groupcreate_cursor, 0xff52a3db);
		defaultColors.put(key_groupcreate_sectionShadow, 0xff000000);
		defaultColors.put(key_groupcreate_sectionText, 0xff7c8288);
		defaultColors.put(key_groupcreate_spanText, 0xff222222);
		defaultColors.put(key_groupcreate_spanBackground, 0xfff2f2f2);
		defaultColors.put(key_groupcreate_spanDelete, 0xffffffff);


		defaultColors.put(key_actionBarDefaultArchived, 0xff6f7a87);
		defaultColors.put(key_actionBarDefaultArchivedSelector, 0xff5e6772);
		defaultColors.put(key_actionBarDefaultArchivedIcon, 0xffffffff);
		defaultColors.put(key_actionBarDefaultArchivedTitle, 0xffffffff);
		defaultColors.put(key_actionBarDefaultArchivedSearch, 0xffffffff);
		defaultColors.put(key_actionBarDefaultArchivedSearchPlaceholder, 0x88ffffff);
		defaultColors.put(key_listSelector, 0x0f000000);

		defaultColors.put(key_windowBackgroundWhite, 0xffffffff);
		defaultColors.put(key_windowBackgroundWhiteBlackText, 0xff222222);
		defaultColors.put(key_windowBackgroundWhiteRedText, 0xffcd5a5a);
		defaultColors.put(key_windowBackgroundWhiteGrayText2, 0xff82868a);

		defaultColors.put(key_windowBackgroundGray, 0xfff0f0f0);
		defaultColors.put(key_emptyListPlaceholder, 0xff959595);
		defaultColors.put(key_progressCircle, 0xff1c93e3);


		defaultColors.put(key_actionBarDefaultIcon, 0xffffffff);
		defaultColors.put(key_actionBarActionModeDefaultIcon, 0xff676a6f);
		defaultColors.put(key_fastScrollActive, 0xff52a3db);
		defaultColors.put(key_fastScrollInactive, 0xffc9cdd1);
		defaultColors.put(key_fastScrollText, 0xffffffff)
		;
		defaultColors.put(key_profile_title, 0xffffffff);
		defaultColors.put(key_profile_actionIcon, 0xff81868a);
		defaultColors.put(key_profile_actionBackground, 0xffffffff);
		defaultColors.put(key_profile_actionPressedBackground, 0xfff2f2f2);

		defaultColors.put(key_player_actionBar, 0xffffffff);
		defaultColors.put(key_player_actionBarSelector, 0x0f000000);
		defaultColors.put(key_player_actionBarTitle, 0xff2f3438);
		defaultColors.put(key_player_actionBarTop, 0x99000000);
		defaultColors.put(key_player_actionBarSubtitle, 0xff8a8a8a);
		defaultColors.put(key_player_actionBarItems, 0xff8a8a8a);

		defaultColors.put(key_avatar_subtitleInProfileBlue, 0xffd7eafa);
		defaultColors.put(key_avatar_actionBarSelectorBlue, 0xff4981ad);
		defaultColors.put(key_avatar_actionBarIconBlue, 0xffffffff);
		defaultColors.put(key_avatar_backgroundActionBarBlue, 0xff598fba);

		defaultColors.put(key_divider, 0xffd9d9d9);

		defaultColors.put(key_avatar_backgroundBlue, 0xff549cdd);

	}

	public static void createDialogDrawable(Context context){
		Resources resources = context.getResources();
		moveUpDrawable = resources.getDrawable(R.drawable.preview_open);
	}
	public static Drawable createBarSelectorDrawable(int color, boolean masked) {
		if (Build.VERSION.SDK_INT >= 21) {
			Drawable maskDrawable = null;
			if (masked) {
				maskPaint.setColor(0xffffffff);
				maskDrawable = new Drawable() {
					@Override
					public void draw(Canvas canvas) {
						android.graphics.Rect bounds = getBounds();
						canvas.drawCircle(bounds.centerX(), bounds.centerY(),
								AndroidUtilities.dp(18), maskPaint);
					}

					@Override
					public void setAlpha(int alpha) {

					}

					@Override
					public void setColorFilter(ColorFilter colorFilter) {

					}

					@Override
					public int getOpacity() {
						return PixelFormat.UNKNOWN;
					}
				};
			}
			ColorStateList colorStateList = new ColorStateList(
					new int[][] {
							new int[] {}
					},
					new int[] {
							color
					});
			return new RippleDrawable(colorStateList, null, maskDrawable);
		} else {
			StateListDrawable stateListDrawable = new StateListDrawable();
			stateListDrawable.addState(new int[] {
					android.R.attr.state_pressed
			}, new ColorDrawable(color));
			stateListDrawable.addState(new int[] {
					android.R.attr.state_focused
			}, new ColorDrawable(color));
			stateListDrawable.addState(new int[] {
					android.R.attr.state_selected
			}, new ColorDrawable(color));
			stateListDrawable.addState(new int[] {
					android.R.attr.state_activated
			}, new ColorDrawable(color));
			stateListDrawable.addState(new int[] {}, new ColorDrawable(0x00000000));
			return stateListDrawable;
		}
	}

	public static int getColor(String key){
		return getDefaultColor(key);
	}

	public static int getDefaultColor(String key) {
		Integer value = defaultColors.get(key);
		return value;
	}

	public static Drawable getSelectorDrawable(boolean whiteBackground) {
		return getSelectorDrawable(getColor(key_listSelector), whiteBackground);
	}

	public static Drawable getSelectorDrawable(int color, boolean whiteBackground) {
		if (whiteBackground) {
			return getSelectorDrawable(color, key_windowBackgroundWhite);
		} else {
			return createSelectorDrawable(color, 2);
		}
	}
	public static Drawable getSelectorDrawable(int color, String backgroundColor) {
		if (backgroundColor != null) {
			if (Build.VERSION.SDK_INT >= 21) {
				Drawable maskDrawable = new ColorDrawable(0xffffffff);
				ColorStateList colorStateList = new ColorStateList(
						new int[][]{StateSet.WILD_CARD},
						new int[]{color}
				);
				return new RippleDrawable(colorStateList, new ColorDrawable(getColor(backgroundColor)), maskDrawable);
			} else {
				StateListDrawable stateListDrawable = new StateListDrawable();
				stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(color));
				stateListDrawable.addState(new int[]{android.R.attr.state_selected}, new ColorDrawable(color));
				stateListDrawable.addState(StateSet.WILD_CARD, new ColorDrawable(getColor(backgroundColor)));
				return stateListDrawable;
			}
		} else {
			return createSelectorDrawable(color, 2);
		}
	}

	public static Drawable createSelectorDrawable(int color) {
		return createSelectorDrawable(color, 1, -1);
	}

	public static Drawable createSelectorDrawable(int color, int maskType) {
		return createSelectorDrawable(color, maskType, -1);
	}
	public static Drawable createSelectorDrawable(final int color, final int maskType, final int radius) {
		Drawable drawable;
		if (Build.VERSION.SDK_INT >= 21) {
			Drawable maskDrawable = null;
			if ((maskType == 1 || maskType == 5) && Build.VERSION.SDK_INT >= 23) {
				maskDrawable = null;
			} else if (maskType == 1 || maskType == 3 || maskType == 4 || maskType == 5 || maskType == 6 || maskType == 7) {
				maskPaint.setColor(0xffffffff);
				maskDrawable = new Drawable() {

					RectF rect;

					@Override
					public void draw(Canvas canvas) {
						android.graphics.Rect bounds = getBounds();
						if (maskType == 7) {
							if (rect == null) {
								rect = new RectF();
							}
							rect.set(bounds);
							canvas.drawRoundRect(rect, AndroidUtilities.dp(6), AndroidUtilities.dp(6), maskPaint);
						} else {
							int rad;
							if (maskType == 1 || maskType == 6) {
								rad = AndroidUtilities.dp(20);
							} else if (maskType == 3) {
								rad = (Math.max(bounds.width(), bounds.height()) / 2);
							} else {
								rad = (int) Math.ceil(Math.sqrt((bounds.left - bounds.centerX()) * (bounds.left - bounds.centerX()) + (bounds.top - bounds.centerY()) * (bounds.top - bounds.centerY())));
							}
							canvas.drawCircle(bounds.centerX(), bounds.centerY(), rad, maskPaint);
						}
					}

					@Override
					public void setAlpha(int alpha) {

					}

					@Override
					public void setColorFilter(ColorFilter colorFilter) {

					}

					@Override
					public int getOpacity() {
						return PixelFormat.UNKNOWN;
					}
				};
			} else if (maskType == 2) {
				maskDrawable = new ColorDrawable(0xffffffff);
			}
			ColorStateList colorStateList = new ColorStateList(
					new int[][]{StateSet.WILD_CARD},
					new int[]{color}
			);
			RippleDrawable rippleDrawable = new RippleDrawable(colorStateList, null, maskDrawable);
			if (Build.VERSION.SDK_INT >= 23) {
				if (maskType == 1) {
					rippleDrawable.setRadius(radius <= 0 ? AndroidUtilities.dp(20) : radius);
				} else if (maskType == 5) {
					rippleDrawable.setRadius(RippleDrawable.RADIUS_AUTO);
				}
			}
			return rippleDrawable;
		} else {
			StateListDrawable stateListDrawable = new StateListDrawable();
			stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(color));
			stateListDrawable.addState(new int[]{android.R.attr.state_selected}, new ColorDrawable(color));
			stateListDrawable.addState(StateSet.WILD_CARD, new ColorDrawable(0x00000000));
			return stateListDrawable;
		}
	}
	public static void setCombinedDrawableColor(Drawable combinedDrawable, int color, boolean isIcon) {
		if (!(combinedDrawable instanceof CombinedDrawable)) {
			return;
		}
		Drawable drawable;
		if (isIcon) {
			drawable = ((CombinedDrawable) combinedDrawable).getIcon();
		} else {
			drawable = ((CombinedDrawable) combinedDrawable).getBackground();
		}
		if (drawable instanceof ColorDrawable) {
			((ColorDrawable) drawable).setColor(color);
		} else {
			drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
		}
	}
	public static Drawable createRoundRectDrawable(int rad, int defaultColor) {
		ShapeDrawable defaultDrawable = new ShapeDrawable(new RoundRectShape(new float[]{rad, rad, rad, rad, rad, rad, rad, rad}, null, null));
		defaultDrawable.getPaint().setColor(defaultColor);
		return defaultDrawable;
	}

	public static Drawable createRadSelectorDrawable(int color, final int topRad, final int bottomRad) {
		Drawable drawable;
		if (Build.VERSION.SDK_INT >= 21) {
			maskPaint.setColor(0xffffffff);
			Drawable maskDrawable = new Drawable() {

				private Path path = new Path();
				private RectF rect = new RectF();
				private float[] radii = new float[8];

				@Override
				public void draw(Canvas canvas) {
					radii[0] = radii[1] = radii[2] = radii[3] = AndroidUtilities.dp(topRad);
					radii[4] = radii[5] = radii[6] = radii[7] = AndroidUtilities.dp(bottomRad);
					rect.set(getBounds());
					path.addRoundRect(rect, radii, Path.Direction.CW);
					canvas.drawPath(path, maskPaint);
				}

				@Override
				public void setAlpha(int alpha) {

				}

				@Override
				public void setColorFilter(ColorFilter colorFilter) {

				}

				@Override
				public int getOpacity() {
					return PixelFormat.UNKNOWN;
				}
			};
			ColorStateList colorStateList = new ColorStateList(
					new int[][]{StateSet.WILD_CARD},
					new int[]{color}
			);
			return new RippleDrawable(colorStateList, null, maskDrawable);
		} else {
			StateListDrawable stateListDrawable = new StateListDrawable();
			stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(color));
			stateListDrawable.addState(new int[]{android.R.attr.state_selected}, new ColorDrawable(color));
			stateListDrawable.addState(StateSet.WILD_CARD, new ColorDrawable(0x00000000));
			return stateListDrawable;
		}
	}
	public static Drawable createSimpleSelectorDrawable(Context context, int resource, int defaultColor, int pressedColor) {
		Resources resources = context.getResources();
		Drawable defaultDrawable = resources.getDrawable(resource).mutate();
		if (defaultColor != 0) {
			defaultDrawable.setColorFilter(new PorterDuffColorFilter(defaultColor, PorterDuff.Mode.MULTIPLY));
		}
		Drawable pressedDrawable = resources.getDrawable(resource).mutate();
		if (pressedColor != 0) {
			pressedDrawable.setColorFilter(new PorterDuffColorFilter(pressedColor, PorterDuff.Mode.MULTIPLY));
		}
		StateListDrawable stateListDrawable = new StateListDrawable() {
			@Override
			public boolean selectDrawable(int index) {
				if (Build.VERSION.SDK_INT < 21) {
					Drawable drawable = Theme.getStateDrawable(this, index);
					ColorFilter colorFilter = null;
					if (drawable instanceof BitmapDrawable) {
						colorFilter = ((BitmapDrawable) drawable).getPaint().getColorFilter();
					} else if (drawable instanceof NinePatchDrawable) {
						colorFilter = ((NinePatchDrawable) drawable).getPaint().getColorFilter();
					}
					boolean result = super.selectDrawable(index);
					if (colorFilter != null) {
						drawable.setColorFilter(colorFilter);
					}
					return result;
				}
				return super.selectDrawable(index);
			}
		};
		stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
		stateListDrawable.addState(new int[]{android.R.attr.state_selected}, pressedDrawable);
		stateListDrawable.addState(StateSet.WILD_CARD, defaultDrawable);
		return stateListDrawable;
	}
	private static Method StateListDrawable_getStateDrawableMethod;
	private static Field BitmapDrawable_mColorFilter;

	@SuppressLint("PrivateApi")
	private static Drawable getStateDrawable(Drawable drawable, int index) {
		if (Build.VERSION.SDK_INT >= 29 && drawable instanceof StateListDrawable) {
			//return ((StateListDrawable) drawable).getStateDrawable(index);
			return null;
		} else {
			if (StateListDrawable_getStateDrawableMethod == null) {
				try {
					StateListDrawable_getStateDrawableMethod = StateListDrawable.class.getDeclaredMethod("getStateDrawable", int.class);
				} catch (Throwable ignore) {

				}
			}
			if (StateListDrawable_getStateDrawableMethod == null) {
				return null;
			}
			try {
				return (Drawable) StateListDrawable_getStateDrawableMethod.invoke(drawable, index);
			} catch (Exception ignore) {

			}
			return null;
		}
	}
	public static void setSelectorDrawableColor(Drawable drawable, int color, boolean selected) {
		if (drawable instanceof StateListDrawable) {
			try {
				Drawable state;
				if (selected) {
					state = getStateDrawable(drawable, 0);
					if (state instanceof ShapeDrawable) {
						((ShapeDrawable) state).getPaint().setColor(color);
					} else {
						state.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
					}
					state = getStateDrawable(drawable, 1);
				} else {
					state = getStateDrawable(drawable, 2);
				}
				if (state instanceof ShapeDrawable) {
					((ShapeDrawable) state).getPaint().setColor(color);
				} else {
					state.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
				}
			} catch (Throwable ignore) {

			}
		} else if (Build.VERSION.SDK_INT >= 21 && drawable instanceof RippleDrawable) {
			RippleDrawable rippleDrawable = (RippleDrawable) drawable;
			if (selected) {
				rippleDrawable.setColor(new ColorStateList(
						new int[][]{StateSet.WILD_CARD},
						new int[]{color}
				));
			} else {
				if (rippleDrawable.getNumberOfLayers() > 0) {
					Drawable drawable1 = rippleDrawable.getDrawable(0);
					if (drawable1 instanceof ShapeDrawable) {
						((ShapeDrawable) drawable1).getPaint().setColor(color);
					} else {
						drawable1.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
					}
				}
			}
		}
	}

	public static Drawable createSimpleSelectorRoundRectDrawable(int rad, int defaultColor, int pressedColor) {
		return createSimpleSelectorRoundRectDrawable(rad, defaultColor, pressedColor, pressedColor);
	}

	public static Drawable getRoundRectSelectorDrawable(int color) {
		if (Build.VERSION.SDK_INT >= 21) {
			return new RippleDrawable(new ColorStateList(new int[][]{StateSet.WILD_CARD}, new int[]{419430400 | (16777215 & color)}), null, createRoundRectDrawable(AndroidUtilities.dp(3.0f), -1));
		}
		StateListDrawable stateListDrawable = new StateListDrawable();
		stateListDrawable.addState(new int[]{16842919}, createRoundRectDrawable(AndroidUtilities.dp(3.0f), (color & ViewCompat.MEASURED_SIZE_MASK) | 419430400));
		stateListDrawable.addState(new int[]{16842913}, createRoundRectDrawable(AndroidUtilities.dp(3.0f), 419430400 | (16777215 & color)));
		stateListDrawable.addState(StateSet.WILD_CARD, new ColorDrawable(0));
		return stateListDrawable;
	}


	public static Drawable createSimpleSelectorRoundRectDrawable(int rad, int defaultColor, int pressedColor, int maskColor) {
		ShapeDrawable defaultDrawable = new ShapeDrawable(new RoundRectShape(new float[]{rad, rad, rad, rad, rad, rad, rad, rad}, null, null));
		defaultDrawable.getPaint().setColor(defaultColor);
		ShapeDrawable pressedDrawable = new ShapeDrawable(new RoundRectShape(new float[]{rad, rad, rad, rad, rad, rad, rad, rad}, null, null));
		pressedDrawable.getPaint().setColor(maskColor);
		if (Build.VERSION.SDK_INT >= 21) {
			ColorStateList colorStateList = new ColorStateList(
					new int[][]{StateSet.WILD_CARD},
					new int[]{pressedColor}
			);
			return new RippleDrawable(colorStateList, defaultDrawable, pressedDrawable);
		} else {
			StateListDrawable stateListDrawable = new StateListDrawable();
			stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
			stateListDrawable.addState(new int[]{android.R.attr.state_selected}, pressedDrawable);
			stateListDrawable.addState(StateSet.WILD_CARD, defaultDrawable);
			return stateListDrawable;
		}
	}
	public static Drawable createSimpleSelectorCircleDrawable(int size, int defaultColor, int pressedColor) {
		OvalShape ovalShape = new OvalShape();
		ovalShape.resize(size, size);
		ShapeDrawable defaultDrawable = new ShapeDrawable(ovalShape);
		defaultDrawable.getPaint().setColor(defaultColor);
		ShapeDrawable pressedDrawable = new ShapeDrawable(ovalShape);
		if (Build.VERSION.SDK_INT >= 21) {
			pressedDrawable.getPaint().setColor(0xffffffff);
			ColorStateList colorStateList = new ColorStateList(
					new int[][]{StateSet.WILD_CARD},
					new int[]{pressedColor}
			);
			return new RippleDrawable(colorStateList, defaultDrawable, pressedDrawable);
		} else {
			pressedDrawable.getPaint().setColor(pressedColor);
			StateListDrawable stateListDrawable = new StateListDrawable();
			stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
			stateListDrawable.addState(new int[]{android.R.attr.state_focused}, pressedDrawable);
			stateListDrawable.addState(StateSet.WILD_CARD, defaultDrawable);
			return stateListDrawable;
		}
	}

	public static void createCommonResources(Context context){
		if(dividerPaint == null){
			dividerPaint = new Paint();
			dividerPaint.setStrokeWidth(1);
		}
		checkboxSquare_checkPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		checkboxSquare_checkPaint.setStyle(Paint.Style.STROKE);
		checkboxSquare_checkPaint.setStrokeWidth(AndroidUtilities.dp(2));
		checkboxSquare_checkPaint.setStrokeCap(Paint.Cap.ROUND);
		checkboxSquare_eraserPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		checkboxSquare_eraserPaint.setColor(0);
		checkboxSquare_eraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
		checkboxSquare_backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	}

	public static void applyCommonTheme() {
		if (dividerPaint == null) {
			return;
		}
		dividerPaint.setColor(getColor(key_divider));
	}
}