package UI;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class StockControl extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StockControl frame = new StockControl();
					frame.setVisible(true);
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
	public StockControl() {
		setResizable(false);
		setTitle("\u5E93\u5B58\u7BA1\u7406");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// get screen size
		Toolkit kit = Toolkit.getDefaultToolkit(); // 定义工具包
		Dimension screenSize = kit.getScreenSize(); // 获取屏幕的尺寸
		int screenWidth = screenSize.width; // 获取屏幕的宽
		int screenHeight = screenSize.height; // 获取屏幕的高

		int windowWidth = 450;
		int windowHeight = 300;

		setBounds((screenWidth - windowWidth) / 2, (screenHeight - windowHeight) / 2, windowWidth, windowHeight);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton stockIn = new JButton("\u5165\u5E93");
		stockIn.setBounds(74, 42, 292, 72);
		contentPane.add(stockIn);

		JButton stockOut = new JButton("\u51FA\u5E93");
		stockOut.setBounds(74, 158, 292, 72);
		contentPane.add(stockOut);

		// add action listion
		stockIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CheckIn.main(null);
			}
		});

		stockOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CheckOut.main(null);
			}
		});

	}

}
