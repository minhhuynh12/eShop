
package kh.com.metfone.emoney.eshop.ui.customs;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import kh.com.metfone.emoney.eshop.R;
import kh.com.metfone.emoney.eshop.utils.DataUtils;

import java.text.DecimalFormat;

/**
 * Custom implementation of the MarkerView.
 *
 * @author Philipp Jahoda
 */
public class XYMarkerView extends MarkerView {

    private TextView tvContent;

    private DecimalFormat format;

    public XYMarkerView(Context context) {
        super(context, R.layout.custom_marker_view);

        tvContent = (TextView) findViewById(R.id.tvContent);
        format = new DecimalFormat("###.0");
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
//    @Override
//    public void refreshContent(Entry e, Highlight highlight) {
//
//        tvContent.setText("x: " + xAxisValueFormatter.getFormattedValue(e.getX(), null) + ", y: " + format.format(e.getY()));
//
//        super.refreshContent(e, highlight);
//    }
//
//    @Override
//    public MPPointF getOffset() {
//        return new MPPointF(-(getWidth() / 2), -getHeight());
//    }

    @Override
    public void refreshContent(Entry e, int dataSetIndex) {
        if (e instanceof CandleEntry) {

            CandleEntry ce = (CandleEntry) e;

            tvContent.setText(DataUtils.formatterLong.format(ce.getHigh()));
        } else {
            if ((e.getVal() - (int) (e.getVal())) > 0) {
                tvContent.setText(DataUtils.getDoubleString(e.getVal()));
            } else {
                tvContent.setText(DataUtils.formatterLong.format(e.getVal()));
            }
        }
    }

    @Override
    public int getXOffset() {
        return -(getWidth() / 2);
    }

    @Override
    public int getYOffset() {
        return -getHeight();
    }
}
