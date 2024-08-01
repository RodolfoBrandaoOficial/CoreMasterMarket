package CoreMasterMarket.gui.pdv;

import CoreMasterMarket.service.PedidoService;

import javax.swing.*;
import java.awt.*;

public class PedidoPanel extends JPanel {

    private JList<String> list;
    private DefaultListModel<String> model;

    public PedidoPanel() {
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

        PedidoService pedidoService = new PedidoService(model);
        pedidoService.loadDataFromAPI();

        list.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    pedidoService.editItemAt(index);
                }
            }
        });

        addButton.addActionListener(e -> pedidoService.addItem());
        updateButton.addActionListener(e -> {
            int index = list.getSelectedIndex();
            if (index != -1) {
                pedidoService.editItemAt(index);
            } else {
                JOptionPane.showMessageDialog(PedidoPanel.this, "Selecione um item para atualizar");
            }
        });

        deleteButton.addActionListener(e -> {
            int index = list.getSelectedIndex();
            if (index != -1) {
                pedidoService.deleteItemAt(index);
            } else {
                JOptionPane.showMessageDialog(PedidoPanel.this, "Selecione um item para excluir");
            }
        });
    }
}
