package com.tleaf.tiary.photo;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.tleaf.tiary.R;
import com.tleaf.tiary.model.MyGallery;


public class GalleryAdapter_ extends BaseAdapter {

	private Context mContext;
	private LayoutInflater infalter;
	private ArrayList<MyGallery> photoArr = new ArrayList<MyGallery>();
	private	ImageLoader imageLoader;

	public GalleryAdapter_(Context c, ImageLoader imageLoader) {
		infalter = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContext = c;
		this.imageLoader = imageLoader;
		// clearCache();
	}

	@Override
	public int getCount() {
		return photoArr.size();
	}

	@Override
	public MyGallery getItem(int position) {
		return photoArr.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}


	public ArrayList<MyGallery> getSelected() {
		ArrayList<MyGallery> arr = new ArrayList<MyGallery>();
		for (int i = 0; i < photoArr.size(); i++) {
			if (photoArr.get(i).isSeleted) {
				arr.add(photoArr.get(i));
			}
		}
		return arr;
	}

	public void addAll(ArrayList<MyGallery> arr) {
		try {
			this.photoArr.clear();
			this.photoArr.addAll(arr);

		} catch (Exception e) {
			e.printStackTrace();
		}
		notifyDataSetChanged();
	}

	public void changeSelection(View v, int position) {
		if (photoArr.get(position).isSeleted) {
			photoArr.get(position).isSeleted = false;
		} else {
			photoArr.get(position).isSeleted = true;
		}

		((ViewHolder) v.getTag()).img_photo_select.setSelected(photoArr
				.get(position).isSeleted);
	}


	public void clearCache() {
		imageLoader.clearDiscCache();
		imageLoader.clearMemoryCache();
	}

	public void clear() {
		photoArr.clear();
		notifyDataSetChanged();


	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = infalter.inflate(R.layout.item_gallery, null);
			holder = new ViewHolder();
			holder.img_photo = (ImageView) convertView.findViewById(R.id.img_gallery_photo);
			holder.img_photo_select = (ImageView) convertView.findViewById(R.id.img_gallery_select);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}



		holder.img_photo.setTag(position); //포지션 저장

		try {
			imageLoader.displayImage("file://" + photoArr.get(position).sdcardPath,
					holder.img_photo, new SimpleImageLoadingListener() {
				@Override
				public void onLoadingStarted(String imageUri, View view) {
					holder.img_photo.setImageResource(R.drawable.no_media);
					super.onLoadingStarted(imageUri, view);
				}
			});

			holder.img_photo_select.setSelected(photoArr.get(position).isSeleted);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertView;
	}


	public class ViewHolder {
		ImageView img_photo;
		ImageView img_photo_select;
	}


	//	private boolean isActionMultiplePick;
	//	public void setMultiplePick(boolean isMultiplePick) {
	//		this.isActionMultiplePick = isMultiplePick;
	//	}

	//	public void selectAll(boolean selection) {
	//		for (int i = 0; i < data.size(); i++) {
	//			data.get(i).isSeleted = selection;
	//
	//		}
	//		notifyDataSetChanged();
	//	}

	//	public boolean isAllSelected() {
	//		boolean isAllSelected = true;
	//
	//		for (int i = 0; i < data.size(); i++) {
	//			if (!data.get(i).isSeleted) {
	//				isAllSelected = false;
	//				break;
	//			}
	//		}
	//
	//		return isAllSelected;
	//	}

	//	public boolean isAnySelected() {
	//		boolean isAnySelected = false;
	//		for (int i = 0; i <photoArr.size(); i++) {
	//			if (photoArr.get(i).isSeleted) {
	//				isAnySelected = true;
	//				break;
	//			}
	//		}
	//		return isAnySelected;
	//	}
}
