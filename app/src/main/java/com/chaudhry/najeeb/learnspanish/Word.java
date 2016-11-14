package com.chaudhry.najeeb.learnspanish;

public class Word {

    private String mDefaultTranslation;
    private String mLearnSpanishTranslation;
    //Image resource ID for the word
    private int mImageResourceId = NO_IMAGE_PROVIDED;
    //Constant vlaue that represents no image was provided for this word
    private static final int NO_IMAGE_PROVIDED = -1;
    //Audrio resource ID for the word
    private int mAudioResourceId;

    public Word(String defaultTranslation, String spanishTranslation, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mLearnSpanishTranslation = spanishTranslation;
        mAudioResourceId = audioResourceId;
    }

    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mLearnSpanishTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    public String getSpanishTranslation() {
        return mLearnSpanishTranslation;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    //Return whether or not there is an image for this word
    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;  //if mImageResourceId is not eqaual to -1 return true
    }

    //Return audio resource ID of the word
    public int getmAudioResourceId() {
        return mAudioResourceId;
    }
}
