package br.com.leodean.Cadastro.domain;

public enum EnumTipoTransacao {

    TRANS_DIA(1, "  TRANS_DIA", "Transferências no mesmo dia do agendamento tem uma taxa de $3 mais 3% do valor a ser transferido"),
    TRANS_DIA_10(2, "TRANS_DIA_10", "Transferências até 10 dias da data de agendamento possuem uma taxa de $12"),
    TRANS_RESGRESSIVA(3, "TRANS_RESGRESSIVA", "Operações do tipo C tem uma taxa regressiva conforme a data de transferência"),
    TRANS_TIPO_VALOR(4, "TRANS_TIPO_VALOR", "Operações do tipo D tem a taxa igual a A, B ou C dependendo do valor da transferência");

    private final Integer id;
    private final String nome;
    private final String descricao;

    EnumTipoTransacao(Integer id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
