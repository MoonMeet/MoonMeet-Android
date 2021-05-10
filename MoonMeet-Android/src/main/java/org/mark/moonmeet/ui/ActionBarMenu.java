package org.mark.moonmeet.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import org.mark.moonmeet.components.LayoutHelper;
import org.mark.moonmeet.utils.AndroidUtilities;

public class ActionBarMenu extends LinearLayout {
	
	protected ActionBar parentActionBar;
	protected boolean isActionMode;
	
	public ActionBarMenu(Context context, ActionBar layer) {
		super(context);
		setOrientation(LinearLayout.HORIZONTAL);
		parentActionBar = layer;
	}
	
	public ActionBarMenu(Context context) {
		super(context);
	}
	
	protected void updateItemsBackgroundColor() {
		int count = getChildCount();
		for (int a = 0; a < count; a++) {
			View view = getChildAt(a);
			if (view instanceof ActionBarMenuItem) {
				view.setBackgroundDrawable(Theme.createSelectorDrawable(isActionMode ? parentActionBar.itemsActionModeBackgroundColor : parentActionBar.itemsBackgroundColor));
			}
		}
	}
	
	protected void updateItemsColor() {
		int count = getChildCount();
		for (int a = 0; a < count; a++) {
			View view = getChildAt(a);
			if (view instanceof ActionBarMenuItem) {
				((ActionBarMenuItem) view).setIconColor(isActionMode ? parentActionBar.itemsActionModeColor : parentActionBar.itemsColor);
			}
		}
	}
	
	public View addItemResource(int id, int resourceId) {
		LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = li.inflate(resourceId, null);
		view.setTag(id);
		addView(view);
		LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
		layoutParams.height = LayoutHelper.MATCH_PARENT;
		view.setBackgroundDrawable(Theme.createBarSelectorDrawable(parentActionBar.itemsBackgroundColor));
		view.setLayoutParams(layoutParams);
		view.setOnClickListener(view1 -> onItemClick((Integer) view1.getTag()));
		return view;
	}
	
	public ActionBarMenuItem addItem(int id, Drawable drawable) {
		return addItem(id, 0, null, isActionMode ? parentActionBar.itemsActionModeBackgroundColor : parentActionBar.itemsBackgroundColor, drawable, AndroidUtilities.dp(48), null);
	}
	
	public ActionBarMenuItem addItem(int id, int icon) {
		return addItem(id, icon, isActionMode ? parentActionBar.itemsActionModeBackgroundColor : parentActionBar.itemsBackgroundColor);
	}
	
	public ActionBarMenuItem addItem(int id, CharSequence text) {
		return addItem(id, 0, text, isActionMode ? parentActionBar.itemsActionModeBackgroundColor : parentActionBar.itemsBackgroundColor, null, 0, text);
	}
	
	public ActionBarMenuItem addItem(int id, int icon, int backgroundColor) {
		return addItem(id, icon, null, backgroundColor, null, AndroidUtilities.dp(48), null);
	}
	
	public ActionBarMenuItem addItemWithWidth(int id, int icon, int width) {
		return addItem(id, icon, null, isActionMode ? parentActionBar.itemsActionModeBackgroundColor : parentActionBar.itemsBackgroundColor, null, width, null);
	}
	
	public ActionBarMenuItem addItemWithWidth(int id, int icon, int width, CharSequence title) {
		return addItem(id, icon, null, isActionMode ? parentActionBar.itemsActionModeBackgroundColor : parentActionBar.itemsBackgroundColor, null, width, title);
	}
	
