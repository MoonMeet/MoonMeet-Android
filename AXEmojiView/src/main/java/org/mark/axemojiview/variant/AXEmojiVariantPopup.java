/*
 * Copyright (C) 2020 - Amir Hossein Aghajari
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */


package org.mark.axemojiview.variant;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.mark.axemojiview.emoji.Emoji;
import org.mark.axemojiview.listener.OnEmojiActions;
import org.mark.axemojiview.view.AXEmojiImageView;

public abstract class AXEmojiVariantPopup {

    public AXEmojiVariantPopup(@NonNull final View rootView, @Nullable final OnEmojiActions listener) {}

    public abstract boolean isShowing();

    public abstract void show(@NonNull final AXEmojiImageView clickedImage, @NonNull final Emoji emoji, boolean fromRecent);

    public abstract void dismiss();

    public boolean onTouch(MotionEvent event, RecyclerView recyclerView) {
        return false;
    }
}
