package com.chaudhry.najeeb.learnspanish;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class FamilyActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;    //Handles playback of all the sound files
    private AudioManager mAudioManager;  //Handles audio focus when playing a sound file

    //This listener gets triggered when the MediaPlayer has completed playing the audio file
    //MediaPlayer.OnCompletionListener is interface.  I am Implementing MediaPlayer.OnCompletionListener as java inner class
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    //This listener gets triggered whenever audio focus changes i.e., we gain or lose audio focus because of another app or device
    //Anonymous class implemented
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                //The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                //short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                //our app is allowed to continue playing sound but at a lower volume. We'll treat
                //both cases the same way because our app is playing short sound files.

                //Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                //Below without if statement I was getting following error: java.lang.NullPointerException:
                // Attempt to invoke virtual method 'void android.media.MediaPlayer.pause()' on a null object reference
                if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    mMediaPlayer.seekTo(0);
                }
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                //The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                //The AUDIOFOCUS_LOSS case means we've lost audio focus and stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);

        //Create and setup AudioManager to request audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //Create an array
        //Define array final because an anonymous class cannot access local varialbes in its enclosing
        // scope that are not declared final
        final ArrayList<Word> words = new ArrayList<Word>();

        //Word w = new Word("one", "uno");
        //words.add(w);                         //This line & above line is same as words.add(new Word("one", "uno");

        //Add objects into above array
        words.add(new Word("Aunt", "Tía", R.drawable.family_aunt, R.raw.family_aunt));
        words.add(new Word("Brother", "Hermano", R.drawable.family_brother, R.raw.family_brother));
        words.add(new Word("Brother in law", "Cuñado", R.drawable.family_brotherinlaw, R.raw.family_brother_in_law));
        words.add(new Word("Cousin (female)", "Prima", R.drawable.family_cousinfemale, R.raw.family_cousin_female));
        words.add(new Word("Cousin (male)", "Primo", R.drawable.family_cousinmale, R.raw.family_cousin_male));
        words.add(new Word("Cousins", "Primos", R.drawable.family_cousins, R.raw.family_cousins));
        words.add(new Word("Daughter", "Hija", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word("Daughter-in-law", "Nuera", R.drawable.family_daughterinlaw, R.raw.family_daughter_in_law));
        words.add(new Word("Father", "Padre", R.drawable.family_father, R.raw.family_father));
        words.add(new Word("Father-in-law", "Suegro", R.drawable.family_fatherinlaw, R.raw.family_father_in_law));
        words.add(new Word("Grandchildren", "Nietos", R.drawable.family_grandchildren, R.raw.family_grandchildren));
        words.add(new Word("Granddaughter", "Nieta", R.drawable.family_granddaughter, R.raw.family_granddaughter));
        words.add(new Word("Grandfather", "Abuelo", R.drawable.family_grandfather, R.raw.family_grandfather));
        words.add(new Word("Grandmother", "Abuela", R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word("Grandparents", "Abuelos", R.drawable.family_grandparents, R.raw.family_grandparents));
        words.add(new Word("Grandson", "Nieto", R.drawable.family_grandson, R.raw.family_grandson));
        words.add(new Word("Husband", "Esposo", R.drawable.family_husband, R.raw.family_husband));
        words.add(new Word("Mother", "Madre", R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word("Mother-in-law", "Suegra", R.drawable.family_motherinlaw, R.raw.family_mother_in_law));
        words.add(new Word("Nephew", "Sobrino", R.drawable.family_nephew, R.raw.family_nephew));
        words.add(new Word("Niece", "Sobrina", R.drawable.family_niece, R.raw.family_niece));
        words.add(new Word("Parents", "Padres", R.drawable.family_parents, R.raw.family_parents));
        words.add(new Word("Sister", "Hermana", R.drawable.family_sister, R.raw.family_sister));
        words.add(new Word("Sister-in-law", "Cuñada", R.drawable.family_sisterinlaw, R.raw.family_sister_in_law));
        words.add(new Word("Son", "Hijo", R.drawable.family_son, R.raw.family_son));
        words.add(new Word("Son-in-law", "Yerno", R.drawable.family_soninlaw, R.raw.family_son_in_law));
        words.add(new Word("Stepbrother", "Hermanastro", R.drawable.family_stepbrother, R.raw.family_stepbrother));
        words.add(new Word("Stepdaughter", "Hijastra", R.drawable.family_stepdaughter, R.raw.family_stepdaughter));
        words.add(new Word("Stepfather", "Padrastro", R.drawable.family_stepfather, R.raw.family_stepfather));
        words.add(new Word("Stepmother", "Madrastra", R.drawable.family_stepmother, R.raw.family_stepmother));
        words.add(new Word("Stepsister", "Hermanastra", R.drawable.family_stepsister, R.raw.family_stepsister));
        words.add(new Word("Stepson", "Hijastro", R.drawable.family_stepson, R.raw.family_stepson));
        words.add(new Word("Uncle", "Tío", R.drawable.family_uncle, R.raw.family_uncle));
        words.add(new Word("Wife", "Esposa", R.drawable.family_wife, R.raw.family_wife));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_family);
        ListView listView = (ListView) findViewById(R.id.list);
        if (listView != null) {
            listView.setAdapter(adapter);
        }

        //Set listener on list view
        if (listView != null) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    //Release the media player if it currently exists because we are about to
                    //play a different sound file
                    releaseMediaPlayer();

                    //Get the Word object at the given position the user clicked on
                    Word myWordPosition = words.get(position);

                    // Request audio focus so in order to play the audio file. The app needs to play a
                    // short audio file, so we will request audio focus with a short amount of time
                    // with AUDIOFOCUS_GAIN_TRANSIENT.
                    int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                            AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                    if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        //We have audio focus now

                        //You can use either factory method or constructor method.
                        //Different create methods available, for example create MediaPlayer for a given URI
                        mMediaPlayer = MediaPlayer.create(FamilyActivity.this, myWordPosition.getmAudioResourceId());
                        mMediaPlayer.start(); //no need to call prepare(); create() does that for us

                        //Setup a listener on the media player, so that we stop and release the
                        //media player once the sounds have finished playing
                        mMediaPlayer.setOnCompletionListener(mCompletionListener);
                    }
                }
            });
        }
    }//end onCreate

    //Clean up the media player by releasing its resources
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();
            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
        }
    }//End releaseMediaPlayer()

    //User is starting to play sound, before sounds finishes playing, user moves to some other
    // activty, then release media player resources immediately.
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

}