	public ActionBarMenuItem addItem(int id, int icon, CharSequence text, int backgroundColor, Drawable drawable, int width, CharSequence title) {
		ActionBarMenuItem menuItem = new ActionBarMenuItem(getContext(), this, backgroundColor, isActionMode ? parentActionBar.itemsActionModeColor : parentActionBar.itemsColor, text != null);
		menuItem.setTag(id);
		if (text != null) {
			menuItem.textView.setText(text);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width != 0 ? width : ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
			layoutParams.leftMargin = layoutParams.rightMargin = AndroidUtilities.dp(14);
			addView(menuItem, layoutParams);
		} else {
			if (drawable != null) {
				menuItem.iconView.setImageDrawable(drawable);
			} else if (icon != 0) {
				menuItem.iconView.setImageResource(icon);
			}
			addView(menuItem, new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT));
		}
		menuItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ActionBarMenuItem item = (ActionBarMenuItem) view;
                if (item.hasSubMenu()) {
                    if (parentActionBar.actionBarMenuOnItemClick.canOpenMenu()) {
                        item.toggleSubMenu();
                    }
                } else if (item.isSearchField()) {
                    parentActionBar.onSearchFieldVisibilityChanged(item.toggleSearch(true));
                } else {
                    ActionBarMenu.this.onItemClick((Integer) view.getTag());
                }
            }
        });
		if (title != null) {
			menuItem.setContentDescription(title);
		}
		return menuItem;
	}
	
	public View addItem(int id,View view) {
		view.setTag(id);
		addView(view);
		view.setOnClickListener(view1 -> onItemClick((Integer) view1.getTag()));
		return view;
	}
	
	public void hideAllPopupMenus() {
        int count = getChildCount();
        for (int a = 0; a < count; a++) {
            View view = getChildAt(a);
            if (view instanceof ActionBarMenuItem) {
                ((ActionBarMenuItem) view).closeSubMenu();
            }
        }
    }
	
	protected void setPopupItemsColor(int color, boolean icon) {
        for (int a = 0, count = getChildCount(); a < count; a++) {
            final View view = getChildAt(a);
            if (view instanceof ActionBarMenuItem) {
                ((ActionBarMenuItem) view).setPopupItemsColor(color, icon);
            }
        }
    }
	
	protected void setPopupItemsSelectorColor(int color) {
        for (int a = 0, count = getChildCount(); a < count; a++) {
            final View view = getChildAt(a);
            if (view instanceof ActionBarMenuItem) {
                ((ActionBarMenuItem) view).setPopupItemsSelectorColor(color);
            }
        }
    }

    protected void redrawPopup(int color) {
        for (int a = 0, count = getChildCount(); a < count; a++) {
            final View view = getChildAt(a);
            if (view instanceof ActionBarMenuItem) {
                ((ActionBarMenuItem) view).redrawPopup(color);
            }
        }
    }

    public void onItemClick(int id) {
        if (parentActionBar.actionBarMenuOnItemClick != null) {
            parentActionBar.actionBarMenuOnItemClick.onItemClick(id);
        }
    }
	
	public void clearItems() {
		removeAllViews();
	}

	public void onMenuButtonPressed() {
        int count = getChildCount();
        for (int a = 0; a < count; a++) {
            View view = getChildAt(a);
            if (view instanceof ActionBarMenuItem) {
                ActionBarMenuItem item = (ActionBarMenuItem) view;
                if (item.getVisibility() != VISIBLE) {
                    continue;
                }
                if (item.hasSubMenu()) {
                    item.toggleSubMenu();
                    break;
                } else if (item.overrideMenuClick) {
                    onItemClick((Integer) item.getTag());
                    break;
                }
            }
        }
    }
	
	public void closeSearchField(boolean closeKeyboard) {
        int count = getChildCount();
        for (int a = 0; a < count; a++) {
            View view = getChildAt(a);
            if (view instanceof ActionBarMenuItem) {
                ActionBarMenuItem item = (ActionBarMenuItem) view;
                if (item.isSearchField() && item.isSearchFieldVisible()) {
                    if (item.listener == null || item.listener.canCollapseSearch()) {
                        parentActionBar.onSearchFieldVisibilityChanged(false);
                        item.toggleSearch(closeKeyboard);
                    }
                    break;
                }
            }
        }
    }
	
	public void setSearchTextColor(int color, boolean placeholder) {
        int count = getChildCount();
        for (int a = 0; a < count; a++) {
            View view = getChildAt(a);
            if (view instanceof ActionBarMenuItem) {
                ActionBarMenuItem item = (ActionBarMenuItem) view;
                if (item.isSearchField()) {
                    if (placeholder) {
                        item.getSearchField().setHintTextColor(color);
                    } else {
                        item.getSearchField().setTextColor(color);
                    }
                    break;
                }
            }
        }
    }
	
	public void setSearchFieldText(String text) {
        int count = getChildCount();
        for (int a = 0; a < count; a++) {
            View view = getChildAt(a);
            if (view instanceof ActionBarMenuItem) {
                ActionBarMenuItem item = (ActionBarMenuItem) view;
                if (item.isSearchField()) {
                    item.setSearchFieldText(text, false);
                    item.getSearchField().setSelection(text.length());
                }
            }
        }
    }

    public void onSearchPressed() {
        int count = getChildCount();
        for (int a = 0; a < count; a++) {
            View view = getChildAt(a);
            if (view instanceof ActionBarMenuItem) {
                ActionBarMenuItem item = (ActionBarMenuItem) view;
                if (item.isSearchField()) {
                    item.onSearchPressed();
                }
            }
        }
    }
	
	public void openSearchField(boolean toggle, String text, boolean animated) {
        int count = getChildCount();
        for (int a = 0; a < count; a++) {
            View view = getChildAt(a);
            if (view instanceof ActionBarMenuItem) {
                ActionBarMenuItem item = (ActionBarMenuItem) view;
                if (item.isSearchField()) {
                    if (toggle) {
                        parentActionBar.onSearchFieldVisibilityChanged(item.toggleSearch(true));
                    }
                    item.setSearchFieldText(text, animated);
                    item.getSearchField().setSelection(text.length());
                    break;
                }
            }
        }
    }
	
	public void openSearchField(boolean toggle, String text) {
		int count = getChildCount();
		for (int a = 0; a < count; a++) {
			View view = getChildAt(a);
			if (view instanceof ActionBarMenuItem) {
				ActionBarMenuItem item = (ActionBarMenuItem) view;
				if (item.isSearchField()) {
					if (toggle) {
						parentActionBar.onSearchFieldVisibilityChanged(item.toggleSearch(true));
					}
					item.getSearchField().setText(text);
					item.getSearchField().setSelection(text.length());
					break;
				}
			}
		}
	}
	
	public ActionBarMenuItem getItem(int id) {
        View v = findViewWithTag(id);
        if (v instanceof ActionBarMenuItem) {
            return (ActionBarMenuItem) v;
        }
        return null;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        int count = getChildCount();
        for (int a = 0; a < count; a++) {
            View view = getChildAt(a);
            view.setEnabled(enabled);
        }
    }
}
