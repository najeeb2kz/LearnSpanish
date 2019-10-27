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


public class NumbersActivity extends AppCompatActivity {

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
        //words.add(w);      //This line & above line is same as words.add(new Word("one", "uno");

        //Add objects into above array
        words.add(new Word("Zero", "Cero (say-ro)", R.drawable.number_0, R.raw.number_zero));

        words.add(new Word("One", "Uno (oo-no)", R.drawable.number_1, R.raw.number_one));
        words.add(new Word("Two", "Dos (dose)", R.drawable.number_2, R.raw.number_two));
        words.add(new Word("Three", "Tres (trace)", R.drawable.number_3, R.raw.number_three));
        words.add(new Word("Four", "Cuatro (kwat-ro)", R.drawable.number_4, R.raw.number_four));
        words.add(new Word("Five", "Cinco (sink-o)", R.drawable.number_5, R.raw.number_five));
        words.add(new Word("Six", "Seis (saze)", R.drawable.number_6, R.raw.number_six));
        words.add(new Word("Seven", "Siete (see-yet-eh)", R.drawable.number_7, R.raw.number_seven));
        words.add(new Word("Eight", "Ocho (och-o)", R.drawable.number_8, R.raw.number_eight));
        words.add(new Word("Nine", "Nueve (new-eh-veh)", R.drawable.number_9, R.raw.number_nine));
        words.add(new Word("Ten", "Diez (dee-ace)", R.drawable.number_10, R.raw.number_ten));

        words.add(new Word("Eleven", "Once (ohn-say)", R.drawable.number_11, R.raw.number_eleven));
        words.add(new Word("Twelve", "Doce (dos-say)", R.drawable.number_12, R.raw.number_twelve));
        words.add(new Word("Thirteen", "Trece (treh-seh)", R.drawable.number_13, R.raw.number_thirteen));
        words.add(new Word("Fourteen", "Catorce (ca-TOR-say)", R.drawable.number_14, R.raw.number_fourteen));
        words.add(new Word("Fifteen", "Quince (KEEN-say)", R.drawable.number_15, R.raw.number_fifteetn));
        words.add(new Word("Sixteen", "Dieciséis (dee-AY-see-saze)", R.drawable.number_16, R.raw.number_sixteen));
        words.add(new Word("Seventeen", "Diecisiete (dee-AY-see-see-AY-tay)", R.drawable.number_17, R.raw.number_seventeen));
        words.add(new Word("Eighteen", "Dieciocho (dee-AY-see-och-o)", R.drawable.number_18, R.raw.number_eighteen));
        words.add(new Word("Nineteen", "Diecinueve (dee-AY-see-new-EH-veh)", R.drawable.number_19, R.raw.number_nineteen));
        words.add(new Word("Twenty", "Veinte (Veh-een-tee)", R.drawable.number_20, R.raw.number_twenty));

        words.add(new Word("Twenty one", "Veintiuno (Veh-een-tee-oo-no)", R.drawable.number_21, R.raw.number_twenty_one));
        words.add(new Word("Twenty two", "Veintidós (Veh-een-tee-DOS)", R.drawable.number_22, R.raw.number_twenty_two));
        words.add(new Word("Twenty three", "Veintitrés (Veh-een-tee-TRACE)", R.drawable.number_23, R.raw.number_twenty_three));
        words.add(new Word("Twenty four", "Veinticuatro (Veh-een-tee-KWAT-ro)", R.drawable.number_24, R.raw.number_twenty_four));
        words.add(new Word("Twenty five", "Veinticinco (Veh-een-tee-SINK-o)", R.drawable.number_25, R.raw.number_twenty_five));
        words.add(new Word("Twenty six", "Veintiséis (Veh-een-tee-saze)", R.drawable.number_26, R.raw.number_twenty_six));
        words.add(new Word("Twenty seven", "Veintisiete (Veh-een-tee-see-ay-tay)", R.drawable.number_27, R.raw.number_twenty_seven));
        words.add(new Word("Twenty eight", "Veintiocho (Veh-een-tee-OCH-o)", R.drawable.number_28, R.raw.number_twenty_eight));
        words.add(new Word("Twenty nine", "Veintinueve (Veh-een-tee-new-EH-veh)", R.drawable.number_29, R.raw.number_twenty_nine));
        words.add(new Word("Thirty", "Treinta (treh-een-tah)", R.drawable.number_30, R.raw.number_thirty));

