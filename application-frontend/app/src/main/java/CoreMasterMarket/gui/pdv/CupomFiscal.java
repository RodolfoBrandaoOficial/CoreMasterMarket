package CoreMasterMarket.gui.pdv;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class CupomFiscal extends JFrame {

    private JsonObject vendaData;

    public CupomFiscal(JsonObject vendaData) {
        this.vendaData = vendaData;
        initComponents();
    }

    private void initComponents() {
        setTitle("Cupom Fiscal");
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(255, 255, 204)); // Amarelo claro, semelhante a cupons fiscais

        // Header
        panel.add(createHeader());

        // Venda Details
        panel.add(createVendaDetails());

        // Items
        panel.add(createVendaItens());

        // Total
        panel.add(createTotal());

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        JButton btnPrint = new JButton("Imprimir");
        JButton btnEmail = new JButton("Enviar por E-mail");
        JButton btnSms = new JButton("Enviar por SMS");

        btnPrint.addActionListener(e -> printCupom());
        btnEmail.addActionListener(e -> enviarEmail());
        btnSms.addActionListener(e -> enviarSMS());

        buttonPanel.add(btnPrint);
        buttonPanel.add(btnEmail);
        buttonPanel.add(btnSms);

        panel.add(buttonPanel);

        add(panel);
    }

    private JPanel createHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridLayout(3, 1));
        headerPanel.setBackground(new Color(255, 255, 204)); // Manter o fundo amarelo
        headerPanel.add(new JLabel("--------------------------------------------------", SwingConstants.CENTER));
        headerPanel.add(new JLabel("                  CUPOM FISCAL                    ", SwingConstants.CENTER));
        headerPanel.add(new JLabel("--------------------------------------------------", SwingConstants.CENTER));
        return headerPanel;
    }

    private JPanel createVendaDetails() {
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridLayout(3, 1));
        detailsPanel.setBackground(new Color(255, 255, 204)); // Manter o fundo amarelo
        detailsPanel.add(new JLabel("Data e Hora: " + vendaData.get("dataHoraInicio").getAsString()));
        detailsPanel.add(new JLabel("Método de Pagamento: " + vendaData.get("modoPagamento").getAsString()));
        detailsPanel.add(new JLabel("Cliente ID: " + vendaData.get("idCliente").getAsString()));
        return detailsPanel;
    }

    private JPanel createVendaItens() {
        JPanel itensPanel = new JPanel();
        itensPanel.setLayout(new BoxLayout(itensPanel, BoxLayout.Y_AXIS));
        itensPanel.setBackground(new Color(255, 255, 204)); // Manter o fundo amarelo
        itensPanel.add(new JLabel("--------------------------------------------------"));
        itensPanel.add(new JLabel("Itens da Venda:"));

        JsonArray itens = vendaData.getAsJsonArray("listVendaItens");
        for (int i = 0; i < itens.size(); i++) {
            JsonObject item = itens.get(i).getAsJsonObject();
            itensPanel.add(new JLabel("Produto ID: " + item.get("idProduto").getAsString() +
                                      " Quantidade: " + item.get("quantidade").getAsString() +
                                      " Desconto: " + item.get("desconto").getAsString() +
                                      " Acréscimo: " + item.get("acrescimo").getAsString()));
        }
        return itensPanel;
    }

    private JPanel createTotal() {
        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new GridLayout(1, 1));
        totalPanel.setBackground(new Color(255, 255, 204)); // Manter o fundo amarelo
        totalPanel.add(new JLabel("--------------------------------------------------"));
        totalPanel.add(new JLabel("Total: R$ " + vendaData.get("totalValue").getAsString()));
        totalPanel.add(new JLabel("--------------------------------------------------"));
        return totalPanel;
    }

    private void printCupom() {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setPrintable(new CupomFiscalPrintable());
        if (printerJob.printDialog()) {
            try {
                printerJob.print();
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        }
    }

    private void enviarEmail() {
        // Implementar envio de e-mail
        JOptionPane.showMessageDialog(this, "Funcionalidade de envio por e-mail não implementada.");
    }

    private void enviarSMS() {
        // Implementar envio de SMS
        JOptionPane.showMessageDialog(this, "Funcionalidade de envio por SMS não implementada.");
    }

    public static void mostrarCupom(JsonObject vendaData) {
        SwingUtilities.invokeLater(() -> {
            CupomFiscal cupom = new CupomFiscal(vendaData);
            cupom.setVisible(true);
        });
    }

    private class CupomFiscalPrintable implements java.awt.print.Printable {
        @Override
        public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
            if (pageIndex > 0) {
                return NO_SUCH_PAGE;
            }

            Graphics2D g2d = (Graphics2D) g;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

            // Define o formato e a fonte
            g2d.setFont(new Font("Monospaced", Font.PLAIN, 12));
            g2d.setColor(Color.BLACK);
            int y = 20;

            // Print header
            g2d.drawString("--------------------------------------------------", 0, y);
            y += 15;
            g2d.drawString("                  CUPOM FISCAL                    ", 0, y);
            y += 15;
            g2d.drawString("--------------------------------------------------", 0, y);
            y += 20;

            // Print venda details
            g2d.drawString("Data e Hora: " + vendaData.get("dataHoraInicio").getAsString(), 0, y);
            y += 15;
            g2d.drawString("Método de Pagamento: " + vendaData.get("modoPagamento").getAsString(), 0, y);
            y += 15;
            g2d.drawString("Cliente ID: " + vendaData.get("idCliente").getAsString(), 0, y);
            y += 20;

            // Print itens
            g2d.drawString("--------------------------------------------------", 0, y);
            y += 15;
            g2d.drawString("Itens da Venda:", 0, y);
            y += 15;

            JsonArray itens = vendaData.getAsJsonArray("listVendaItens");
            for (int i = 0; i < itens.size(); i++) {
                JsonObject item = itens.get(i).getAsJsonObject();
                g2d.drawString("Produto ID: " + item.get("idProduto").getAsString() +
                               " Quantidade: " + item.get("quantidade").getAsString() +
                               " Desconto: " + item.get("desconto").getAsString() +
                               " Acréscimo: " + item.get("acrescimo").getAsString(), 0, y);
                y += 15;
            }

            // Print total
            g2d.drawString("--------------------------------------------------", 0, y);
            y += 15;
            g2d.drawString("Total: R$ " + vendaData.get("totalValue").getAsString(), 0, y);
            y += 15;
            g2d.drawString("--------------------------------------------------", 0, y);

            return PAGE_EXISTS;
        }
    }
}
