package es.devjacl.transportcard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ToggleButton;


/**
 * Created by jeacelo on 29/08/2017.
 */

public class Preferencias extends Activity {

    private ToggleButton BusBtn, MetroBtn, SaldoBtn;
    private EditText BusText, MetroText, SaldoText;

    @Override public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferencias);

        BusBtn = (ToggleButton) findViewById(R.id.BusActivate);
        MetroBtn = (ToggleButton) findViewById(R.id.MetroActivate);
        SaldoBtn = (ToggleButton) findViewById(R.id.SaldoActivate);
        BusText = (EditText) findViewById(R.id.precioBus);
        MetroText = (EditText) findViewById(R.id.precioMetro);
        SaldoText = (EditText) findViewById(R.id.saldoManual);

        Bundle b = this.getIntent().getExtras();

        BusText.setText(b.getString("PRECIO BUS"));
        MetroText.setText(b.getString("PRECIO METRO"));
        SaldoText.setText(b.getString("SALDO"));

        BusBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (BusBtn.isChecked()) BusText.setEnabled(true);
                else BusText.setEnabled(false);
            }
        });

        MetroBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (MetroBtn.isChecked()) MetroText.setEnabled(true);
                else MetroText.setEnabled(false);
            }
        });

        SaldoBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (SaldoBtn.isChecked()) SaldoText.setEnabled(true);
                else SaldoText.setEnabled(false);
            }
        });
    }

    public void lanzarMain (View view)
    {
        Intent i = new Intent();
        i.putExtra("PRECIO BUS", BusText.getText().toString());
        i.putExtra("PRECIO METRO", MetroText.getText().toString());
        i.putExtra("SALDO", SaldoText.getText().toString());

        setResult(RESULT_OK, i);
        finish();
    }

}
