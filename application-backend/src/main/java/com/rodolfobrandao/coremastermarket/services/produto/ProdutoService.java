package com.rodolfobrandao.coremastermarket.services.produto;

import com.rodolfobrandao.coremastermarket.dtos.produto.CreateProdutoDTO;
import com.rodolfobrandao.coremastermarket.dtos.produto.UpdateProdutoDTO;
import com.rodolfobrandao.coremastermarket.entities.produto.Marca;
import com.rodolfobrandao.coremastermarket.entities.produto.Produto;
import com.rodolfobrandao.coremastermarket.repositories.produto.MarcaRepository;
import com.rodolfobrandao.coremastermarket.repositories.produto.ProdutoRepository;
import com.rodolfobrandao.coremastermarket.tools.CustomException;
import com.rodolfobrandao.coremastermarket.tools.JsonUtil;
import io.swagger.v3.core.util.Json;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProdutoService {
    final ProdutoRepository produtoRepository;
    private final MarcaRepository marcaRepository;
    LocalDateTime dataAtualTime = LocalDateTime.now();
    LocalDate dataAtual = LocalDate.now();


    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository, MarcaRepository marcaRepository) {
        this.produtoRepository = produtoRepository;
        this.marcaRepository = marcaRepository;
    }

    public Page<Produto> findAll(int page, int size, String sortname, String sortorder) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.fromString(sortorder), sortname));
        return produtoRepository.findAll(pageRequest);
    }

    public Produto findById(Long id) {
        return produtoRepository.findById(id).orElseThrow(() -> new RuntimeException(JsonUtil.createMessageJson("Produto não encontrado", 404)));

    }

    public Optional<Produto> findByCodigoBarras(String codigoBarras) {

        return produtoRepository.findByCodigoBarras(codigoBarras);

    }

    public Produto deleteById(Long id) {
        try {
            Produto produto = produtoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException(JsonUtil.createMessageJson("Produto não encontrado", 404)));
            produto.setAtivo(false);
            produto.setAtualizadoEm(dataAtualTime);
            return produtoRepository.save(produto);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Produto enabledById(Long id) {
        try {
            Produto produto = produtoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException(JsonUtil.createMessageJson("Produto não encontrado", 404)));
            produto.setAtivo(true);
            produto.setAtualizadoEm(dataAtualTime);
            return produtoRepository.save(produto);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    public Produto create(CreateProdutoDTO dto) {
        Json j = new Json();
        try {
            Marca marca = marcaRepository.findById(dto.marca())
                    .orElseThrow(() -> new RuntimeException(JsonUtil.createMessageJson("Marca não encontrada", 404)));

            boolean exists = produtoRepository.findByCodigoBarras(dto.codigoBarras()).isPresent();
            if (exists) {
                throw new RuntimeException(JsonUtil.createMessageJson("Produto já cadastrado", 400));
            }

            LocalDateTime criadoEm = (dto.criadoEm() == null) ? LocalDateTime.now() : dto.criadoEm();

            Produto produto = new Produto(dto.precoVenda(), dto.codigoBarras(), dto.tipoEmbalagem(), dto.descricao(),
                    BigDecimal.valueOf(dto.quantidade()), criadoEm, marca, dto.ativo());

            return produtoRepository.save(produto);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    public void reduzirEstoque(Long produtoId, BigDecimal quantidade) {
        try {
            Produto produto = produtoRepository.findById(produtoId)
                    .orElseThrow(() -> new CustomException("Produto não encontrado", HttpStatus.NOT_FOUND));

            BigDecimal novaQuantidade = produto.getQuantidade().subtract(quantidade);
            if (novaQuantidade.compareTo(BigDecimal.ZERO) < 0) {
                throw new CustomException("Quantidade de estoque insuficiente", HttpStatus.BAD_REQUEST);
            }

            produto.setQuantidade(novaQuantidade);
            produtoRepository.save(produto);
        } catch (Exception ex) {
            throw new CustomException("Erro ao reduzir estoque: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void devolverEstoque(Long produtoId, BigDecimal quantidade) {
        try {
            Produto produto = produtoRepository.findById(produtoId)
                    .orElseThrow(() -> new CustomException("Produto não encontrado", HttpStatus.NOT_FOUND));

            BigDecimal novaQuantidade = produto.getQuantidade().add(quantidade);
            produto.setQuantidade(novaQuantidade);
            produtoRepository.save(produto);
        } catch (Exception ex) {
            throw new CustomException("Erro ao devolver estoque: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public Map<String, List<Produto>> createMultiple(List<CreateProdutoDTO> dtos) {
        List<Produto> produtosCriados = new ArrayList<>();
        List<Produto> produtosIgnorados = new ArrayList<>();

        for (CreateProdutoDTO dto : dtos) {
            Optional<Produto> existingProduto = produtoRepository.findByCodigoBarras(dto.codigoBarras());
            if (existingProduto.isPresent()) {
                produtosIgnorados.add(existingProduto.get());
                continue;
            }

            Marca marca = marcaRepository.findById(dto.marca()).orElseThrow(() -> new RuntimeException(JsonUtil.createMessageJson("Marca não encontrada", 404)))    ;

            Produto produto = new Produto(dto.precoVenda(), dto.codigoBarras(), dto.tipoEmbalagem(), dto.descricao(), BigDecimal.valueOf(dto.quantidade()), dto.criadoEm() == null ? dataAtualTime : dto.criadoEm(), marca, dto.ativo());
            produtosCriados.add(produtoRepository.save(produto));
        }

        Map<String, List<Produto>> resultado = new HashMap<>();
        resultado.put("produtosCriados", produtosCriados);
        resultado.put("produtosIgnorados", produtosIgnorados);

        return resultado;
    }

    public Produto update(UpdateProdutoDTO dto) {
        Produto produto = produtoRepository.findById(dto.id()).orElseThrow(() -> new RuntimeException(JsonUtil.createMessageJson("Produto não encontrado", 404)));
        Marca marca = marcaRepository.findById(dto.marca()).orElseThrow(() -> new RuntimeException(JsonUtil.createMessageJson("Marca não encontrada", 404)));
        produto.setPrecoVenda(dto.precoVenda());
        produto.setCodigoBarras(dto.codigoBarras());
        produto.setTipoEmbalagem(dto.tipoEmbalagem());
        produto.setDescricao(dto.descricao());
        produto.setQuantidade(BigDecimal.valueOf(dto.quantidade()));
        produto.setAtualizadoEm(dto.atualizadoEm() == null ? dataAtual.atStartOfDay() : dto.atualizadoEm());
        produto.setMarca(marca);
        produto.setAtivo(dto.ativo());
        return produtoRepository.save(produto);
    }

    public Page<Produto> findByQuery(String qtype, String query, String oper, int page, int size, String sortname, String sortorder) {
        PageRequest pageRequest = PageRequest.of(page -1, size, Sort.by(Sort.Direction.fromString(sortorder), sortname));
        return produtoRepository.findAll(pageRequest);
    }

    @Transactional
    public void atualizarEstoque(Long produtoId, BigDecimal quantidadeVendida) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        BigDecimal novaQuantidade = produto.getQuantidade().subtract(quantidadeVendida);
        if (novaQuantidade.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Quantidade em estoque insuficiente");
        }
        produto.setQuantidade(novaQuantidade);
        produtoRepository.save(produto);
    }
}