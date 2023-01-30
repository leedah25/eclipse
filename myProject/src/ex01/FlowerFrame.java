package ex01;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class FlowerFrame extends JFrame implements ActionListener {
	Container con = getContentPane();
	String[] flowers = { "미니다발　", "중간다발　", "대형다발　", "작은바구니", "대형바구니", "센터피스　" };
	String[] imageSrc = { "images/미니다발.jpg", "images/중간다발.jpg", "images/대형다발.jpg", "images/작은바구니.jpg",
			"images/대형바구니.jpg", "images/센터피스.jpg" };
	JButton btnOrder = new JButton("주문하기");
	JButton btnCancel = new JButton("로그아웃");
	JButton btnCartAdd = new JButton("장바구니추가");
	JButton btnCart = new JButton("  장바구니  ");
	String[] num = { "1개", "2개", "3개" };
	String id = "";
	JRadioButton rdo[] = new JRadioButton[flowers.length];
	JComboBox<String> comboNum = new JComboBox<>(num);
	FlowerDao fdao = FlowerDao.getInstance();

	public FlowerFrame(String id) {
		this.id = id;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("꽃 선택창");
		setUi(id);
		btnOrder.addActionListener(this);
		btnCancel.addActionListener(this);
		btnCart.addActionListener(this);
		btnCartAdd.addActionListener(this);

		setSize(800, 600);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void setUi(String id) {
		//위쪽 판넬 정리
		JPanel pnlNorth = new JPanel();
		JLabel lblTitle = new JLabel(id + " 님의 꽃을 선택해주세요.");
		lblTitle.setFont(new Font("맑은고딕", Font.BOLD, 20));
		lblTitle.setOpaque(true);
		lblTitle.setBackground(Color.pink);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		pnlNorth.setBackground(Color.pink);
		pnlNorth.add(lblTitle);
		pnlNorth.add(btnCart);
		con.add(pnlNorth, BorderLayout.NORTH);
		
		//가운데 판넬 정리
		JPanel pnlCenter = new JPanel();
		JPanel pnlCenterLeft = new JPanel();
		JPanel pnlCenterRight = new JPanel();
		
		pnlCenter.setLayout(new FlowLayout());
		pnlCenter.setBackground(Color.pink);
		
		// 가운데 왼쪽 판넬 정리
		pnlCenterLeft.setLayout(new GridLayout(6, 2));
		ButtonGroup group = new ButtonGroup();
		for (int i = 0; i < flowers.length; i++) {
			pnlCenter.add(new JLabel(new ImageIcon(imageSrc[i])));
			JPanel pnlInner = new JPanel();
			pnlInner.setBackground(Color.pink);
			rdo[i] = new JRadioButton(flowers[i]);
			rdo[i].setBackground(Color.white);
			rdo[i].setFont(new Font("맑은고딕", Font.BOLD, 15));
			group.add(rdo[i]);
			pnlInner.add(rdo[i]);
			pnlInner.add(comboNum);
			pnlCenter.add(pnlInner);
		}
		rdo[0].setSelected(true);
		
		// 가운데 오른쪽 판넬 정리
		pnlCenterRight.add(new JLabel("갯수 : "));
		pnlCenterRight.add(comboNum);
		pnlCenterRight.add(btnOrder);
		pnlCenterRight.add(btnCartAdd);
		
		pnlCenter.add(pnlCenterLeft);
		pnlCenter.add(pnlCenterRight);
		con.add(pnlCenter, BorderLayout.CENTER);

		JPanel pnlSouth = new JPanel();
		pnlSouth.setBackground(Color.PINK);
		
		pnlSouth.add(btnCancel);

		con.add(pnlSouth, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btnOrder) { // 주문하기
			int result = JOptionPane.showConfirmDialog(FlowerFrame.this, "주문 하시겠습니까?", 
							"주문확인", JOptionPane.YES_NO_OPTION);
			if (result == 0) {
				JOptionPane.showMessageDialog(FlowerFrame.this, "주문완료!.감사합니다.", 
								"완료", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);

			}
		} else if (obj == btnCancel) { // 로그아웃
			FlowerFrame.this.dispose();
			new MainFrame();

		} else if (obj == btnCart) { // 장바구니 확인
			new CartFrame(id);

		} else if (obj == btnCartAdd) { // 장바구니 추가
			int result = JOptionPane.showConfirmDialog(FlowerFrame.this, "장바구니에 추가하시겠습니까?", 
							"확인", JOptionPane.YES_NO_OPTION);
			if (result == 0) {
				insertInfo();
			}
		}

	}

	private void insertInfo() {
		String id = this.id; // 이름 받아옴~~
		String item = "";
		for (int i = 0; i < rdo.length; i++) {
			boolean result = rdo[i].isSelected();
			if (result) {   // 꽃 선택된것 텍스트 받아오기
				item = rdo[i].getText();
				break;
			} 
		}
		String quantitystr = comboNum.getSelectedItem().toString();
		int quantity = 0;
		switch (quantitystr) {
		case "1개":
			quantity = 1;
			break;
		case "2개":
			quantity = 2;
			break;
		case "3개":
			quantity = 3;
			break;
		}

		FlowerVo flowerVo = new FlowerVo(id, item, quantity, null, null);
		System.out.println(flowerVo.toString());
		boolean result = fdao.addPurchase(flowerVo);
		if (result) { // 꽃선택 완료시
			new CartFrame(id); //
		}
	}

}
