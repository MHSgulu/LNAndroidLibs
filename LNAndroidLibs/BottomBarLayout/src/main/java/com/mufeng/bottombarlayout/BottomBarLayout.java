package com.mufeng.bottombarlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;



/**
 * @author ChayChan
 * @description: 底部页签根节点
 * @date 2017/6/23  11:02
 */
public class BottomBarLayout extends LinearLayout {

    private ViewPager2 mViewPager;
    private List<BottomBarItem> mItemViews = new ArrayList<>();
    private int mCurrentItem;//当前条目的索引
    private boolean mSmoothScroll;

    public BottomBarLayout(Context context) {
        this(context, null);
    }

    public BottomBarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BottomBarLayout);
        mSmoothScroll = ta.getBoolean(R.styleable.BottomBarLayout_smoothScroll, false);
        ta.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    @Override
    public void setOrientation(int orientation) {
        super.setOrientation(orientation);
    }

    public void setViewPager(ViewPager2 viewPager) {
        this.mViewPager = viewPager;

        if (mViewPager != null) {
            FragmentStateAdapter adapter = (FragmentStateAdapter) mViewPager.getAdapter();
            if (adapter != null && adapter.getItemCount() != getChildCount()) {
                throw new IllegalArgumentException("LinearLayout的子View数量必须和ViewPager条目数量一致");
            }
        }

        if (mViewPager != null) {
            mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    BottomBarLayout.this.onPageSelected(position);
                }
            });
        }
    }

    private void init() {
        mItemViews.clear();
        int childCount = getChildCount();
        if (childCount == 0) {
            return;
        }

        for (int i = 0; i < childCount; i++) {
            if (getChildAt(i) instanceof BottomBarItem) {
                BottomBarItem bottomBarItem = (BottomBarItem) getChildAt(i);
                mItemViews.add(bottomBarItem);
                //设置点击监听
                bottomBarItem.setOnClickListener(new MyOnClickListener(i));
            } else {
                throw new IllegalArgumentException("BottomBarLayout的子View必须是BottomBarItem");
            }
        }

        if (mCurrentItem < mItemViews.size())
            mItemViews.get(mCurrentItem).refreshTab(true);
    }

    public void addItem(BottomBarItem item) {
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1;
        item.setLayoutParams(layoutParams);
        addView(item);
        init();
    }

    public void removeItem(int position) {
        if (position >= 0 && position < mItemViews.size()) {
            BottomBarItem item = mItemViews.get(position);
            if (mItemViews.contains(item)) {
                resetState();
                removeViewAt(position);
                init();
            }
        }
    }

    public void onPageSelected(int position) {
        resetState();
        mItemViews.get(position).refreshTab(true);
        if (onItemSelectedListener != null) {
            onItemSelectedListener.onItemSelected(getBottomItem(position), mCurrentItem, position);
        }
        mCurrentItem = position;//记录当前位置
    }



    private class MyOnClickListener implements OnClickListener {

        private int currentIndex;

        public MyOnClickListener(int i) {
            this.currentIndex = i;
        }

        @Override
        public void onClick(View v) {
            //回调点击的位置
            if (mViewPager != null) {
                //有设置viewPager
                if (currentIndex == mCurrentItem) {
                    //如果还是同个页签，使用setCurrentItem不会回调OnPageSelecte(),所以在此处需要回调点击监听
                    if (onItemSelectedListener != null) {
                        onItemSelectedListener.onItemSelected(getBottomItem(currentIndex), mCurrentItem, currentIndex);
                    }
                } else {
                    mViewPager.setCurrentItem(currentIndex, mSmoothScroll);
                }
            } else {
                //没有设置viewPager
                if (onItemSelectedListener != null) {
                    onItemSelectedListener.onItemSelected(getBottomItem(currentIndex), mCurrentItem, currentIndex);
                }

                updateTabState(currentIndex);
            }
        }
    }

    private void updateTabState(int position) {
        resetState();
        mCurrentItem = position;
        mItemViews.get(mCurrentItem).refreshTab(true);
    }

    /**
     * 重置当前按钮的状态
     */
    private void resetState() {
        if (mCurrentItem < mItemViews.size()) {
            if (mItemViews.get(mCurrentItem).isSelected()){
                mItemViews.get(mCurrentItem).refreshTab(false);
            }
        }
    }

    public void setCurrentItem(int currentItem) {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(currentItem, mSmoothScroll);
        } else {
            if (onItemSelectedListener != null) {
                onItemSelectedListener.onItemSelected(getBottomItem(currentItem), mCurrentItem, currentItem);
            }
            updateTabState(currentItem);
        }
    }

    /**
     * 设置未读数
     *
     * @param position  底部标签的下标
     * @param unreadNum 未读数
     */
    public void setUnread(int position, int unreadNum) {
        mItemViews.get(position).setUnreadNum(unreadNum);
    }

    /**
     * 设置提示消息
     *
     * @param position 底部标签的下标
     * @param msg      未读数
     */
    public void setMsg(int position, String msg) {
        mItemViews.get(position).setMsg(msg);
    }

    /**
     * 隐藏提示消息
     *
     * @param position 底部标签的下标
     */
    public void hideMsg(int position) {
        mItemViews.get(position).hideMsg();
    }

    /**
     * 显示提示的小红点
     *
     * @param position 底部标签的下标
     */
    public void showNotify(int position) {
        mItemViews.get(position).showNotify();
    }

    /**
     * 隐藏提示的小红点
     *
     * @param position 底部标签的下标
     */
    public void hideNotify(int position) {
        mItemViews.get(position).hideNotify();
    }

    public int getCurrentItem() {
        return mCurrentItem;
    }

    public void setSmoothScroll(boolean smoothScroll) {
        this.mSmoothScroll = smoothScroll;
    }

    public BottomBarItem getBottomItem(int position) {
        return mItemViews.get(position);
    }

    private OnItemSelectedListener onItemSelectedListener;

    public interface OnItemSelectedListener {
        void onItemSelected(BottomBarItem bottomBarItem, int previousPosition, int currentPosition);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }
}
