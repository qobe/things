package kobe.things.game;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by mkobe on 2/16/2015.
 */
public class QuestionDeck {

    private ArrayList<String> mActiveDeck = new ArrayList();
    private ArrayList<String> mDiscardPile = new ArrayList();
    private int mActiveIndex;
    private int mDiscardIndex;

    public QuestionDeck(){
        mDiscardIndex = 0;
        importDeck("/sdcard/exampledeck.txt");
        mActiveIndex = mActiveDeck.size();
    }

    public QuestionDeck(String deckId){
        //get deck id
    }

    private void importDeck(String filePath){
        //read deck from file
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();
            while(line != null){
                mActiveDeck.add(line);
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            Log.e("FileReader", "File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList getActiveDeck(){
        return mActiveDeck;
    }

    public ArrayList getDiscardPile(){
        return mDiscardPile;
    }

    public void discard(){
        mDiscardPile.add(mDiscardIndex, mActiveDeck.remove(mActiveIndex));
        mDiscardIndex++;
    }

    public String drawNext(){
        String result = mActiveDeck.get(mActiveIndex);
        mActiveIndex--;
        return result;
    }

    public int getIndex(){
        return mActiveIndex;
    }
}
