package es.devjacl.transportcard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements Dialogo.ItemListener {

    private TextView saldoTxt, viajesBusTxt, viajesMetroTxt;
    private Button recargarBtn;
    private ImageButton BusBtn, MetroBtn;
    public double PRECIO_BUS;
    public double PRECIO_METRO;
    private double saldoDouble;
    private int viajesBus, viajesMetro;
    SharedPreferences.Editor editor;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        saldoDouble = prefs.getFloat("SALDO", 0);
        PRECIO_BUS = prefs.getFloat("PRECIO BUS", 0);
        PRECIO_METRO = prefs.getFloat("PRECIO METRO", 0);

        saldoDouble = Math.round(saldoDouble * Math.pow(10, 2)) / Math.pow(10, 2);
        PRECIO_BUS = Math.round(PRECIO_BUS * Math.pow(10, 2)) / Math.pow(10, 2);
        PRECIO_METRO = Math.round(PRECIO_METRO * Math.pow(10, 2)) / Math.pow(10, 2);

        saldoTxt = (TextView) findViewById(R.id.saldo);
        viajesBusTxt = (TextView) findViewById(R.id.textoBus);
        viajesMetroTxt = (TextView) findViewById(R.id.textoMetro);
        recargarBtn = (Button) findViewById(R.id.recargar);
        BusBtn = (ImageButton) findViewById(R.id.bus);
        MetroBtn = (ImageButton) findViewById(R.id.metro);

        viajesBus = (int) (saldoDouble / PRECIO_BUS);
        viajesMetro = (int) (saldoDouble / PRECIO_METRO);

        updateTexts();

        recargarBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Dialogo dialogo = new Dialogo();
                dialogo.show(fragmentManager, "tagAlerta");
            }
        });

        BusBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                updateSaldo(saldoDouble-PRECIO_BUS);
            }
        });

        MetroBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                updateSaldo(saldoDouble-PRECIO_METRO);
            }
        });
    }

    private void updateSaldo(double saldo) {
        if (saldo >= 0) {
            saldoDouble = saldo;
            saldoDouble = Math.round(saldoDouble * Math.pow(10, 2)) / Math.pow(10, 2);

            if (PRECIO_BUS > 0)
                viajesBus = (int) (saldoDouble / PRECIO_BUS);
            else
                viajesBus = 0;

            if (PRECIO_METRO > 0)
                viajesMetro = (int) (saldoDouble / PRECIO_METRO);
            else
                viajesMetro = 0;

            editor = prefs.edit();
            editor.putFloat("SALDO", (float) saldoDouble);
            editor.putFloat("PRECIO BUS", (float) PRECIO_BUS);
            editor.putFloat("PRECIO METRO", (float) PRECIO_METRO);
            editor.commit();

            updateTexts();
        }
    }

    private void updateTexts(){
        saldoTxt.setText(String.valueOf(saldoDouble) + " €");
        viajesBusTxt.setText((String.valueOf(viajesBus)) + " viajes");
        viajesMetroTxt.setText((String.valueOf(viajesMetro)) + " viajes");
    }

    public void lanzarPreferencias(View view){
        Intent i = new Intent(this, Preferencias.class);

        Bundle b = new Bundle();
        b.putString("PRECIO BUS", String.valueOf(PRECIO_BUS));
        b.putString("PRECIO METRO", String.valueOf(PRECIO_METRO));
        b.putString("SALDO", String.valueOf(saldoDouble));

        i.putExtras(b);
        startActivityForResult(i, 1234);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true; /** true -> el menú ya está visible */
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_preferencias) {
            lanzarPreferencias(null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override protected void onActivityResult (int requestCode,
                                               int resultCode, Intent data){
        if (requestCode==1234 && resultCode==RESULT_OK) {
            if (data.getExtras().getBoolean("CHANGE SALDO"))
                saldoDouble = Double.parseDouble(data.getExtras().getString("SALDO"));
            if (data.getExtras().getBoolean("CHANGE BUS")) {
                PRECIO_BUS = Double.parseDouble(data.getExtras().getString("PRECIO BUS"));
                PRECIO_BUS = Math.round(PRECIO_BUS * Math.pow(10, 2)) / Math.pow(10, 2);
            }
            if (data.getExtras().getBoolean("CHANGE METRO")) {
                PRECIO_METRO = Double.parseDouble(data.getExtras().getString("PRECIO METRO"));
                PRECIO_METRO = Math.round(PRECIO_METRO * Math.pow(10, 2)) / Math.pow(10, 2);
            }
            updateSaldo(saldoDouble);
        }
    }

    public void onItemSelect(String item){
        saldoDouble += Double.parseDouble(item);
        updateSaldo(saldoDouble);
    }
}