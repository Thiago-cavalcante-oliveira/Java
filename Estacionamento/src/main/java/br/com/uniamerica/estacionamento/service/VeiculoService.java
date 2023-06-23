package br.com.uniamerica.estacionamento.service;

import br.com.uniamerica.estacionamento.entity.Marca;
import br.com.uniamerica.estacionamento.entity.Modelo;
import br.com.uniamerica.estacionamento.entity.Veiculo;
import br.com.uniamerica.estacionamento.repository.MarcaRepositorio;
import br.com.uniamerica.estacionamento.repository.ModeloRepositorio;
import br.com.uniamerica.estacionamento.repository.MovimentacaoRepositorio;
import br.com.uniamerica.estacionamento.repository.VeiculoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VeiculoService {
    @Autowired
    private VeiculoRepositorio repository;
    @Autowired
    private MarcaRepositorio marcaRepositorio;
    @Autowired
    private ModeloRepositorio modeloRepositorio;
    @Autowired
    private MovimentacaoRepositorio movimentacaoRepositorio;

    @Transactional(rollbackFor = Exception.class)
    public Veiculo BuscarPorID(final Long id) {
        Veiculo veiculo = this.repository.findById(id).orElse(null);
        if (id == null) {
            throw new RuntimeException("Você não informou um id para consultar.");
        } else if (!repository.checaId(id)) {
            throw new RuntimeException("ID não localizado.");
        }
        return veiculo;
    }

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Veiculo veiculo) {
        if (veiculo.getPlaca() == null) {
            throw new RuntimeException("Placa não informado");
        } else if  (!veiculo.getPlaca().matches("^[a-z|A-Z]{3}-[0-9]{1}[a-z|A-Z]{1}[0-9]{2}$" )
                && !veiculo.getPlaca().matches("^[a-z|A-Z]{4}-[a-z|A-Z]{3}$") &&
                !veiculo.getPlaca().matches("^[a-z|A-Z]{2}-[0-9]{3}[a-z|A-Z]{2}$") &&
                !veiculo.getPlaca().matches("^[a-z|A-Z]{3}-[0-9]{4}$|^[a-z|A-Z]{3}-[0-9]{3}$")) {
            throw new RuntimeException("Placa informada inválida. A placa deve seguir o padrão do Detran");
        } else if (veiculo.getCor() == null) {
            throw new RuntimeException("cor não informado");
        } else if (veiculo.getTipo() == null) {
            throw new RuntimeException("tipo não informado");
        } else if (veiculo.getAno() < 1900 || veiculo.getAno() > 2024) {
            throw new RuntimeException("Ano não informado");
        } else if (this.repository.checaPlaca(veiculo.getPlaca())) {
            throw new RuntimeException("placa já está cadastrada.");
        } else if (veiculo.getTipo() == null) {
            throw new RuntimeException("Tipo do veiculo não informado");
        } else if (!this.modeloRepositorio.existsById(veiculo.getModelo().getId())) {
            throw new RuntimeException("Modelo não cadastrado na base de dados.");
        } else if (!repository.checaAtivoModelo(veiculo.getModelo().getId())) {
            throw new RuntimeException("O modelo está desativado");
        }
        repository.save(veiculo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void Atualizar(final Long id, final Veiculo veiculo) {

        if (veiculo.getPlaca() == null) {
            throw new RuntimeException("placa não informado");
        } else if (!veiculo.getPlaca().matches("^[a-z|A-Z]{3}-[0-9]{1}[a-z|A-Z]{3}$" )
                && !veiculo.getPlaca().matches("^[a-z|A-Z]{4}-[a-z|A-Z]{3}$") &&
        !veiculo.getPlaca().matches("^[a-z|A-Z]{2}-[0-9]{3}[a-z|A-Z]{2}$") &&
        !veiculo.getPlaca().matches("^[a-z|A-Z]{3}-[0-9]{4}$|^[a-z|A-Z]{3}-[0-9]{3}$")) {
            throw new RuntimeException("Placa informada inválida. A placa deve seguir o padrão do Detran");
        } else if (id != veiculo.getId()) {
            throw new RuntimeException("Existe um erro com o ID informado");
        } else if (veiculo.getCor() == null) {
            throw new RuntimeException("cor não informado");
        } else if (veiculo.getTipo() == null) {
            throw new RuntimeException("tipo não informado");
        } else if (veiculo.getAno() < 1900 || veiculo.getAno() > 2024) {
            throw new RuntimeException("Ano não informado ou inválido");
        } else if (!repository.checaAtivoModelo(veiculo.getModelo().getId())) {
            throw new RuntimeException("O modelo está desativado");
        } else if (repository.checaPlaca(veiculo.getPlaca())) {
            if (veiculo.getId() != repository.checaUsoRetornaId(veiculo.getPlaca())) {
                throw new RuntimeException("Placa já está cadastrada em outro veiculo.");
            }
        }
        repository.save(veiculo);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletar(final Long id) {
        Veiculo veiculo = this.repository.findById(id).orElse(null);
        if (veiculo == null) {
            throw new RuntimeException("ID não encontrado na base de dados.");
        } else {
            if (repository.checaMoviemntacaoAbertaSemSaida(id)) {
                throw new RuntimeException("Veiculo nao pode ser deletado, esta em uma movimentacao aberta");
            } else if (movimentacaoRepositorio.existsById(veiculo.getId())) {
                veiculo.setAtivo(false);
                repository.save(veiculo);
            } else {
                repository.delete(veiculo);
            }
        }
    }
}
