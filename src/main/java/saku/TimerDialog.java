package saku;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * カウントダウン設定ダイアログ
 *
 * @version 1.0
 */
public class TimerDialog extends JDialog {

	/**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = 394786882684710091L;
	
	/**
	 * コンポーネント類
	 */
	private final JPanel contentPanel = new JPanel();
	private JSpinner spinner;
	private JComboBox<String> comboBox;
	
	private static final String SEL_NONE = "<NONE>";

	/**
	 * メインメソッド
	 * 
	 * @param args コマンドライン引数
	 */

	public static void main(String[] args) {
		try {
			TimerDialog dialog = new TimerDialog("noname");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * コンストラクタ
	 * 
	 * @param name タイマ名
	 */
	public TimerDialog(final String name) {
		setResizable(false);
		setModal(true);
		setTitle("Time Left Settings");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 290, 130);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{132, 132, 0};
		gbl_contentPanel.rowHeights = new int[]{25, 25, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblNewLabel = new JLabel("Time left (sec):");
			lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = 0;
			gbc_lblNewLabel.gridy = 0;
			contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		}
		{
			spinner = new JSpinner();
			spinner.setModel(new SpinnerNumberModel(60, 0, 3600, 1));
			spinner.setValue(MainProperties.getInstance().getCountDown(name));
			GridBagConstraints gbc_spinner = new GridBagConstraints();
			gbc_spinner.fill = GridBagConstraints.BOTH;
			gbc_spinner.insets = new Insets(0, 0, 5, 0);
			gbc_spinner.gridx = 1;
			gbc_spinner.gridy = 0;
			contentPanel.add(spinner, gbc_spinner);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Hot key:");
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
			GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
			gbc_lblNewLabel_1.fill = GridBagConstraints.BOTH;
			gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
			gbc_lblNewLabel_1.gridx = 0;
			gbc_lblNewLabel_1.gridy = 1;
			contentPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		}
		{
			comboBox = new JComboBox<String>();
			
			/* 登録済のアクションキーを選択から削除 */
			List<String> accs = new ArrayList<String>();
			for (String t : KeyMapper.getInstance().getKeyText()) {
				if (!isRegistered(name, t)) {
					accs.add(t);
				}
			}
			comboBox.setModel(new DefaultComboBoxModel<String>(accs.toArray(new String[0])));
			comboBox.insertItemAt(SEL_NONE, 0);
			String sel = MainProperties.getInstance().getActionKey(name);
			if (sel == null) {
				sel = SEL_NONE;
			}
			comboBox.setSelectedItem(sel);
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.fill = GridBagConstraints.BOTH;
			gbc_comboBox.gridx = 1;
			gbc_comboBox.gridy = 1;
			contentPanel.add(comboBox, gbc_comboBox);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						/* カウントダウンの設定 */
						MainProperties.getInstance().setCountDown(name, (Integer)spinner.getValue());
						
						/* アクションキーの設定 */
						String sel = (String) comboBox.getSelectedItem();
						if (sel == null || sel.equals(SEL_NONE)) {
							MainProperties.getInstance().setActionKey(name, null);
						} else {
							MainProperties.getInstance().setActionKey(name, (String)comboBox.getSelectedItem());
						}
						
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	/**
	 * アクションキー登録があるか確認する．
	 * 
	 * @param name タイマボタンの識別子
	 * @param text アクションキー名
	 * @return 登録時はtrue、未登録時はfalse
	 */
	private boolean isRegistered(String name, String text) {
		for (Entry<String, String> e : MainProperties.getInstance().getActionKeys().entrySet()) {
			String key = (String) e.getKey();
			String value = (String) e.getValue();
			
			/* すでに他ボタンに割り当てられているかチェック */
			if (!key.startsWith(name) && value.equals(text)) {
				return true;
			}
		}
		return false;
	}
}
