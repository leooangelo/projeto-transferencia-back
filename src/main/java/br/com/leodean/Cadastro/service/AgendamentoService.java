package br.com.leodean.Cadastro.service;

import br.com.leodean.Cadastro.domain.AgendamentoRequest;
import br.com.leodean.Cadastro.domain.EnumTipoTransacao;
import br.com.leodean.Cadastro.domain.databaseDomain.AgendamentoDataBase;
import br.com.leodean.Cadastro.domain.dto.AgendamentoDTO;
import br.com.leodean.Cadastro.domain.mapper.AgendamentoMapper;
import br.com.leodean.Cadastro.exceptions.ExceptionApiCadastro;
import br.com.leodean.Cadastro.repositories.IAgendamentoRepository;
import br.com.leodean.Cadastro.service.interfaces.IAgendamentoService;
import br.com.leodean.Cadastro.service.interfaces.auth.ITokenService;
import br.com.leodean.Cadastro.utils.DataValidatorUtil;
import br.com.leodean.Cadastro.utils.interfaces.ICalculaValorTransferencia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Leonardo Angelo
 * @since 19/08/2023
 */
@Service
public class AgendamentoService implements IAgendamentoService {

    @Autowired
    private ICalculaValorTransferencia iCalculaValorTransferencia;
    @Autowired
    private IAgendamentoRepository iAgendamentoRepository;
    @Autowired
    private ITokenService tokenService;

    @Override
    public Page<AgendamentoDTO> listarAgendamentos(Pageable pageable){

        try{
            var customerID = tokenService.getCustomerIdByToken();

            List<AgendamentoDTO> listaAgendamentos = new ArrayList<>();
            var agendamentosPaginadoList =  iAgendamentoRepository.findByIdPessoaOrigem(pageable,customerID);
            for(AgendamentoDataBase agendamento : agendamentosPaginadoList){
                var agendamentoDTO = AgendamentoMapper.mappToResponse(agendamento);
                listaAgendamentos.add(agendamentoDTO);
            }

            return new PageImpl<>(listaAgendamentos);
        } catch (ExceptionApiCadastro e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionApiCadastro(HttpStatus.INTERNAL_SERVER_ERROR, "AGENDAMENTO-01", e.getMessage());
        }
    }

    @Override
    public AgendamentoDTO createAgendamento(AgendamentoRequest request) {

        try{
            var customerID = tokenService.getCustomerIdByToken();

            var taxaTransacao = tipoTaxaTrasacao(request);
            AgendamentoMapper.mappTaxaToObject(taxaTransacao, request);

            var agendamentoDataBase = AgendamentoMapper.mappToDataBase(request,taxaTransacao, customerID);

            iAgendamentoRepository.save(agendamentoDataBase);

            return AgendamentoMapper.mappToResponse(agendamentoDataBase.getIdTransacao(),request,taxaTransacao);
        } catch (ExceptionApiCadastro e) {
            throw e;
        } catch (Exception e) {
            throw new ExceptionApiCadastro(HttpStatus.INTERNAL_SERVER_ERROR, "AGENDAMENTO-01", e.getMessage());
        }
    }


    private BigDecimal tipoTaxaTrasacao(AgendamentoRequest request){
        var diferencaData = DataValidatorUtil.verificaData(request.getDataTransacao(), request.getDataAgendamento());

        if(request.getEnumTipoTransacao().getNome().equals(EnumTipoTransacao.TRANS_DIA.getNome()) && diferencaData == 0)
        {
            return iCalculaValorTransferencia.caculoValorTransferenciaD0(request.getValorTransacao());
        }

        else if(request.getEnumTipoTransacao().getNome().equals(EnumTipoTransacao.TRANS_DIA_10.getNome()) && diferencaData <= 10)
        {
            return iCalculaValorTransferencia.calculaValorTransferenciaD10(request.getValorTransacao());
        }

        else if(request.getEnumTipoTransacao().getNome().equals(EnumTipoTransacao.TRANS_RESGRESSIVA.getNome()) && diferencaData > 10)
        {
            return iCalculaValorTransferencia.calculaValorTransferenciaRegressiva(diferencaData,request.getValorTransacao());
        }

        else if(request.getEnumTipoTransacao().getNome().equals(EnumTipoTransacao.TRANS_TIPO_VALOR.getNome()))
            return iCalculaValorTransferencia.caculaValorTransferenciaTipoValor(diferencaData,request.getValorTransacao());

        return new BigDecimal("0");
    }



}
