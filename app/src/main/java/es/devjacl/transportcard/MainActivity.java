package es.devjacl.transportcard;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;;


public class MainActivity extends AppCompatActivity {

    private TextView saldoTxt, viajesBusTxt, viajesMetroTxt;
    private Button recargarBtn;
    private ImageButton BusBtn, MetroBtn;
    public double PRECIO_BUS = 0.93;
    public double PRECIO_METRO = 0.82;
    private double saldoDouble;
    private int viajesBus, viajesMetro;
    private String saldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saldoTxt = (TextView) findViewById(R.id.saldo);
        viajesBusTxt = (TextView) findViewById(R.id.textoBus);
        viajesMetroTxt = (TextView) findViewById(R.id.textoMetro);
        recargarBtn = (Button) findViewById(R.id.recargar);
        BusBtn = (ImageButton) findViewById(R.id.bus);
        MetroBtn = (ImageButton) findViewById(R.id.metro);

        saldo = saldoTxt.getText().toString();
        saldoDouble = (Double.parseDouble(saldo.substring(0, saldo.length() - 2)));
        saldoDouble = Math.round(saldoDouble*Math.pow(10,2))/Math.pow(10,2);

        viajesBus = (int) (saldoDouble / PRECIO_BUS);
        viajesMetro = (int) (saldoDouble / PRECIO_METRO);

        viajesBusTxt.setText((String.valueOf(viajesBus)) + " viajes");
        viajesMetroTxt.setText((String.valueOf(viajesMetro)) + " viajes");

        recargarBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                updateSaldo(5);
            }
        });

        BusBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View arg0) {
                updateSaldo(-PRECIO_BUS);
            }
        });

        MetroBtn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View arg0) {
                updateSaldo(-PRECIO_METRO);
            }
        });
    }

    private void updateSaldo(double saldo)
    {
        saldoDouble += saldo;
        saldoDouble = Math.round(saldoDouble*Math.pow(10,2))/Math.pow(10,2);
        saldoTxt.setText(String.valueOf(saldoDouble) + " €");
        updateViajes();
    }

    private void updateViajes()
    {
        viajesBus = (int) (saldoDouble / PRECIO_BUS);
        viajesMetro = (int) (saldoDouble / PRECIO_METRO);

        viajesBusTxt.setText((String.valueOf(viajesBus)) + " viajes");
        viajesMetroTxt.setText((String.valueOf(viajesMetro)) + " viajes");
    }
}