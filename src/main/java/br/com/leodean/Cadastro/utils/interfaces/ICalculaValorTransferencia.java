package br.com.leodean.Cadastro.utils.interfaces;

import java.math.BigDecimal;

public interface ICalculaValorTransferencia {
    BigDecimal caculoValorTransferenciaD0(BigDecimal valorTransacao);

    BigDecimal calculaValorTransferenciaD10(BigDecimal valorTransacao);

    BigDecimal calculaValorTransferenciaRegressiva(long diferencaData, BigDecimal valorTransacao);

    BigDecimal caculaValorTransferenciaTipoValor(long diferencaData, BigDecimal valorTransacao);
}
