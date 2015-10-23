package com.v2cc.im.blah.message;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.v2cc.im.blah.R;
import com.v2cc.im.blah.base.activity.BaseActivity;
import com.v2cc.im.blah.base.utils.SMSUtil;
import com.v2cc.im.blah.base.view.StatusBarCompat;
import com.v2cc.im.blah.db.DataBaseHelperUtil;

import java.util.ArrayList;


/**
 * Created by Steve ZHANG (stevzhg@gmail.com)
 * 2015/9/29.
 * If it works, I created this. If not, I didn't.
 */
public class MessageActivity extends BaseActivity implements OnClickListener {
    private Toolbar toolbar;
    private String name;// 昵称
    private String phoneNum;
    FloatingActionButton mFAB;
    private ArrayList<MessageBean> messageHistories;// 聊天信息集合
    ListView listView;// 聊天信息列表
    private MessageListViewAdapter adapter;// 聊天信息列表适配器
    private DataBaseHelperUtil util;
    SMSUtil smsUtil;

    public static void actionStart(Context context, Bundle bundle) {
        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void initViews() {
        setContentView(R.layout.activity_message);
        listView = (ListView) findViewById(R.id.lv);

        messageHistories = new ArrayList<MessageBean>();
        adapter = new MessageListViewAdapter(MessageActivity.this, messageHistories);
        listView.setAdapter(adapter);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mFAB = (FloatingActionButton) findViewById(R.id.fab);

        // 设置FloatingActionButton的点击事件
        mFAB.setOnClickListener(this);
    }

    @Override
    public void initData() {
    }

    @Override
    public void configViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 透明状态栏
        StatusBarCompat.compat(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:// 发送按钮
                postMessage(new MessageBean("", name, phoneNum,
                        System.currentTimeMillis() + "", "blah blah", "", "0", "0"));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // TODO 从数据库取 name 并设置 Toolbar 标题
//        toolbar.setTitle(getIntent().getStringExtra("name"));

        // TODO phone number formatting
        String s = getIntent().getStringExtra("phoneNum");
        phoneNum = s.replaceAll("-", "");

        util = DataBaseHelperUtil.getInstance(this);
        new AsyncTask<String, Void, Void>() {
            protected Void doInBackground(String... s) {
                if (messageHistories == null) {
                    messageHistories = new ArrayList<>();
                }
                messageHistories.clear();
                messageHistories.addAll(util.getMessageHistory(s[0]));
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                updateUI();
            }
        }.execute(phoneNum);
    }

    private void postMessage(final MessageBean messageBean) {
        messageHistories.add(messageBean);
        adapter.notifyDataSetChanged();
//        listView.setSelection(messageHistories.size() - 1);

        // Todo determines whether it should send message by sms
        smsUtil = new SMSUtil();
        smsUtil.sendSMS(phoneNum);
        Log.d("postMessage", phoneNum);

        saveSMStoDB(name, phoneNum, "blah blah", "0");
    }

    private void saveSMStoDB(final String name, final String phoneNum, final String content, final String source) {

        MessageBean mb = new MessageBean();
        mb.setContent(content);
        mb.setName(name);
        mb.setPhoneNum(phoneNum);
        mb.setSource(source);
        mb.setTime(System.currentTimeMillis() + "");
        mb.setStatus("0");

        new AsyncTask<MessageBean, Void, Void>() {
            protected Void doInBackground(MessageBean... mb) {
                util.insertToTable(DataBaseHelperUtil.TABLE_NAME_MESSAGE, mb[0]);
                util.insertRecentChat(mb[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                updateUI();
            }
        }.execute(mb);
    }

    private void updateUI() {
        adapter.notifyDataSetChanged();
    }

}
