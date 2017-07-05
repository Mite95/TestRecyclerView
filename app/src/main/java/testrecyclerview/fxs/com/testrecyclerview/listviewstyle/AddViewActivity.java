package testrecyclerview.fxs.com.testrecyclerview.listviewstyle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kevin.wraprecyclerview.WrapRecyclerView;

import java.util.ArrayList;
import java.util.List;

import testrecyclerview.fxs.com.testrecyclerview.ItemDivider;
import testrecyclerview.fxs.com.testrecyclerview.R;
import testrecyclerview.fxs.com.testrecyclerview.listening.LoadCallback;
import testrecyclerview.fxs.com.testrecyclerview.model.InkmanData;


/**
 * RecyclerView不能添加头部布局和尾部布局，<br>
 * 但是这个需求还是有的，所以在GitHub上有大神就基于ListView添加头部布局的方式给RecyclerView也增加了该功能<br>
 * 继承RecyclerView增加addHeaderView和addFooterView的方法<br>
 * WrapRecyclerView的地址：https://github.com/xuehuayous/WrapRecyclerView<br><br>
 *
 * 只需在Gradle中添加compile 'com.kevin:wraprecyclerview:1.0.6'即可使用WrapRecyclerView<br>
 */
public class AddViewActivity extends AppCompatActivity {

    private WrapRecyclerView recyclerView;
    private Toolbar toolbar;

    /*********头部布局**************/
    private View _headLyaout;
    private LayoutInflater _headInflater;
    /*********尾部布局**************/
    private View _loadLyaout;
    private LayoutInflater _loadInflater;

    private List<InkmanData> list;
    private MyAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_view);
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
    }


    private void initView() {
        recyclerView = (WrapRecyclerView)findViewById(R.id.recyclerView);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        /*********头部布局**************/
        _headInflater  = LayoutInflater.from(AddViewActivity.this);
        _headLyaout = _headInflater.inflate(R.layout.head_layout,null);
        /*********尾部布局**************/
        _loadInflater  = LayoutInflater.from(AddViewActivity.this);
        _loadLyaout = _loadInflater.inflate(R.layout.load_layout,null);
    }


    private void init() {

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
        recyclerView.addHeaderView(_headLyaout);
        recyclerView.addFooterView(_loadLyaout);
        recyclerView.setAdapter(adapter);
        //增加分割线
        recyclerView.addItemDecoration(new ItemDivider(this, LinearLayoutManager.HORIZONTAL));



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
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(AddViewActivity.this).inflate(R.layout.iamge_text_itme,parent,false));
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

}
