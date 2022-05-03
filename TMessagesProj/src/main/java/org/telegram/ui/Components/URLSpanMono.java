/*
 * This is the source code of Telegram for Android v. 5.x.x.
 * It is licensed under GNU GPL v. 2 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 *
 * Copyright Nikolai Kudashov, 2013-2018.
 */

package org.telegram.ui.Components;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.SharedConfig;
import org.telegram.ui.ActionBar.Theme;

import org.viento.colibrix.helpers.EntitiesHelper;

public class URLSpanMono extends MetricAffectingSpan {

    private CharSequence currentMessage;
    private int currentStart;
    private int currentEnd;
    private byte currentType;
    private TextStyleSpan.TextStyleRun style;

    public URLSpanMono(CharSequence message, int start, int end, byte type) {
        this(message, start, end, type, null);
    }

    public URLSpanMono(CharSequence message, int start, int end, byte type, TextStyleSpan.TextStyleRun run) {
        currentMessage = message;
        currentStart = start;
        currentEnd = end;
        currentType = type;
        style = run;
    }

    public CharSequence getTextToCopy() {
        return EntitiesHelper.commonizeSpans(currentMessage.subSequence(currentStart, currentEnd));
    }

    public void copyToClipboard() {
        AndroidUtilities.addToClipboard(getTextToCopy());
    }

    public TextStyleSpan.TextStyleRun getStyle() {
        return style;
    }

    @Override
    public void updateMeasureState(TextPaint p) {
        p.setTextSize(AndroidUtilities.dp(SharedConfig.fontSize - 1));
        p.setFlags(p.getFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        if (style != null) {
            style.applyStyle(p);
        } else {
            p.setTypeface(Typeface.MONOSPACE);
        }
    }

    @Override
    public void updateDrawState(TextPaint p) {
        p.setTextSize(AndroidUtilities.dp(SharedConfig.fontSize - 1));
        if (currentType == 2) {
            p.setColor(0xffffffff);
        } else if (currentType == 1) {
            p.setColor(Theme.getColor(Theme.key_chat_messageTextOut));
        } else {
            p.setColor(Theme.getColor(Theme.key_chat_messageTextIn));
        }
        if (style != null) {
            style.applyStyle(p);
        } else {
            p.setTypeface(Typeface.MONOSPACE);
            p.setUnderlineText(false);
        }
    }
}
