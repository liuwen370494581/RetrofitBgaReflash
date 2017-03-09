package star.liuwen.com.startretrofit.widget;

/**
 * Created by cuishijie on 2016/12/20.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import star.liuwen.com.startretrofit.R;


/**
 * Created by cuishijie on 2016/12/16.
 * LoadingLayout 是一个封装了加载页，结果页，无结果页，错误页切换的{@link FrameLayout}
 * 可以轻松切换当前页面状态通过{@link #showLoading()} {@link #showNetError()} {@link #showResult()} {@link #showNoResult()}
 * <p>
 * 默认情况下，整个错误页面是可以点击的，用户可以通过点击错误页面来重新加载数据。
 * 允许通过{@link #setNetErrorClickableView(int)}或者xml中添加"netErrorClickableView:@+id/resId"属性来设置可点击的view
 * 使用{@link #setOnNetErrorClickListener(OnClickListener)}监听用户对错误页面的点击行为。
 * <p>
 * 此外，允许在xml中自定义不同状态的页面布局
 * app:loadingLayout="@layout/default_loading_layout"
 * app:netErrorClickableView="@+id/clickableView"
 * app:netErrorLayout="@layout/default_net_error_layout"
 * app:noResultLayout="@layout/default_no_result_layout"
 * <p>
 * 和scrollView一样，只允许拥有一个直接子view。如果有复杂的层级UI，请在唯一个子layout中实现
 */

public class LoadingLayout extends FrameLayout {
    private static final String TAG = "LoadingLayout";
    private LayoutInflater mInflater;
    private View mLoadingView, mNoResultView, mNetErrorView;
    private View mContentView;
    private OnClickListener mOnRetryClickListener;
    private View mNetErrorClickableView = null;
    //TODO define your default loadingView, noResultView, netErrorView below And just define it in the xml layout when u need a custom layout some time
    private static final int defaultLoadingResId = 0;
    private static final int defaultNoResultResId = 0;
    private static final int defaultNetErrorResId = 0;

    public LoadingLayout(Context context) {
        super(context);
        init(null);
    }

    public LoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public View setLoadingView(@LayoutRes int layout) {
        removeView(mLoadingView);
        mLoadingView = mInflater.inflate(layout, null);
        if (mLoadingView != null) {
            mLoadingView.setTag(TAG);
            addView(mLoadingView);
        } else {
            throw new IllegalStateException("No such layout found in res folder !");
        }
        return mLoadingView;
    }

    public View getLoadingView() {
        return mLoadingView;
    }

    public View setNoResultView(@LayoutRes int layout) {
        removeView(mNoResultView);
        mNoResultView = mInflater.inflate(layout, null);
        if (mNoResultView != null) {
            mNoResultView.setTag(TAG);
            addView(mNoResultView);
        } else {
            throw new IllegalStateException("No such layout found in res folder !");
        }
        return mNoResultView;
    }

    public View getNoResultView() {
        return mNoResultView;
    }


    public View setNetErrorView(@LayoutRes int layout) {
        return this.setNetErrorView(layout, 0);
    }

    /**
     * @param layout
     * @param clickableResId defines a clickable view in the netErrorView, which is usually used to retry the data fetching
     *                       after a net error occurred. Use {@link #setOnNetErrorClickListener(OnClickListener)} to monitor
     *                       user's behavior.
     *                       resId = 0, which means the whole netErrorView is clickable.
     */
    public View setNetErrorView(@LayoutRes int layout, @IdRes int clickableResId) {
        removeView(mNetErrorView);
        mNetErrorView = mInflater.inflate(layout, null);
        if (mNetErrorView != null) {
            if (clickableResId != 0) {
                mNetErrorClickableView = mNetErrorView.findViewById(clickableResId);
            }
            if (mNetErrorClickableView == null) {
                mNetErrorClickableView = mNetErrorView;
            }
            if (mOnRetryClickListener != null && mNetErrorClickableView != null) {
                mNetErrorClickableView.setOnClickListener(mOnRetryClickListener);
            }
            mNetErrorView.setTag(TAG);
            addView(mNetErrorView);
        } else {
            throw new IllegalStateException("No such layout found in res folder !");
        }
        return mNetErrorView;
    }

    public View getNetErrorView() {
        return mNetErrorView;
    }

