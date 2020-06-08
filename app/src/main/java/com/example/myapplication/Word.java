package com.example.myapplication;

public class Word {
    private String mDefaultTranslation;
    private String mMiwokTranslation;
    private int mAudioResourceId;
    private int mImageResourceId=NO_IMAGE_PROVIDED;
    private static final int NO_IMAGE_PROVIDED=-1;
    public Word(String DefaultTranslation,String MiwokTranslation,int AudioResourceId)
    {
        mDefaultTranslation=DefaultTranslation;
        mMiwokTranslation=MiwokTranslation;
        mAudioResourceId=AudioResourceId;
    }
    public Word(String DefaultTranslation,String MiwokTranslation,int ImageResourceId,int AudioResourceId)
    {
        mAudioResourceId=AudioResourceId;
        mDefaultTranslation=DefaultTranslation;
        mMiwokTranslation=MiwokTranslation;
        mImageResourceId=ImageResourceId;
    }
    public String getDefaultTranslation(){
        return mDefaultTranslation;
    }
    public String getMiwokTranslation(){
        return mMiwokTranslation;
    }
    public int getImageResourceId(){return mImageResourceId;}
    public int getAudioResourceId(){return mAudioResourceId;}
    public boolean hasImage()
    {
        return mImageResourceId!=NO_IMAGE_PROVIDED;
    }

}
