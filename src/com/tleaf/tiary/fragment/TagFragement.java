package com.tleaf.tiary.fragment;

import java.util.ArrayList;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.tleaf.tiary.MainActivity;
import com.tleaf.tiary.R;
import com.tleaf.tiary.adapter.DiaryListAdapter;
import com.tleaf.tiary.db.DataManager;
import com.tleaf.tiary.model.Diary;
import com.tleaf.tiary.util.Util;

/** 태그검색 기능을 담당하는 태그 뷰를 담당하는 클래스 **/
public class TagFragement extends BaseFragment {

	private Context mContext;
	private DataManager dataMgr;

	private String type;
	private ArrayList<Diary> diaryArr;

	private AutoCompleteTextView autotxt;
	private LinearLayout ll;
	private LinearLayout.LayoutParams llp_txt;
	private LinearLayout.LayoutParams llp_img;
	private LinearLayout.LayoutParams llp_layout;

	private ArrayList<String> tagArr;

	private ListView lv;

	private DiaryListAdapter mAdapter;
	private int no;


	public TagFragement() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mContext = getActivity();
		dataMgr = new DataManager(mContext);
		tagArr = new ArrayList<String>();
		diaryArr = new ArrayList<Diary>();
		no = 0;

		View rootView = inflater.inflate(R.layout.fragment_search, container, false);

		//		Spinner spin = (Spinner) rootView.findViewById(R.id.spin_search);
		//		spin.setVisibility(View.GONE);

		ArrayList<String> strArr = dataMgr.getDistinctTagList();

		ArrayAdapter<String> wordApater = new ArrayAdapter<String>(mContext,
				android.R.layout.simple_dropdown_item_1line, strArr);
		autotxt = (AutoCompleteTextView) rootView.findViewById(R.id.autotxt_search);
		autotxt.setAdapter(wordApater);


		ImageView img_search = (ImageView) rootView.findViewById(R.id.img_search);
		img_search.setOnClickListener(cl);


		lv = (ListView) rootView.findViewById(R.id.list_diary_search);
		lv.setOnItemClickListener(mItemClickListener);

		mAdapter = new DiaryListAdapter(mContext, R.layout.item_diary_); 
		lv.setAdapter(mAdapter);

		ll = (LinearLayout) rootView.findViewById(R.id.layout_serachWord);
		llp_txt = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		llp_txt.setMargins(7, 5, 5, 5);
		llp_img = new LinearLayout.LayoutParams(40, 40);
		llp_img.setMargins(0, 5, 5, 5);

		llp_layout = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		llp_layout.setMargins(5, 5, 8, 5);

		return rootView;
	}

	class Info {
		int viewIndex;
		String tagContent;

		Info(int viewIndex, String tagContent) {
			this.viewIndex = viewIndex;
			this.tagContent = tagContent;
		}

	}

	private OnClickListener cl = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Util.hideKeyboard(mContext, autotxt.getApplicationWindowToken());
			String tag = autotxt.getText().toString().trim();
			if(tagArr.contains(tag)) {
				Util.tst(mContext, "이미 선택한 태그 입니다");
				return;
			}
			if(TextUtils.isEmpty(tag)) {
				Util.tst(mContext, "원하는 태그를 입력해주세요");
				return;
			}
			if(!dataMgr.isContainedTag(tag)){
				Util.tst(mContext, "입력하신 태그에 해당하는 일기가 없습니다");
				return;
			}
			tagArr.add(tag);
			//			int lastIndex = tagArr.size() -1;
			putTagTextViewInLayout(tag);
			setDiaryList();
		}
	};

	/** 검색 목록에 들어간 태그를 삭제 클릭시 처리하는 메서드 **/
	private OnClickListener cl_delete = new OnClickListener() {

		@Override
		public void onClick(View v) {
			ImageView layout = (ImageView) v;
			Info i = (Info)layout.getTag();

			ll.removeViewAt(i.viewIndex);
			tagArr.remove(i.tagContent);
			setDiaryList();
		}
	};

	/** 태그 검색어를 추가시 검색 목록에 태그를 동적으로 추가해주는 메서드 **/
	private void putTagTextViewInLayout(String tag) {//(int lastIndex) {
		int no = 0;
		LinearLayout layout = new LinearLayout(mContext);
		layout.setLayoutParams(llp_layout);
		layout.setBackgroundColor(getResources().getColor(R.color.bottom_menu));
		layout.setGravity(Gravity.CENTER_VERTICAL);
		layout.setPadding(5, 5, 5, 5);

		TextView tx = new TextView(mContext);
		tx.setLayoutParams(llp_txt);
		tx.setTextSize(20);
		tx.setTextColor(getResources().getColor(R.color.diary_title));
		tx.setText(tag);

		//		tx.setText(tagArr.get(lastIndex));

		ImageView img = new ImageView(mContext);
		img.setLayoutParams(llp_img);
		img.setImageResource(R.drawable.tag_cancel);
		img.setColorFilter(getResources().getColor(R.color.diary_title));
		img.setOnClickListener(cl_delete);


		Info i = new Info(no, tag);
		img.setTag(i);

		layout.addView(tx);
		layout.addView(img);

		ll.addView(layout, no++);
	}

	/** 태그 검색 목록에 따른 다이어리 목록을 가져오는 메서드 **/
	private void setDiaryList() {
		Util.ll("tagArr", tagArr.toString());
		diaryArr.clear();
		for(int i = 0; i < tagArr.size(); i++) {
			diaryArr.addAll(dataMgr.getDiaryListByTag(tagArr.get(i)));
		}
		mAdapter.updateItem(diaryArr);
	}

	private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Diary diary = diaryArr.get(position);
			Fragment fragment = new DiaryFragment(diary);
			MainActivity.changeFragment(fragment);
		}

	};


	@Override
	public boolean onBackPressed() {
		// TODO Auto-generated method stub
		return false;
	}


}
