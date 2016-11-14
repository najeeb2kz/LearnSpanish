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


public class FoodActivity extends AppCompatActivity {

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
        words.add(new Word("Apple", "Manzana", R.drawable.food_apple, R.raw.food_apple));
        words.add(new Word("Avocado", "Aguacate", R.drawable.food_avocado, R.raw.food_avocado));
        words.add(new Word("Baby bottle", "Biberón", R.drawable.food_babybottle, R.raw.food_baby_bottle));
        words.add(new Word("Bacon", "Tocino", R.drawable.food_bacon, R.raw.food_bacon));
        words.add(new Word("Banana", "Banano", R.drawable.food_banana, R.raw.food_banana));
        words.add(new Word("Beer", "Cerveza", R.drawable.food_beer, R.raw.food_beer));
        words.add(new Word("Bread", "Pan", R.drawable.food_bread, R.raw.food_bread));
        words.add(new Word("Burger", "Hamburguesa", R.drawable.food_burger, R.raw.food_hamburger));
        words.add(new Word("Cake", "Pastel", R.drawable.food_cake, R.raw.food_cake));
        words.add(new Word("Carrot", "Zanahoria", R.drawable.food_carrot, R.raw.food_carrot));
        words.add(new Word("Champagne", "Champán", R.drawable.food_champagne, R.raw.food_champagne));
        words.add(new Word("Cherry", "Cereza", R.drawable.food_cherry, R.raw.food_cherry));
        words.add(new Word("Chicken", "Pollo", R.drawable.food_chicken, R.raw.food_chicken));
        words.add(new Word("Chille", "Chile", R.drawable.food_chille, R.raw.food_chille));
        words.add(new Word("Chocolate", "Chocolate", R.drawable.food_chocolate, R.raw.food_chocolate));
        words.add(new Word("Coffee", "Café", R.drawable.food_coffee, R.raw.food_coffee));
        words.add(new Word("Cone", "Cono", R.drawable.food_cone, R.raw.food_cone));
        words.add(new Word("Cookie", "Galleta", R.drawable.food_cookie, R.raw.food_cookie));
        words.add(new Word("Corn", "Maiz", R.drawable.food_corn, R.raw.food_corn));
        words.add(new Word("Cucumber", "Pepino", R.drawable.food_cucumber, R.raw.food_cucumber));
        words.add(new Word("Egg", "Huevo", R.drawable.food_egg, R.raw.food_egg));
        words.add(new Word("Eggplant", "Berenjena", R.drawable.food_eggplant, R.raw.food_eggplant));
        words.add(new Word("Flan", "Flan", R.drawable.food_flan, R.raw.food_flan));
        words.add(new Word("Fries", "Papas fritas", R.drawable.food_fries, R.raw.food_fries));
        words.add(new Word("Grapes", "Uvas", R.drawable.food_grapes, R.raw.food_grapes));
        words.add(new Word("Honey", "Miel", R.drawable.food_honey, R.raw.food_honey));
        words.add(new Word("Hotdog", "Perro caliente", R.drawable.food_hotdog, R.raw.food_hotdog));
        words.add(new Word("Icecream", "Helado", R.drawable.food_icecream, R.raw.food_ice_cream));
        words.add(new Word("Lemon", "Limón", R.drawable.food_lemon, R.raw.food_lemon));
        words.add(new Word("Lollipop", "Chupete", R.drawable.food_lollipop, R.raw.food_lollipop));
        words.add(new Word("Martini", "Martini", R.drawable.food_martini, R.raw.food_martini));
        words.add(new Word("Milk", "Leche", R.drawable.food_milk, R.raw.food_milk));
        words.add(new Word("Mushroom", "Hongo", R.drawable.food_mushroom, R.raw.food_mushroom));
        words.add(new Word("Nut", "Nuez", R.drawable.food_nut, R.raw.food_nut));
        words.add(new Word("Orange", "Naranja", R.drawable.food_orange, R.raw.food_orange));
        words.add(new Word("Peach", "Melocoton", R.drawable.food_peach, R.raw.food_peach));
        words.add(new Word("Peanut", "Maní", R.drawable.food_peanut, R.raw.food_peanut));
        words.add(new Word("Pear", "Pera", R.drawable.food_pear, R.raw.food_pear));
        words.add(new Word("Pinacolada", "Piña colada", R.drawable.food_pinacolada, R.raw.food_pine_colada));
        words.add(new Word("Pineapple", "Piña", R.drawable.food_pineapple, R.raw.food_pina));
        words.add(new Word("Plum", "Ciruela", R.drawable.food_plum, R.raw.food_plum));
        words.add(new Word("Popcorn", "Rosetas", R.drawable.food_popcorn, R.raw.food_popcorn));
        words.add(new Word("Potato", "Papa", R.drawable.food_potato, R.raw.food_potato));
        words.add(new Word("Rice", "Arroz", R.drawable.food_rice, R.raw.food_rice));
        words.add(new Word("Salad", "Ensalada", R.drawable.food_salad, R.raw.food_salad));
        words.add(new Word("Soup", "Sopa", R.drawable.food_soup, R.raw.food_soup));
        words.add(new Word("Spaghetti", "Espagueti", R.drawable.food_spaghetti, R.raw.food_spaghetti));
        words.add(new Word("Strawberry", "Fresa", R.drawable.food_strawberry, R.raw.food_strawberry));
        words.add(new Word("Sugarcane", "Caña de azúcar", R.drawable.food_sugarcane, R.raw.food_sugarcane));
        words.add(new Word("Taco", "Taco", R.drawable.food_taco, R.raw.food_taco));
        words.add(new Word("Tea", "Té", R.drawable.food_tea, R.raw.food_tea));
        words.add(new Word("Tomato", "Tomate", R.drawable.food_tomato, R.raw.food_tomato));
        words.add(new Word("Watermellon", "Melon", R.drawable.food_watermelon, R.raw.food_watermelon));
        words.add(new Word("Wheat", "Trigo", R.drawable.food_wheat, R.raw.food_wheat));
        words.add(new Word("Wine", "Vino", R.drawable.food_wine, R.raw.food_vino));
        words.add(new Word("Yam", "Batata", R.drawable.food_yam, R.raw.food_sweet_potato));

        //Adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_food);
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
                        mMediaPlayer = MediaPlayer.create(FoodActivity.this, myWordPosition.getmAudioResourceId());
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
