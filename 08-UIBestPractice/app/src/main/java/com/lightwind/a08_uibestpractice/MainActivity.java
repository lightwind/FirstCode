package com.lightwind.a08_uibestpractice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.msg_recycler_view)
    RecyclerView mMsgRecyclerView;
    @BindView(R.id.input_text)
    EditText mInputText;
    @BindView(R.id.send)
    Button mSend;
    private List<Msg> mMsgList = new ArrayList<>();
    private MsgAdapter mMsgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // 初始化消息数据
        initMsg();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mMsgRecyclerView.setLayoutManager(layoutManager);
        mMsgAdapter = new MsgAdapter(mMsgList);
        mMsgRecyclerView.setAdapter(mMsgAdapter);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mInputText.getText().toString();
                if (!"".equals(content)) {
                    Msg msg = new Msg(content, Msg.TYPE_SENT);
                    mMsgList.add(msg);
                    // 当有新消息是，刷新RecyclerView列表
                    mMsgAdapter.notifyItemInserted(mMsgList.size() - 1);
                    // 将RecyclerView定位到最后一行
                    mMsgRecyclerView.scrollToPosition(mMsgList.size() - 1);
                    // 清空输入框中的内容
                    mInputText.setText("");
                }
            }
        });
    }

    /**
     * 初始化消息数据
     */
    private void initMsg() {
        Msg msg1 = new Msg("Hello guy", Msg.TYPE_RECEIVED);
        mMsgList.add(msg1);
        Msg msg2 = new Msg("Hello. Who is that?", Msg.TYPE_SENT);
        mMsgList.add(msg2);
        Msg msg3 = new Msg("This is Tom. Nice talking to you.", Msg.TYPE_RECEIVED);
        mMsgList.add(msg3);
    }
}
