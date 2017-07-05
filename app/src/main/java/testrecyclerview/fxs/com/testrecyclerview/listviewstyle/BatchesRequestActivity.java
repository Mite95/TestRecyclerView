package testrecyclerview.fxs.com.testrecyclerview.listviewstyle;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import testrecyclerview.fxs.com.testrecyclerview.ItemDivider;
import testrecyclerview.fxs.com.testrecyclerview.MyRecyclerView;
import testrecyclerview.fxs.com.testrecyclerview.R;
import testrecyclerview.fxs.com.testrecyclerview.listening.LoadCallback;
import testrecyclerview.fxs.com.testrecyclerview.model.InkmanData;

public class BatchesRequestActivity extends AppCompatActivity {

    private MyRecyclerView recyclerView;
    private Toolbar toolbar;
    /*********尾部布局**************/
    private View _loadLyaout;
    private LayoutInflater _loadInflater;

    private List<InkmanData> list;
    private MyAdapter adapter;

    private RecyclerView.LayoutManager layoutManager;
    private MyHandler myHandler;



    private int size;
    private boolean isLoadData = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batches_request);
        initView();
        initData();
        init();

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


    private void init() {
        myHandler = new MyHandler();

        layoutManager = recyclerView.getLayoutManager();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapter = new MyAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addFooterView(_loadLyaout);
        recyclerView.setAdapter(adapter);
        //增加分割线
        recyclerView.addItemDecoration(new ItemDivider(this,LinearLayoutManager.VERTICAL));
        //滑动到底部监听
        recyclerView.setLoadingData(new MyRecyclerView.LoadingData() {
            @Override
            public void onLoadMore() {
                if(!isLoadData){
                    new MyThread().start();
                    Toast.makeText(getApplicationContext(),"加载更多",Toast.LENGTH_SHORT).show();
                    isLoadData = true;
                }
            }
        });
    }


    /**
     * 初始化View
     */
    private void initView() {
        recyclerView = (MyRecyclerView)findViewById(R.id.recyclerView);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        /*********尾部布局**************/
        _loadInflater  = LayoutInflater.from(BatchesRequestActivity.this);
        _loadLyaout = _loadInflater.inflate(R.layout.load_layout,null);
    }


    /**
     * RecycLerView适配器
     */
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        private List<InkmanData> list;
        private LoadCallback callback;

        public MyAdapter(List<InkmanData> list){
            this.list = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(BatchesRequestActivity.this).inflate(R.layout.iamge_text_itme,parent,false));
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


        class MyViewHolder extends RecyclerView.ViewHolder{

            private ImageView head;
            private TextView name;
            private TextView tel;

            public MyViewHolder(View itemView) {
                super(itemView);

                head = (ImageView)itemView.findViewById(R.id.image);
                name = (TextView)itemView.findViewById(R.id.name);
                tel = (TextView)itemView.findViewById(R.id.tel);
            }
        }
    }


    //模拟网络请求延迟
    class MyThread extends Thread{

        @Override
        public void run() {
            super.run();
            try {
                sleep(2000);
                myHandler.sendEmptyMessage(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    class MyHandler extends Handler{

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
            adapter.notifyDataSetChanged();
            size = list.size();
        }
    }
}
