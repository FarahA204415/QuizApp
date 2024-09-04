package com.example.spmquizapp_a204415_farah_androidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import java.util.List;

class PageContent {
    String headline;
    String content;

    PageContent(String headline, String content) {
        this.headline = headline;
        this.content = content;
    }
}

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<PageContent> contentList;

    public ViewPagerAdapter(Context context, List<PageContent> contentList) {
        this.context = context;
        this.contentList = contentList;
    }

    @Override
    public int getCount() {
        return contentList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_viewpager, container, false);

        TextView headlineText = view.findViewById(R.id.headlineText);
        TextView contentText = view.findViewById(R.id.contentText);

        PageContent pageContent = contentList.get(position);
        headlineText.setText(pageContent.headline);
        contentText.setText(pageContent.content);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
