package yao.muyuer.dailyrecord.activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import yao.muyuer.dailyrecord.R;
import yao.muyuer.dailyrecord.dbservice.UserService;
import yao.muyuer.library.activity.base.BaseActivity;

public class ReportAllActivity extends BaseActivity implements OnClickListener {

	private Button btn_back;
	private TextView txtZc, txtSy, txtJy, txtJyl;
	private UserService service;
	private ProgressBar pb;

	private LineChartView lineChart;
	String[] date = {"10-22","11-22","12-22","1-22","6-22","5-23","5-22","6-22","5-23","5-22"};//X轴的标注
	float[] score= {50,42,90,33,10,74,22,18,79,20};//图表的数据点
	float[] score2;
	private List<PointValue> mPointValues = new ArrayList<PointValue>();
	private List<PointValue> mPointValues2 = new ArrayList<PointValue>();
	private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}
	@Override
	protected int setLayoutResourceID() {
		return R.layout.report_all_layout;
	}
	@Override
	protected void setUpView() {
		btn_back=(Button)findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
		txtZc=(TextView)findViewById(R.id.txtZc);
		txtSy=(TextView)findViewById(R.id.txtSy);
		txtJy=(TextView)findViewById(R.id.txtJy);
		txtJyl=(TextView)findViewById(R.id.txtJyl);
		pb=(ProgressBar)findViewById(R.id.pb);
		txtZc.setOnClickListener(this);


		service = new UserService(this);
		lineChart = (LineChartView)findViewById(R.id.line_chart);

		initView();
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_back:
			ReportAllActivity.this.finish();
			break;
		default:
			break;
		}
	}

	private void initView(){
		double sy=service.findBillAllMoney(1);
		double zc=service.findBillAllMoney(0);

		txtZc.setText(zc + "元");
		txtSy.setText(sy + "元");
		txtJy.setText(sy - zc + "元");
		int jyl = (int)(100-(zc/(sy+0.00))*100);
		txtJyl.setText("你的结余率为"+jyl+"%");
		pb.setProgress(jyl);

		score= service.findBillListAllMoney(0);
		score2= service.findBillListAllMoney(1);
		date=service.findBillListAllMoneyMc(0);
		getAxisXLables();//获取x轴的标注
		getAxisPoints();//获取坐标点
		initLineChart();//初始化
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==1){
			switch (resultCode) {
			case RESULT_OK:
				Toast.makeText(ReportAllActivity.this, "第二个页面返回的文件名：" + data.getStringExtra("fname"), Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		}
		
	}

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
		for (int i = 0; i < score2.length; i++) {
			mPointValues2.add(new PointValue(i, score2[i]));
		}
	}
	private void initLineChart() {
		Line line = new Line(mPointValues).setColor(Color.parseColor("#22ae10"));  //折线的颜色（橙色）
		List<Line> lines = new ArrayList<Line>();
		line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
		line.setCubic(false);//曲线是否平滑，即是曲线还是折线
		line.setFilled(false);//是否填充曲线的面积
		line.setHasLabels(true);//曲线的数据坐标是否加上备注
//      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
		line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
		line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
		lines.add(line);

		Line line2 = new Line(mPointValues2).setColor(Color.parseColor("#f4624e"));  //折线的颜色（橙色）
		line2.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
		line2.setCubic(false);//曲线是否平滑，即是曲线还是折线
		line2.setFilled(false);//是否填充曲线的面积
		line2.setHasLabels(true);//曲线的数据坐标是否加上备注
//      line2.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
		line2.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
		line2.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）

		lines.add(line2);
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

}
