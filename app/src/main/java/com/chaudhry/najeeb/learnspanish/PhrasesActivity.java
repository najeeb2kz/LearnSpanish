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


public class PhrasesActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;  //Handle playback of all the sound files
    private AudioManager mAudioManager;  //Handle audio when playing a sound file

    //This listener gets triggered whenever audio focus changes i.e., we gain or lose audio focus because of another app or device
    //Anonymous class implemented
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                //The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                //Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                //Below without if statement I was getting following error: java.lang.NullPointerException:
                // Attempt to invoke virtual method 'void android.media.MediaPlayer.pause()' on a null object reference
                if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                    mMediaPlayer.seekTo(0);
                }
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    //This listener gets triggered when the MediaPlayer has completed playing the audio file
    //MediaPlayer.OnCompletionListener is interface.  I am Implementing MediaPlayer.OnCompletionListener as java inner class
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
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
        words.add(new Word("I", "Yo", R.raw.phrase_i));
        words.add(new Word("You (s)", "Tú (informal)", R.raw.phrase_you_s_informal));
        words.add(new Word("You (s)", "Usted (formal)", R.raw.phrase_you_s_formal));
        words.add(new Word("He", "Él", R.raw.phrase_he));
        words.add(new Word("She", "Ella", R.raw.phrase_she));
        words.add(new Word("We", "Nosotros", R.raw.phrase_we));
        words.add(new Word("You (p)", "Vosostros (very formal)", R.raw.phrase_you_p_very_formal));
        words.add(new Word("You (p)", "Ustedes (formal)", R.raw.phrase_you_p_formal));
        words.add(new Word("They", "Ellos (male or mixed group)", R.raw.phrase_they_male));
        words.add(new Word("They", "Ellas (female group)", R.raw.phrase_they_female));
        words.add(new Word("Mr/Sir", "Señor", R.raw.phrase_mr_sir));
        words.add(new Word("Mrs/Ms", "Señora", R.raw.phrase_mrs_ms));
        words.add(new Word("Miss", "Señorita", R.raw.phrase_miss));
        words.add(new Word("Hi", "Hola", R.raw.phrase_hi));

        words.add(new Word("Good morning", "Buenos días", R.raw.phrase_good_morning));
        words.add(new Word("Good afternoon", "Buenas tardes", R.raw.phrase_good_afternoon));
        words.add(new Word("Good evening", "Buena noches", R.raw.phrase_good_evening));
        words.add(new Word("How's it going?", "Cómo te va?", R.raw.phrase_hows_it_going));
        words.add(new Word("What's going on?", "Qué pasa", R.raw.phrase_whats_going_on));

        words.add(new Word("Goodbye", "Adiós", R.raw.phrase_goodbye));
        words.add(new Word("Goodbye (informal)", "Chau", R.raw.phrase_goodbye_informal));
        words.add(new Word("See you later", "Hasta luego", R.raw.phrase_see_you_later));
        words.add(new Word("See you soon", "Hasta pronto", R.raw.phrase_see_you_soon));
        words.add(new Word("See you tomorrow", "Hasta mañana", R.raw.phrase_see_you_tomorrow));
        words.add(new Word("See you on Friday", "Te veo el viernes", R.raw.phrase_see_you_friday));
        words.add(new Word("Have a good day", "Que tenga un buen día", R.raw.phrase_have_a_nice_day));
        words.add(new Word("Good luck", "Buena suerte", R.raw.phrase_good_luck));
        words.add(new Word("Take care", "Cuídate", R.raw.phrase_take_care));

        words.add(new Word("How are you? (informal)", "Cómo estás?", R.raw.phrase_how_are_you_informal));
        words.add(new Word("How are you? (S/formal)", "Cómo está usted?", R.raw.phrase_how_are_you_s_formal));
        words.add(new Word("How are you? (p)", "Cómo están ustedes?", R.raw.phrase_how_are_you_p));
        words.add(new Word("I am fine, thank you.", "Estoy bien gracias", R.raw.phrase_i_am_fine_thank_you));
        words.add(new Word("I am very well", "Estoy muy bien", R.raw.phrase_i_am_very_well));

        words.add(new Word("It's nice to meet you", "Mucho gusto", R.raw.phrase_its_nice_to_meet_you));
        words.add(new Word("It's a pleasure", "Es un placer", R.raw.phrase_its_a_pleasure));
        words.add(new Word("Where are you from? (Informal)", "De donde eres tú?", R.raw.phrase_where_are_you_from_informal));
        words.add(new Word("Where are you from? (formal)", "De donde es usted?", R.raw.phrase_where_are_you_from_formal));

        words.add(new Word("Where are you going?", "A dónde vas", R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?", "Cuál es tu nombre?", R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...", "Mi nombre es...", R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?", "Como te sientes?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I’m feeling good.", "Me siento bien", R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "Vienes?", R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I’m coming.", "Si, ya Vengo", R.raw.phrase_yes_im_coming));
        words.add(new Word("I’m coming.", "Ya Vengo", R.raw.phrase_im_coming));
        words.add(new Word("Let’s go.", "Vamonos", R.raw.phrase_lets_go));
        words.add(new Word("Come here.", "Ven aca", R.raw.phrase_come_here));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_phrases);
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
                        mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, myWordPosition.getmAudioResourceId());
                        mMediaPlayer.start(); //no need to call prepare(); create() does that for us

                        //Setup a listener on the media player, so that we stop and release the
                        // media player once the sounds have finished playing
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
