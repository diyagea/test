package UI;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import java.awt.Dialog.ModalExclusionType;

public class CheckOut extends JFrame {

	private JPanel contentPane;
	private static CheckOut frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new CheckOut();
					frame.setVisible(true);
					frame.setAlwaysOnTop(true); // 总在最前面
					frame.setResizable(false); // 不能改变大小
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CheckOut() {
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("\u51FA\u5E93");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// get screen size
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		int screenWidth = screenSize.width; // 获取屏幕的宽
		int screenHeight = screenSize.height; // 获取屏幕的高

		int windowWidth = 473;
		int windowHeight = 517;

		setBounds((screenWidth - windowWidth) / 2, (screenHeight - windowHeight) / 2, windowWidth, windowHeight);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lable_ID = new JLabel("ID\uFF1A");
		lable_ID.setBounds(62, 32, 69, 30);
		lable_ID.setFont(new Font("Dialog", Font.PLAIN, 18));
		contentPane.add(lable_ID);

		JLabel label_desc1 = new JLabel("\u8BF4\u660E1\uFF1A");
		label_desc1.setBounds(62, 88, 80, 37);
		label_desc1.setFont(new Font("Dialog", Font.PLAIN, 18));
		contentPane.add(label_desc1);

		JLabel label_desc2 = new JLabel("\u8BF4\u660E2\uFF1A");
		label_desc2.setBounds(62, 148, 80, 30);
		label_desc2.setFont(new Font("Dialog", Font.PLAIN, 18));
		contentPane.add(label_desc2);

		JLabel label_type = new JLabel("\u7C7B\u578B\uFF1A");
		label_type.setBounds(62, 216, 69, 21);
		label_type.setFont(new Font("Dialog", Font.PLAIN, 18));
		contentPane.add(label_type);

		JLabel label_loca = new JLabel("\u5E93\u5B58\u4F4D\u7F6E\uFF1A");
		label_loca.setBounds(62, 273, 102, 37);
		label_loca.setFont(new Font("Dialog", Font.PLAIN, 18));
		contentPane.add(label_loca);

		JLabel label_count = new JLabel("\u5E93\u5B58\u6570\u91CF\uFF1A");
		label_count.setBounds(62, 332, 90, 45);
		label_count.setFont(new Font("Dialog", Font.PLAIN, 18));
		contentPane.add(label_count);

		JTextPane textPane_ID = new JTextPane();
		textPane_ID.setEditable(false);
		textPane_ID.setBounds(173, 32, 145, 30);
		contentPane.add(textPane_ID);

		JTextPane textPane_desc1 = new JTextPane();
		textPane_desc1.setEditable(false);
		textPane_desc1.setBounds(173, 88, 145, 30);
		contentPane.add(textPane_desc1);

		JTextPane textPane_desc2 = new JTextPane();
		textPane_desc2.setEditable(false);
		textPane_desc2.setBounds(173, 148, 145, 30);
		contentPane.add(textPane_desc2);

		JTextPane txtpnAd = new JTextPane();
		txtpnAd.setEditable(false);
		txtpnAd.setBounds(173, 216, 145, 30);
		contentPane.add(txtpnAd);

		JTextPane textPane_loca = new JTextPane();
		textPane_loca.setEditable(false);
		textPane_loca.setBounds(174, 279, 145, 31);
		contentPane.add(textPane_loca);

		JTextPane textPane_count = new JTextPane();
		textPane_count.setEditable(false);
		textPane_count.setBounds(173, 343, 145, 32);
		contentPane.add(textPane_count);

		JButton btnNewButton = new JButton("\u786E\u8BA4");
		btnNewButton.setBounds(173, 426, 93, 30);
		contentPane.add(btnNewButton);

		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});

	}
}
