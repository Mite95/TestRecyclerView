package testrecyclerview.fxs.com.testrecyclerview.listviewstyle;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

import testrecyclerview.fxs.com.testrecyclerview.ItemDivider;
import testrecyclerview.fxs.com.testrecyclerview.MyRecyclerView;
import testrecyclerview.fxs.com.testrecyclerview.PullToRefreshRecyclerView;
import testrecyclerview.fxs.com.testrecyclerview.R;
import testrecyclerview.fxs.com.testrecyclerview.listening.LoadCallback;
import testrecyclerview.fxs.com.testrecyclerview.listening.RecycleViewItmeClickListener;
import testrecyclerview.fxs.com.testrecyclerview.model.InkmanData;

public class SynthesizeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private MyRecyclerView recyclerView;
    private PullToRefreshRecyclerView pullToRefreshRecyclerView;
    /*********头部布局**************/
    private View _headLyaout;
    private LayoutInflater _headInflater;
    /*********尾部布局**************/
    private View _loadLyaout;
    private LayoutInflater _loadInflater;


    private List<InkmanData> list;
    private MyAdapter myAdapter;
    private MyHandler myHandler;
    private RefreshHandler refreshHandler;

    private int size;
    private boolean isLoadData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synthesize);
        initView();
        initData();
        init();
    }


    private void init() {
        recyclerView = pullToRefreshRecyclerView.getRefreshableView();

        myHandler = new MyHandler();
        refreshHandler = new RefreshHandler();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        myAdapter = new MyAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addHeaderView(_headLyaout);
        recyclerView.addFooterView(_loadLyaout);
        recyclerView.setAdapter(myAdapter);
        //增加分割线
        recyclerView.addItemDecoration(new ItemDivider(this,LinearLayoutManager.VERTICAL));
        //滑动到底部监听
        recyclerView.setLoadingData(new MyRecyclerView.LoadingData() {
            @Override
            public void onLoadMore() {
                if (!isLoadData) {
                    new MyThread(false).start();
                    Toast.makeText(getApplicationContext(), "加载更多", Toast.LENGTH_SHORT).show();
                    isLoadData = true;
                }
            }
        });

        pullToRefreshRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<MyRecyclerView>() {
            @Override
            public void onRefresh(PullToRefreshBase<MyRecyclerView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                new MyThread(true).start();
            }
        });

        myAdapter.setOnItemClickListener(new RecycleViewItmeClickListener() {
            @Override
            public void OnItmeClickListener(View view, int position) {
                Toast.makeText(getApplicationContext(),""+list.get(position).getTel(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initData() {
        list = new ArrayList<InkmanData>();
        for(int i = 0;i < 20;i++){
            InkmanData data = new InkmanData();
            data.setName("联系人" + i);
            data.setTel("183000000000" + i);
            list.add(data);

        }
        size = list.size();
    }


    private void initView() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        pullToRefreshRecyclerView = (PullToRefreshRecyclerView)findViewById(R.id.pullToRefreshRecyclerView);
        /*********头部布局**************/
        _headInflater  = LayoutInflater.from(SynthesizeActivity.this);
        _headLyaout = _headInflater.inflate(R.layout.head_layout,null);
        /*********尾部布局**************/
        _loadInflater  = LayoutInflater.from(SynthesizeActivity.this);
        _loadLyaout = _loadInflater.inflate(R.layout.load_layout,null);
    }


    /**
     * RecycLerView适配器
     */
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        private List<InkmanData> list;
        private RecycleViewItmeClickListener recycleViewItmeClickListener;

        public MyAdapter(List<InkmanData> list){
            this.list = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(SynthesizeActivity.this).inflate(R.layout.iamge_text_itme,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.name.setText(list.get(position).getName());
            holder.tel.setText(list.get(position).getTel());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }


        public void setOnItemClickListener(RecycleViewItmeClickListener recycleViewItmeClickListener){
            this.recycleViewItmeClickListener = recycleViewItmeClickListener;
        }


        class MyViewHolder extends RecyclerView.ViewHolder{

            private ImageView head;
            private TextView name;
            private TextView tel;

            public MyViewHolder(final View itemView) {
                super(itemView);

                head = (ImageView)itemView.findViewById(R.id.image);
                name = (TextView)itemView.findViewById(R.id.name);
                tel = (TextView)itemView.findViewById(R.id.tel);

                if(recycleViewItmeClickListener!=null){
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            recycleViewItmeClickListener.OnItmeClickListener(itemView,getPosition());
                        }
                    });
                }
            }
        }
    }


    /**
     *模拟网络请求延迟
     */
     class MyThread extends Thread{

        public boolean isRefresh = false;

        public MyThread(boolean isRefresh){
            this.isRefresh = isRefresh;
        }

        @Override
        public void run() {
            super.run();
            try {
                sleep(2000);
                if (isRefresh){
                    refreshHandler.sendEmptyMessage(0);
                }else{
                    myHandler.sendEmptyMessage(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 模拟分请求
     */
    class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            for(int i = size;i < size+20;i++){
                InkmanData data = new InkmanData();
                data.setName("联系人"+i);
                data.setTel("183000000000" + i);
                list.add(data);
            }
            isLoadData = false;
            myAdapter.notifyDataSetChanged();
            size = list.size();
        }
    }


    /**
     * 模拟刷新请求的数据
     */
    class RefreshHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            list.clear();
            for(int i = size;i < size+20;i++){
                myAdapter.notifyDataSetChanged();
                InkmanData data = new InkmanData();
                data.setName("联系人(刷新了)"+i);
                data.setTel("183000000000" + i);
                list.add(data);
            }
            size = list.size();
            pullToRefreshRecyclerView.onRefreshComplete();
        }
    }
}
