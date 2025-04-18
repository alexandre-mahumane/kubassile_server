package com.kubassile.kubassile.service;

import com.kubassile.kubassile.domain.order.Order;
import com.kubassile.kubassile.domain.order.enums.Status;
import com.kubassile.kubassile.domain.payments.enums.PaymentMethod;
import com.kubassile.kubassile.domain.payments.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class DocumentsService {

    static class Item {
        String nome;
        String descricao;
        double total;

        public Item(String nome, String descricao, double total) {
            this.nome = nome;
            this.descricao = descricao;
            this.total = total;
        }
    }

    public void imprimirFatura(Order order, Long paymentMethod, Long paymentStatus, Double value) {
        try {
            LocalDateTime data = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            List<Item> itens = new ArrayList<>();
            itens.add(new Item(order.getType(), order.getDescription(), value));

            double total = itens.stream().mapToDouble(i -> i.total).sum();
            double iva = total * 0.17;

            StringBuilder sb = new StringBuilder();

            sb.append((char) 0x1B).append("!").append((char) 0x38); // bold + double height + width
            sb.append("KUBASSILE\n");

            sb.append((char) 0x1B).append("!").append((char) 0x00); // reset style
            sb.append("Data: ").append(data.format(formatter)).append("\n");
            sb.append("-------------------------------\n");
            sb.append("Pedido Nº: ").append(order.getId()).append("\n");
            sb.append("Status: ").append(Status.fromId(order.getOrderStatusId())).append("\n");
            sb.append("Método de Pagamento: ").append(PaymentMethod.fromId(paymentMethod).label()).append("\n");
            sb.append("Status: ").append(PaymentStatus.fromId(paymentStatus).label()).append("\n");

            sb.append("-------------------------------\n");
            sb.append("Produto       Descrição    Total\n");

            for (Item item : itens) {
                List<String> linhasDescricao = quebrarDescricao(item.descricao, 12);

                sb.append(String.format("%-10s %-12s %9.2f\n",
                        item.nome,
                        linhasDescricao.get(0),
                        item.total));

                for (int i = 1; i < linhasDescricao.size(); i++) {
                    sb.append(String.format("%-10s %-10s\n", "", linhasDescricao.get(i)));
                }
            }

            sb.append("-------------------------------\n");
            sb.append(String.format("TOTAL:                %7.2f\n", total));
            sb.append(String.format("IVA(17%%):             %7.2f\n", iva));
            sb.append("-------------------------------\n");
            sb.append("Obrigado pela preferência!\n\n\n");

            try (OutputStream outputStream = new FileOutputStream("_texto_" + order.getId() + ".txt")) {
                outputStream.write(sb.toString().getBytes(StandardCharsets.UTF_8));
            }

            InputStream input = new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8));
            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            PrintService service = PrintServiceLookup.lookupDefaultPrintService();

            if (service != null) {
                DocPrintJob job = service.createPrintJob();
                Doc doc = new SimpleDoc(input, flavor, null);
                PrintRequestAttributeSet attrs = new HashPrintRequestAttributeSet();
                attrs.add(new Copies(1));
                job.print(doc, attrs);
                System.out.println("Fatura enviada para impressora.");
            } else {
                System.out.println("Nenhuma impressora padrão encontrada.");
            }

            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<String> quebrarDescricao(String texto, int limite) {
        List<String> partes = new ArrayList<>();
        int inicio = 0;
        while (inicio < texto.length()) {
            int fim = Math.min(inicio + limite, texto.length());
            partes.add(texto.substring(inicio, fim));
            inicio = fim;
        }
        return partes;
    }
}
