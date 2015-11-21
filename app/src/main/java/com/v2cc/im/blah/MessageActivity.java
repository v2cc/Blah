package com.v2cc.im.blah;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.v2cc.im.blah.db.DataBaseHelper;
import com.v2cc.im.blah.models.MessageBean;
import com.v2cc.im.blah.views.adapters.MessageListViewAdapter;
import com.v2cc.im.blah.utils.ActivityCollector;
import com.v2cc.im.blah.utils.SMSUtil;
import com.v2cc.im.blah.views.StatusBarCompat;

import java.util.ArrayList;


/**
 * Created by Steve ZHANG (stevzhg@gmail.com)
 * 2015/9/29.
 * If it works, I created this. If not, I didn't.
 */
public class MessageActivity extends BaseActivity implements OnClickListener {
    private Toolbar toolbar;
    private String name;// 昵称
    private String phone;
    private FloatingActionButton mFAB;
    private ArrayList<MessageBean> messageHistories;// 聊天信息集合
    private ListView listView;// 聊天信息列表
    private MessageListViewAdapter adapter;// 聊天信息列表适配器
    private DataBaseHelper util;
    private SMSUtil smsUtil;

    public static void actionStart(Context context, Bundle bundle) {
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_message);

        listView = (ListView) findViewById(R.id.lv);
        messageHistories = new ArrayList<MessageBean>();
        adapter = new MessageListViewAdapter(MessageActivity.this, messageHistories);
        listView.setAdapter(adapter);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("name"));
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFAB = (FloatingActionButton) findViewById(R.id.fab);
        // 设置FloatingActionButton的点击事件
        mFAB.setOnClickListener(this);

        // 透明状态栏
        StatusBarCompat.compat(this);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.d(getClass().getSimpleName(),"click home");
                ActivityCollector.finishActivity(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:// 发送按钮
                postMessage(new MessageBean("", "", phone,
                        System.currentTimeMillis() + "", MessageBean.MSG_CONTENT,
                        MessageBean.MSG_TYPE_TEXT, MessageBean.MSG_SOURCE_SEND, ""));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        phone = getIntent().getStringExtra("phone");
        name = getIntent().getStringExtra("name");

        util = DataBaseHelper.getInstance(this);
        new AsyncTask<String, Void, Void>() {
            protected Void doInBackground(String... s) {
                if (messageHistories == null) {
                    messageHistories = new ArrayList<>();
                }
                messageHistories.clear();
                messageHistories.addAll(util.getMessageLogs(s[0]));
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                adapter.notifyDataSetChanged();
            }
        }.execute(phone);
    }

    private void postMessage(MessageBean messageBean) {
        messageHistories.add(messageBean);
        listView.setSelection(messageHistories.size() - 1);
        adapter.refresh(messageHistories);

        // Todo determines whether it should send message by sms
        smsUtil = new SMSUtil();
        smsUtil.sendSMS(phone);
        Log.d("postMessage", phone);

        // insert sms to db
        new AsyncTask<MessageBean, Void, Void>() {
            protected Void doInBackground(MessageBean... mb) {
                util.insertRecentChats(mb[0]);
                util.insertToTable(DataBaseHelper.TABLE_NAME_MESSAGE_LOGS, mb[0]);
                return null;
            }
        }.execute(messageBean);
    }
}