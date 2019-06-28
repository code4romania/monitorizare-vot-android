package ro.code4.monitorizarevot.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ro.code4.monitorizarevot.net.model.County;

public class CountyAdapter extends ArrayAdapter<County> {

    public CountyAdapter(Context context, int textViewResourceId, List<County> items) {
        super(context, textViewResourceId, items);
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView result = (TextView) super.getView(position, convertView, parent);
        result.setText(getTextFor(position));
        return result;
    }

    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView result = (TextView) super.getDropDownView(position, convertView, parent);
        result.setText(getTextFor(position));
        return result;
    }

    private String getTextFor(int position) {
        County county = getItem(position);
        assert county != null;
        return county.getName();
    }

    @Override
    public int getPosition(@Nullable County item) {
        if (item == null) {
            return -1;
        }

        for (int i = 0; i < getCount(); i ++) {
            County registeredItem = getItem(i);
            assert registeredItem != null;
            if (registeredItem.getId() == item.getId()) {
                return i;
            }
        }

        return super.getPosition(item);
    }
}
