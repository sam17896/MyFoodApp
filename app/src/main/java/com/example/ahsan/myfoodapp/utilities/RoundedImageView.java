package com.example.ahsan.myfoodapp.utilities;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.example.ahsan.myfoodapp.R;

public class RoundedImageView extends ImageView {
    public static final int DEFAULT_BORDER_WIDTH = 0;
    public static final int DEFAULT_RADIUS = 0;
    private static final ScaleType[] SCALE_TYPES = new ScaleType[]{ScaleType.MATRIX, ScaleType.FIT_XY, ScaleType.FIT_START, ScaleType.FIT_CENTER, ScaleType.FIT_END, ScaleType.CENTER, ScaleType.CENTER_CROP, ScaleType.CENTER_INSIDE};
    public static final String TAG = "RoundedImageView";
    private Drawable mBackgroundDrawable;
    private ColorStateList mBorderColor;
    private int mBorderWidth;
    private int mCornerRadius;
    private Drawable mDrawable;
    private boolean mOval;
    private int mResource;
    private boolean mRoundBackground;
    private ScaleType mScaleType;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$android$widget$ImageView$ScaleType = new int[ScaleType.values().length];

        static {
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ScaleType.CENTER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ScaleType.CENTER_CROP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ScaleType.CENTER_INSIDE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ScaleType.FIT_CENTER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ScaleType.FIT_START.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ScaleType.FIT_END.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$android$widget$ImageView$ScaleType[ScaleType.FIT_XY.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public RoundedImageView(Context context) {
        super(context);
        this.mCornerRadius = DEFAULT_RADIUS;
        this.mBorderWidth = DEFAULT_RADIUS;
        this.mBorderColor = ColorStateList.valueOf(RoundedDrawable.DEFAULT_BORDER_COLOR);
        this.mOval = false;
        this.mRoundBackground = false;
    }

    public RoundedImageView(Context context, AttributeSet attrs) {
        this(context, attrs, DEFAULT_RADIUS);
    }

    public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mCornerRadius = DEFAULT_RADIUS;
        this.mBorderWidth = DEFAULT_RADIUS;
        this.mBorderColor = ColorStateList.valueOf(RoundedDrawable.DEFAULT_BORDER_COLOR);
        this.mOval = false;
        this.mRoundBackground = false;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundedImageView, defStyle, DEFAULT_RADIUS);
        int index = a.getInt(DEFAULT_RADIUS, -1);
        if (index >= 0) {
            setScaleType(SCALE_TYPES[index]);
        } else {
            setScaleType(ScaleType.FIT_CENTER);
        }
        this.mCornerRadius = a.getDimensionPixelSize(1, -1);
        this.mBorderWidth = a.getDimensionPixelSize(2, -1);
        if (this.mCornerRadius < 0) {
            this.mCornerRadius = DEFAULT_RADIUS;
        }
        if (this.mBorderWidth < 0) {
            this.mBorderWidth = DEFAULT_RADIUS;
        }
        this.mBorderColor = a.getColorStateList(3);
        if (this.mBorderColor == null) {
            this.mBorderColor = ColorStateList.valueOf(RoundedDrawable.DEFAULT_BORDER_COLOR);
        }
        this.mRoundBackground = a.getBoolean(4, false);
        this.mOval = a.getBoolean(5, false);
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs();
        a.recycle();
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        invalidate();
    }

    public ScaleType getScaleType() {
        return this.mScaleType;
    }

    public void setScaleType(ScaleType scaleType) {
        if (scaleType == null) {
            throw new NullPointerException();
        } else if (this.mScaleType != scaleType) {
            this.mScaleType = scaleType;
            switch (AnonymousClass1.$SwitchMap$android$widget$ImageView$ScaleType[scaleType.ordinal()]) {
              //  case DBHelper.DB_VERSION /*1*/:
                case R.styleable.View_paddingStart /*2*/:
                case R.styleable.View_paddingEnd /*3*/:
                case R.styleable.View_theme /*4*/:
                case R.styleable.Toolbar_contentInsetStart /*5*/:
                case R.styleable.Toolbar_contentInsetEnd /*6*/:
                case R.styleable.Toolbar_contentInsetLeft /*7*/:
                    super.setScaleType(ScaleType.FIT_XY);
                    break;
                default:
                    super.setScaleType(scaleType);
                    break;
            }
            updateDrawableAttrs();
            updateBackgroundDrawableAttrs();
            invalidate();
        }
    }

