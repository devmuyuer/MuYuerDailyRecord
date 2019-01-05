package yao.muyuer.dailyrecord.activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import lecho.lib.hellocharts.formatter.ColumnChartValueFormatter;
import lecho.lib.hellocharts.formatter.SimpleColumnChartValueFormatter;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;
import yao.muyuer.dailyrecord.R;
import yao.muyuer.dailyrecord.constants.Config;
import yao.muyuer.dailyrecord.dbservice.UserService;
import yao.muyuer.library.fragment.base.BaseFragment;
import yao.muyuer.library.xlistviewutil.XListView;

public class ReportActivity extends BaseFragment implements OnClickListener,XListView.IXListViewListener {

	//无关变量
  private static final String LOG_TAG="<<<<<<日志>>>>>>";

	private long e;
	private RelativeLayout rl_all;
	private TextView txt_all;
	private TextView tvHint, txtHint1, txtHint2, txtHint3;

	private LineChartView lineChart;
	String[] date = {"10-22","11-22","12-22","1-22","6-22","5-23","5-22","6-22","5-23","5-22"};//X轴的标注
	int[] score= {50,42,90,33,10,74,22,18,79,20};//图表的数据点
	private List<PointValue> mPointValues = new ArrayList<PointValue>();
	private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();

	private PieChartView pieChart;
	private PieChartData pieChardata;
	List<SliceValue> pieValues = new ArrayList<SliceValue>();
	private int[] pieData = {21,20,9,2,8,33,14,12};

    private ColumnChartView columnChart, columnChart2;
    private ColumnChartData columnChardata;

    List<Column> columnValues = new ArrayList<Column>();
    private List<Float> columnList = new ArrayList<Float>();
	public final static String[] week = new String[]{"用餐", "零食", "交通", "充值", "购物", "娱乐", "住房"};
    float[] columnScore= {12f,15f,0.6f,0.33f,10f,0.74f,0.22f,18f,0.79f,0.20f};
    /**
 * 获得数据库note表相关数据所用变量
 */
	private UserService service;
	/**
	 * 通讯相关的变量定义
	 */
	private static final int Code_Note = 1;
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	@Override
	protected int setLayoutResourceID() {
		return R.layout.report_layout;
	}
	@Override
	protected void setUpView() {
		tvHint = $(R.id.tvHint);
		txtHint1 = $(R.id.txtHint1);
		service = new UserService(this.getActivity());
		double zc=service.findBillMoney(0);
		txtHint1.setText("本月从1日开始已支出" + zc + "元");
		txtHint2 = $(R.id.txtHint2);
		Calendar now = Calendar.getInstance();
		int day = now.get(Calendar.DAY_OF_MONTH);
		txtHint2.setText("日均" + String.format("%.2f",zc/day) + "元");

		txtHint3 = $(R.id.txtHint3);
		zc=service.findBillMoney(1);
		txtHint3.setText("本月从1日开始已收入" + zc + "元");

		tvHint.setText("记账第" + Config.FoundingTime + "，原你经济独立，有事做有人爱");

		rl_all = $(R.id.rl_all);
		txt_all= $(R.id.txt_all);
		rl_all.setOnClickListener(this);
		txt_all.setOnClickListener(this);

        columnChart = $(R.id.column_chart);
		columnChart2 = $(R.id.column_chart2);
        initColumnChart();
		initColumnChart2();
    }

