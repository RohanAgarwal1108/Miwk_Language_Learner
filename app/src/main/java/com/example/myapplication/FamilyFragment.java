package com.example.myapplication;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;


public class FamilyFragment extends Fragment {
    private MediaPlayer mp;
    private AudioManager am;
    AudioManager.OnAudioFocusChangeListener afChangeListener=new AudioManager.OnAudioFocusChangeListener(){
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT||focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK)
            {
                mp.pause();
                mp.seekTo(0);
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_GAIN)
            {
                mp.start();
            }
            else if(focusChange==AudioManager.AUDIOFOCUS_LOSS)
            {
                releaseMediaPlayer();
            }
        }
    };
    MediaPlayer.OnCompletionListener mCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    public FamilyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);
        am=(AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> word = new ArrayList<Word>();
        word.add(new Word("father","әpә",R.drawable.family_father,R.raw.family_father));
        word.add(new Word("mother","әṭa",R.drawable.family_mother,R.raw.family_mother));
        word.add(new Word("son","angsi",R.drawable.family_son,R.raw.family_son));
        word.add(new Word("daughter","tune",R.drawable.family_daughter,R.raw.family_daughter));
        word.add(new Word("older brother","taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        word.add(new Word("younger brother","chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        word.add(new Word("older sister","teṭe",R.drawable.family_older_sister,R.raw.family_older_sister));
        word.add(new Word("younger sister","kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        word.add(new Word("grandmother","ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        word.add(new Word("grandfather","paapa",R.drawable.family_grandfather,R.raw.family_father));
        WordAdapter adapter=new WordAdapter(getActivity(),word,R.color.category_family);
        ListView myview=rootView.findViewById(R.id.list);
        myview.setAdapter(adapter);
        myview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                int result=am.requestAudioFocus(afChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {
                    mp= MediaPlayer.create(getActivity(), word.get(position).getAudioResourceId());

                    mp.start();
                    mp.setOnCompletionListener(mCompletionListener);
                }
            }
        });
        return rootView;
    }
    private void releaseMediaPlayer()
    {
        // If the media player is not null, then it may be currently playing a sound.
        if (mp != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mp.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mp = null;
            am.abandonAudioFocus(afChangeListener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
