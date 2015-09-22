package cn.bingoogolapple.media.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.media.util.UIUtil;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/9/21 下午10:40
 * 描述:
 */
public class LyricView extends TextView {
    private static final String TAG = LyricView.class.getSimpleName();
    private static final int DEFAULT_LINE_COUNT = 3;
    private int mLightColor = Color.parseColor("#008D14");
    private int mDefaultColor = Color.parseColor("#FFFFFF");
    private int mLightTextSize;
    private int mDefaultTextSize;
    private Paint mPaint;
    private List<String> mLyrics;
    private int mLightIndex;
    private int mLineSpacing;

    public LyricView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDefaultAttrs();
    }

    private void initDefaultAttrs() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mLightTextSize = UIUtil.sp2px(getContext(), 15);
        mDefaultTextSize = UIUtil.sp2px(getContext(), 13);
        mLineSpacing = UIUtil.sp2px(getContext(), 5);

        mLyrics = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mLyrics.add("我是静态歌词" + i);
        }
        mLightIndex = 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 1 绘制高亮歌词
        mPaint.setColor(mLightColor);
        mPaint.setTextSize(mLightTextSize);
        String lyric = mLyrics.get(mLightIndex);
        Rect lyricRect = getTextRect(lyric);
        float lightY = getHeight() / 2 + lyricRect.height() / 2;
        drawHorizontalCenterText(canvas, lyric, lyricRect.width(), lightY);

        // 2 绘制默认歌词
        float lineHeight = lyricRect.height() + mLineSpacing;
        mPaint.setColor(mDefaultColor);
        mPaint.setTextSize(mDefaultTextSize);

        int endI = mLightIndex - 1;
        int startI = mLightIndex - DEFAULT_LINE_COUNT;
        // 2.1 画高亮行前面的歌词
        if (endI >= 0) {
            startI = startI < 0 ? 0 : startI;
            drawDefaultText(canvas, lightY, lineHeight, startI, endI);
        }
        // 2.2 画高亮行后面的歌词
        startI = mLightIndex + 1;
        int lineCount = mLyrics.size();
        if (startI < lineCount) {
            endI = mLightIndex + DEFAULT_LINE_COUNT;
            endI = endI < lineCount ? endI : lineCount - 1;
            drawDefaultText(canvas, lightY, lineHeight, startI, endI);
        }
    }

    private void drawDefaultText(Canvas canvas, float lightY, float lineHeight, int startI, int endI) {
        String lyric;
        Rect lyricRect;
        for (int i = startI; i <= endI; i++) {
            lyric = mLyrics.get(i);
            lyricRect = getTextRect(lyric);
            drawHorizontalCenterText(canvas, lyric, lyricRect.width(), lightY + (i - mLightIndex) * lineHeight);
        }
    }

    private Rect getTextRect(String lyric) {
        Rect rect = new Rect();
        mPaint.getTextBounds(lyric, 0, lyric.length(), rect);
        return rect;
    }

    private void drawHorizontalCenterText(Canvas canvas, String lyric, float lyricWidth, float y) {
        float x = getWidth() / 2 - lyricWidth / 2;
        canvas.drawText(lyric, x, y, mPaint);
    }
}