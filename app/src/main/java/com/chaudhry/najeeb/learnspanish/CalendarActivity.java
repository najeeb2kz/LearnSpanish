package com.chaudhry.najeeb.learnspanish;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class CalendarActivity extends AppCompatActivity {

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
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                //Pause playback and reset player to the start of the file. That way, we can
                //play the word from the beginning when we resume playback.
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
        words.add(new Word("Monday", "Lunes", R.drawable.calendar_mon, R.raw.calendar_monday));
        words.add(new Word("Tuesday", "Martes", R.drawable.calendar_tue, R.raw.calendar_tuesday));
        words.add(new Word("Wednesday", "Miércoles", R.drawable.calendar_wed, R.raw.calendar_wednesday));
        words.add(new Word("Thursday", "Jueves", R.drawable.calendar_thu, R.raw.calendar_thursday));
        words.add(new Word("Friday", "Viernes", R.drawable.calendar_fri, R.raw.calendar_friday));
        words.add(new Word("Saturday", "Sábado", R.drawable.calendar_sat, R.raw.calendar_saturday));
        words.add(new Word("Sunday", "Domingo", R.drawable.calendar_sun, R.raw.calendar_sunday));
        words.add(new Word("January", "Enero", R.drawable.calendar_jan, R.raw.calendar_january));
        words.add(new Word("February", "Febrero", R.drawable.calendar_feb, R.raw.calendar_february));
        words.add(new Word("March", "Marzo", R.drawable.calendar_mar, R.raw.calendar_march));
        words.add(new Word("April", "Abril", R.drawable.calendar_apr, R.raw.calendar_april));
        words.add(new Word("May", "Mayo", R.drawable.calendar_may, R.raw.calendar_may));
        words.add(new Word("June", "Junio", R.drawable.calendar_jun, R.raw.calendar_june));
        words.add(new Word("July", "Julio", R.drawable.calendar_jul, R.raw.calendar_july));
        words.add(new Word("August", "Agosto", R.drawable.calendar_aug, R.raw.calendar_august));
        words.add(new Word("September", "Septiembre", R.drawable.calendar_sep, R.raw.calendar_september));
        words.add(new Word("October", "Octubre", R.drawable.calendar_oct, R.raw.calendar_october));
        words.add(new Word("November", "Noviembre", R.drawable.calendar_nov, R.raw.calendar_november));
        words.add(new Word("December", "Diciembre", R.drawable.calendar_dec, R.raw.calendar_december));

        //Adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_calendar);
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
                    // play a different sound file
                    releaseMediaPlayer();

                    //Get the Word object at the given position the user clicked on
                    Word myWordPosition = words.get(position);

                    //Request audio focus so in order to play the audio file. The app needs to play a
                    // short audio file, so we will request audio focus with a short amount of time
                    // with AUDIOFOCUS_GAIN_TRANSIENT.
                    int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                            AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                    if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        //We have audio focus now

                        //You can use either factory method or constructor method.
                        //Different create methods available, for example create MediaPlayer for a given URI
                        mMediaPlayer = MediaPlayer.create(CalendarActivity.this, myWordPosition.getmAudioResourceId());
                        mMediaPlayer.start(); //no need to call prepare(); create() does that for us

                        //Setup a listener on the media player, so that we stop and release the
                        //media player once the sounds have finished playing
                        mMediaPlayer.setOnCompletionListener(mCompletionListener);
                    }
                }
            });
        }
    }//End onCreate

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
