package com.thomas.playlists;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.widget.EditText;

import com.thomas.playlists.listeners.SavePlaylistListener;

/**
 * Created by ThomasHiron on 25/10/2014.
 *
 * Affiche d'une alerte pour saisir le nom de la playlist
 */
public class DialogPlaylistTitle
{
    public DialogPlaylistTitle(final Context context, final SavePlaylistListener savePlaylistListener)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        /* Le titre */
        alertDialogBuilder.setTitle(R.string.playlistTitle);

        /* Ajout d'un champ input */
        final EditText input = new EditText(context);

        /* Ajout du type du champ */
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        /* Ajout du champ dans le dialog */
        alertDialogBuilder.setView(input);

        /* Configuration et listeners */
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(R.string.validate, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        savePlaylistListener.dialogValidated(input.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        /* Fermeture de l'alert, pas d'enregistrement */
                        dialog.cancel();
                    }
                });

        /* Creataion de l'alert */
        AlertDialog alertDialog = alertDialogBuilder.create();

        /* Affichage */
        alertDialog.show();
    }
}
