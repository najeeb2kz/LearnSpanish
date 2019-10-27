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


public class ColorsActivity extends AppCompatActivity {

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
        words.add(new Word("Amber", "Ámbar", R.drawable.color_amber, R.raw.color_amber));
        words.add(new Word("Black", "Negro", R.drawable.color_black, R.raw.color_black));
        words.add(new Word("Blue grey", "Gris azulado", R.drawable.color_blue_grey, R.raw.color_blue_grey));
        words.add(new Word("Blue", "Azul", R.drawable.color_blue, R.raw.color_blue));
        words.add(new Word("Brown", "Castaño", R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word("Cyan", "Ciánico", R.drawable.color_cyan, R.raw.color_cyan));
        words.add(new Word("Dark orange", "Naranja oscuro", R.drawable.color_deep_orange, R.raw.color_dark_orange));
        words.add(new Word("Dark purple", "Morado oscuro", R.drawable.color_deep_purple, R.raw.color_dark_purple));
        words.add(new Word("Fuchsia", "Fucsia", R.drawable.color_fuchsia, R.raw.color_fuchsia));
        words.add(new Word("Green", "Verde", R.drawable.color_green, R.raw.color_green));
        words.add(new Word("Grey", "Gris", R.drawable.color_grey, R.raw.color_grey));
        words.add(new Word("Indigo", "Índigo", R.drawable.color_indigo, R.raw.color_indigo));
        words.add(new Word("Light blue", "Azul claro", R.drawable.color_light_blue, R.raw.color_light_blue));
        words.add(new Word("Light green", "Verde claro", R.drawable.color_light_green, R.raw.color_light_green));
        words.add(new Word("Lime", "Lima", R.drawable.color_lime, R.raw.color_lime));
        words.add(new Word("Metatic gold", "Oro metálico", R.drawable.color_metatic_gold, R.raw.color_metalic_gold));
        words.add(new Word("Orange", "Anaranjado (naranja)", R.drawable.color_orange, R.raw.color_orange));
        words.add(new Word("Pink", "Rosado", R.drawable.color_pink, R.raw.color_pink));
        words.add(new Word("Purple", "Morado", R.drawable.color_purple, R.raw.color_purple));
        words.add(new Word("Red", "Rojo", R.drawable.color_red, R.raw.color_red));
        words.add(new Word("Silver", "Plateado", R.drawable.color_silver, R.raw.color_silver));
        //words.add(new Word("Teal", "Trullo", R.drawable.color_teal, R.raw.color_red));
        words.add(new Word("Turquoise", "Turquesa", R.drawable.color_turquoise, R.raw.color_turquoise));
        words.add(new Word("Violet", "Violeta", R.drawable.color_violet, R.raw.color_violet));
        words.add(new Word("White", "Blanco", R.drawable.color_white, R.raw.color_white));
        words.add(new Word("Yellow", "Amarillo", R.drawable.color_yellow, R.raw.color_yellow));

        //Adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_colors);
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
                        mMediaPlayer = MediaPlayer.create(ColorsActivity.this, myWordPosition.getmAudioResourceId());
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
