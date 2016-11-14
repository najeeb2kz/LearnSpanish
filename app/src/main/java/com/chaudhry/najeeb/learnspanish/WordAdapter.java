package com.chaudhry.najeeb.learnspanish;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

//WordAdapter is responsible for loading the right info from word ojbect (array) into layout.
public class WordAdapter extends ArrayAdapter<Word> {

    //Resource ID for background color for this list of words
    private int mColorResourceID;

    public WordAdapter(Context context, ArrayList<Word> word, int colorResourceID) {
        //passing in 0 for resource id because we don't need to rely on superclass array adapter inflating or creating list view for us
        //instead our getView method will manually handle the inflating of the layout from the layout resource id for ourselves
        super(context, 0, word);
        mColorResourceID = colorResourceID;
    }

     //Provides a view for an AdapterView (ListView, GridView, etc.)
     //@param position The position in the list of data that should be displayed in the list item view.
     //@param convertView The recycled view to populate.
     //@param parent The parent ViewGroup that is used for inflation. List item will be added to parent view group.
     //@return The View for the position in the AdapterView.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Currently listItemView is referencing root linear layout of list item layout
        View listItemView = convertView;
        if (listItemView == null) {  //Sometimes convertView view can be null. If null then create one to use
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        //getItem(position) is defined in super class ArrayAdapter.  This method returns item in the list at given idex position
        Word currentWord = getItem(position);

        //listItemView.findViewById(): we are calling findViewById() method on listItemView
        TextView defaultTranslationTextView = (TextView) listItemView.findViewById(R.id.defaultTranslationTextView);
        defaultTranslationTextView.setText(currentWord.getDefaultTranslation());

        TextView learnSpanishTranslationTextView = (TextView) listItemView.findViewById(R.id.learnSpanishTranslationTextView);
        learnSpanishTranslationTextView.setText(currentWord.getSpanishTranslation());

        //Find the ImageView in list_item.xml layout with the ID image
        ImageView iconView = (ImageView) listItemView.findViewById(R.id.iconView);
        if (currentWord.hasImage()) {
            iconView.setImageResource(currentWord.getImageResourceId());
            //Make sure the view is visible
            iconView.setVisibility(View.VISIBLE);
        } else {
            iconView.setVisibility(View.GONE);  //GONE means view is hidden and doesn't take any space
        }

        //set background color on linear layout which contains two TextViews
        LinearLayout linearLayoutForTextViews = (LinearLayout) listItemView.findViewById(R.id.text_container); //Instead of LinearLayout, could have used View
        //Find color that resource ID maps to
        int color = ContextCompat.getColor(getContext(), mColorResourceID);
        //Set background color of the container view
        linearLayoutForTextViews.setBackgroundColor(color);

        //Return the whole list item layout containing 2 TextViews and one ImageView so it can be shown in the ListView.
        return listItemView;
    }
}
