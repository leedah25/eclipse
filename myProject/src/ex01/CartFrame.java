package ex01;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class CartFrame extends JFrame implements ActionListener {
	Container con = getContentPane();
	UserDao dao = UserDao.getInstance();
	FlowerDao flwDao = FlowerDao.getInstance();
	JTable table;
	DefaultTableModel model = null;
	String[] columns = { "순번", "구매 상품", "구매 수량", "담은 날짜", "비고" };
	JButton btnBack = new JButton("계속쇼핑하기");
	JButton btnUpdate = new JButton("수정");
	JButton btnDelete = new JButton("삭제");
	JButton btnOrder = new JButton("주문하기");
	// 수정할 품목
	String[] flowers = { "미니다발　", "중간다발　", "대형다발　", "작은바구니", "대형바구니", "센터피스　" };
	String[] num = { "1개", "2개", "3개" };
	JComboBox<String> comboItem = new JComboBox<>(flowers);
	JComboBox<String> comboNum = new JComboBox<>(num);
	
	String updateItem = "";
	int updateQuan = 0;

	String id = "";
	List<FlowerVo> list;

	public CartFrame(String id) {
		this.id = id;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("회원 장바구니");
		setSize(800, 500);
		
		setUi();
		showData();
		setLocationRelativeTo(null);
		btnBack.addActionListener(this);
		btnUpdate.addActionListener(this);
		btnDelete.addActionListener(this);
		btnOrder.addActionListener(this);
		table.addMouseListener(adapter);
		
		setVisible(true);
	}

	private void setUi() {
		JPanel pnlCenter = new JPanel(new BorderLayout());

		model = new DefaultTableModel(columns, 0); // columns 위에서 생성한 String배열
		table = new JTable(model);

		table.getColumn("순번").setPreferredWidth(5);
		table.getColumn("구매 상품").setPreferredWidth(50);
		table.getColumn("구매 수량").setPreferredWidth(100);
		table.getColumn("담은 날짜").setPreferredWidth(30);
		table.getColumn("비고").setPreferredWidth(250);

		table.setRowHeight(25);

		pnlCenter.add(new JScrollPane(table));
		con.add(pnlCenter);

		UserVo vo = dao.selectById(id);
		String id = vo.getId();
		String name = vo.getName();
		String gender = vo.getGender();
		String address = vo.getAddress();
		Date date = vo.getJoindate();

		JPanel pnlNorth = new JPanel();
		pnlNorth.setPreferredSize(new Dimension(500, 200));
		pnlNorth.setLayout(new FlowLayout());
		JPanel pnlNorthLeft = new JPanel();
		JPanel pnlNorthRight = new JPanel();
		pnlNorth.add(pnlNorthLeft);
		pnlNorth.add(pnlNorthRight);

		// pnlNorthLeft 구간
		pnlNorthLeft.setLayout(new GridLayout(10, 2));
		JLabel lblName = new JLabel("* 이름 : " + name);
		JLabel lblId = new JLabel("* 아이디 : " + id);
		JLabel lblGender = new JLabel("* 성별 : " + gender);
		JLabel lblAddress = new JLabel("* 주소 : " + address);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String joindate = format.format(date);
		JLabel lblJoindate = new JLabel("* 가입일자 : " + joindate);

		lblName.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblId.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblGender.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblAddress.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblJoindate.setFont(new Font("맑은 고딕", Font.BOLD, 20));

		pnlNorthLeft.add(new JLabel(""));
		pnlNorthLeft.add(lblName);
		pnlNorthLeft.add(lblId);
		pnlNorthLeft.add(lblGender);
		pnlNorthLeft.add(lblAddress);
		pnlNorthLeft.add(lblJoindate);

		// pnlNorthRight 구간
		pnlNorthRight.setLayout(new GridLayout(3, 2));

		JLabel lblUpdItem = new JLabel("구매 상품: ");
		JLabel lblUpdQuan = new JLabel("구매 수량: ");
		JLabel lbl1 = new JLabel("수정 품목");
		JLabel lbl2 = new JLabel("선택 항목");

		pnlNorthRight.add(lbl1);
		pnlNorthRight.add(lbl2);
		pnlNorthRight.add(lblUpdItem);
		pnlNorthRight.add(comboItem);
		pnlNorthRight.add(lblUpdQuan);
		pnlNorthRight.add(comboNum);

		con.add(pnlNorth, BorderLayout.NORTH);

		JPanel pnlSouth = new JPanel();
		pnlSouth.add(btnBack);
		pnlSouth.add(btnUpdate);
		pnlSouth.add(btnDelete);
		pnlSouth.add(btnOrder);
		con.add(pnlSouth, BorderLayout.SOUTH);
	}

	private void showData() {
		list = flwDao.selectPuchaseById(id);
		model.setRowCount(0);
		System.out.println("list:" + list);
		int num = 1;
		for (FlowerVo fv : list) {
			Vector<Object> vec = new Vector<>();
			String no = Integer.toString(num);
			String item = fv.getItem();
			int quantity = fv.getQuantity();
			Date purchasedate = fv.getPurchaseDate();
			vec.add(no);
			num++;
			vec.add(item);
			vec.add(quantity);
			vec.add(purchasedate);
			model.addRow(vec);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int row = table.getSelectedRow(); // 테이블의 줄 번호 : 0,1,2...
		Object obj = e.getSource();
		if (obj == btnBack) { // 계속 쇼핑하기
			CartFrame.this.dispose();
			
		} else if (obj == btnUpdate) { // 장바구니 수정

			if (row == -1) {
				JOptionPane.showMessageDialog(CartFrame.this, "수정할 해당 열을 클릭하여 선택해주세요", "알림", JOptionPane.ERROR_MESSAGE);
			} else {
				FlowerVo vo = list.get(row); // 해당 줄의 FlowerVo 데이터!
				updateInfo();
				flwDao.updateCart(vo, updateItem, updateQuan);
				JOptionPane.showMessageDialog(CartFrame.this, "수정완료", "수정", JOptionPane.INFORMATION_MESSAGE);
				showData();
			}
		} else if (obj == btnDelete) { // 장바구니 삭제
			if (row == -1) {
				JOptionPane.showMessageDialog(CartFrame.this, "삭제할 해당 열을 클릭하여 선택해주세요", "알림", JOptionPane.ERROR_MESSAGE);
			} else {
				FlowerVo vo = list.get(row);
				flwDao.deleteCart(vo);
				JOptionPane.showMessageDialog(CartFrame.this, "삭제완료", "삭제", JOptionPane.INFORMATION_MESSAGE);
				showData();
			}
		} else if (obj == btnOrder) { // 주문하기
			int result = JOptionPane.showConfirmDialog(CartFrame.this, "주문 하시겠습니까?", "주문확인", JOptionPane.YES_NO_OPTION);
			if (result == 0) {
				JOptionPane.showMessageDialog(CartFrame.this, "주문완료(,,>᎑<,,)💗감사합니다.", "완료", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
		}

	}

	private void updateInfo() {
		updateItem = comboItem.getSelectedItem().toString();
		String quantity = comboNum.getSelectedItem().toString();
		switch (quantity) {
		case "1개":
			updateQuan = 1;
			break;
		case "2개":
			updateQuan = 2;
			break;
		case "3개":
			updateQuan = 3;
			break;
		}
	}
	
	MouseAdapter adapter = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			int row = table.getSelectedRow();

			Object itemrow = table.getModel().getValueAt(row, 1);
			Object quanrow = table.getModel().getValueAt(row, 2);
			String itemstr = itemrow.toString();
			System.out.println("선택한 품목: "+itemstr);
			int index=0;
			switch(itemstr) {
			case "미니다발　":
				index = 0;
				break;
			case "중간다발　":
				index = 1;
				break;
			case "대형다발　":
				index = 2;
				break;
			case "작은바구니":
				index = 3;
				break;
			case "대형바구니":
				index = 4;
				break;
			case "센터피스　":
				index = 5;
				break;
			}
			comboItem.setSelectedIndex(index);
			comboNum.setSelectedItem(quanrow.toString()+"개");
			System.out.println("선택한 품목의 index: "+index);
		}
	};
}