    public void setNetErrorClickableView(@IdRes int clickableResId) {
        if (mNetErrorView != null) {
            mNetErrorClickableView = mNetErrorView.findViewById(clickableResId);
        }
        if (mOnRetryClickListener != null && mNetErrorClickableView != null) {
            mNetErrorClickableView.setOnClickListener(mOnRetryClickListener);
        }
    }

    public void setOnNetErrorClickListener(OnClickListener listener) {
        mOnRetryClickListener = listener;
        if (mNetErrorClickableView != null) {
            mNetErrorClickableView.setOnClickListener(mOnRetryClickListener);
        }
    }

    public void showResult() {
        handleChildViewVisibility(true, false, false, false);
    }

    public void showLoading() {
        handleChildViewVisibility(false, true, false, false);
    }

    public void showNoResult() {
        handleChildViewVisibility(false, false, true, false);
    }

    public void showNetError() {
        handleChildViewVisibility(false, false, false, true);
    }

    @Override
    public void onViewAdded(View child) {
        if (!TAG.equals(child.getTag())) {
            if (mContentView == null) {
                child.setTag(null);
                mContentView = child;
            } else {
                throw new IllegalStateException("LoadingLayout can host only one direct child view, please handle subviews in your child view");
            }
        }
    }

    private void init(AttributeSet attrs) {
        mInflater = LayoutInflater.from(getContext());
        TypedArray a = null;
        int loadingLayoutRes = 0;
        int noResultLayoutRes = 0;
        int netErrorLayoutRes = 0;
        int netErrorClickableRes = 0;
        if (attrs != null) {
            a = getContext().obtainStyledAttributes(attrs,R.styleable.LoadingLayout);
            loadingLayoutRes = a.getResourceId(R.styleable.LoadingLayout_loadingLayout, 0);
            noResultLayoutRes = a.getResourceId(R.styleable.LoadingLayout_noResultLayout, 0);
            netErrorLayoutRes = a.getResourceId(R.styleable.LoadingLayout_netErrorLayout, 0);
            netErrorClickableRes = a.getResourceId(R.styleable.LoadingLayout_netErrorClickableView, 0);
        }
        if (loadingLayoutRes == 0) {
            loadingLayoutRes = defaultLoadingResId;
        }
        if (noResultLayoutRes == 0) {
            noResultLayoutRes = defaultNoResultResId;
        }
        if (netErrorLayoutRes == 0) {
            netErrorLayoutRes = defaultNetErrorResId;
        }
        if (loadingLayoutRes != 0) {
            mLoadingView = mInflater.inflate(loadingLayoutRes, null);
        }
        if (noResultLayoutRes != 0) {
            mNoResultView = mInflater.inflate(noResultLayoutRes, null);
        }
        if (netErrorLayoutRes != 0) {
            mNetErrorView = mInflater.inflate(netErrorLayoutRes, null);
        }

        if (mNetErrorView != null) {
            if (netErrorClickableRes != 0) {
                mNetErrorClickableView = mNetErrorView.findViewById(netErrorClickableRes);
            } else {
                mNetErrorClickableView = mNetErrorView;
            }
        }
        if (mLoadingView != null) {
            mLoadingView.setTag(TAG);
            addView(mLoadingView);
        }
        if (mNoResultView != null) {
            mNoResultView.setTag(TAG);
            addView(mNoResultView);
        }
        if (mNetErrorView != null) {
            mNetErrorView.setTag(TAG);
            addView(mNetErrorView);
        }
        if (a != null) {
            a.recycle();
        }
        handleChildViewVisibility(true, false, false, false);
    }

    private void handleChildViewVisibility(boolean isContentVisible, boolean isLoadingVisible,
                                           boolean isNoResultVisible, boolean isNetErrorVisible) {
        if (mContentView != null) {
            mContentView.setVisibility(isContentVisible ? VISIBLE : GONE);
        }
        if (mLoadingView != null) {
            mLoadingView.setVisibility(isLoadingVisible ? VISIBLE : GONE);
        }
        if (mNoResultView != null) {
            mNoResultView.setVisibility(isNoResultVisible ? VISIBLE : GONE);
        }
        if (mNetErrorView != null) {
            mNetErrorView.setVisibility(isNetErrorVisible ? VISIBLE : GONE);
        }
    }
}
