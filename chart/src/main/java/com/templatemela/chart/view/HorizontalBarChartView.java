package com.templatemela.chart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Region;
import android.graphics.Shader;
import android.util.AttributeSet;
import com.templatemela.chart.model.Bar;
import com.templatemela.chart.model.BarSet;
import com.templatemela.chart.model.ChartSet;

import java.util.ArrayList;

public class HorizontalBarChartView extends BaseBarChartView {
    public HorizontalBarChartView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setOrientation(Orientation.HORIZONTAL);
        setMandatoryBorderSpacing();
    }

    public HorizontalBarChartView(Context context) {
        super(context);
        setOrientation(Orientation.HORIZONTAL);
        setMandatoryBorderSpacing();
    }

    @Override
    public void onDrawChart(Canvas canvas, ArrayList<ChartSet> arrayList) {
        ArrayList<ChartSet> arrayList2 = arrayList;
        int size = arrayList.size();
        int size2 = arrayList2.get(0).size();
        for (int i = 0; i < size2; i++) {
            float y = arrayList2.get(0).getEntry(i).getY() - drawingOffset;
            for (int i2 = 0; i2 < size; i2++) {
                BarSet barSet = (BarSet) arrayList2.get(i2);
                Bar bar = (Bar) barSet.getEntry(i);
                if (barSet.isVisible() && bar.getValue() != 0.0f) {
                    if (!bar.hasGradientColor()) {
                        style.barPaint.setColor(bar.getColor());
                    } else {
                        style.barPaint.setShader(new LinearGradient(getZeroPosition(), bar.getY(), bar.getX(), bar.getY(), bar.getGradientColors(), bar.getGradientPositions(), Shader.TileMode.MIRROR));
                    }
                    applyShadow(style.barPaint, barSet.getAlpha(), bar.getShadowDx(), bar.getShadowDy(), bar.getShadowRadius(), bar.getShadowColor());
                    if (style.hasBarBackground) {
                        drawBarBackground(canvas, getInnerChartLeft(), y, getInnerChartRight(), y + barWidth);
                    }
                    if (bar.getValue() > 0.0f) {
                        drawBar(canvas, getZeroPosition(), y, bar.getX(), y + barWidth);
                    } else {
                        drawBar(canvas, bar.getX(), y, getZeroPosition(), y + barWidth);
                    }
                    y += barWidth;
                    if (i2 != size - 1) {
                        y += style.setSpacing;
                    }
                }
            }
        }
    }


    @Override
    public void onPreDrawChart(ArrayList<ChartSet> arrayList) {
        if (arrayList.get(0).size() == 1) {
            style.barSpacing = 0.0f;
            calculateBarsWidth(arrayList.size(), 0.0f, (getInnerChartBottom() - getInnerChartTop()) - (getBorderSpacing() * 2.0f));
        } else {
            calculateBarsWidth(arrayList.size(), arrayList.get(0).getEntry(1).getY(), arrayList.get(0).getEntry(0).getY());
        }
        calculatePositionOffset(arrayList.size());
    }

    @Override
    public ArrayList<ArrayList<Region>> defineRegions(ArrayList<ChartSet> arrayList) {
        int size = arrayList.size();
        int size2 = arrayList.get(0).size();
        ArrayList<ArrayList<Region>> arrayList2 = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            arrayList2.add(new ArrayList(size2));
        }
        for (int i2 = 0; i2 < size2; i2++) {
            float y = arrayList.get(0).getEntry(i2).getY() - drawingOffset;
            for (int i3 = 0; i3 < size; i3++) {
                Bar bar = (Bar) ((BarSet) arrayList.get(i3)).getEntry(i2);
                if (bar.getValue() > 0.0f) {
                    arrayList2.get(i3).add(new Region((int) getZeroPosition(), (int) y, (int) bar.getX(), (int) (barWidth + y)));
                } else if (bar.getValue() < 0.0f) {
                    arrayList2.get(i3).add(new Region((int) bar.getX(), (int) y, (int) getZeroPosition(), (int) (barWidth + y)));
                } else {
                    arrayList2.get(i3).add(new Region(((int) getZeroPosition()) - 1, (int) y, (int) getZeroPosition(), (int) (barWidth + y)));
                }
                if (i3 != size - 1) {
                    y += style.setSpacing;
                }
            }
        }
        return arrayList2;
    }
}
