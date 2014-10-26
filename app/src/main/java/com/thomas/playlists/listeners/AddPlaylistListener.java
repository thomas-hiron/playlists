package com.thomas.playlists.listeners;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.thomas.playlists.PlaylistSearch;
import com.thomas.playlists.R;
import com.thomas.playlists.interfaces.OnPlaylistAdded;

import java.util.ArrayList;

import static com.thomas.playlists.R.id.editMinDanceability;

/**
 * Created by ThomasHiron on 20/10/2014.
 */
public class AddPlaylistListener implements View.OnClickListener
{
    private View mView;
    private OnPlaylistAdded mListener = null;

    public AddPlaylistListener(OnPlaylistAdded listener, View view)
    {
        mListener = listener;
        mView = view;
    }

    /**
     * Au clic sur le bouton de validation, on valide le formulaire
     *
     * @param view Le bouton cliqué
     */
    @Override
    public void onClick(View view)
    {
        /* On ferme le clavier */
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

        /* Le champs artist/genre */
        EditText eArtistGenre = (EditText) mView.findViewById(R.id.editArtistGenre);
        String artistGenre = eArtistGenre.getText().toString();

        /* Si le champ est vide, on affiche un toast et on stoppe la validation */
        if(artistGenre.equals(""))
        {
            Toast.makeText(view.getContext(), "Le champ artiste/genre doit être renseigné.", Toast.LENGTH_LONG).show();
            return;
        }

        /* Sinon on continue et on construit l'objet de recherche */
        PlaylistSearch playlistSearch = new PlaylistSearch();

        /* La requête */
        playlistSearch.setArtistGenre(artistGenre);

        /* Si artiste ou genre */
        RadioGroup rgArtistGenre = (RadioGroup) mView.findViewById(R.id.radioArtistGenre);
        int radioCheckedId = rgArtistGenre.getCheckedRadioButtonId();

        /* On set si genre ou artist */
        playlistSearch.isArtist(radioCheckedId == R.id.radioArtist);

        /* Le nombre de résultats */
        EditText eNbResults = (EditText) mView.findViewById(R.id.editNbResults);
        String nbResults = eNbResults.getText().toString();

        /* Si non vide */
        if(!nbResults.equals(""))
            playlistSearch.setNbResults(Integer.parseInt(nbResults));

        /* La dancabilité */
        EditText eDanceability = (EditText) mView.findViewById(editMinDanceability);
        String danceability = eDanceability.getText().toString();

        /* Si non vide */
        if(!danceability.equals(""))
            playlistSearch.setDanceability(Float.parseFloat(danceability) / 10);

        /* L'année de création */
        EditText eDateCreation = (EditText) mView.findViewById(R.id.editDateCreation);
        String dateCreation = eDateCreation.getText().toString();

        /* Si non vide */
        if(!dateCreation.equals(""))
            playlistSearch.setDateCreation(Integer.parseInt(dateCreation));

        /* Le tempo minimum */
        EditText eTempo = (EditText) mView.findViewById(R.id.editMinTempo);
        String tempo = eTempo.getText().toString();

        /* Si non vide */
        if(!tempo.equals(""))
            playlistSearch.setTempo(Integer.parseInt(tempo));

        /* Tous les types de musique */
        LinearLayout parent = (LinearLayout) mView.findViewById(R.id.musicTypes);
        CheckBox child;
        ArrayList<String> musicTypes = new ArrayList<String>();

        /* On parcourt les checkbox pour tester s'ils sont cochés */
        for(int i = 0, l = parent.getChildCount(); i < l; ++i)
        {
            child = (CheckBox) parent.getChildAt(i);

            if(child.isChecked())
                musicTypes.add(child.getText().toString());
        }

        /* On ajoute, il peut être vide de tout manière */
        playlistSearch.setMusicTypes(musicTypes);

        /* On fait appel à l'activité principale pour lancer la requête */
        mListener.onPlaylistAdded(playlistSearch);
    }
}
