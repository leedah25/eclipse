package ex01;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener {
	Container con = getContentPane();
	JTextField tfId = new JTextField("",10); // lee
	JPasswordField tfpw = new JPasswordField("",10); //1234
	JButton btnLogin = new JButton("로그인");
	JButton btnJoin = new JButton("회원가입");
	JoinDialog dialog = new JoinDialog(this, "회원 가입", true);
	UserDao dao = UserDao.getInstance();

	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("꽃 로그인");
		setSize(500, 300);
		setLocationRelativeTo(null);
		setUi();
		btnLogin.addActionListener(this);
		btnJoin.addActionListener(this);

		setVisible(true);

	}

	private void setUi() {
		JLabel label = new JLabel("로그인");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 50));
		label.setOpaque(true);
		label.setBackground(Color.pink);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		con.add(label, BorderLayout.NORTH);

		JPanel pnlCenter = new JPanel();
		pnlCenter.setLayout(new GridLayout(3, 3, 20, 20));
		JLabel lblid = new JLabel("아이디: ");
		JLabel lblpw = new JLabel("비밀번호: ");
		lblid.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblpw.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnLogin.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnJoin.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		tfId.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		tfpw.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		
		pnlCenter.add(lblid);
		pnlCenter.add(tfId);
		pnlCenter.add(lblpw);
		pnlCenter.add(tfpw);
		pnlCenter.add(btnLogin);
		pnlCenter.add(btnJoin);
		con.add(pnlCenter);

	}

	public static void main(String[] args) {
		new MainFrame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj == btnJoin) { // 회원가입
			dialog.setVisible(true);
		} else if (obj == btnLogin) { // 로그인
			String id = tfId.getText();
			String pw = new String(tfpw.getPassword());
			UserVo vo = new UserVo();
			vo.setId(id);
			vo.setPw(pw);
			boolean result = dao.Login(vo);
			// 로그인 성공
			if (result) {
				JOptionPane.showMessageDialog(MainFrame.this, "로그인을 성공하셨습니다.", 
									"알림", JOptionPane.INFORMATION_MESSAGE);
				MainFrame.this.dispose(); // 로그인 화면 삭제
				new FlowerFrame(id); // 꽃 선택 화면 띄우기
			}
			// 로그인 실패
			else {
				JOptionPane.showMessageDialog(MainFrame.this, "로그인을 실패했습니다.", 
									"알림", JOptionPane.WARNING_MESSAGE);
				tfId.setText("");
				tfpw.setText("");
			}
		}
	}
}
