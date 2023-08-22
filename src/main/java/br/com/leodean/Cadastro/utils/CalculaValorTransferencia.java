package br.com.leodean.Cadastro.utils;

import br.com.leodean.Cadastro.utils.interfaces.ICalculaValorTransferencia;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Leonardo Angelo
 * @since 18/08/2023
 */
@Component
public class CalculaValorTransferencia implements ICalculaValorTransferencia {

    @Value("${taxaTransfD0}")
    private Double taxaTransfD0;

    @Value("${taxaTransfD20}")
    private Double taxaTransfD20;

    @Value("${taxaTransfD30}")
    private Double taxaTransfD30;

    @Value("${taxaTransfD40}")
    private Double taxaTransfD40;

    @Value("${taxaTransfD}")
    private Double taxaTransfD;

    @Value("${acresimoTaxaD0}")
    private Double acresimoTaxaD0;

    @Value("${acresimoTaxaD10}")
    private Double acresimoTaxaD10;


    public BigDecimal caculoValorTransferenciaD0(BigDecimal valorTransacao) {
        var valorTransferido = valorTransacao.multiply(new BigDecimal(taxaTransfD0)).divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
        var valorTotal = valorTransferido.add(new BigDecimal(acresimoTaxaD0));

        return valorTotal;
    }

    public BigDecimal calculaValorTransferenciaD10(BigDecimal valorTransacao){
        var valorTotal = new BigDecimal(acresimoTaxaD10);

        return valorTotal;
    }

    public BigDecimal calculaValorTransferenciaRegressiva(long diferencaData,BigDecimal valorTransacao){

        if(diferencaData <= 20L){
            var valorTotal =  valorTransacao.multiply(new BigDecimal(taxaTransfD20)).divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
            return valorTotal;
        }
        else if (diferencaData <= 30L){
            var valorTotal =  valorTransacao.multiply(new BigDecimal(taxaTransfD30)).divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
            return valorTotal;
        }
        else if(diferencaData <= 40L){
            var valorTotal =  valorTransacao.multiply(new BigDecimal(taxaTransfD40)).divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
            return valorTotal;
        }

        var valorTotal =  valorTransacao.multiply(new BigDecimal(taxaTransfD)).divide(new BigDecimal(100)).setScale(2, RoundingMode.HALF_UP);
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
