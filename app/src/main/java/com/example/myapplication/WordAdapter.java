package com.example.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    public int mColorResourceId;
    WordAdapter(Activity context, ArrayList<Word>wording, int colorResourceId)
    {
        super(context,0,wording);
        mColorResourceId=colorResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView= LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        Word word=getItem(position);
        View textContainer=listItemView.findViewById(R.id.text_container);
        int color= ContextCompat.getColor(getContext(),mColorResourceId);
        textContainer.setBackgroundColor(color);
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        miwokTextView.setText(word.getMiwokTranslation());
        TextView defaultTextView=listItemView.findViewById(R.id.default_text_view);
        defaultTextView.setText(word.getDefaultTranslation());
        ImageView miwokImageView=listItemView.findViewById(R.id.image);
        if(word.hasImage()==true)
        {
            miwokImageView.setImageResource(word.getImageResourceId());
        }
        else
        {
            miwokImageView.setVisibility(View.GONE);
        }
            return listItemView;
    }
}