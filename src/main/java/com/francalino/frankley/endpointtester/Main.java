package com.francalino.frankley.endpointtester;

import java.awt.Button;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.http.HttpResponse;

import javax.swing.JOptionPane;

import com.francalino.frankley.endpointtester.util.HttpHelper;

public class Main {
	
	private static String DEFAULT_ULT = "https://frankley.francalino.com";
	
	public static void main(String[] args) {
		Frame frame = new Frame("Endpoint Tester");
		Panel panel = new Panel();

		// Use GridBagLayout para maior flexibilidade no layout
		GridBagLayout gridBagLayout = new GridBagLayout();
		panel.setLayout(gridBagLayout);

		// Use GridBagConstraints para controlar o posicionamento dos componentes
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL; // Expande o componente horizontalmente
		gbc.insets = new Insets(5, 5, 5, 5); // Define margens entre os componentes

		// panel.setLayout(new GridLayout(4, 2)); // Define o layout como GridLayout com
		// 3 linhas e 2 colunas

		Label urlLabel = new Label("URL:");
		TextField urlField = new TextField(DEFAULT_ULT);

		Label responseCodeLabel = new Label("Código de resposta:");
		Label responseBodyLabel = new Label("Corpo da resposta:");
		Label responseCodeValue = new Label("");

		TextArea responseBodyValue = new TextArea("Este é um exemplo de TextArea.", 10, 40);

		Button button = new Button("Solicitar requisição");

		// Configura a posição e o tamanho dos componentes usando GridBagConstraints
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1; // Define o campo URL para ocupar 1 coluna
		gbc.weightx = 0.25; // Define a proporção da primeira coluna para 25%
		panel.add(urlLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1; // Define o campo URL para ocupar 1 coluna
		gbc.weightx = 0.75; // Define a proporção da segunda coluna para 75%
		panel.add(urlField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1; // Define o campo URL para ocupar 1 coluna
		gbc.weightx = 0.25; // Define a proporção da primeira coluna para 25%
		panel.add(responseCodeLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1; // Define o campo URL para ocupar 1 coluna
		gbc.weightx = 0.75; // Define a proporção da segunda coluna para 75%
		panel.add(responseCodeValue, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2; // Define o campo URL para ocupar 1 coluna
		gbc.weightx = 0.25; // Define a proporção da primeira coluna para 25%
		panel.add(responseBodyLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2; // Define o campo URL para ocupar 1 coluna
		gbc.weightx = 0.75; // Define a proporção da segunda coluna para 75%
		panel.add(responseBodyValue, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2; // Define o botão para ocupar 2 colunas
		panel.add(button, gbc);

		frame.add(panel);
		frame.setSize(800, 400);
		frame.setVisible(true);

		// Adiciona um WindowListener para fechar a janela quando o botão de fechar é
		// clicado
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frame.dispose(); // Fecha a janela
			}
		});

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {

				try {

					String url = urlField.getText();

					HttpResponse<String> resposta = HttpHelper.get(url);

					responseCodeValue.setText(String.valueOf(resposta.statusCode()));
					responseBodyValue.setText(resposta.body());

				} catch (Exception e) {

					StringWriter sw = new StringWriter();
					PrintWriter pw = new PrintWriter(sw);
					e.printStackTrace(pw);
					String stackTrace = sw.toString();

					responseBodyValue.setText(stackTrace);
					
					Throwable rootCause = e;
					
					while(rootCause.getCause() != null)						
						rootCause = rootCause.getCause();
											
					JOptionPane.showMessageDialog(
						null,
						rootCause.toString(),
						"Erro ao conectar",
						JOptionPane.PLAIN_MESSAGE
					);
					
				}
			}
		});

	}
}