        words.add(new Word("Thirty one", "Treinta y uno (treh-een-tah ee oo-no)", R.drawable.number_31, R.raw.number_thirty_one));
        words.add(new Word("Thirty two", "Treinta y dos (treh-een-tah ee DOS)", R.drawable.number_32, R.raw.number_thirty_two));
        words.add(new Word("Thirty three", "Treinta y tres (treh-een-tah ee tres)", R.drawable.number_33, R.raw.number_thirty_three));
        words.add(new Word("Thirty four", "Treinta y cuatro (treh-een-tah ee KWAT-ro)", R.drawable.number_34, R.raw.number_thirty_four));
        words.add(new Word("Thirty five", "Treinta y cinco (treh-een-tah ee SINK-o)", R.drawable.number_35, R.raw.number_thirty_five));
        words.add(new Word("Thirty six", "Treinta y seis (treh-een-tah ee saze)", R.drawable.number_36, R.raw.number_thirty_six));
        words.add(new Word("Thirty seven", "Treinta y siete (treh-een-tah ee see-ay-tay)", R.drawable.number_37, R.raw.number_thirty_seven));
        words.add(new Word("Thirty eight", "Treinta y ocho (treh-een-tah ee OCH-o)", R.drawable.number_38, R.raw.number_thirty_eight));
        words.add(new Word("Thirty nine", "Treinta y nueve (treh-een-tah ee new-EH-veh)", R.drawable.number_39, R.raw.number_thirty_nine));
        words.add(new Word("Forty", "Cuarenta (kwar-EN-tah)", R.drawable.number_40, R.raw.number_forty));

        words.add(new Word("Forty one", "Cuarenta y uno (kwar-EN-tah ee oo-no)", R.drawable.number_41, R.raw.number_forty_one));
        words.add(new Word("Forty two", "Cuarenta y dos (kwar-EN-tah ee DOS)", R.drawable.number_42, R.raw.number_forty_two));
        words.add(new Word("Forty three", "Cuarenta y tres (kwar-EN-tah ee tres)", R.drawable.number_43, R.raw.number_forty_three));
        words.add(new Word("Forty four", "Cuarenta y cuatro (kwar-EN-tah ee KWAT-ro)", R.drawable.number_44, R.raw.number_forty_four));
        words.add(new Word("Forty five", "Cuarenta y cinco (kwar-EN-tah ee SINK-o)", R.drawable.number_45, R.raw.number_forty_five));
        words.add(new Word("Forty six", "Cuarenta y seis (kwar-EN-tah ee saze)", R.drawable.number_46, R.raw.number_forty_six));
        words.add(new Word("Forty seven", "Cuarenta y siete (kwar-EN-tah ee see-ay-tay)", R.drawable.number_47, R.raw.number_forty_seven));
        words.add(new Word("Forty eight", "Cuarenta y ocho (kwar-EN-tah ee OCH-o)", R.drawable.number_48, R.raw.number_forty_eight));
        words.add(new Word("Forty nine", "Cuarenta y nueve (kwar-EN-tah ee new-EH-veh)", R.drawable.number_49, R.raw.number_forty_nine));
        words.add(new Word("Fifty", "Cincuenta (sink-KWEN-tah)", R.drawable.number_50, R.raw.number_fifty));

        words.add(new Word("Fifty one", "Cincuenta y uno (sink-KWEN-tah ee oo-no)", R.drawable.number_51, R.raw.number_fifty_one));
        words.add(new Word("Fifty two", "Cincuenta y dos (sink-KWEN-tah ee DOS)", R.drawable.number_52, R.raw.number_fifty_two));
        words.add(new Word("Fifty three", "Cincuenta y tres (sink-KWEN-tah ee tres)", R.drawable.number_53, R.raw.number_fifty_three));
        words.add(new Word("Fifty four", "Cincuenta y cuatro (sink-KWEN-tah ee KWAT-ro)", R.drawable.number_54, R.raw.number_fifty_four));
        words.add(new Word("Fifty five", "Cincuenta y cinco (sink-KWEN-tah ee SINK-o)", R.drawable.number_55, R.raw.number_fifty_five));
        words.add(new Word("Fifty six", "Cincuenta y seis (sink-KWEN-tah ee saze)", R.drawable.number_56, R.raw.number_fifty_six));
        words.add(new Word("Fifty seven", "Cincuenta y siete (sink-KWEN-tah ee see-ay-tay)", R.drawable.number_57, R.raw.number_fifty_seven));
        words.add(new Word("Fifty eight", "Cincuenta y ocho (sink-KWEN-tah ee OCH-o)", R.drawable.number_58, R.raw.number_fifty_eight));
        words.add(new Word("Fifty nine", "Cincuenta y nueve (sink-KWEN-tah ee new-EH-veh)", R.drawable.number_59, R.raw.number_fifty_nine));
        words.add(new Word("Sixty", "Sesenta (seh-SEHN-tah)", R.drawable.number_60, R.raw.number_sixty));

