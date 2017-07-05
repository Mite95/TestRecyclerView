package testrecyclerview.fxs.com.testrecyclerview.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import testrecyclerview.fxs.com.testrecyclerview.R;
import testrecyclerview.fxs.com.testrecyclerview.gridding.BaseGridActivity;
import testrecyclerview.fxs.com.testrecyclerview.gridding.GridLFActivity;
import testrecyclerview.fxs.com.testrecyclerview.listviewstyle.AddViewActivity;
import testrecyclerview.fxs.com.testrecyclerview.listviewstyle.BaseRecyclerViewActivity;
import testrecyclerview.fxs.com.testrecyclerview.listviewstyle.BatchesRequestActivity;
import testrecyclerview.fxs.com.testrecyclerview.listviewstyle.ClickItmeRecycleViewActivity;
import testrecyclerview.fxs.com.testrecyclerview.listviewstyle.LineRecycleViewActivity;
import testrecyclerview.fxs.com.testrecyclerview.listviewstyle.PullToRefreshActivity;
import testrecyclerview.fxs.com.testrecyclerview.listviewstyle.SynthesizeActivity;
import testrecyclerview.fxs.com.testrecyclerview.waterfall.WaterfallActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button bt;
    private Button bt2;
    private Button bt3;
    private Button bt4;
    private Button bt5;
    private Button bt6;
    private Button bt7;
    private Button bt8;
    private Button bt9;
    private Button bt10;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        init();
    }

    private void init() {
        bt.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        bt5.setOnClickListener(this);
        bt6.setOnClickListener(this);
        bt7.setOnClickListener(this);
        bt8.setOnClickListener(this);
        bt9.setOnClickListener(this);
        bt10.setOnClickListener(this);
    }

    private void initView() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        bt = (Button)findViewById(R.id.bt);
        bt2 = (Button)findViewById(R.id.bt2);
        bt3 = (Button)findViewById(R.id.bt3);
        bt4 = (Button)findViewById(R.id.bt4);
        bt5 = (Button)findViewById(R.id.bt5);
        bt6 = (Button)findViewById(R.id.bt6);
        bt7 = (Button)findViewById(R.id.bt7);
        bt8 = (Button)findViewById(R.id.bt8);
        bt9 = (Button)findViewById(R.id.bt9);
        bt10 = (Button)findViewById(R.id.bt10);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt:
                startActivity(new Intent(getApplicationContext(),BaseRecyclerViewActivity.class));
                break;
            case R.id.bt2:
                startActivity(new Intent(getApplicationContext(),LineRecycleViewActivity.class));
                break;
            case R.id.bt3:
                startActivity(new Intent(getApplicationContext(),ClickItmeRecycleViewActivity.class));
                break;
            case R.id.bt4:
                startActivity(new Intent(getApplicationContext(),PullToRefreshActivity.class));
                break;
            case R.id.bt5:
                startActivity(new Intent(getApplicationContext(),BatchesRequestActivity.class));
                break;
            case R.id.bt6:
                startActivity(new Intent(getApplicationContext(),AddViewActivity.class));
                break;
            case R.id.bt7:
                startActivity(new Intent(getApplicationContext(),SynthesizeActivity.class));
                break;
            case R.id.bt8:
                startActivity(new Intent(getApplicationContext(),BaseGridActivity.class));
                break;
            case R.id.bt9:
                startActivity(new Intent(getApplicationContext(),GridLFActivity.class));
                break;
            case R.id.bt10:
                startActivity(new Intent(getApplicationContext(),WaterfallActivity.class));
                break;
        }

    }
}
