package sh.ajb.com.endokhhtp.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import sh.ajb.com.endokhhtp.Base.BaseActivity;
import sh.ajb.com.endokhhtp.Bean.Student;
import sh.ajb.com.endokhhtp.R;
import sh.ajb.com.endokhhtp.widget.PickValueView;

/**
 * Created by liuwen on 2017/2/20.
 * Rx的写法 理解RxJava的写法
 * 依赖关系
 * compile 'io.reactivex:rxjava:1.2.3'
 * compile 'io.reactivex:rxandroid:1.2.1'
 */
public class RxJavaActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_view_activity);

        //1.创建一个观察者  subscriber是Observer的子类
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.e("MainActivity", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.e("MainActivity", "onNext");
            }
        };


        //2.创建一个被观察者
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
            }
        });
        //3.被观察者关联观察者
        observable.subscribe(subscriber);

        //4.被观察者的其他写法
//          Observable.from();
//          Observable.just();
        //内部的核心代码

        // 注意：这不是 subscribe() 的源码，而是将源码中与性能、兼容性、扩展性有关的代码剔除后的核心代码。
//        如果需要看源码，可以去 RxJava 的 GitHub 仓库下载。
//        public Subscription subscribe(Subscriber subscriber) {
//            subscriber.onStart();
//            onSubscribe.call(subscriber);
//            return subscriber;
        //还可以自定义的Action
        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.e("MainActivity", "onNextAction");
            }
        };

        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            // onError()
            @Override
            public void call(Throwable throwable) {
                Log.e("MainActivity", "onErrorAction");
            }
        };
        Action0 onCompletedAction = new Action0() {
            // onCompleted()
            @Override
            public void call() {
                Log.e("MainActivity", "onCompletedAction");
            }
        };

        // observable.subscribe(onNextAction,onErrorAction,onCompletedAction);

        //Rxjava同步的列子
        //  exampleOne();
        //Rxjava异步的列子
        // exampleTwo();

        //Rxjava变换最简单的列子 map()方法的使用 是1对1的转换
        //exampleThree();

        //RxJava变换的列子 flatMap()方法的使用 是1对多的转换
        //exampleFour();
    }

    private void exampleFour() {
        //先创建一个观察者
        Subscriber subscriber = new Subscriber() {
            @Override
            public void onCompleted() {
                Log.e("MainActivity", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                Log.e("MainActivity", "onNext");
            }
        };

        //在创建一个被观察者
        Student[] students = new Student[]{};
        Observable.from(students).map(new Func1<Student, String>() {
            @Override
            public String call(Student student) {
                return getName(student.getName());
            }
        }).subscribe(subscriber);

    }

    private String getName(String name) {

        return name;
    }


    private void exampleThree() {

        Observable.just(555).map(new Func1<Integer, Bitmap>() {
            @Override
            public Bitmap call(Integer integer) {
                return null;
            }
        }).subscribe(new Action1<Bitmap>() {
            @Override
            public void call(Bitmap bitmap) {

            }
        });
    }

    private void exampleTwo() {

        //被观察者订阅了观察者  这个是属于异步的列子
        final String[] names = {"小明", "小刘", "小芳", "小李"};
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(names + "");
            }
        }).subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程 Io线程既是IO读写和
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Toast.makeText(RxJavaActivity.this, s.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void exampleOne() {
        //被观察者订阅了观察者  这个是属于同步的列子
        final String[] names = {"小明", "小刘", "小芳", "小李"};
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(names + "");
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.e("MainActivity", s.toString());
            }
        });
    }

}
