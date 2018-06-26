package noteboard;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

public class Mainboard extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int count;
	Dimension gezi = new Dimension(45, 45);
	Font font = new Font("΢���ź�", Font.BOLD, 17);
	JFrame f = new JFrame("���±�");
	JMenuBar mb = new JMenuBar();
	JMenu wenjian = new JMenu("�ļ�");
	JMenu bianji = new JMenu("�༭");
	JMenu geshi = new JMenu("��ʽ");
	JMenuItem xinjian = new JMenuItem("�½�");
	JMenuItem dakai = new JMenuItem("��");
	JMenuItem baocun = new JMenuItem("����");
	JMenuItem chexiao = new JMenuItem("����");
	JMenuItem jianqie = new JMenuItem("����");
	JMenuItem fuzhi = new JMenuItem("����");
	JMenuItem zhantie = new JMenuItem("ճ��");
	JMenuItem quanxuan = new JMenuItem("ȫѡ");
	JMenuItem ziti = new JMenuItem("����");
	JCheckBoxMenuItem zidonghuanhang = new JCheckBoxMenuItem("�Զ�����");
	JTextArea ta = new JTextArea();
	FileDialog sv;
	FileDialog op;
	boolean flag;

	public Mainboard() {
		count = 0;
		JFontChooser fc = new JFontChooser();
		flag = true;
		UndoManager um = new UndoManager();
		ta.setFont(new Font("΢���ź�", Font.BOLD, 22));
		wenjian.setPreferredSize(gezi);
		bianji.setPreferredSize(gezi);
		geshi.setPreferredSize(gezi);
		wenjian.setFont(font);
		bianji.setFont(font);
		geshi.setFont(font);
		xinjian.setPreferredSize(gezi);
		dakai.setPreferredSize(gezi);
		baocun.setPreferredSize(gezi);
		chexiao.setPreferredSize(gezi);
		jianqie.setPreferredSize(gezi);
		fuzhi.setPreferredSize(gezi);
		zhantie.setPreferredSize(gezi);
		quanxuan.setPreferredSize(gezi);
		zidonghuanhang.setPreferredSize(new Dimension(95, 45));
		ziti.setPreferredSize(new Dimension(95, 45));
		xinjian.setFont(font);
		dakai.setFont(font);
		baocun.setFont(font);
		chexiao.setFont(font);
		jianqie.setFont(font);
		fuzhi.setFont(font);
		zhantie.setFont(font);
		quanxuan.setFont(font);
		zidonghuanhang.setFont(font);
		ziti.setFont(font);
		wenjian.add(xinjian);
		wenjian.add(dakai);
		wenjian.add(baocun);
		bianji.add(chexiao);
		bianji.add(jianqie);
		bianji.add(fuzhi);
		bianji.add(zhantie);
		bianji.add(quanxuan);
		geshi.add(zidonghuanhang);
		geshi.add(ziti);
		mb.add(wenjian);
		mb.add(bianji);
		mb.add(geshi);
		sv = new FileDialog(f, "�洢", FileDialog.SAVE);
		op = new FileDialog(f, "��", FileDialog.LOAD);

		xinjian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ta.setText(" ");
				count = 0;
			}
		});
		baocun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					save();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		});
		dakai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op.setVisible(true);
				String s;
				File f1 = new File(op.getDirectory(), op.getFile());
				try {
					FileReader fr = new FileReader(f1);
					BufferedReader br = new BufferedReader(fr);
					while ((s = br.readLine()) != null)
						ta.append(s);
					fr.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		chexiao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				um.undo();
			}
		});
		ta.getDocument().addUndoableEditListener(new UndoableEditListener() {
			public void undoableEditHappened(UndoableEditEvent e) {
				um.addEdit(e.getEdit());
			}
		});
		fuzhi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ta.copy();
			}
		});
		zhantie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ta.paste();
			}
		});
		jianqie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ta.cut();
			}
		});
		quanxuan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ta.selectAll();
			}
		});
		zidonghuanhang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zidonghuanhang.setState(flag);
				ta.setLineWrap(flag);
				flag = !flag;
			}
		});
		ziti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc.showDialog(ta);
				ta.setFont(fc.getSelectedFont());
			}
		});
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				if (count == 1)
					System.exit(0);
				else {
					int res = JOptionPane.showConfirmDialog(null, "δ�����Ƿ����", "δ���棡", JOptionPane.YES_NO_OPTION);
					if (res == JOptionPane.YES_OPTION)
						System.exit(0);
					else {
						try {
							save();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});

		f.setExtendedState(MAXIMIZED_BOTH);
		f.add(ta);
		f.setJMenuBar(mb);
		f.setVisible(true);
	}

	public void save() throws IOException {
		sv.setVisible(true);
		File f1 = new File(sv.getDirectory(), sv.getFile());
		FileWriter fw = new FileWriter(f1);
		BufferedWriter bw = new BufferedWriter(fw);
		String gt = ta.getText();
		bw.write(gt, 0, gt.length());
		bw.flush();
		count = 1;
		fw.close();
	}

	public static void main(String[] args) {
		new Mainboard();
	}
}
