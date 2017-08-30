package es.devjacl.transportcard;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by jeacelo on 30/08/2017.
 */

public class Dialogo extends DialogFragment {

    private EditText recarga;
    private Button btn5;
    private Button btn10;
    private Button btn20;

    public interface ItemListener {
        void onItemSelect(String inputText);
    }

    ItemListener listener;

    @Override public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (ItemListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +" debe implementar ItemListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v = inflater.inflate(R.layout.dialog, null);
        builder.setView(v);

        recarga = (EditText) v.findViewById(R.id.saldoManualIn);
        btn5 = (Button) v.findViewById(R.id.button5);
        btn10 = (Button) v.findViewById(R.id.button10);
        btn20 = (Button) v.findViewById(R.id.button20);

        btn5.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recarga.setText("5");
                    }
                }
        );

        btn10.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recarga.setText("10");
                    }
                }
        );

        btn20.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recarga.setText("20");
                    }
                }
        );

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                listener.onItemSelect(recarga.getText().toString());
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        return builder.create();
    }

}