    /**
     * 柱状图监听器
     *
     * @author 1017
     *
     */
    private class ValueTouchListener implements
            ColumnChartOnValueSelectListener {

        @Override
        public void onValueSelected(int columnIndex, int subcolumnIndex,
                                    SubcolumnValue value) {
// generateLineData(value.getColor(), 100);
        }

        @Override
        public void onValueDeselected() {

// generateLineData(ChartUtils.COLOR_GREEN, 0);

        }
    }
    /**
     * 初始化
     */
    private void initColumnChart() {
		columnScore= service.findBillListMoney(0);
		String[] week=service.findBillListMoneyMc(0);
        // 使用的 7列，每列1个subcolumn。
        int numSubcolumns = 1;
        int numColumns = columnScore.length;
        //定义一个圆柱对象集合
        List<Column> columns = new ArrayList<Column>();
        //子列数据集合
        List<SubcolumnValue> values;

        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        //遍历列数numColumns
        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<SubcolumnValue>();
            //遍历每一列的每一个子列
            for (int j = 0; j < numSubcolumns; ++j) {
                //为每一柱图添加颜色和数值
                float f = columnScore[i];
                values.add(new SubcolumnValue(f, ChartUtils.pickColor()));
            }
            //创建Column对象
            Column column = new Column(values);
            //这一步是能让圆柱标注数据显示带小数的重要一步 让我找了好久问题
            //作者回答https://github.com/lecho/hellocharts-android/issues/185
            ColumnChartValueFormatter chartValueFormatter = new SimpleColumnChartValueFormatter(2);
            column.setFormatter(chartValueFormatter);
            //是否有数据标注
            column.setHasLabels(true);
            //是否是点击圆柱才显示数据标注
            column.setHasLabelsOnlyForSelected(false);
            columns.add(column);
            //给x轴坐标设置描述
            axisValues.add(new AxisValue(i).setLabel(week[i]));
        }
        //创建一个带有之前圆柱对象column集合的ColumnChartData
        columnChardata= new ColumnChartData(columns);

        //定义x轴y轴相应参数
        Axis axisX = new Axis();
        Axis axisY = new Axis().setHasLines(true);
        axisY.setName("支出分类(%)");//轴名称
        axisY.hasLines();//是否显示网格线
        axisY.setTextColor(R.color.font_color);//颜色

