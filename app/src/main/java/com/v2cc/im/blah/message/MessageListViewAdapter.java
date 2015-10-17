package com.v2cc.im.blah.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.v2cc.im.blah.R;

import java.util.ArrayList;

/**
 * 聊天内容界面的ListView适配器
 *
 * @author yeliangliang
 * @ClassName: ChatViewListAdapter
 * @date 2015-7-31 上午10:44:02
 */
public class MessageListViewAdapter extends BaseAdapter {
    private ArrayList<MessageBean> mList;
    private Context mContext;
    private LayoutInflater inflater;
    private ViewHolder holder;
//    private FaceUtil faceUtil;

    public MessageListViewAdapter(Context context, ArrayList<MessageBean> list) {
        this.mContext = context;
        this.mList = list;
        inflater = LayoutInflater.from(mContext);
//        faceUtil = FaceUtil.getInstance(mContext);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_message_list, null);
            holder.rl_chat_send = (RelativeLayout) convertView.findViewById(R.id.rl_chat_send);
            holder.rl_chat_receive = (RelativeLayout) convertView.findViewById(R.id.rl_chat_receive);
            holder.tv_content_send = (TextView) convertView.findViewById(R.id.tv_chat_send);
            holder.tv_content_receive = (TextView) convertView.findViewById(R.id.tv_chat_receive);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MessageBean messageBean = mList.get(position);
        // 判断是接收还是发送
        if (messageBean.getSource().equals("1")) {
            // 接收信息
            holder.rl_chat_send.setVisibility(View.GONE);
            holder.rl_chat_receive.setVisibility(View.VISIBLE);
            holder.tv_content_receive.setText(messageBean.getContent());
        } else if (messageBean.getSource().equals("0")) {
            // 发送信息
            holder.rl_chat_send.setVisibility(View.VISIBLE);
            holder.rl_chat_receive.setVisibility(View.GONE);
            holder.tv_content_send.setText(messageBean.getContent());
        }
        return convertView;
    }

    private class ViewHolder {
        private RelativeLayout rl_chat_send;// 聊天信息布局发送
        private RelativeLayout rl_chat_receive;// 聊天信息布局接收
        private TextView tv_content_send;// 发送的文字消息展示
        private TextView tv_content_receive;// 接收的文字消息展示
    }

}