    public void setImageDrawable(Drawable drawable) {
        this.mResource = DEFAULT_RADIUS;
        this.mDrawable = RoundedDrawable.fromDrawable(drawable);
        updateDrawableAttrs();
        super.setImageDrawable(this.mDrawable);
    }

    public void setImageBitmap(Bitmap bm) {
        this.mResource = DEFAULT_RADIUS;
        this.mDrawable = RoundedDrawable.fromBitmap(bm);
        updateDrawableAttrs();
        super.setImageDrawable(this.mDrawable);
    }

    public void setImageResource(int resId) {
        if (this.mResource != resId) {
            this.mResource = resId;
            this.mDrawable = resolveResource();
            updateDrawableAttrs();
            super.setImageDrawable(this.mDrawable);
        }
    }

    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        setImageDrawable(getDrawable());
    }

    private Drawable resolveResource() {
        Resources rsrc = getResources();
        if (rsrc == null) {
            return null;
        }
        Drawable d = null;
        if (this.mResource != 0) {
            try {
                d = rsrc.getDrawable(this.mResource);
            } catch (Exception e) {
                Log.w(TAG, "Unable to find resource: " + this.mResource, e);
                this.mResource = DEFAULT_RADIUS;
            }
        }
        return RoundedDrawable.fromDrawable(d);
    }

    private void updateDrawableAttrs() {
        updateAttrs(this.mDrawable, false);
    }

    private void updateBackgroundDrawableAttrs() {
        updateAttrs(this.mBackgroundDrawable, true);
    }

    private void updateAttrs(Drawable drawable, boolean background) {
        if (drawable != null) {
            if (drawable instanceof RoundedDrawable) {
                RoundedDrawable scaleType = ((RoundedDrawable) drawable).setScaleType(this.mScaleType);
                float f = (!background || this.mRoundBackground) ? (float) this.mCornerRadius : 0.0f;
                scaleType = scaleType.setCornerRadius(f);
                int i = (!background || this.mRoundBackground) ? this.mBorderWidth : DEFAULT_RADIUS;
                scaleType.setBorderWidth(i).setBorderColors(this.mBorderColor).setOval(this.mOval);
            } else if (drawable instanceof LayerDrawable) {
                LayerDrawable ld = (LayerDrawable) drawable;
                int layers = ld.getNumberOfLayers();
                for (int i2 = DEFAULT_RADIUS; i2 < layers; i2++) {
                    updateAttrs(ld.getDrawable(i2), background);
                }
            }
        }
    }

    @Deprecated
    public void setBackgroundDrawable(Drawable background) {
        this.mBackgroundDrawable = RoundedDrawable.fromDrawable(background);
        updateBackgroundDrawableAttrs();
        super.setBackgroundDrawable(this.mBackgroundDrawable);
    }

    public int getCornerRadius() {
        return this.mCornerRadius;
    }

    public void setCornerRadius(int radius) {
        if (this.mCornerRadius != radius) {
            this.mCornerRadius = radius;
            updateDrawableAttrs();
            updateBackgroundDrawableAttrs();
        }
    }

    public int getBorderWidth() {
        return this.mBorderWidth;
    }

    public void setBorderWidth(int width) {
        if (this.mBorderWidth != width) {
            this.mBorderWidth = width;
            updateDrawableAttrs();
            updateBackgroundDrawableAttrs();
            invalidate();
        }
    }

    public int getBorderColor() {
        return this.mBorderColor.getDefaultColor();
    }

    public void setBorderColor(int color) {
        setBorderColors(ColorStateList.valueOf(color));
    }

    public ColorStateList getBorderColors() {
        return this.mBorderColor;
    }

    public void setBorderColors(ColorStateList colors) {
        if (!this.mBorderColor.equals(colors)) {
            if (colors == null) {
                colors = ColorStateList.valueOf(RoundedDrawable.DEFAULT_BORDER_COLOR);
            }
            this.mBorderColor = colors;
            updateDrawableAttrs();
            updateBackgroundDrawableAttrs();
            if (this.mBorderWidth > 0) {
                invalidate();
            }
        }
    }

    public boolean isOval() {
        return this.mOval;
    }

    public void setOval(boolean oval) {
        this.mOval = oval;
        updateDrawableAttrs();
        updateBackgroundDrawableAttrs();
        invalidate();
    }

    public boolean isRoundBackground() {
        return this.mRoundBackground;
    }

    public void setRoundBackground(boolean roundBackground) {
        if (this.mRoundBackground != roundBackground) {
            this.mRoundBackground = roundBackground;
            updateBackgroundDrawableAttrs();
            invalidate();
        }
    }
}
