package ex01;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class JoinDialog extends JDialog implements ActionListener {
	JTextField tfId = new JTextField(10);
	JPasswordField tfpw = new JPasswordField(10);
	JPasswordField tfpwOk = new JPasswordField(10);
	JTextField tfName = new JTextField(10);
	ButtonGroup buttonGroup = new ButtonGroup();
	JRadioButton rdoMale = new JRadioButton("남",true);
	JRadioButton rdoFemale = new JRadioButton("여");
	JButton btnJoin = new JButton("입력");
	JButton btnCancel = new JButton("취소");
	JTextField tfaddress = new JTextField(50);
	UserDao dao = UserDao.getInstance();

	public JoinDialog(MainFrame movieFrame, String title, boolean modal) {
		super(movieFrame, title, modal);
		setSize(500, 600);
		setUi();
		setLocationRelativeTo(null);
		btnJoin.addActionListener(this);
		btnCancel.addActionListener(this);
	}

	private void setUi() {
		JLabel label = new JLabel("회원가입");
		label.setFont(new Font("맑은고딕", Font.BOLD, 40));
		label.setOpaque(true);
		label.setBackground(Color.pink);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		add(label, BorderLayout.NORTH);

		buttonGroup.add(rdoMale);
		buttonGroup.add(rdoFemale);
		JPanel pnlBtn = new JPanel();
		pnlBtn.add(rdoMale);
		pnlBtn.add(rdoFemale);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6, 2, 10, 5));
		JLabel lblid = new JLabel("아이디: ");
		JLabel lblpw = new JLabel("비밀번호: ");
		JLabel lblrpw = new JLabel("비밀번호 확인: ");
		JLabel lblname = new JLabel("이름: ");
		JLabel lblgender = new JLabel("성별: ");
		JLabel lbladdress = new JLabel("주소: ");
		
		//label 글자 키우기
		lblid.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblpw.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblrpw.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblname.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblgender.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lbladdress.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		rdoMale.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		rdoFemale.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnJoin.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnCancel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		
		panel.add(lblid);
		panel.add(tfId);
		panel.add(lblpw);
		panel.add(tfpw);
		panel.add(lblrpw);
		panel.add(tfpwOk);
		panel.add(lblname);
		panel.add(tfName);
		panel.add(lblgender);
		panel.add(pnlBtn);
		panel.add(lbladdress);
		panel.add(tfaddress);

		add(panel, BorderLayout.CENTER);

		JPanel pnlSouth = new JPanel();
		pnlSouth.add(btnJoin);
		pnlSouth.add(btnCancel);
		add(pnlSouth, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj == btnJoin) {
			String id = tfId.getText();
			String pw = new String(tfpw.getPassword());
			String pwRe = new String(tfpwOk.getPassword());
			String name = tfName.getText();
			String address = tfaddress.getText();
			String gender = "";
			Enumeration<AbstractButton> enumer = buttonGroup.getElements();
			while (enumer.hasMoreElements()) {
				JRadioButton button = (JRadioButton) enumer.nextElement();
				if (button.isSelected()) {
					gender = button.getText();
					break;
				}
			}

			if (pw.equals(pwRe)) { // 비밀번호, 비밀번호 확인이 동일한 경우
				UserVo vo = new UserVo();
				vo.setId(id);
				vo.setPw(pw);
				vo.setName(name);
				vo.setGender(gender);
				vo.setAddress(address);
				boolean result = dao.addMember(vo);
				if (result) {
					JoinDialog.this.dispose();
					new MainFrame();
					JOptionPane.showMessageDialog(null, "회원가입이 되었습니다.", 
									"알림", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "빠진부분을 채워주세요.", 
									"알림", JOptionPane.WARNING_MESSAGE);
				}

			} else { // 비밀번호,비밀번호 확인이 다른경우 경고메세지
				JOptionPane.showMessageDialog(null, "비밀번호를 다시 확인해주세요", 
								"알림", JOptionPane.WARNING_MESSAGE);
				tfpw.setText("");
				tfpwOk.setText("");
			}

		} else if (obj == btnCancel) {
			this.setVisible(false);
		}

	}

}
