package com.thomas.playlists.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.thomas.playlists.interfaces.OnPlaylistAdded} interface
 * to handle interaction events.
 */
public class AddPlaylistFragment extends Fragment implements View.OnClickListener
{
    private OnPlaylistAdded mListener;

    public AddPlaylistFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_playlist, container, false);

        /* Ajout du listener au clic sur le bouton */
        Button validate = (Button) view.findViewById(R.id.validAddPlaylist);
        validate.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            mListener = (OnPlaylistAdded) activity;
        }
        catch(ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + " must implement OnPlaylistAdded");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    /**
     * Au clic sur le bouton de validation, on valide le formulaire
     *
     * @param view Le bouton cliqué
     */
    @Override
    public void onClick(View view)
    {
        /* L'activité courante */
        Activity ac = AddPlaylistFragment.this.getActivity();

        /* Le champs artist/genre */
        EditText eArtistGenre = (EditText) ac.findViewById(R.id.editArtistGenre);
        String artistGenre = eArtistGenre.getText().toString();

        /* Si le champ est vide, on affiche un toast et on stoppe la validation */
        if(artistGenre.equals(""))
        {
            Toast.makeText(ac, "Le champ artiste/genre doit être renseigné.", Toast.LENGTH_LONG).show();
            return;
        }

        /* Sinon on continue et on construit l'objet de recherche */
        PlaylistSearch playlistSearch = new PlaylistSearch();

        /* La requête */
        playlistSearch.setArtistGenre(artistGenre);

        /* Si artiste ou genre */
        RadioGroup rgArtistGenre = (RadioGroup) ac.findViewById(R.id.radioArtistGenre);
        int radioCheckedId = rgArtistGenre.getCheckedRadioButtonId();

        /* On set si ou artist */
        playlistSearch.isArtist(radioCheckedId == R.id.radioArtist);

        /* Le nombre de résultats */
        EditText eNbResults = (EditText) ac.findViewById(R.id.editNbResults);
        String nbResults = eNbResults.getText().toString();

        /* Si non vide */
        if(!nbResults.equals(""))
            playlistSearch.setNbResults(Integer.parseInt(nbResults));

        /* La dancabilité */
        EditText eDanceability = (EditText) ac.findViewById(editMinDanceability);
        String danceability = eDanceability.getText().toString();

        /* Si non vide */
        if(!danceability.equals(""))
            playlistSearch.setDanceability(Integer.parseInt(danceability) / 10);

        /* L'année de création */
        EditText eDateCreation = (EditText) ac.findViewById(R.id.editDateCreation);
        String dateCreation = eDateCreation.getText().toString();

        /* Si non vide */
        if(!dateCreation.equals(""))
            playlistSearch.setDateCreation(Integer.parseInt(dateCreation));

        /* Le tempo minimum */
        EditText eTempo = (EditText) ac.findViewById(R.id.editMinTempo);
        String tempo = eTempo.getText().toString();

        /* Si non vide */
        if(!tempo.equals(""))
            playlistSearch.setTempo(Integer.parseInt(tempo));

        /* Tous les types de musique */
        LinearLayout parent = (LinearLayout) ac.findViewById(R.id.musicTypes);
        CheckBox child = null;
        ArrayList<String> musicTypes = new ArrayList<String>();

        /* On parcourt les checkbox pour tester s'ils sont cochés */
        for(int i = 0, l = parent.getChildCount() ; i < l ; ++i)
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
