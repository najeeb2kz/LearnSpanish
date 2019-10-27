package com.chaudhry.najeeb.learnspanish;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;


public class AnimalActivity extends AppCompatActivity {

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
        //Define array final because an anonymous class cannot access local variables in its enclosing
        //scope that are not declared final
        final ArrayList<Word> words = new ArrayList<Word>();

        //Word w = new Word("one", "uno");
        //words.add(w);                         //This line & above line is same as words.add(new Word("one", "uno");

        //Add objects into above array
        words.add(new Word("Ant", "Hormiga", R.drawable.animal_ant, R.raw.animal_ant));
        words.add(new Word("Bat", "Murciélago", R.drawable.animal_bat, R.raw.animal_bat));
        words.add(new Word("Bear", "Oso", R.drawable.animal_bear, R.raw.animal_bear));
        words.add(new Word("Bee", "Abeja", R.drawable.animal_bee, R.raw.animal_bee));
        words.add(new Word("Bird", "Pájaro", R.drawable.animal_bird, R.raw.animal_bird));
        words.add(new Word("Boar", "Jabali", R.drawable.animal_boar, R.raw.animal_boar));
        words.add(new Word("Buffalo", "Búfalo", R.drawable.animal_buffalo, R.raw.animal_buffalo));
        words.add(new Word("Bull", "Toro", R.drawable.animal_bull, R.raw.animal_bull));
        words.add(new Word("Butterfly", "Mariposa", R.drawable.animal_butterfly, R.raw.animal_butterfly));
        words.add(new Word("Camel", "Camello", R.drawable.animal_camel, R.raw.animal_camel));
        words.add(new Word("Cat", "Gato", R.drawable.animal_cat, R.raw.animal_cat));
        words.add(new Word("Caterpiller", "Oruga", R.drawable.animal_caterpiller, R.raw.animal_caterpillar));
        words.add(new Word("Chick", "Polluelo", R.drawable.animal_chick, R.raw.animal_chick));
        words.add(new Word("Cockroach", "Cucaracha", R.drawable.animal_cockroach, R.raw.animal_cockroach));
        words.add(new Word("Cow", "Vaca", R.drawable.animal_cow, R.raw.animal_cow));
        words.add(new Word("Crab", "Cangrejo", R.drawable.animal_crab, R.raw.animal_crab));
        words.add(new Word("Crocodile", "Cocodrilo", R.drawable.animal_crocodile, R.raw.animal_crocodile));
        words.add(new Word("Deer", "Venado", R.drawable.animal_deer, R.raw.animal_deer));
        words.add(new Word("Dog", "Perro", R.drawable.animal_dog, R.raw.animal_dog));
        words.add(new Word("Duck", "Pato", R.drawable.animal_duck, R.raw.animal_duck));
        words.add(new Word("Eagle", "Águila", R.drawable.animal_eagle, R.raw.animal_eagle));
        words.add(new Word("Elephant", "Elefante", R.drawable.animal_elephant, R.raw.animal_elephant));
        words.add(new Word("Ferret", "Hurón", R.drawable.animal_ferret, R.raw.animal_ferret));
        words.add(new Word("Fish", "Pescado", R.drawable.animal_fish, R.raw.animal_fish));
        words.add(new Word("Fox", "Zorro", R.drawable.animal_fox, R.raw.animal_fox));
        words.add(new Word("Frog", "Rana", R.drawable.animal_frog, R.raw.animal_frog));
        words.add(new Word("Goat", "Chivo", R.drawable.animal_goat, R.raw.animal_goat));
        words.add(new Word("Hen", "Gallina", R.drawable.animal_hen, R.raw.animal_hen));
        words.add(new Word("Horse", "Caballo", R.drawable.animal_horse, R.raw.animal_horse));
        words.add(new Word("Jaguar", "Jaguar", R.drawable.animal_jaguar, R.raw.animal_jaugar));
        words.add(new Word("Lion", "León", R.drawable.animal_lion, R.raw.animal_lion));
        words.add(new Word("Lizard", "Lagartija", R.drawable.animal_lizard, R.raw.animal_lizard));
        words.add(new Word("Monkey", "Mono", R.drawable.animal_monkey, R.raw.animal_monkey));
        words.add(new Word("Mouse", "Ratón", R.drawable.animal_mouse, R.raw.animal_mouse));
        words.add(new Word("Octopus", "Pulpo", R.drawable.animal_octopus, R.raw.animal_octopus));
        words.add(new Word("Owl", "Búho", R.drawable.animal_owl, R.raw.animal_owl));
        words.add(new Word("Penguin", "Pingüino", R.drawable.animal_penguin, R.raw.animal_penguin));
        words.add(new Word("Pig", "Puerco", R.drawable.animal_pig, R.raw.animal_pig));
        words.add(new Word("Rabbit", "Conejo", R.drawable.animal_rabbit, R.raw.animal_rabbit));
        words.add(new Word("Rat", "Rata", R.drawable.animal_rat, R.raw.animal_rat));
        words.add(new Word("Rhino ", "Rinoceronte", R.drawable.animal_rhino, R.raw.animal_rhino));
        words.add(new Word("Rooster", "Gallo", R.drawable.animal_rooster, R.raw.animal_rooster));
        words.add(new Word("Scorpion", "Escorpión", R.drawable.animal_scorpion, R.raw.animal_scorpion));
        words.add(new Word("Shark", "Tiburón", R.drawable.animal_shark, R.raw.animal_shark));
        words.add(new Word("Sheep", "Oveja", R.drawable.animal_sheep, R.raw.animal_sheep));
        words.add(new Word("Shrimp", "Camarón", R.drawable.animal_shrimp, R.raw.animal_shrimp));
        words.add(new Word("Snail", "Caracol", R.drawable.animal_snail, R.raw.animal_snail));
        words.add(new Word("Snake", "Serpiente", R.drawable.animal_snake, R.raw.animal_snake));
        words.add(new Word("Spider", "Araña", R.drawable.animal_spider, R.raw.animal_spider));
        words.add(new Word("Squid", "Calamar", R.drawable.animal_squid, R.raw.animal_squid));
        words.add(new Word("Squirrel", "Ardilla", R.drawable.animal_squirrel, R.raw.animal_squirrel));
        words.add(new Word("Tiger", "Tigre", R.drawable.animal_tiger, R.raw.animal_tiger));
        words.add(new Word("Turkey", "Pavo", R.drawable.animal_turkey, R.raw.animal_turkey));
        words.add(new Word("Turtle", "Tortuga", R.drawable.animal_turtle, R.raw.animal_turtle));
        words.add(new Word("Wolf", "Lobo", R.drawable.animal_wolf, R.raw.animal_wolf));

        //Adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_animal);
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
                        mMediaPlayer = MediaPlayer.create(AnimalActivity.this, myWordPosition.getmAudioResourceId());
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
    // activity, then release media player resources immediately.
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

}
