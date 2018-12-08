package ro.code4.monitorizarevot.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RvListener implements RecyclerView.OnItemTouchListener {

    @Nullable
    private View mChildView;

    private int mChildViewPosition;

    private GestureDetector mGestureDetector;

    private OnItemClickListener mListener;

    public RvListener(Context context, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mChildView = rv.findChildViewUnder(e.getX(), e.getY());
        mChildViewPosition = rv.getChildLayoutPosition(mChildView);

        return mChildView != null && mGestureDetector.onTouchEvent(e);
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    public interface OnItemClickListener {

        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public static class ItemClickListenerAdapter implements OnItemClickListener {

        @Override
        public void onItemClick(View view, int position) {
        }

        @Override
        public void onItemLongClick(View view, int position) {
        }
    }

    protected class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (mChildView != null) {
                mListener.onItemClick(mChildView, mChildViewPosition);
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            if (mChildView != null) {
                mListener.onItemLongClick(mChildView, mChildViewPosition);
            }
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }
}
