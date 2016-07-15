package br.org.eldorado.workshop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {
    private static final Integer[] IMAGES = new Integer[]{android.R.drawable.ic_menu_add,
            android.R.drawable.ic_dialog_alert, android.R.drawable.ic_lock_silent_mode,
            android.R.drawable.ic_popup_reminder, android.R.drawable.ic_dialog_dialer,
            android.R.drawable.ic_menu_preferences, android.R.drawable.ic_menu_camera,
            android.R.drawable.ic_menu_revert, android.R.drawable.ic_menu_mylocation,
            android.R.drawable.ic_lock_idle_low_battery, android.R.drawable.ic_menu_report_image,
            android.R.drawable.ic_menu_manage, android.R.drawable.ic_media_next,
            android.R.drawable.ic_partial_secure, android.R.drawable.ic_media_play,
            android.R.drawable.ic_lock_idle_alarm, android.R.drawable.ic_menu_day,
            android.R.drawable.ic_menu_zoom, android.R.drawable.ic_menu_edit,
            android.R.drawable.ic_menu_myplaces, android.R.drawable.ic_menu_mapmode,
            android.R.drawable.ic_menu_delete, android.R.drawable.ic_menu_today,
            android.R.drawable.ic_menu_sort_alphabetically, android.R.drawable.ic_menu_call,
            android.R.drawable.ic_menu_my_calendar, android.R.drawable.ic_menu_info_details,
            android.R.drawable.ic_menu_more, android.R.drawable.ic_menu_month};

    private Context mContext;

    public ImageAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return IMAGES.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        GridItemHolder itemHolder;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.grid_item, viewGroup, false);
            itemHolder = new GridItemHolder();
            itemHolder.imageView = (ImageView) view.findViewById(R.id.gridImageItem);
            itemHolder.textView = (TextView) view.findViewById(R.id.gridTextItem);
            view.setTag(itemHolder);
        } else {
            itemHolder = (GridItemHolder) view.getTag();
        }
        itemHolder.imageView.setImageResource(IMAGES[i]);
        itemHolder.textView.setText(mContext.getString(R.string.grid_item_text, i));

        return view;
    }

    private class GridItemHolder {
        ImageView imageView;
        TextView textView;
    }
}
