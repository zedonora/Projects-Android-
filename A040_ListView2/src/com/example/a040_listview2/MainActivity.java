package com.example.a040_listview2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

class Item {
	String name = "";
	String eng = "";
	public Item() {
	}
	public Item(String name, String eng) {
		this.name = name;
		this.eng = eng;
	}
	
}
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// 1. 다량의 데이터
		// 2. AdapterView (ListView)
		// 3. Adapter 생성 후 연결 (한행을 디자인한 layout 준비)
		Item item[] = {new Item("사과","apple"),
				new Item("배","pear"),
				new Item("수박","watermelon"),
				new Item("딸기","strawberry"),
				new Item("포도","grape"),
				new Item("자몽","grapefruit"),
				new Item("바나나","banana"),
				new Item("호박","pumpkin"),
				new Item("오이","cucumber"),
				new Item("사과","apple"),
				new Item("배","pear"),
				new Item("수박","watermelon"),
				new Item("딸기","strawberry"),
				new Item("포도","grape"),
				new Item("자몽","grapefruit"),
				new Item("바나나","banana"),
				new Item("호박","pumpkin"),
				new Item("오이","cucumber"),
				new Item("사과","apple"),
				new Item("배","pear"),
				new Item("수박","watermelon"),
				new Item("딸기","strawberry"),
				new Item("포도","grape"),
				new Item("자몽","grapefruit"),
				new Item("바나나","banana"),
				new Item("호박","pumpkin"),
				new Item("오이","cucumber"),
				new Item("사과","apple"),
				new Item("배","pear"),
				new Item("수박","watermelon"),
				new Item("딸기","strawberry"),
				new Item("포도","grape"),
				new Item("자몽","grapefruit"),
				new Item("바나나","banana"),
				new Item("호박","pumpkin"),
				new Item("오이","cucumber"),
				new Item("사과","apple"),
				new Item("배","pear"),
				new Item("수박","watermelon"),
				new Item("딸기","strawberry"),
				new Item("포도","grape"),
				new Item("자몽","grapefruit"),
				new Item("바나나","banana"),
				new Item("호박","pumpkin"),
				new Item("오이","cucumber")
		};
//		String str[] = {"사과","배","수박","딸기","포도","자몽","바나나","호박","오이",
//						"사과","배","수박","딸기","포도","자몽","바나나","호박","오이",
//						"사과","배","수박","딸기","포도","자몽","바나나","호박","오이",
//						"사과","배","수박","딸기","포도","자몽","바나나","호박","오이",
//						"사과","배","수박","딸기","포도","자몽","바나나","호박","오이"};
//		String eng[] = {"apple","pear","watermelon","strawberry","grape","grapefruit",
//				"banana","pumpkin","cucumber",
//				"apple","pear","watermelon","strawberry","grape","grapefruit",
//				"banana","pumpkin","cucumber",
//				"apple","pear","watermelon","strawberry","grape","grapefruit",
//				"banana","pumpkin","cucumber",
//				"apple","pear","watermelon","strawberry","grape","grapefruit",
//				"banana","pumpkin","cucumber",
//				"apple","pear","watermelon","strawberry","grape","grapefruit",
//				"banana","pumpkin","cucumber",
//				"apple","pear","watermelon","strawberry","grape","grapefruit",
//				"banana","pumpkin","cucumber",
//		};
		ListView lv = (ListView) findViewById(R.id.listView1);
		MyAdapter ma = new MyAdapter(getApplicationContext(), // 현재화면의 제어권자
									R.layout.row, // 리스트 뷰의 한행의 디자인
									item); // 다량의 데이터
		lv.setAdapter(ma);
		
	} // end of onCreate
} // end of class
class MyAdapter extends BaseAdapter{
	int row;
	Item item[];
	LayoutInflater lif; // xml의 레이아웃을 읽어와서 객체로 사용하기 위해서 필요
	public MyAdapter(Context context, int row, Item[] item) {
		this.row = row;
		this.item = item;
		this.lif = (LayoutInflater) context.getSystemService
									(Context.LAYOUT_INFLATER_SERVICE);
		// LayoutInflater 객체 얻어오기
	}
	@Override
	public int getCount() { // (필수) ListView 에 몇 항목을 보여줄지
		return item.length;
	}
	@Override
	public Object getItem(int position) { // (선택) position번째의 내용을 리턴
		return item[position].name+":"+item[position].eng;
	}
	@Override
	public long getItemId(int position) { // (선택) position번째의 유니크한 id를 리턴
		return position;
	}
	// (필수) 해당 Position번째의 레이아웃에 어떤 데이터를 보여줄지를 설정
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			// convertView 과정이 시간이 오래걸린다.
			convertView = lif.inflate(/*R.layout.row*/row, null); // xml 레이아웃을 객체로 변환
		}
		// 한행의 레이아웃에 데이터를 연결
//		TextView tv = (TextView) findViewById(R.id.textView1); -> Activity에서 상속을 받아야 
//		findViewById를 사용할 수 있기에 대신에 쓸수 있게 LayoutInflator를 사용했다. 
		TextView tv = (TextView) convertView.findViewById(R.id.textView1);
		tv.setText(item[position].name);
		TextView tv2 = (TextView) convertView.findViewById(R.id.textView2);
		tv2.setText(item[position].eng);
		
		return convertView;
	}
} // end of class MyAdapter
