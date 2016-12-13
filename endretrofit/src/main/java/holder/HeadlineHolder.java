package holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ywl5320.rxjavaretrofit.R;


/**
 * Created by liuwen on 2016/12/13.
 */

public class HeadlineHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView content;

    public HeadlineHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.headline_title_tv);
        content = (TextView) itemView.findViewById(R.id.headline_content_tv);
    }
}