        words.add(new Word("Sixty one", "Sesenta y uno (seh-SEHN-tah ee oo-no)", R.drawable.number_61, R.raw.number_sixty_one));
        words.add(new Word("Sixty two", "Sesenta y dos (seh-SEHN-tah ee DOS)", R.drawable.number_62, R.raw.number_sixty_two));
        words.add(new Word("Sixty three", "Sesenta y tres (seh-SEHN-tah ee tres)", R.drawable.number_63, R.raw.number_sixty_three));
        words.add(new Word("Sixty four", "Sesenta y cuatro (seh-SEHN-tah ee KWAT-ro)", R.drawable.number_64, R.raw.number_sixty_four));
        words.add(new Word("Sixty five", "Sesenta y cinco (seh-SEHN-tah ee SINK-o)", R.drawable.number_65, R.raw.number_sixty_five));
        words.add(new Word("Sixty six", "Sesenta y seis (seh-SEHN-tah ee saze)", R.drawable.number_66, R.raw.number_sixty_six));
        words.add(new Word("Sixty seven", "Sesenta y siete (seh-SEHN-tah ee see-ay-tay)", R.drawable.number_67, R.raw.number_sixty_seven));
        words.add(new Word("Sixty eight", "Sesenta y ocho (seh-SEHN-tah ee OCH-o)", R.drawable.number_68, R.raw.number_sixty_eight));
        words.add(new Word("Sixty nine", "Sesenta y nueve (seh-SEHN-tah ee new-EH-veh)", R.drawable.number_69, R.raw.number_sixty_nine));
        words.add(new Word("Seventy", "Setenta (seh-TEHN-tah)", R.drawable.number_70, R.raw.number_seventy));

        words.add(new Word("Seventy one", "Setenta y uno (seh-TEHN-tah ee oo-no)", R.drawable.number_71, R.raw.number_seventy_one));
        words.add(new Word("Seventy two", "Setenta y dos (seh-TEHN-tah ee DOS)", R.drawable.number_72, R.raw.number_seventy_two));
        words.add(new Word("Seventy three", "Setenta y tres (seh-TEHN-tah ee tres)", R.drawable.number_73, R.raw.number_seventy_three));
        words.add(new Word("Seventy four", "Setenta y cuatro (seh-TEHN-tah ee KWAT-ro)", R.drawable.number_74, R.raw.number_seventy_four));
        words.add(new Word("Seventy five", "Setenta y cinco (seh-TEHN-tah ee SINK-o)", R.drawable.number_75, R.raw.number_seventy_five));
        words.add(new Word("Seventy six", "Setenta y seis (seh-TEHN-tah ee saze)", R.drawable.number_76, R.raw.number_seventy_six));
        words.add(new Word("Seventy seven", "Setenta y siete (seh-TEHN-tah ee see-ay-tay)", R.drawable.number_77, R.raw.number_seventy_seven));
        words.add(new Word("Seventy eight", "Setenta y ocho (seh-TEHN-tah ee OCH-o)", R.drawable.number_78, R.raw.number_seventy_eight));
        words.add(new Word("Seventy nine", "Setenta y nueve (seh-TEHN-tah ee new-EH-veh)", R.drawable.number_79, R.raw.number_seventy_nine));
        words.add(new Word("Eighty", "Ochenta (och-EHN-tah)", R.drawable.number_80, R.raw.number_eighty));

