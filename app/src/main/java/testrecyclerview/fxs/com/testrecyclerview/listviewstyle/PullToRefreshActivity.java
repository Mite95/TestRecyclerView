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
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

import testrecyclerview.fxs.com.testrecyclerview.ItemDivider;
import testrecyclerview.fxs.com.testrecyclerview.MyRecyclerView;
import testrecyclerview.fxs.com.testrecyclerview.R;
import testrecyclerview.fxs.com.testrecyclerview.PullToRefreshRecyclerView;

public class PullToRefreshActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;

    private PullToRefreshRecyclerView refreshRecyclerView;

    private List<String> data;

    private MyHandler myHandler;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refresh);
        initView();
        initData();
        init();
    }

    private void initData() {
        data = new ArrayList<String>();
        for (int i = 0;i < 50;i++){
            data.add("未更新"+i);
        }
    }

    private void init() {
        recyclerView = refreshRecyclerView.getRefreshableView();

        myHandler = new MyHandler();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapter = new MyAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        //增加分割线
        recyclerView.addItemDecoration(new ItemDivider(this,LinearLayoutManager.VERTICAL));

        refreshRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<MyRecyclerView>() {
            @Override
            public void onRefresh(PullToRefreshBase<MyRecyclerView> refreshView) {
                String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

                new MyThread().start();
            }
        });
    }

    private void initView() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        refreshRecyclerView = (PullToRefreshRecyclerView)findViewById(R.id.pullToRefreshRecyclerView);
    }


    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        private List<String> data;

        public MyAdapter(List<String> data){

            this.data = data;

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(PullToRefreshActivity.this).inflate(R.layout.recycler_view_itme,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.textView.setText(data.get(position));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);

            textView = (TextView)itemView.findViewById(R.id.text);

        }
    }


    /**
     * ，模拟网络请求延迟
     */
    class MyThread extends Thread{
        @Override
        public void run() {
            super.run();
            try {
                sleep(1000);
                myHandler.sendEmptyMessage(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 模拟消息请求达到成功
     */
    class MyHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            data.clear();
            refreshRecyclerView.onRefreshComplete();
            for (int i = 0;i < 50;i++){
                data.add("已更新"+i);
            }
            adapter.notifyDataSetChanged();
        }
    }

}
