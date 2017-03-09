package sh.ajb.com.endggmm.Fragment;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.aigestudio.datepicker.bizs.calendars.DPCManager;
import cn.aigestudio.datepicker.bizs.decors.DPDecor;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;
import sh.ajb.com.endggmm.R;
import sh.ajb.com.endggmm.Utils.DateTimeUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageJokeFragment extends Fragment {


    public ImageJokeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View view = inflater.inflate(R.layout.fragment_image_joke, container, false);
        initView(view);
        return view;

    }

    private void initView(View view) {
        List<String> tmpTL = new ArrayList<>();
        tmpTL.add("2015-10-5");
        tmpTL.add("2015-10-6");
        tmpTL.add("2015-10-7");
        tmpTL.add("2015-10-8");
        tmpTL.add("2015-10-9");
        tmpTL.add("2015-10-10");
        tmpTL.add("2015-10-11");
        DPCManager.getInstance().setDecorTL(tmpTL);

        List<String> tmpTR = new ArrayList<>();
        tmpTR.add("2015-10-10");
        tmpTR.add("2015-10-11");
        tmpTR.add("2015-10-12");
        tmpTR.add("2015-10-13");
        tmpTR.add("2015-10-14");
        tmpTR.add("2015-10-15");
        tmpTR.add("2015-10-16");
        DPCManager.getInstance().setDecorTR(tmpTR);

        DatePicker datePicker = (DatePicker) view.findViewById(R.id.main_dp);
        datePicker.setTodayDisplay(true);
        String currentDate = DateTimeUtil.getCurrentTime_Today();
        String[] sCurrentDate = currentDate.split("-");
        String year = sCurrentDate[0];
        String month = sCurrentDate[1];
        datePicker.setDate(Integer.parseInt(year), Integer.parseInt(month));
        datePicker.setMode(DPMode.SINGLE);
//        datePicker.setDPDecor(new DPDecor() {
//            @Override
//            public void drawDecorTL(Canvas canvas, Rect rect, Paint paint, String data) {
//                super.drawDecorTL(canvas, rect, paint, data);
//                switch (data) {
//                    case "2015-10-5":
//                    case "2015-10-7":
//                    case "2015-10-9":
//                    case "2015-10-11":
//                        paint.setColor(Color.GREEN);
//                        canvas.drawRect(rect, paint);
//                        break;
//                    default:
//                        paint.setColor(Color.RED);
//                        canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2, paint);
//                        break;
//                }
//            }
//
//
//            @Override
//            public void drawDecorTR(Canvas canvas, Rect rect, Paint paint, String data) {
//                super.drawDecorTR(canvas, rect, paint, data);
//                switch (data) {
//                    case "2015-10-10":
//                    case "2015-10-11":
//                    case "2015-10-12":
//                        paint.setColor(Color.BLUE);
//                        canvas.drawCircle(rect.centerX(), rect.centerY(), rect.width() / 2, paint);
//                        break;
//                    default:
//                        paint.setColor(Color.YELLOW);
//                        canvas.drawRect(rect, paint);
//                        break;
//                }
//            }
//        });

        //单选模式
        datePicker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                Toast.makeText(getActivity(), date, Toast.LENGTH_LONG).show();
            }
        });


//        //多选模式
//        datePicker.setOnDateSelectedListener(new DatePicker.OnDateSelectedListener() {
//            @Override
//            public void onDateSelected(List<String> date) {
//                String result = "";
//                Iterator iterator = date.iterator();
//                while (iterator.hasNext()) {
//                    result += iterator.next();
//                    if (iterator.hasNext()) {
//                        result += "\n";
//                    }
//                }
//                Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();
//            }
//        });

        // 对话框下的DatePicker示例 Example in dialog
        Button btnPick = (Button) view.findViewById(R.id.main_btn);
        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
                dialog.show();
                DatePicker picker = new DatePicker(getActivity());
                picker.setDate(2015, 10);
                picker.setMode(DPMode.SINGLE);
                picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
                    @Override
                    public void onDatePicked(String date) {
                        Toast.makeText(getActivity(), date, Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setContentView(picker, params);
                dialog.getWindow().setGravity(Gravity.CENTER);
            }
        });
    }

}