        words.add(new Word("Eighty one", "Ochenta y uno (och-EHN-tah ee oo-no)", R.drawable.number_81, R.raw.number_eighty_one));
        words.add(new Word("Eighty two", "Ochenta y dos (och-EHN-tah ee DOS)", R.drawable.number_82, R.raw.number_eighty_two));
        words.add(new Word("Eighty three", "Ochenta y tres (och-EHN-tah ee tres)", R.drawable.number_83, R.raw.number_eighty_three));
        words.add(new Word("Eighty four", "Ochenta y cuatro (och-EHN-tah ee KWAT-ro)", R.drawable.number_84, R.raw.number_eighty_four));
        words.add(new Word("Eighty five", "Ochenta y cinco (och-EHN-tah ee SINK-o)", R.drawable.number_85, R.raw.number_eighty_five));
        words.add(new Word("Eighty six", "Ochenta y seis (och-EHN-tah ee saze)", R.drawable.number_86, R.raw.number_eighty_six));
        words.add(new Word("Eighty seven", "Ochenta y siete (och-EHN-tah ee see-ay-tay)", R.drawable.number_87, R.raw.number_eighty_seven));
        words.add(new Word("Eighty eight", "Ochenta y ocho (och-EHN-tah ee OCH-o)", R.drawable.number_88, R.raw.number_eighty_eight));
        words.add(new Word("Eighty nine", "Ochenta y nueve (och-EHN-tah ee new-EH-veh)", R.drawable.number_89, R.raw.number_eighty_nine));
        words.add(new Word("Ninety", "Noventa (no-VEHN-tah)", R.drawable.number_90, R.raw.number_ninety));

        words.add(new Word("Ninety one", "Noventa y uno (no-VEHN-tah ee oo-no)", R.drawable.number_91, R.raw.number_ninety_one));
        words.add(new Word("Ninety two", "Noventa y dos (no-VEHN-tah ee DOS)", R.drawable.number_92, R.raw.number_ninety_two));
        words.add(new Word("Ninety three", "Noventa y tres (no-VEHN-tah ee tres)", R.drawable.number_93, R.raw.number_ninety_three));
        words.add(new Word("Ninety four", "Noventa y cuatro (no-VEHN-tah ee KWAT-ro)", R.drawable.number_94, R.raw.number_ninety_four));
        words.add(new Word("Ninety five", "Noventa y cinco (no-VEHN-tah ee SINK-o)", R.drawable.number_95, R.raw.number_ninety_five));
        words.add(new Word("Ninety six", "Noventa y seis (no-VEHN-tah ee saze)", R.drawable.number_96, R.raw.number_ninety_six));
        words.add(new Word("Ninety seven", "Noventa y siete (no-VEHN-tah ee see-ay-tay)", R.drawable.number_97, R.raw.number_ninety_seven));
        words.add(new Word("Ninety eight", "Noventa y ocho (no-VEHN-tah ee OCH-o)", R.drawable.number_98, R.raw.number_ninety_eight));
        words.add(new Word("Ninety nine", "Noventa y nueve (no-VEHN-tah ee new-EH-veh)", R.drawable.number_99, R.raw.number_ninety_nine));
        words.add(new Word("Hundred", "Cien (see-EHN)", R.drawable.number_100, R.raw.number_one_hundred));

        words.add(new Word("Thousand", "Mil", R.drawable.number_1k, R.raw.number_1k));
        words.add(new Word("Ten thousand", "Diez mil", R.drawable.number_10k, R.raw.number_10k));
        words.add(new Word("One hundred thousand", "Cien mil", R.drawable.number_100k, R.raw.number_100k));
        words.add(new Word("Million", "Un millón", R.drawable.number_1m, R.raw.number_1m));
        words.add(new Word("Ten million", "Diez millones", R.drawable.number_10m, R.raw.number_10m));
        words.add(new Word("One hundred million", "Cien millones", R.drawable.number_100m, R.raw.number_100m));
        words.add(new Word("Billion", "Un billón", R.drawable.number_1b, R.raw.number_1b));
        words.add(new Word("Ten billion", "Diez billones", R.drawable.number_10b, R.raw.number_10b));
        words.add(new Word("One hundred billion", "Cien billones", R.drawable.number_100b, R.raw.number_100b));
        words.add(new Word("Trillion", "Un trillón", R.drawable.number_1t, R.raw.number_1t));

        //Adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_numbers);
        ListView listView = (ListView) findViewById(R.id.list);
        if (listView != null) {
            listView.setAdapter(adapter);
        }

        //Set a click listener on list view to play audio when list item is clicked on
        if (listView != null) {
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                    // Release the media player if it currently exists because we are about to
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
                        mMediaPlayer = MediaPlayer.create(NumbersActivity.this, myWordPosition.getmAudioResourceId());
                        mMediaPlayer.start(); //no need to call prepare(); create() does that for us

                        //Setup a listener on the media player, so that we stop and release the
                        // media player once the sounds have finished playing
                        mMediaPlayer.setOnCompletionListener(mCompletionListener);
                    }
                }
            });
        }
    }//end onCreate()

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