        axisX.hasLines();
        axisX.setTextColor(R.color.font_color);
        axisX.setValues(axisValues);
        //把X轴Y轴数据设置到ColumnChartData 对象中
        columnChardata.setAxisXBottom(axisX);
        columnChardata.setAxisYLeft(axisY);
        //给表填充数据，显示出来
        columnChart.setColumnChartData(columnChardata);

    }
	private void initColumnChart2() {
		columnScore= service.findBillListMoney(1);
		String[] week=service.findBillListMoneyMc(1);
		// 使用的 7列，每列1个subcolumn。
		int numSubcolumns = 1;
		int numColumns = columnScore.length;
		//定义一个圆柱对象集合
		List<Column> columns = new ArrayList<Column>();
		//子列数据集合
		List<SubcolumnValue> values;

		List<AxisValue> axisValues = new ArrayList<AxisValue>();
		//遍历列数numColumns
		for (int i = 0; i < numColumns; ++i) {

			values = new ArrayList<SubcolumnValue>();
			//遍历每一列的每一个子列
			for (int j = 0; j < numSubcolumns; ++j) {
				//为每一柱图添加颜色和数值
				float f = columnScore[i];
				values.add(new SubcolumnValue(f, ChartUtils.pickColor()));
			}
			//创建Column对象
			Column column = new Column(values);
			//这一步是能让圆柱标注数据显示带小数的重要一步 让我找了好久问题
			//作者回答https://github.com/lecho/hellocharts-android/issues/185
			ColumnChartValueFormatter chartValueFormatter = new SimpleColumnChartValueFormatter(2);
			column.setFormatter(chartValueFormatter);
			//是否有数据标注
			column.setHasLabels(true);
			//是否是点击圆柱才显示数据标注
			column.setHasLabelsOnlyForSelected(false);
			columns.add(column);
			//给x轴坐标设置描述
			axisValues.add(new AxisValue(i).setLabel(week[i]));
		}
		//创建一个带有之前圆柱对象column集合的ColumnChartData
		columnChardata= new ColumnChartData(columns);

		//定义x轴y轴相应参数
		Axis axisX = new Axis();
		Axis axisY = new Axis().setHasLines(true);
		axisY.setName("收入分类(%)");//轴名称
		axisY.hasLines();//是否显示网格线
		axisY.setTextColor(R.color.font_color);//颜色

		axisX.hasLines();
		axisX.setTextColor(R.color.font_color);
		axisX.setValues(axisValues);
		//把X轴Y轴数据设置到ColumnChartData 对象中
		columnChardata.setAxisXBottom(axisX);
		columnChardata.setAxisYLeft(axisY);
		//给表填充数据，显示出来
		columnChart2.setColumnChartData(columnChardata);

	}


	/**
	 * 初始化
	 */
	private void initPieChart() {
        pieChardata = new PieChartData();
        pieChardata.setHasLabels(true);//显示表情
        pieChardata.setHasLabelsOnlyForSelected(false);//不用点击显示占的百分比
        pieChardata.setHasLabelsOutside(false);//占的百分比是否显示在饼图外面
        pieChardata.setHasCenterCircle(true);//是否是环形显示
        pieChardata.setValues(pieValues);//填充数据
        pieChardata.setCenterCircleColor(Color.WHITE);//设置环形中间的颜色
        pieChardata.setCenterCircleScale(0.5f);//设置环形的大小级别
        pieChardata.setCenterText1("饼图测试");//环形中间的文字1
        pieChardata.setCenterText1Color(Color.BLACK);//文字颜色
        pieChardata.setCenterText1FontSize(14);//文字大小

        pieChardata.setCenterText2("饼图测试");
        pieChardata.setCenterText2Color(Color.BLACK);
        pieChardata.setCenterText2FontSize(18);
        /**这里也可以自定义你的字体   Roboto-Italic.ttf这个就是你的字体库*/
//		Typeface tf = Typeface.createFromAsset(this.getAssets(), "Roboto-Italic.ttf");
//		data.setCenterText1Typeface(tf);

        pieChart.setPieChartData(pieChardata);
        pieChart.setValueSelectionEnabled(true);//选择饼图某一块变大
        pieChart.setAlpha(0.9f);//设置透明度
        pieChart.setCircleFillRatio(1f);//设置饼图大小

	}

	/**
	 * 监听事件
	 */
	private PieChartOnValueSelectListener selectListener = new PieChartOnValueSelectListener() {

		@Override
		public void onValueDeselected() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onValueSelected(int arg0, SliceValue value) {
			// TODO Auto-generated method stub
			//Toast.makeText(ReportActivity.this, "Selected: " + value.getValue(), Toast.LENGTH_SHORT).show();
		}
	};

	/**
	 * 设置X 轴的显示
	 */
	private void getAxisXLables() {
		for (int i = 0; i < date.length; i++) {
			mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
		}
	}
	/**
	 * 图表的每个点的显示
	 */
	private void getAxisPoints() {
		for (int i = 0; i < score.length; i++) {
			mPointValues.add(new PointValue(i, score[i]));
		}
	}
	private void initLineChart() {
		Line line = new Line(mPointValues).setColor(Color.parseColor("#FFCD41"));  //折线的颜色（橙色）
		List<Line> lines = new ArrayList<Line>();
		line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
		line.setCubic(false);//曲线是否平滑，即是曲线还是折线
		line.setFilled(false);//是否填充曲线的面积
		line.setHasLabels(true);//曲线的数据坐标是否加上备注
//      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
		line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
		line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
		lines.add(line);
		LineChartData data = new LineChartData();
		data.setLines(lines);

		//坐标轴
		Axis axisX = new Axis(); //X轴
		axisX.setHasTiltedLabels(true);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
		axisX.setTextColor(Color.GRAY);  //设置字体颜色
		//axisX.setName("date");  //表格名称
		axisX.setTextSize(10);//设置字体大小
		axisX.setMaxLabelChars(8); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
		axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
		data.setAxisXBottom(axisX); //x 轴在底部
		//data.setAxisXTop(axisX);  //x 轴在顶部
		axisX.setHasLines(true); //x 轴分割线

		// Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
		Axis axisY = new Axis();  //Y轴
		axisY.setName("");//y轴标注
		axisY.setTextSize(10);//设置字体大小
		data.setAxisYLeft(axisY);  //Y轴设置在左边
		//data.setAxisYRight(axisY);  //y轴设置在右边


		//设置行为属性，支持缩放、滑动以及平移
		lineChart.setInteractive(true);
		lineChart.setZoomType(ZoomType.HORIZONTAL);
		lineChart.setMaxZoom((float) 2);//最大方法比例
		lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
		lineChart.setLineChartData(data);
		lineChart.setVisibility(View.VISIBLE);
		/**注：下面的7，10只是代表一个数字去类比而已
		 * 当时是为了解决X轴固定数据个数。见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
		 */
		Viewport v = new Viewport(lineChart.getMaximumViewport());
		v.left = 0;
		v.right = 7;
		lineChart.setCurrentViewport(v);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = null;
		switch (v.getId()) {
		case R.id.rl_all:
			intent = new Intent(getActivity(), ReportAllActivity.class);
			getActivity().startActivity(intent);
			break;
		case R.id.txt_all:
			intent = new Intent(getActivity(), ReportAllActivity.class);
			getActivity().startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		
	}
}
