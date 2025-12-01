package com.vendas.sistemavendas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Sistema de Vendas com Desconto
 * Aplicação desenvolvida sem ferramentas visuais
 */
public class SistemaVendas {
    
    // Componentes da janela principal
    private JFrame janelaPrincipal;
    private JTextField campoValor;
    private JButton botaoCalcular;
    private JLabel rotuloValor;
    
    /**
     * Construtor - Inicializa a interface gráfica
     */
    public SistemaVendas() {
        criarInterface();
    }
    
    /**
     * Cria a interface gráfica da aplicação
     */
    private void criarInterface() {
        // Configuração da janela principal
        janelaPrincipal = new JFrame("Sistema de Vendas");
        janelaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janelaPrincipal.setSize(400, 150);
        janelaPrincipal.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        janelaPrincipal.setLocationRelativeTo(null); // Centraliza na tela
        
        // Criação do rótulo
        rotuloValor = new JLabel("Valor da Venda (R$):");
        rotuloValor.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Criação do campo de texto
        campoValor = new JTextField(15);
        campoValor.setFont(new Font("Arial", Font.PLAIN, 14));
        
        // Criação do botão
        botaoCalcular = new JButton("Calcular Total");
        botaoCalcular.setFont(new Font("Arial", Font.BOLD, 14));
        botaoCalcular.setPreferredSize(new Dimension(150, 30));
        
        // Adicionar ação ao botão
        botaoCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processarVenda();
            }
        });
        
        // Adicionar componentes à janela
        janelaPrincipal.add(rotuloValor);
        janelaPrincipal.add(campoValor);
        janelaPrincipal.add(botaoCalcular);
        
        // Tornar a janela visível
        janelaPrincipal.setVisible(true);
    }
    
    /**
     * Processa a venda e calcula o valor total
     */
    private void processarVenda() {
        try {
            // Validação e leitura do valor da venda
            String textoValor = campoValor.getText().trim();
            
            // Verificação se o campo está vazio
            if (textoValor.isEmpty()) {
                JOptionPane.showMessageDialog(
                    janelaPrincipal,
                    "Por favor, informe o valor da venda!",
                    "Campo Vazio",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            
            // Conversão do valor
            double valorVenda = Double.parseDouble(textoValor);
            
            // Verificação se o valor é positivo
            if (valorVenda <= 0) {
                JOptionPane.showMessageDialog(
                    janelaPrincipal,
                    "O valor da venda deve ser maior que zero!",
                    "Valor Inválido",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            double valorFinal = valorVenda;
            double percentualDesconto = 0;
            
            // Verificar se o valor é maior que 500
            if (valorVenda > 500) {
                percentualDesconto = solicitarDesconto();
                
                // Se o usuário cancelou, retorna
                if (percentualDesconto < 0) {
                    return;
                }
                
                // Calcula o valor final com desconto
                double desconto = valorVenda * (percentualDesconto / 100);
                valorFinal = valorVenda - desconto;
            }
            
            // Exibe o resultado
            exibirResultado(valorVenda, percentualDesconto, valorFinal);
            
            // Limpa o campo para nova venda
            campoValor.setText("");
            campoValor.requestFocus();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                janelaPrincipal,
                "Por favor, informe um valor numérico válido!\nExemplo: 1000 ou 1000.50",
                "Erro de Formato",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    /**
     * Solicita o percentual de desconto ao usuário
     * @return Percentual de desconto (0-100) ou -1 se cancelado
     */
    private double solicitarDesconto() {
        while (true) {
            String input = JOptionPane.showInputDialog(
                janelaPrincipal,
                "Venda acima de R$ 500,00!\n\nInforme o percentual de desconto:",
                "Desconto Disponível",
                JOptionPane.QUESTION_MESSAGE
            );
            
            // Usuário cancelou
            if (input == null) {
                return -1;
            }
            
            // Campo vazio
            if (input.trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                    janelaPrincipal,
                    "Por favor, informe um percentual válido!",
                    "Campo Vazio",
                    JOptionPane.WARNING_MESSAGE
                );
                continue;
            }
            
            try {
                double percentual = Double.parseDouble(input.trim());
                
                // Validação do percentual
                if (percentual < 0 || percentual > 100) {
                    JOptionPane.showMessageDialog(
                        janelaPrincipal,
                        "O percentual deve estar entre 0 e 100!",
                        "Percentual Inválido",
                        JOptionPane.ERROR_MESSAGE
                    );
                    continue;
                }
                
                return percentual;
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    janelaPrincipal,
                    "Por favor, informe apenas números!\nExemplo: 10 ou 15.5",
                    "Erro de Formato",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    
    /**
     * Exibe o resultado do cálculo
     * @param valorOriginal Valor original da venda
     * @param percentualDesconto Percentual de desconto aplicado
     * @param valorFinal Valor final após desconto
     */
    private void exibirResultado(double valorOriginal, double percentualDesconto, double valorFinal) {
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("═══════════════════════════════════\n");
        mensagem.append("          RESUMO DA VENDA\n");
        mensagem.append("═══════════════════════════════════\n\n");
        mensagem.append(String.format("Valor Original: R$ %.2f\n", valorOriginal));
        
        if (percentualDesconto > 0) {
            double valorDesconto = valorOriginal - valorFinal;
            mensagem.append(String.format("Desconto: %.2f%% (R$ %.2f)\n", percentualDesconto, valorDesconto));
            mensagem.append("───────────────────────────────────\n");
        }
        
        mensagem.append(String.format("VALOR TOTAL: R$ %.2f\n", valorFinal));
        mensagem.append("═══════════════════════════════════");
        
        JOptionPane.showMessageDialog(
            janelaPrincipal,
            mensagem.toString(),
            "Resultado",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Método principal - Inicia a aplicação
     */
    public static void main(String[] args) {
        // Executa a interface gráfica na thread correta
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SistemaVendas();
            }
        });
    }
}