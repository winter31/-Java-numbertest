package numberList;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JTable;

public class reusltUI extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private SystemManager sm = null;
	public DefaultTableModel dm;
	/**
	 * Launch the application.
	 */
	public reusltUI(SystemManager sm) {
		this.sm = sm;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(700, 200, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		String[] columns = {"연 번 ", "결과 값"};
		JButton btnNewButton = new JButton("닫기");
		contentPane.add(btnNewButton, BorderLayout.SOUTH);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==1){
					dispose();
				}
			}
		});
		
		dm = new DefaultTableModel(columns, 0){
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(dm);
		int countNum = 1;
		String numResult ="";
		JScrollPane scroll = new JScrollPane(table);
/*		while (dm.getRowCount() != 0) {
			dm.removeRow(0);
		}*/
		for(int[] num : sm.numberList){
			for(int a = 0; a< num.length;a++){
				numResult += " "+num[a];
			}
			Object[] rowResult = {countNum++,numResult};
			dm.addRow(rowResult);
			numResult = "";
		}
		contentPane.add(scroll, BorderLayout.CENTER);
		
	}
}
