package com.rodolfobrandao.coremastermarket.services.pve;

import com.rodolfobrandao.coremastermarket.dtos.pdv.CreateMetadosPagamentosDTO;
import com.rodolfobrandao.coremastermarket.entities.pdv.MetadosPagamentos;
import com.rodolfobrandao.coremastermarket.repositories.pdv.MetadosPagamentosRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MetadosPagamentoService {

    private final MetadosPagamentosRepository metadosPagamentosRepository;

    public MetadosPagamentoService(MetadosPagamentosRepository metadosPagamentosRepository) {
        this.metadosPagamentosRepository = metadosPagamentosRepository;
    }

    public MetadosPagamentos create(CreateMetadosPagamentosDTO dto) {
        try {
            MetadosPagamentos metadosPagamentos = new MetadosPagamentos();
            metadosPagamentos.setNome(dto.nome());
            return metadosPagamentosRepository.save(metadosPagamentos);
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao criar metado de pagamento: " + ex.getMessage());
        }
    }

    public MetadosPagamentos update(CreateMetadosPagamentosDTO dto) {
        try {
            MetadosPagamentos metadosPagamentos = new MetadosPagamentos();
            metadosPagamentos.setNome(dto.nome());
            return metadosPagamentosRepository.save(metadosPagamentos);
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao criar metado de pagamento: " + ex.getMessage());
        }
    }

    public MetadosPagamentos deleteById(Long id) {
        try {
            MetadosPagamentos metadosPagamentos = metadosPagamentosRepository.findById(id).orElse
                    (new MetadosPagamentos());
            metadosPagamentosRepository.delete(metadosPagamentos);
            // Mensagem de retorno sucesso
            return metadosPagamentos;
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar metado de pagamento: " + ex.getMessage());
        }
    }

    public Collection<MetadosPagamentos> findAll() {
        try {
            return metadosPagamentosRepository.findAll();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao buscar todos os metados de pagamento: " + ex.getMessage());
        }
    }
}
