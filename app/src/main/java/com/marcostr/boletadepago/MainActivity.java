package com.marcostr.boletadepago;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final int horasMensuales = 160;
    
    EditText edt_sueldoBruto, edt_HoradeIngreso, edt_HoradeSalida;
    Button button_CalcularPago;
    TextView txv_HotasTotales, txv_HorasEfectivas, txv_HorasExtra20, txv_HorasExtra30, txv_PagoHorasExtra20, txv_PagoHorasExtra30, txv_MontoaPagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_sueldoBruto = findViewById(R.id.edt_sueldoBruto);
        edt_HoradeIngreso = findViewById(R.id.edt_HoradeIngreso);
        edt_HoradeSalida = findViewById(R.id.edt_HoradeSalida);
        button_CalcularPago = findViewById(R.id.button_CalcularPago);

        txv_HotasTotales = findViewById(R.id.txv_HotasTotales);
        txv_HorasEfectivas = findViewById(R.id.txv_HorasEfectivas);
        txv_HorasExtra20 = findViewById(R.id.txv_HorasExtra20);
        txv_HorasExtra30 = findViewById(R.id.txv_HorasExtra30);
        txv_PagoHorasExtra20 = findViewById(R.id.txv_PagoHorasExtra20);
        txv_PagoHorasExtra30 = findViewById(R.id.txv_PagoHorasExtra30);
        txv_MontoaPagar = findViewById(R.id.txv_MontoaPagar);

        button_CalcularPago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoHoraIngreso = edt_HoradeIngreso.getText().toString();
                String textoHoraSalida = edt_HoradeSalida.getText().toString();
                String textoSueldo = edt_sueldoBruto.getText().toString();

                String mensajeError = "";
                if(!horaValida(textoHoraIngreso)){
                    mensajeError = "Hora de Ingreso,";
                }

                if(!horaValida(textoHoraSalida)){
                    mensajeError = mensajeError + " Hora de Salida,";
                }

                if(!sueldoValido(textoSueldo)){
                    mensajeError = mensajeError + "Sueldo";
                }

                if(!validarHorario(textoHoraIngreso, textoHoraSalida)){
                    mensajeError = mensajeError + "La hora de salida no puede ser menor a la hora de ingreso";
                }

                if(mensajeError.isEmpty()){
                    //Campos llenados correctamente
                    int horasRegulares = obtenerHorasTrabajadasHorarioRegular(textoHoraIngreso, textoHoraSalida);
                    int horasAl20 = obtenerHorasTrabajadasAl20(textoHoraIngreso, textoHoraSalida);
                    int horasAl30 = obtenerHorasTrabajadasAl30(textoHoraIngreso, textoHoraSalida);
                    int horasTotales = horasRegulares + horasAl20+ horasAl30;

                    int horasEfectivas = obtenerHorasEfectivas(textoHoraIngreso, textoHoraSalida);

                    double pagoxHoraRegular = obtenerSueldoHoraRegular(textoSueldo);
                    double pagoxHoraAl20 = obtenerSueldoHoraAl20(pagoxHoraRegular);
                    double pagoxHoraAl30 = obtenerSueldoHoraAl30(pagoxHoraRegular);

                    double totalPagarAl20 = horasAl20 * pagoxHoraAl20;
                    double totalPagarAl30 = horasAl30 * pagoxHoraAl30;

                    double totalPagoDelDia = (horasRegulares * pagoxHoraRegular) + totalPagarAl20 + totalPagarAl30;

                    //Mostrar Reporte
                    txv_HotasTotales.setText(getString(R.string.horas_totales) + horasTotales);
                    txv_HorasEfectivas.setText(getString(R.string.horas_efectivas) + horasEfectivas);
                    txv_HorasExtra20.setText(getString(R.string.horas_extra_al_20) + horasAl20);
                    txv_HorasExtra30.setText(getString(R.string.horas_extra_al_30) + horasAl30);
                    txv_PagoHorasExtra20.setText(getString(R.string.pago_de_horas_extras_al_20) + totalPagarAl20);
                    txv_PagoHorasExtra30.setText(getString(R.string.pago_de_horas_extras_al_30) + totalPagarAl30);
                    txv_MontoaPagar.setText(getString(R.string.monto_total_a_pagar) + totalPagoDelDia );
                }else{
                    //Mostrar mensaje de error
                    //Toast Corrija los siguientes campos: + mensajeError
                }

            }
        });

    }

    private boolean validarHorario(String textoHoraIngreso, String textoHoraSalida) {
        //Valida que el ingreso no supere la hora de salida
        return false;
    }

    private int obtenerHorasEfectivas(String textoHoraIngreso, String textoHoraSalida) {
        return 0;
    }

    private int obtenerHorasTrabajadasAl30(String textoHoraIngreso, String textoHoraSalida) {
        return 0;
    }

    private int obtenerHorasTrabajadasAl20(String textoHoraIngreso, String textoHoraSalida) {
        return 0;
    }

    private int obtenerHorasTrabajadasHorarioRegular(String textoHoraIngreso, String textoHoraSalida) {
        return 0;
    }

    private double obtenerSueldoHoraRegular(String textoSueldo) {
        // usar horasMensuales
        return 0;
    }

    private double obtenerSueldoHoraAl30(double sueldoHoraRegular) {
        return 0;
    }

    private double obtenerSueldoHoraAl20(double sueldoHoraRegular) {
        return 0;
    }

    private boolean horaValida(String horaIngresada){
        boolean horaValida = false;
        int hora, minutos;
        if(horaIngresada.length() == 5 ){
            String[] arrayHora = horaIngresada.split(":");
            hora = Integer.parseInt(arrayHora[0]);
            minutos = Integer.parseInt(arrayHora[1]);
            if(hora >= 1 && hora <= 24){
                if(minutos>=0 && minutos<60) {
                    horaValida = true;
                }else{
                    horaValida = false;
                }
            }else{
                horaValida = false;
            }
        }else{
            return false;
        }
        return horaValida;
    }

    private boolean sueldoValido(String sueldoBruto) {
        try {
            if(sueldoBruto.isEmpty()){
               return false;
            }

            double sueldoBrutovalido = Double.parseDouble(sueldoBruto);
            if(sueldoBrutovalido < 0){
                return false;
            }

            return true;
        }catch (Exception ex){
            return false;
        }
    }

}