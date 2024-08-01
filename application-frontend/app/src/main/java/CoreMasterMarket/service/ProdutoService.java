/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CoreMasterMarket.service;

/**
 *
 * @author rodolfobrandao
 */
import CoreMasterMarket.entities.Produto;
import java.util.List;
import javax.swing.DefaultListModel;

public class ProdutoService {

    public ProdutoService(DefaultListModel<String> model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Métodos para interagir com o backend (Exemplo fictício)
    public void save(Produto produto) {
        // Implementar chamada HTTP POST para salvar produto
    }

    public void update(Produto produto) {
        // Implementar chamada HTTP PUT para atualizar produto
    }

    public void delete(Long codigo) {
        // Implementar chamada HTTP DELETE para deletar produto
    }

    public List<Produto> findAll() {
        // Implementar chamada HTTP GET para buscar todos os produtos
        return null;
    }

    public Produto findByCodigo(Long codigo) {
        // Implementar chamada HTTP GET para buscar produto por código
        return null;
    }

    public void loadDataFromAPI() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void deleteItemAt(int index) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void editItemAt(int index) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void addItem() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
