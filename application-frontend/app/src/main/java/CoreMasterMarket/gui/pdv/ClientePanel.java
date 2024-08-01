package CoreMasterMarket.gui.pdv;

import CoreMasterMarket.service.ClienteService;

import javax.swing.*;
import java.awt.*;

public class ClientePanel extends JPanel {

    private JList<String> list;
    private DefaultListModel<String> model;

    public ClientePanel() {
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

        ClienteService clienteService = new ClienteService(model);
        clienteService.loadDataFromAPI();

        list.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    clienteService.editItemAt(index);
                }
            }
        });

        addButton.addActionListener(e -> clienteService.addItem());
        updateButton.addActionListener(e -> {
            int index = list.getSelectedIndex();
            if (index != -1) {
                clienteService.editItemAt(index);
            } else {
                JOptionPane.showMessageDialog(ClientePanel.this, "Selecione um item para atualizar");
            }
        });

        deleteButton.addActionListener(e -> {
            int index = list.getSelectedIndex();
            if (index != -1) {
                clienteService.deleteItemAt(index);
            } else {
                JOptionPane.showMessageDialog(ClientePanel.this, "Selecione um item para excluir");
            }
        });
    }
}
