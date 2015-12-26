package com.ado.jsonconvert;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ado.jsonconvert.exception.BusiException;
import com.alibaba.fastjson.JSONObject;

public class JsonConvert extends JFrame {

	private static final long serialVersionUID = 1L;

	private static String siteArray[] = Config.getInstacne().getHostList()
			.toArray(new String[0]);

	private static String siteNameArray[] = Config.getInstacne()
			.getHostNameList().toArray(new String[0]);

	private JTextArea jsonText, urlText;
	private JTextField apiText;

	private JLabel hostLabel;

	public JsonConvert() {
		init();
		initFrame();
	}

	private void initFrame() {
		setTitle("json转换请求");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setVisible(true);
	}

	private void init() {

		JPanel panelContainer = new JPanel();
		panelContainer.setLayout(new GridBagLayout()); // 设置布局

		// 显示json区域
		initHostArea(panelContainer, 0);
		initAPIArea(panelContainer, 1);
		initShowJsonArea(panelContainer, 2);
		initShowURLArea(panelContainer, 3);
		initShowButtonArea(panelContainer, 4);

		setContentPane(panelContainer);
	}

	/**
	 * 初始化主机区域信息
	 * 
	 * @param parent
	 */
	private void initHostArea(JPanel parent, int gridy) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		final JComboBox comboBox = new JComboBox();
		for (int i = 0; i < siteNameArray.length; i++) {
			comboBox.addItem(siteNameArray[i]);
		}
		panel.add(comboBox);

		hostLabel = new JLabel();
		hostLabel.setText(siteArray[0]);
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int selIndex = comboBox.getSelectedIndex();
				hostLabel.setText(siteArray[selIndex]);
			}
		});
		panel.add(hostLabel);

		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridx = 0;
		c1.gridy = gridy;
		c1.weightx = 1.0;
		c1.weighty = 1.0;

		c1.fill = GridBagConstraints.CENTER;
		parent.add(panel, c1);
	}

	/**
	 * API
	 * 
	 * @param parent
	 * @param gridy
	 */
	private void initAPIArea(JPanel parent, int gridy) {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("API："));
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JLabel apiLabel = new JLabel("api：");
		panel.add(apiLabel);

		apiText = new JTextField();
		panel.add(apiText);

		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridx = 0;
		c1.gridy = gridy;
		c1.weightx = 1.0;
		c1.weighty = 1.0;

		c1.fill = GridBagConstraints.BOTH;
		parent.add(panel, c1);
	}

	private void initShowJsonArea(JPanel parent, int gridy) {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("JSON："));
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JLabel showUrl = new JLabel("json：");
		panel.add(showUrl);

		jsonText = new JTextArea();
		panel.add(jsonText);

		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridx = 0;
		c1.gridy = gridy;
		c1.weightx = 1.0;
		c1.weighty = 1.0;

		c1.fill = GridBagConstraints.BOTH;
		parent.add(panel, c1);
	}

	private void initShowURLArea(JPanel parent, int gridy) {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("URL："));
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JLabel showUrl = new JLabel("url：");
		panel.add(showUrl);

		urlText = new JTextArea();
		urlText.setEditable(false);
		panel.add(urlText);

		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridx = 0;
		c1.gridy = gridy;
		c1.weightx = 1.0;
		c1.weighty = 1.0;

		c1.fill = GridBagConstraints.BOTH;
		parent.add(panel, c1);
	}

	private void initShowButtonArea(JPanel parent, int gridy) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JButton exchangeBtn = new JButton("转换");
		exchangeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String json = jsonText.getText();
					String url = json2RequestUrl(json);
					urlText.setText(url);
				} catch (BusiException be) {
					urlText.setText(be.getMessage());
				}
			}
		});

		panel.add(exchangeBtn);

		GridBagConstraints c1 = new GridBagConstraints();
		c1.gridx = 0;
		c1.gridy = gridy;
		c1.weightx = 1.0;
		c1.weighty = 1.0;

		c1.fill = GridBagConstraints.CENTER;
		parent.add(panel, c1);
	}

	public String json2RequestUrl(String json) {
		if (json == null || json.length() < 1) {
			throw new BusiException("请输入json串");
		}

		StringBuffer sbf = new StringBuffer(hostLabel.getText()
				+ apiText.getText() + "?");
		try {
			JSONObject object = (JSONObject) JSONObject.parse(json);
			for (Iterator iter = object.entrySet().iterator(); iter.hasNext();) {
				Entry entry = (Entry) iter.next();
				sbf.append(entry.getKey() + "=" + entry.getValue() + "&");
			}
		} catch (Exception e) {
			throw new BusiException("请输入正确的json串");
		}

		return sbf.substring(0, sbf.length() - 1);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		new JsonConvert();
	}
}
