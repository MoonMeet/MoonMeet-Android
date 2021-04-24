package org.mark.moonmeet.emoji;

import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.graphics.drawable.DrawableCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.mark.axemojiview.AXEmojiManager;
import org.mark.axemojiview.sticker.Sticker;
import org.mark.axemojiview.sticker.StickerCategory;
import org.mark.axemojiview.sticker.StickerLoader;
import org.mark.axemojiview.sticker.StickerProvider;

public class WhatsAppProvider implements StickerProvider {
    @NonNull
    @Override
    public StickerCategory[] getCategories() {
        return new StickerCategory[]{
                new ShopStickers(),
                new WhatsAppStickers(),
                new WhatsAppStickers(),
                new WhatsAppStickers()
        };
    }

    @NonNull
    @Override
    public StickerLoader getLoader() {
        return new StickerLoader() {
            @Override
            public void onLoadSticker(View view, Sticker sticker) {
                if (sticker.isInstance(Integer.class)) {
                    Glide.with(view).load((int) sticker.getData()).apply(RequestOptions.fitCenterTransform()).into((AppCompatImageView)view);
                }

            }

            @Override
            public void onLoadStickerCategory(View view, StickerCategory stickerCategory, boolean selected) {
                try{
                    if (stickerCategory instanceof ShopStickers) {
                        Drawable dr0 = AppCompatResources.getDrawable(view.getContext(), (Integer) stickerCategory.getCategoryData());
                        Drawable dr = dr0.getConstantState().newDrawable();
                        if (selected) {
                            DrawableCompat.setTint(DrawableCompat.wrap(dr), AXEmojiManager.getStickerViewTheme().getSelectedColor());
                        }else{
                            DrawableCompat.setTint(DrawableCompat.wrap(dr), AXEmojiManager.getStickerViewTheme().getDefaultColor());
                        }
                        ((AppCompatImageView)view).setImageDrawable(dr);
                    } else {
                        Glide.with(view).load(Integer.valueOf(stickerCategory.getCategoryData().toString())).apply(RequestOptions.fitCenterTransform()).into((AppCompatImageView)view);
                    }
                }catch (Exception ignore){
                }
            }
        };
    }

    @Override
    public boolean isRecentEnabled() {
        return true;
    }
}
