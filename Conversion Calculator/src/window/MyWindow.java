package window;

/**
* The MyWindow program implements an application that
* opens a running currency converter for five common currency used.
* 
* restraints: only use numbers
* 			  limit of 5 digtis
* 			  five currency limit
* 
* Currencies: USD, EUR, GBP, CAD, JPY
*
* @author  Raymond Acevedo
* @version 1.0
* @date   07/12/2021
*/

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Currency;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import org.json.JSONObject;


public class MyWindow implements ItemListener, ActionListener {


	private static JTextField txt_a;
	private static JButton btn_c;
	private static JComboBox<String> c1;
	private static JComboBox<String> c2;
	private static String to;
	private static String from;
	private static JLabel title;
	private static JLabel lbl_t; 
	private static JLabel lbl_a;
	private static JLabel lbl_e;
	private static JLabel lbl_f;
	private static JLabel lbl_an1;
	private static JLabel lbl_an2;

	public static void placeComponents(JPanel panel) {

		MyWindow w = new MyWindow();

		// setting box layout
		panel.setLayout(null);

		// title text label
		title = new JLabel("Currency Conversion", SwingConstants.CENTER);
		title.setFont(new Font("Regular", Font.PLAIN, 24));
		title.setBounds(25, 20, 230, 30);
		panel.add(title);

		// amount text to label and text field
		lbl_a = new JLabel("Amount: ");
		lbl_a.setBounds(60, 80, 100, 46);
		lbl_a.setFont(new Font("Regular", Font.PLAIN, 18));
		panel.add(lbl_a);

		txt_a = new JTextField(10);
		txt_a.setFont(new Font("Regular", Font.PLAIN, 18));
		txt_a.setBounds(130, 90, 90, 30);

		// ignore any user input which is not a number 
		// and accept only a limit of 5 digits
		txt_a.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				// Character period = Character.valueOf('.');

				if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE) || (txt_a.getText().length() >= 5)) {
					e.consume(); // ignore event
				}
			}
		});
		panel.add(txt_a);

		// empty error text field
		lbl_e = new JLabel("*");
		lbl_e.setBounds(225, 85, 90, 30);
		lbl_e.setFont(new Font("Regular", Font.PLAIN, 18));
		lbl_e.setForeground(Color.RED);
		lbl_e.setVisible(false);
		panel.add(lbl_e);
											
		// currency base text label and combobox
		lbl_f = new JLabel("From: ");
		lbl_f.setBounds(60, 120, 100, 46);
		lbl_f.setFont(new Font("Regular", Font.PLAIN, 18));
		panel.add(lbl_f);
		

		String s1[] = { "USD", "EUR", "GBP", "CAD", "JPY" }; // array of string containing currencies

		c1 = new JComboBox<>(s1); // jcombobox
		c1.setFont(new Font("Regular", Font.PLAIN, 18));
		c1.setBounds(130, 130, 90, 30);
		c1.setSelectedIndex(0);
		c1.addItemListener(w); // add ItemListener
		panel.add(c1);

		// currency text label and combobox
		lbl_t = new JLabel("To: ");
		lbl_t.setBounds(60, 160, 227, 46);
		lbl_t.setFont(new Font("Regular", Font.PLAIN, 18));
		panel.add(lbl_t);

		String s2[] = { "USD", "EUR", "GBP", "CAD", "JPY" }; // array of string containing currencies

		c2 = new JComboBox<>(s2); // jcombobox
		c2.setFont(new Font("Regular", Font.PLAIN, 18));
		c2.setBounds(130, 170, 90, 30);
		c2.setSelectedIndex(0);
		c2.addItemListener(w); // add ItemListener
		panel.add(c2);

		// conversion button
		btn_c = new JButton("convert");
		btn_c.setFont(new Font("Regular", Font.PLAIN, 16));
		btn_c.setBounds(60, 230, 160, 29);
		panel.add(btn_c);
		btn_c.addActionListener(w); // add ItemListener

		// text field answer first output
		lbl_an1 = new JLabel(" ");
		lbl_an1.setFont(new Font("Regular", Font.PLAIN, 18));
		lbl_an1.setBounds(60, 270, 150, 35);
		panel.add(lbl_an1);

		// text field answer second =output
		lbl_an2 = new JLabel(" ");
		lbl_an2.setFont(new Font("Regular", Font.PLAIN, 18));
		lbl_an2.setBounds(60, 300, 150, 35);
		panel.add(lbl_an2);
	}

    /**
    * manages window functionality 
    *
    * @param  e   key event
    * @return         none
    */
	@Override
	public void actionPerformed(ActionEvent e) {
		// getting current text input
		from = (String) (c1.getSelectedItem());
		to = (String) (c2.getSelectedItem());

		lbl_an1.setForeground(Color.BLACK);
		lbl_an2.setForeground(Color.BLACK);
		txt_a.setForeground(Color.BLACK); 
		lbl_e.setVisible(false);
		
		try {
			// clicked convert button
			String s = e.getActionCommand();

			// convert currency appropriately
			if (s.equals("convert") && test(txt_a.getText()) && !("0".equals(txt_a.getText()))) {

				int k = Integer.parseInt(txt_a.getText());

				// set output result with correct comma and decimal point
				DecimalFormat f = new DecimalFormat("0.00");
				f.setGroupingUsed(true);
				f.setGroupingSize(3);

				String url_str = "https://api.exchangerate.host/convert?from=" + from + "&to=" + to + "&amount=" + k;
				URL url = new URL(url_str);
				HttpURLConnection request = (HttpURLConnection) url.openConnection();
				request.setRequestMethod("GET");
				// request.getResponseCode();

				// if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// get currency conversion result from json
				JSONObject obj = new JSONObject(response.toString());
				Double exchangeRate = obj.getDouble("result");
				
				//  output currency conversions
				lbl_an1.setText(currencySymbol(from) + f.format(k) + " " + from + " = ");
				lbl_an2.setText(currencySymbol(to) + f.format(exchangeRate) + " " + to);

				// output for 0 amount
			} else if (s.equals("convert") && test(txt_a.getText())) {
				lbl_an1.setText(currencySymbol(from) + "00.00 " + from + " = ");
				lbl_an2.setText(currencySymbol(to) + "00.00 " + to);

				// output for empty text field
			} else {
				// output error message 
				lbl_an1.setForeground(Color.RED);
				lbl_an2.setForeground(Color.RED);
				lbl_e.setVisible(true);
				
				lbl_an1.setText("*Input a number");
				lbl_an2.setText(" up to 5 digits");
				
			}
		} catch (

				IOException e1) {
			System.out.println("http connection or json parse failed");
		}
	}
	
    /**
    * verify if current text can be conveted to an integer
    *
    * @param  text   text inside text field
    * @return   true   if text can be converted to a integer 
    *           false  if text cannot be converted to a integer 
    */
	private boolean test(String text) {
		try {
			Integer.parseInt(text);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

    /**
    * gets new combobox state 
    *
    * @param  event   new state from the combobox
    * @return         none
    */
	@Override
	public void itemStateChanged(ItemEvent e) {
		// if combobox 1 state changes
		if (e.getSource() == c1) {
			from = (String) (c1.getSelectedItem());

			// if combobox 2 state changes
		} else {
			to = (String) (c2.getSelectedItem());
		}
	}

    /**
    * Takes the current string representation of the combobox state 
    * and match it with its appropriate symbol 
    *
    * @param  c   string representation of a combobox option
    * @return         none
    */
	private String currencySymbol(String c) {

		Currency jpy = Currency.getInstance("JPY"); // JPY
		Currency usd = Currency.getInstance("USD");// USD & CAD

		String jpy1 = jpy.getSymbol(); // JPY
		String usdCad = usd.getSymbol(); // USD & CAD
		String pound = "\u00A3"; // GBP
		String euro = "\u20AC"; // EUR

		String curr;

		// get appropriate currency symbol
		if (c.equals("USD")) {
			curr = usdCad;
		} else if (c.equals("EUR")) {
			curr = euro;

		} else if (c.equals("GBP")) {
			curr = pound;

		} else if (c.equals("CAD")) {
			curr = usdCad;

		} else if (c.equals("JPY")) {
			curr = jpy1;
		} else {
			curr = usdCad;
		}

		return curr;

	}

}