package CoreMasterMarket.gui.pdv;

import CoreMasterMarket.service.ProdutoService;

import javax.swing.*;
import java.awt.*;

public class ProdutoPanel extends JPanel {

    private JList<String> list;
    private DefaultListModel<String> model;

    public ProdutoPanel() {
        setLayout(new BorderLayout());

        list = new JList<>();
        model = new DefaultListModel<>();
        list.setModel(model);
        JScrollPane scrollPane = new JScrollPane(list);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        ProdutoService produtoService;
        produtoService = new ProdutoService(model);
        produtoService.loadDataFromAPI();

        list.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    produtoService.editItemAt(index);
                }
            }
        });

        addButton.addActionListener(e -> produtoService.addItem());
        updateButton.addActionListener(e -> {
            int index = list.getSelectedIndex();
            if (index != -1) {
                produtoService.editItemAt(index);
            } else {
                JOptionPane.showMessageDialog(ProdutoPanel.this, "Selecione um item para atualizar");
            }
        });

        deleteButton.addActionListener(e -> {
            int index = list.getSelectedIndex();
            if (index != -1) {
                produtoService.deleteItemAt(index);
            } else {
                JOptionPane.showMessageDialog(ProdutoPanel.this, "Selecione um item para excluir");
            }
        });
    }
}
