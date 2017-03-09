package sh.ajb.com.endggmm;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import sh.ajb.com.endggmm.Base.BaseActivity;
import sh.ajb.com.endggmm.Fragment.CircleFragment;
import sh.ajb.com.endggmm.Fragment.ImageJokeFragment;
import sh.ajb.com.endggmm.Fragment.TextJokeFragment;
import sh.ajb.com.endggmm.Fragment.homeFragment;
import sh.ajb.com.endggmm.Fragment.personFragment;
import sh.ajb.com.endggmm.view.BarEntity;
import sh.ajb.com.endggmm.view.BottomTabBar;

public class MainActivity extends BaseActivity implements BottomTabBar.OnSelectListener {

    private BottomTabBar tb;
    private List<BarEntity> bars;
    private homeFragment homeFragment;
    private TextJokeFragment textJokeFragment;
    private ImageJokeFragment imageJokeFragment;
    private CircleFragment circleFragment;
    private personFragment personFragment;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        fragmentManager = getSupportFragmentManager();
        tb = (BottomTabBar) findViewById(R.id.tb);
        tb.setManager(fragmentManager);
        tb.setOnSelectListener(this);
        bars = new ArrayList<>();
        bars.add(new BarEntity("主页", R.mipmap.ic_home_select, R.mipmap.ic_home_unselect));
        bars.add(new BarEntity("段子", R.mipmap.ic_textjoke_select, R.mipmap.ic_textjoke_unselect));
        bars.add(new BarEntity("趣图", R.mipmap.ic_imagejoke_select, R.mipmap.ic_imagejoke_unselect));
        bars.add(new BarEntity("圈子", R.mipmap.ic_dt_select, R.mipmap.ic_dt_unselect));
        bars.add(new BarEntity("个人", R.mipmap.ic_my_select, R.mipmap.ic_my_unselect));
        tb.setBars(bars);
    }


    @Override
    public void onSelect(int position) {
        switch (position) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new homeFragment();
                }
                tb.switchContent(homeFragment);
                break;
            case 1:
                if (textJokeFragment == null) {
                    textJokeFragment = new TextJokeFragment();
                }
                tb.switchContent(textJokeFragment);
                break;
            case 2:
                if (imageJokeFragment == null) {
                    imageJokeFragment = new ImageJokeFragment();
                }
                tb.switchContent(imageJokeFragment);
                break;
            case 3:
                if (circleFragment == null) {
                    circleFragment = new CircleFragment();
                }
                tb.switchContent(circleFragment);
                break;
            case 4:
                if (personFragment == null) {
                    personFragment = new personFragment();
                }
                tb.switchContent(personFragment);
                break;
            default:
                break;
        }
    }
}
