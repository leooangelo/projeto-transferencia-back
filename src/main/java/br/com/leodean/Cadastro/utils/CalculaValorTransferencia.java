package br.com.leodean.Cadastro.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class CalculaValorTransferencia implements ICalculaValorTransferencia{

    public BigDecimal caculoValorTransferenciaD0(BigDecimal valorTransacao) {
        var valorTransferido = valorTransacao.multiply(new BigDecimal(3.0)).divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
        var valorTotal = valorTransferido.add(new BigDecimal(3));

        return valorTotal;
    }

    public BigDecimal calculaValorTransferenciaD10(BigDecimal valorTransacao){
        var valorTotal = new BigDecimal(12);

        return valorTotal;
    }

    public BigDecimal calculaValorTransferenciaRegressiva(long diferencaData,BigDecimal valorTransacao){

        if(diferencaData <= 20L){
            var valorTotal =  valorTransacao.multiply(new BigDecimal(8.2)).divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
            return valorTotal;
        }
        else if (diferencaData <= 30L){
            var valorTotal =  valorTransacao.multiply(new BigDecimal(6.9)).divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
            return valorTotal;
        }
        else if(diferencaData <= 40L){
            var valorTotal =  valorTransacao.multiply(new BigDecimal(4.7)).divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
            return valorTotal;
        }

        var valorTotal =  valorTransacao.multiply(new BigDecimal(1.7)).divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
        return valorTotal;
    }

    public BigDecimal caculaValorTransferenciaTipoValor(long diferencaData,BigDecimal valorTransacao){
        var tipoAFluxoValor = valorTransacao.compareTo(new BigDecimal(1000));
        if(tipoAFluxoValor <= 0 )
            return caculoValorTransferenciaD0(valorTransacao);

        var tipoBFluxoValor = valorTransacao.compareTo(new BigDecimal(2000));
        if(tipoBFluxoValor <= 0 )
            return calculaValorTransferenciaD10(valorTransacao);

        return calculaValorTransferenciaRegressiva(diferencaData,valorTransacao);
    }
}
