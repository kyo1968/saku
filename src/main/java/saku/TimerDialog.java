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
import java.awt.event.ActionEvent;

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
		setModal(true);
		setTitle("Time Left Settings");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 290, 130);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JLabel lblNewLabel = new JLabel("Time left (sec):");
			contentPanel.add(lblNewLabel);
		}
		{
			spinner = new JSpinner();
			spinner.setModel(new SpinnerNumberModel(60, 0, 3600, 1));
			spinner.setValue(MainProperties.getInstance().getCountDown(name));
			contentPanel.add(spinner);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						MainProperties.getInstance().setCountDown(name, (Integer)spinner.getValue());
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
}
