package br.com.leodean.Cadastro.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class DataValidatorUtil {

    public static long verificaData(LocalDate dataTransacao, LocalDate dataAgendamento) {
        var diferencaData = dataAgendamento.until(dataTransacao, ChronoUnit.DAYS);
        return diferencaData;
    }
}
