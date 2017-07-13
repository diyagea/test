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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class CheckIn extends JFrame {

	private static CheckIn frame;
	
	private JPanel contentPane;
	private JTextField text_ID;
	private JTextField text_desc1;
	private JTextField text_desc2;
	private JTextField text_type;
	private JTextField text_loca;
	private JTextField text_count;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new CheckIn();
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
	public CheckIn() {
		setAlwaysOnTop(true);
		setResizable(false);
		setTitle("入库");
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
		lable_ID.setFont(new Font("Dialog", Font.PLAIN, 18));
		lable_ID.setBounds(62, 32, 69, 30);
		contentPane.add(lable_ID);

		JLabel label_desc1 = new JLabel("\u8BF4\u660E1\uFF1A");
		label_desc1.setFont(new Font("Dialog", Font.PLAIN, 18));
		label_desc1.setBounds(62, 88, 80, 37);
		contentPane.add(label_desc1);

		JLabel label_desc2 = new JLabel("\u8BF4\u660E2\uFF1A");
		label_desc2.setFont(new Font("Dialog", Font.PLAIN, 18));
		label_desc2.setBounds(62, 148, 80, 30);
		contentPane.add(label_desc2);

		JLabel label_type = new JLabel("\u7C7B\u578B\uFF1A");
		label_type.setFont(new Font("Dialog", Font.PLAIN, 18));
		label_type.setBounds(62, 211, 69, 21);
		contentPane.add(label_type);

		JLabel label_loca = new JLabel("\u5E93\u5B58\u4F4D\u7F6E\uFF1A");
		label_loca.setFont(new Font("Dialog", Font.PLAIN, 18));
		label_loca.setBounds(62, 277, 102, 37);
		contentPane.add(label_loca);

		JLabel label_count = new JLabel("\u5E93\u5B58\u6570\u91CF\uFF1A");
		label_count.setFont(new Font("Dialog", Font.PLAIN, 18));
		label_count.setBounds(62, 334, 91, 37);
		contentPane.add(label_count);

		text_ID = new JTextField();
		text_ID.setBounds(163, 36, 175, 28);
		contentPane.add(text_ID);
		text_ID.setColumns(10);

		text_desc1 = new JTextField();
		text_desc1.setBounds(163, 94, 175, 30);
		contentPane.add(text_desc1);
		text_desc1.setColumns(10);

		text_desc2 = new JTextField();
		text_desc2.setBounds(163, 151, 175, 30);
		contentPane.add(text_desc2);
		text_desc2.setColumns(10);

		text_type = new JTextField();
		text_type.setBounds(163, 209, 175, 30);
		contentPane.add(text_type);
		text_type.setColumns(10);

		text_loca = new JTextField();
		text_loca.setBounds(163, 283, 175, 30);
		contentPane.add(text_loca);
		text_loca.setColumns(10);

		text_count = new JTextField();
		text_count.setBounds(163, 341, 175, 27);
		contentPane.add(text_count);
		text_count.setColumns(10);

		JButton submit = new JButton("\u786E\u8BA4");
		submit.setBounds(84, 434, 93, 23);
		contentPane.add(submit);

		JButton cancel = new JButton("\u53D6\u6D88");
		cancel.setBounds(240, 434, 93, 23);
		contentPane.add(cancel);
		
		submit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO
			}
		});
		
		cancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
	}
}
