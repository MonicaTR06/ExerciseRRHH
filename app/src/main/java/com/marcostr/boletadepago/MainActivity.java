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
    private final int horaSalidaRegular = 18;

    private final int horaInicioAl30 = 22;

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
                    int horasAl20 = obtenerHorasTrabajadasAl20(textoHoraSalida); //Monica
                    int horasAl30 = obtenerHorasTrabajadasAl30(textoHoraSalida);
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

                    //Marcos

                    //Mostrar mensaje de error
                    //Toast Corrija los siguientes campos: + mensajeError
                }
            }
        });

    }

    private boolean validarHorario(String textoHoraIngreso, String textoHoraSalida) { // Nonis
        //Valida que el ingreso no supere la hora de salida y no sea igual
        return false;
    }

    private int obtenerHorasEfectivas(String textoHoraIngreso, String textoHoraSalida) { // Marcos
        return 0;
    }

    private int obtenerHorasTrabajadasAl30( String textoHoraSalida) {
        String ArrayHSalida[]= textoHoraSalida.split(":");
        int horasSalida = Integer.parseInt(ArrayHSalida[0]);
        int obtenerHorasTrabajadasAl30 = 0;
        if(horasSalida >22){
            obtenerHorasTrabajadasAl30 = horasSalida -22;
        }

        return obtenerHorasTrabajadasAl30;
    }

    private int obtenerHorasTrabajadasAl20(String textoHoraSalida) {
        int horasAl20 = 0;

        String[] arrayHoraSalida = textoHoraSalida.split(":");
        int horaDeSalida = Integer.parseInt(arrayHoraSalida[0]);

        if(horaDeSalida > horaInicioAl30){
            int horas30 = (horaDeSalida - horaInicioAl30);
            horaDeSalida = horaDeSalida - horas30;
        }

        if(horaDeSalida > horaSalidaRegular){
            horasAl20 =  horaDeSalida - horaSalidaRegular;
        }
        return horasAl20;
    }

    private int obtenerHorasTrabajadasHorarioRegular(String textoHoraIngreso, String textoHoraSalida) {

        int horasTrabajadasHorarioRegular = 0;
        final int horarioEmpunto = 0, horaIngresoRegular = 9, horaSalidaRegular = 18, horaAlmuerzo = 13 , hora = 1;

        String[] arrayHoraIngresada = textoHoraIngreso.split(":");
        int horaDeIngreso = Integer.parseInt(arrayHoraIngresada[0]);
        int minutoDeIngreso = Integer.parseInt(arrayHoraIngresada[1]);

        String[] arrayHoraSalida = textoHoraSalida.split(":");
        int horaDeSalida = Integer.parseInt(arrayHoraSalida[0]);

        if(horaDeIngreso<horaIngresoRegular){
            horaDeIngreso = horaIngresoRegular;
            minutoDeIngreso = horarioEmpunto;
        }

        if(minutoDeIngreso > horarioEmpunto){
            horaDeIngreso = horaDeIngreso + hora;
        }

        if(horaDeSalida > horaSalidaRegular){
            horaDeSalida = horaSalidaRegular;
        }

        horasTrabajadasHorarioRegular = horaDeSalida - horaDeIngreso;

        if (horaDeIngreso <= horaAlmuerzo){
            horasTrabajadasHorarioRegular = horasTrabajadasHorarioRegular - hora;
        }

        return horasTrabajadasHorarioRegular;
    }

    private double obtenerSueldoHoraRegular(String textoSueldo) { // Moni
        // usar horasMensuales
        return 0;
    }

    private double obtenerSueldoHoraAl30(double sueldoHoraRegular) { // Moni
        return 0;
    }

    private double obtenerSueldoHoraAl20(double sueldoHoraRegular) { // Moni
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