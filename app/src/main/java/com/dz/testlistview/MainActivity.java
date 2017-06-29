package com.dz.testlistview;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView parentListview;
    private ArrayList<Data> datas;
    private SwipeRefreshLayout srl;
    private ParentAdapter parentAdapter;
    private ScrollView sc;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    srl.setRefreshing(false);
                    parentAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Data.Children child01 = new Data.Children(R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        Data.Children child02 = new Data.Children(R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        Data.Children child03 = new Data.Children(R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        Data.Children child04 = new Data.Children(R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        Data.Children child05 = new Data.Children(R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        ArrayList<Data.Children> childList = new ArrayList<Data.Children>();
        ArrayList<Data.Children> childList1 = new ArrayList<Data.Children>();
        ArrayList<Data.Children> childList2 = new ArrayList<Data.Children>();
        childList.add(child01);
        childList.add(child02);
        childList.add(child03);
        childList.add(child04);
        childList.add(child05);
        childList1.add(child05);
        childList1.add(child05);
        childList2.add(child05);
        childList2.add(child05);
        childList2.add(child05);

        Data parent01 = new Data("头部01", "头部02", childList);
        Data parent02 = new Data("头部01", "头部02", childList1);
        Data parent03 = new Data("头部01", "头部02", childList2);
        datas=new ArrayList<Data>();
        datas.add(parent01);
        datas.add(parent02);
        datas.add(parent03);
        initView();
        setAdapter();
        setListeners();
    }

    private void setListeners() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Data.Children child04 = new Data.Children(R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                        ArrayList<Data.Children> childList4 = new ArrayList<Data.Children>();
                        childList4.add(child04);
                        Data parent04 = new Data("刷新", "刷新", childList4);
                        datas.add(parent04);
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mHandler.sendEmptyMessage(1);
                    }
                }).start();
            }
        });
        parentListview.setOnScrollListener(new AbsListView.OnScrollListener() {
            private boolean isBottom;
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_FLING:
                        //Log.i("info", "SCROLL_STATE_FLING");
                        break;
                    case SCROLL_STATE_IDLE:
                        if (isBottom) {
                            Data.Children child04 = new Data.Children(R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                            ArrayList<Data.Children> childList4 = new ArrayList<Data.Children>();
                            childList4.add(child04);
                            Data parent04 = new Data("加载", "加载", childList4);
                            datas.add(parent04);
                            mHandler.sendEmptyMessage(1);
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem+visibleItemCount == totalItemCount){
                    //Log.i("info", "到底了....");
                    isBottom = true;
                }else{
                    isBottom = false;
                }
            }
        });
    }

    private void setAdapter() {
        parentAdapter=new ParentAdapter();
        parentListview.setAdapter(parentAdapter);

    }

    private void initView() {
        parentListview=(ListView)findViewById(R.id.parentListview);
        srl=(SwipeRefreshLayout)findViewById(R.id.srl);
    }

    class ParentAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Data getItem(int i) {
            return datas.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ParentViewHolder parentViewHolder=null;
            if (view==null){
                view= LayoutInflater.from(getApplicationContext()).inflate(R.layout.parentitem,null);
                parentViewHolder=new ParentViewHolder();
                parentViewHolder.parentTvTop=(TextView)view.findViewById(R.id.parentTvTop);
                parentViewHolder.parentTvBottom=(TextView)view.findViewById(R.id.parentTvBottom);
                parentViewHolder.childListview=(ListView) view.findViewById(R.id.childListview);
                view.setTag(parentViewHolder);
            }
            parentViewHolder= (ParentViewHolder) view.getTag();
            Data data = getItem(i);
            parentViewHolder.parentTvTop.setText(data.getTop());
            parentViewHolder.parentTvBottom.setText(data.getBottom());
            parentViewHolder.childListview.setAdapter(new ChildAdapter(data.getChildren()));
            return view;
        }
        class ParentViewHolder{
            TextView parentTvTop;
            TextView parentTvBottom;
            ListView childListview;
        }
    }

    class ChildAdapter extends BaseAdapter implements ListAdapter {
        private List<Data.Children> children;
        public ChildAdapter(List<Data.Children> children) {
            super();
            this.children=children;
        }

        @Override
        public int getCount() {
            return children.size();
        }

        @Override
        public Data.Children getItem(int i) {
            return children.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ChildViewHolder childViewHolder=null;
            if (view==null){
                view=LayoutInflater.from(getApplicationContext()).inflate(R.layout.childitem,null);
                childViewHolder=new ChildViewHolder();
                childViewHolder.childIv01=(ImageView)view.findViewById(R.id.childIv01);
                childViewHolder.childIv02=(ImageView)view.findViewById(R.id.childIv02);
                view.setTag(childViewHolder);
            }
            childViewHolder= (ChildViewHolder) view.getTag();
            Data.Children child = getItem(i);
            childViewHolder.childIv01.setImageResource(child.getImage01());
            childViewHolder.childIv02.setImageResource(child.getImage02());
            return view;
        }

        class ChildViewHolder{
            ImageView childIv01;
            ImageView childIv02;
//            TextView tv;
        }
    }
}
