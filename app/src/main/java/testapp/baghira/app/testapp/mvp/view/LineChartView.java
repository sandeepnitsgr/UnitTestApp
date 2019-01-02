package testapp.baghira.app.testapp.mvp.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

import testapp.baghira.app.testapp.R;

public class LineChartView extends View {

    private List<Float> datapoints;
    private float[] constantRange;
    private List<String> xData;
    int defaultLineAndPointColor = Color.parseColor("#64caff");
    private float minDataValue;
    private float maxDataValue;
    private int lineColor;
    private boolean showPoints;
    private boolean showShadowGradient;
    private boolean showBackgroundLines;
    private int lineWidth;
    private int pointColor;
    private int pointRadius;
    private int backgroundLinesColor;
    private int gradientStartColor;
    private int gradientEndColor;
    private int axisTextColor;
    private float axisTextSize;
    private int backgroundLinesWidth;
    private float relativeXShift;
    private Paint backLinePaint=new Paint();
    private Paint axisTextPaint=new Paint();
    private Paint linePaint=new Paint();
    private Paint gradientPaint=new Paint();
    private Paint circlePaint=new Paint();

    public LineChartView(Context context) {
        super(context);
        init(null);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void setChartData(List<Float> datapoints, List<String> xData) {
        this.datapoints = new ArrayList<>(datapoints);
        this.xData = new ArrayList<>(xData);
        minDataValue = getMin(datapoints);
        maxDataValue = getMax(datapoints);
        initAxisTextPaint();
        relativeXShift = axisTextPaint.measureText(String.valueOf(maxDataValue));
        invalidate();
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LineChartView);
            showPoints = typedArray.getBoolean(R.styleable.LineChartView_showPoints, true);
            showShadowGradient = typedArray.getBoolean(R.styleable.LineChartView_showShadowGradient, false);
            showBackgroundLines = typedArray.getBoolean(R.styleable.LineChartView_showBackgroundLines, true);
            lineColor = typedArray.getColor(R.styleable.LineChartView_lineColor, defaultLineAndPointColor);
            lineWidth = typedArray.getDimensionPixelSize(R.styleable.LineChartView_lineWidth, dpToPx(2f, getContext()));
            pointColor = typedArray.getColor(R.styleable.LineChartView_pointColor, defaultLineAndPointColor);
            pointRadius = typedArray.getDimensionPixelSize(R.styleable.LineChartView_pointRadius, dpToPx(3f, getContext()));
            backgroundLinesWidth = typedArray.getDimensionPixelSize(R.styleable.LineChartView_backgroundLinesWidth, dpToPx(1f, getContext()));
            backgroundLinesColor = typedArray.getColor(R.styleable.LineChartView_backgroundLinesColor, Color.parseColor("#e0e0e0"));
            gradientStartColor = typedArray.getColor(R.styleable.LineChartView_gradientStartColor, Color.parseColor("#b398dcff"));
            gradientEndColor = typedArray.getColor(R.styleable.LineChartView_gradientEndColor, Color.parseColor("#b3ffffff"));
            axisTextSize = typedArray.getDimension(R.styleable.LineChartView_axisTextSize, dpToPx(12f, getContext()));
            axisTextColor = typedArray.getColor(R.styleable.LineChartView_axisTextColor, Color.BLACK);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);
        drawLineChart(canvas);

    }

    private void initBackgroundLinePaint(){
        backLinePaint.setStyle(Style.STROKE);
        backLinePaint.setStrokeWidth(backgroundLinesWidth);
        backLinePaint.setColor(backgroundLinesColor);
    }

    private void initLinePaint() {
        linePaint.setStyle(Style.STROKE);
        linePaint.setStrokeWidth(lineWidth);
        linePaint.setColor(lineColor);
        linePaint.setAntiAlias(true);
    }

    private void initGradientPaint() {
        gradientPaint.setShader(new LinearGradient(0, 0, 0, getHeight(),
                gradientStartColor, gradientEndColor, Shader.TileMode.MIRROR));
    }

    private void initAxisTextPaint() {
        axisTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        axisTextPaint.setTextSize(axisTextSize);
        axisTextPaint.setColor(axisTextColor);
    }

    private void initCirclePaint(){
        circlePaint.setColor(pointColor);
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Style.FILL);
    }

    private void drawBackground(Canvas canvas) {
        initAxisTextPaint();
        getYAxisLinesPosition();
        float initXPadding = axisTextPaint.measureText(xData.get(0)) / 2;
        for (int count = 0; count < constantRange.length; count++) {
            final float yPos = getYPosNew(count);
            canvas.drawText(String.valueOf(constantRange[count]), 0, yPos + 10, axisTextPaint);
            if (showBackgroundLines) {
                initBackgroundLinePaint();
                canvas.drawLine(getXPos(0) - initXPadding, yPos, getWidth(), yPos, backLinePaint);
            }

        }
    }

    private void getYAxisLinesPosition() {
        int max = (int) Math.ceil(maxDataValue);
        int min = (int) Math.floor(minDataValue);
        int diffRange = (int) Math.ceil((float) (max - min) / 6);
        if (diffRange == 0) {
            diffRange = 1;
        }
        constantRange = new float[8];
        for (int count = 0; count < 8; count++) {
            constantRange[count] = minDataValue + diffRange * count;
        }

    }

    private void drawLineChart(Canvas canvas) {
        Path path = new Path();
        float x = 0;
        float y1 = 0;
        float y2 = 0;
        float relativeYPos = 0;
        float y = 0;


        int i = 0, j = 0;
        for (i = 0; i < datapoints.size(); i++) {
            for (j = 0; j < constantRange.length - 1; j++) {
                if (datapoints.get(i) >= constantRange[j] && datapoints.get(i) <= constantRange[j + 1]) {
                    break;
                }
            }
            if (j < constantRange.length - 1) {
                y1 = getYPosNew(j);
                y2 = getYPosNew(j + 1);
                relativeYPos = (y1 - y2) - ((datapoints.get(i) - constantRange[j]) / (constantRange[j + 1] - constantRange[j])) * (y1 - y2);
                x = getXPos(i);
                y = y2 + relativeYPos;
                if (i == 0) {
                    path.moveTo(x, y);
                } else {
                    path.lineTo(x, y);
                }
            }
        }
        initLinePaint();
        canvas.drawPath(path, linePaint);       //draw graph lines


        if (showShadowGradient) {               //draws gradient top to bottom
            initGradientPaint();
            path.lineTo(getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
            path.lineTo(getXPos(0), getHeight() - getPaddingBottom());
            path.lineTo(getXPos(0), getYPosNew(0));
            canvas.drawPath(path, gradientPaint);
        }



        for (i = 0; i < datapoints.size(); i++) {
            for (j = 0; j < constantRange.length - 1; j++) {
                if (datapoints.get(i) >= constantRange[j] && datapoints.get(i) <= constantRange[j + 1]) {
                    break;
                }
            }
            if (j < constantRange.length - 1) {

                y1 = getYPosNew(j);
                y2 = getYPosNew(j + 1);
                relativeYPos = (y1 - y2) - ((datapoints.get(i) - constantRange[j]) / (constantRange[j + 1] - constantRange[j])) * (y1 - y2);
                x = getXPos(i);
                y = y2 + relativeYPos;
                if (showPoints) {
                    initCirclePaint();
                    canvas.drawCircle(x, y, pointRadius, circlePaint);          //draws circle points
                }
            }

            if (!TextUtils.isEmpty(xData.get(i))) {
                canvas.drawText(xData.get(i),
                        getXPos(i) - (int) (axisTextPaint.measureText(xData.get(i)) / 2.0),
                        getHeight() - getPaddingBottom() / 3,
                        axisTextPaint);
            }
        }

    }

    private float getMin(List<Float> array) {
        float max = array.get(0);
        for (int i = 1; i < array.size(); i++) {
            if (array.get(i) < max) {
                max = array.get(i);
            }
        }
        return max;
    }

    private float getMax(List<Float> array) {
        float max = array.get(0);
        for (int i = 1; i < array.size(); i++) {
            if (array.get(i) > max) {
                max = array.get(i);
            }
        }
        return max;
    }


    private float getYPosNew(float value) {
        float height = getHeight() - getPaddingTop() - getPaddingBottom();


        value = (value / 8) * height;

        value = height - value;

        value += getPaddingTop();

        return value;
    }

    private float getXPos(float value) {
        float width = getWidth() - getPaddingLeft() - getPaddingRight() - relativeXShift;
        float maxValue = datapoints.size() - 1;

        value = (value / maxValue) * width;

        value += getPaddingLeft() + relativeXShift;

        return value;
    }

    private int dpToPx(float dp, Context context) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return (int) px;
    }


    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
        initLinePaint();
    }

    public void setShowPoints(boolean showPoints) {
        this.showPoints = showPoints;
    }

    public void setShowShadowGradient(boolean showShadowGradient) {
        this.showShadowGradient = showShadowGradient;
    }

    public void setShowBackgroundLines(boolean showBackgroundLines) {
        this.showBackgroundLines = showBackgroundLines;
    }

    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
        initLinePaint();
    }

    public void setPointColor(int pointColor) {
        this.pointColor = pointColor;
        initCirclePaint();
    }

    public void setPointRadius(int pointRadius) {
        this.pointRadius = pointRadius;
        initCirclePaint();
    }

    public void setBackgroundLinesColor(int backgroundLinesColor) {
        this.backgroundLinesColor = backgroundLinesColor;
        initBackgroundLinePaint();
    }

    public void setGradientStartColor(int gradientStartColor) {
        this.gradientStartColor = gradientStartColor;
        initGradientPaint();
    }

    public void setGradientEndColor(int gradientEndColor) {
        this.gradientEndColor = gradientEndColor;
        initGradientPaint();
    }
}