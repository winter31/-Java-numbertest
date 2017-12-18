package numberList;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainUI extends JFrame {
	private JTextField textField;   // 범위 지정 1
	private JTextField textField_1; // 범위 지정 2
	private JTextField textField_2; // 수열 지정 숫자 
	private JTextField textField_3; // 수열 합차 지정 
	private SystemManager sm ;
	private JTextField textField_6; // 뽑을 갰수
	private JCheckBox sum;
	private JCheckBox notSum;
	private JCheckBox chckbxNewCheckBox;
	
	public MainUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 500, 400);
		getContentPane().setLayout(new BorderLayout(0, 0));

		JLabel title = new JLabel("숫자 통계 프로그램"); GridLayout mainUIBoard  = new GridLayout(3, 1);
		JPanel centerUI = new JPanel(); JButton resultBT = new JButton("결과 보기");
		resultBT.addMouseListener(new MouseAdapter() {
		
			//------------------------ 여기서 부터 중요(데이타 넘겨주고 결과 결과창에 넘겨주는 역할)----------------------
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==1){
					sm = new SystemManager(Integer.parseInt(textField_6.getText()));
					if(!textField.getText().equals("")&!textField_1.getText().equals("")){
						sm.makingNum(Integer.parseInt(textField.getText()), Integer.parseInt(textField_1.getText()));
						if(!textField_2.getText().equals("")&!textField_3.getText().equals("")){
							if(sum.isSelected()){
								sm.countNumbers(Integer.parseInt(textField_2.getText()), Integer.parseInt(textField_3.getText()), "sum");
							}else if(notSum.isSelected()){
								sm.countNumbers(Integer.parseInt(textField_2.getText()), Integer.parseInt(textField_3.getText()), "notSum");
							}
						}
						if(chckbxNewCheckBox.isSelected()){
							sm.DBcount();
						}
					}else{
						System.out.println("숫자입력을 해주십시오.");
					}
					reusltUI frame = new reusltUI(sm);
					frame.setVisible(true);
				}
			}
		});
		//-------------------------------------------------------------		
		title.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(title, BorderLayout.NORTH);
		getContentPane().add(centerUI, BorderLayout.CENTER);
		getContentPane().add(resultBT, BorderLayout.SOUTH);
		centerUI.setLayout(mainUIBoard);

		JPanel panel_0 = new JPanel();JPanel panel_1 = new JPanel();
		JPanel panel_2 = new JPanel();
		panel_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(172, 175, 170)));
		panel_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(172, 175, 170)));
		panel_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(172, 175, 170)));
		
		
		centerUI.add(panel_0);
		panel_0.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblNewLabel = new JLabel("  범위 지정");
		lblNewLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(172, 175, 170)));
		lblNewLabel.setBackground(SystemColor.activeCaption);
		panel_0.add(lblNewLabel);
		textField = new JTextField();
		panel_0.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel(" ~ ");
		lblNewLabel_5.setBackground(Color.WHITE);
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel_0.add(lblNewLabel_5);
		textField_1 = new JTextField();
		panel_0.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("뽑을 개수");
		panel_0.add(lblNewLabel_8);
		
		textField_6 = new JTextField();
		panel_0.add(textField_6);
		textField_6.setColumns(10);
	
		centerUI.add(panel_2);
		panel_2.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblNewLabel_2 = new JLabel("  수열 지정");
		lblNewLabel_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(172, 175, 170)));
		lblNewLabel_2.setBackground(SystemColor.activeCaption);
		panel_2.add(lblNewLabel_2);
		
		JLabel lblNewLabel_6 = new JLabel("숫자 지정");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setBackground(Color.GRAY);
		panel_2.add(lblNewLabel_6);
		
		textField_2 = new JTextField();
		panel_2.add(textField_2);
		textField_2.setColumns(7);
		
		JLabel lblNewLabel_7 = new JLabel("합 차 숫자");
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_7.setBackground(Color.GRAY);
		panel_2.add(lblNewLabel_7);
		
		textField_3 = new JTextField();
		panel_2.add(textField_3);
		textField_3.setColumns(7);
		
		JPanel panel_5 = new JPanel();
		panel_2.add(panel_5);
		panel_5.setLayout(new GridLayout(1, 0, 0, 0));
		
		sum = new JCheckBox("합");
		panel_5.add(sum);
		
		notSum = new JCheckBox("차");
		panel_5.add(notSum);
		
		centerUI.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("  DB 지정");
		lblNewLabel_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(172, 175, 170)));
		lblNewLabel_1.setBackground(SystemColor.activeCaption);
		panel_1.add(lblNewLabel_1);
		
		chckbxNewCheckBox = new JCheckBox("DB 체크");
		chckbxNewCheckBox.setBackground(Color.WHITE);
		panel_1.add(chckbxNewCheckBox);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel_1.add(panel);
	}
}
