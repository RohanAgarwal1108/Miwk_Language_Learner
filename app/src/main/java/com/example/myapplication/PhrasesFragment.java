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


public class PhrasesFragment extends Fragment {
    private MediaPlayer mp;
    private AudioManager am;
    AudioManager.OnAudioFocusChangeListener afChangeListener= new AudioManager.OnAudioFocusChangeListener() {
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
    public PhrasesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);
        am=(AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        final ArrayList<Word> word = new ArrayList<>();
        word.add(new Word("Where are you going?","minto wuksus",R.raw.phrase_where_are_you_going));
        word.add(new Word("What is your name?","tinnә oyaase'nә",R.raw.phrase_what_is_your_name));
        word.add(new Word("My name is...","oyaaset...",R.raw.phrase_my_name_is));
        word.add(new Word("How are you feeling?","michәksәs?",R.raw.phrase_how_are_you_feeling));
        word.add(new Word("I’m feeling good","kuchi achit",R.raw.phrase_im_feeling_good));
        word.add(new Word("Are you coming?","әәnәs'aa?",R.raw.phrase_are_you_coming));
        word.add(new Word("Yes, I’m coming.","hәә’ әәnәm",R.raw.phrase_yes_im_coming));
        word.add(new Word("I’m coming.","әәnәm",R.raw.phrase_im_coming));
        word.add(new Word("Let’s go","yoowutis",R.raw.phrase_lets_go));
        word.add(new Word("Come here.","әnni'nem",R.raw.phrase_come_here));
        WordAdapter adapter=new WordAdapter(getActivity(),word,R.color.category_phrases);
        ListView myview=rootView.findViewById(R.id.list);
        myview.setAdapter(adapter);
        myview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                int result = am.requestAudioFocus(afChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mp = MediaPlayer.create(getActivity(), word.get(position).getAudioResourceId());
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