package yao.muyuer.dailyrecord.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import java.text.ParseException;
import java.util.Date;
import java.util.List;

import yao.muyuer.dailyrecord.R;
import yao.muyuer.dailyrecord.dbservice.UserService;
import yao.muyuer.dailyrecord.entity.GridViewEntity;
import yao.muyuer.library.activity.base.BaseActivity;
import yao.muyuer.library.calendar.CaledarAdapter;
import yao.muyuer.library.calendar.CalendarBean;
import yao.muyuer.library.calendar.CalendarDateView;
import yao.muyuer.library.calendar.CalendarUtil;
import yao.muyuer.library.calendar.CalendarView;

import static yao.muyuer.library.utils.CommonUtils.px;

public class CalendarActivity extends BaseActivity implements View.OnClickListener {

    public static final int COLOR_BLUE = Color.parseColor("#33B5E5");
    public static final int COLOR_VIOLET = Color.parseColor("#AA66CC");
    public static final int COLOR_GREEN = Color.parseColor("#99CC00");
    public static final int COLOR_ORANGE = Color.parseColor("#FFBB33");
    public static final int COLOR_RED = Color.parseColor("#FF4444");
    public static final int COLOR_FONT = Color.parseColor("#353841");

    CalendarDateView mCalendarDateView;
    ListView mList;
    TextView mTitle, tv_info, tv_add, celendar_date;
    Button btn_back;
    ImageView  ivadd;
    LinearLayout lladd;
    ListView celendar_list;
    private UserService service;
    private Integer[] icons1;
    private Integer[] icons2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setUpView() {
        service = new UserService(this);
        initView();
        initAmountType();
        int[] data = CalendarUtil.getYMD(new Date());
        initList(data[0] + "-" + data[1] + "-" + data[2]);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.calendar_layout;
    }
    private void initAmountType(){
        icons1 = service.GetAmountTypeIcon(1);
        icons2 = service.GetAmountTypeIcon(2);
    }
    private void initView() {
        mCalendarDateView = (CalendarDateView) findViewById(R.id.calendarDateView);
        mList = (ListView) findViewById(R.id.celendar_list);
        mTitle = (TextView) findViewById(R.id.celendar_title);
        tv_info = (TextView) findViewById(R.id.tv_info);
        lladd = (LinearLayout) findViewById(R.id.lladd);
        celendar_list = (ListView) findViewById(R.id.celendar_list);
        celendar_date = (TextView) findViewById(R.id.celendar_date);

        tv_add = (TextView) findViewById(R.id.tv_add);
        tv_add.setOnClickListener(this);

        ivadd = (ImageView) findViewById(R.id.ivadd);
        ivadd.setOnClickListener(this);

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        mCalendarDateView.setAdapter(new CaledarAdapter() {
            @Override
            public View getView(View convertView, ViewGroup parentView, CalendarBean bean) {
                TextView tv_number, txtsy, txtzc;
                LinearLayout ll_group;
                if (convertView == null) {
                    convertView = LayoutInflater.from(parentView.getContext()).inflate(R.layout.calendar_item, null);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(px(48), px(48));
                    convertView.setLayoutParams(params);
                }
                ll_group= (LinearLayout) convertView.findViewById(R.id.ll_group);
                tv_number = (TextView) convertView.findViewById(R.id.tv_number);
                txtsy = (TextView) convertView.findViewById(R.id.txtsy);
                txtzc = (TextView) convertView.findViewById(R.id.txtzc);

                int[] data = CalendarUtil.getYMD(new Date());
                String nowstr = data[0] + "-" + data[1] + "-" + data[2];
              /*  if (bean.toString().equals(nowstr))
                    ll_group.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_calendar_sel));*/
                tv_number.setText("" + bean.day);
                if (bean.mothFlag != 0) {
                    tv_number.setTextColor(0xff9299a1);
                } else {
                    tv_number.setTextColor(COLOR_FONT);//0xffffffff
                }

                //chinaText.setText(bean.chinaDay);
                float[] money = service.findBillMoneyByTime(bean.year + "-" + getDisPlayNumber(bean.moth) + "-" + getDisPlayNumber(bean.day));
                txtsy.setText("");
                txtzc.setText("");
                if (money[0] != 0)
                    txtsy.setText("-" + Double.valueOf(money[0]).toString());
                if (money[1] != 0)
                    txtzc.setText(Double.valueOf(money[1]).toString());

                return convertView;
            }
        });
        mCalendarDateView.setOnItemClickListener(new CalendarView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion, CalendarBean bean) {
                mTitle.setText(bean.year + "/" + getDisPlayNumber(bean.moth) + "/" + getDisPlayNumber(bean.day));
                initList(bean.year + "-" + getDisPlayNumber(bean.moth) + "-" + getDisPlayNumber(bean.day));
            }
        });

        int[] data = CalendarUtil.getYMD(new Date());
        mTitle.setText(data[0] + "年" + data[1] + "月");
        celendar_date.setText(data[1] + "月" + data[2] + "日");
    }

    private void initList(final String time) {
        mList.setAdapter(new BaseAdapter() {
            private List<GridViewEntity> list = null;

            @Override
            public int getCount() {
                // return 10;
                if (list == null) {
                    try {
                        list = service.findBillByTime(time);
                        for (GridViewEntity dbent : list) {
                            int state= dbent.getAmountType();
                            if(state==0){
                                dbent.setIcon(icons1[dbent.getIcon()]);
                            }else if(state==1){
                                dbent.setIcon(icons2[dbent.getIcon()]);
                            }
                        }
                    } catch (ParseException e) {
                    }
                }
                if (list.size() == 0) {
                    int[] data = CalendarUtil.getYMD(new Date());
                    tv_info.setText(data[0] + "年" + data[1] + "月" + data[2]+"日无记账记录");
                    celendar_list.setVisibility(View.GONE);
                    lladd.setVisibility(View.VISIBLE);
                } else {
                    lladd.setVisibility(View.GONE);
                    celendar_list.setVisibility(View.VISIBLE);
                }
                return list.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
               /* if (convertView == null) {
                    convertView = LayoutInflater.from(CalendarActivity.this).inflate(android.R.layout.simple_list_item_1, null);
                }

                TextView textView = (TextView) convertView;
                */

                if (convertView == null) {
                    convertView = LayoutInflater.from(CalendarActivity.this).inflate(R.layout.calendar_list_item, null);
                }

                /*if (list == null) {
                    try {
                        list = service.findBillByTime(time);
                    } catch (ParseException e) {
                    }
                }*/

                ImageView imgbill = (ImageView) convertView.findViewById(R.id.imgbill);
                TextView txtAmountTile = (TextView) convertView.findViewById(R.id.txtAmountTile);
                TextView txtMoney = (TextView) convertView.findViewById(R.id.txtMoney);
                if (list != null && list.size() > position) {
                    txtAmountTile.setText(list.get(position).getAmountTile());
                    txtMoney.setText((list.get(position).getAmountType() == 0 ? "-" : "") + Double.valueOf(list.get(position).getMoney()).toString());
                    if (list.get(position).getAmountType() == 0)
                        txtMoney.setTextColor(COLOR_RED);
                    else
                        txtMoney.setTextColor(COLOR_GREEN);
                    imgbill.setImageResource(list.get(position).getIcon());

                    txtAmountTile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setClass(CalendarActivity.this, BillDataActivity.class);
                            intent.putExtra("billid", list.get(position).getBillId());
                            CalendarActivity.this.startActivity(intent);
                        }
                    });
                }

                return convertView;
            }
        });
    }

    private String getDisPlayNumber(int num) {
        return num < 10 ? "0" + num : "" + num;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.btn_back:
                CalendarActivity.this.finish();
                break;
            case R.id.tv_add:
            case R.id.ivadd:
                Intent intent = new Intent(CalendarActivity.this, AddBillActivity.class);
                CalendarActivity.this.startActivity(intent);
                break;
            default:
                break;
        }
    }

}
