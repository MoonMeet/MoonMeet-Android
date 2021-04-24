package org.mark.moonmeet.emoji;

import org.mark.axemojiview.sticker.Sticker;

public class ShopSticker extends Sticker {
        private static final long serialVersionUID = 3L;

        String title;
        int count;
        public ShopSticker(Sticker[] data, String Title, int StickersSize) {
            super(data);
            this.title = Title;
            this.count = StickersSize;
        }
		
	}