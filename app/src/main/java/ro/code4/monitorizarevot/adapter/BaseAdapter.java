package ro.code4.monitorizarevot.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected Context mContext;

    protected List<T> mItems;

    public BaseAdapter(Context context, List<T> items) {
        mContext = context;
        mItems = (items == null) ? new ArrayList<T>() : items;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public T getItem(int position) {
        if (position >= 0 && position < getItemCount()) {
            return mItems.get(position);
        }

        return null;
    }

    public List<T> getItems() {
        return mItems;
    }

    public void setItems(List<T> items) {
        mItems = (items == null) ? new ArrayList<T>() : items;

        notifyDataSetChanged();
    }

    public void addItem(T item) {
        mItems.add(item);

        notifyItemInserted(mItems.size());
    }
}
