package com.sj.common.gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.sj.common.init.InitPro;
import com.sj.common.utils.EncryptUtil;
import com.sj.common.utils.MACAddressUtils;
public class MainGui extends JFrame implements ActionListener{  
    //定义组件  
	public static boolean isKey = false;
    JLabel userLable, passLable,macLable,lab;  
    JTextField userText,macText;
    private JDialog d;
    JPasswordField password;  
    JButton submitButton,okBut;   
    //构造函数  
    public MainGui(){  
        //创建组件  
        userLable = new JLabel("账号",JLabel.CENTER);  
        passLable = new JLabel("密码",JLabel.CENTER);
        macLable = new JLabel("key码",JLabel.CENTER);
          
        userText = new JTextField(10);  
        macText = new JTextField(10);
        password = new JPasswordField(10);  
          
        submitButton = new JButton("完成");  
        submitButton.setSize(1, 1);
        submitButton.addActionListener(this);
        d = new JDialog(this, "提示信息-key", true);
        d.setBounds(400, 200, 350, 150);//设置弹出对话框的位置和大小
        d.setLayout(new FlowLayout());//设置弹出对话框的布局为流式布局
        lab = new JLabel();//创建lab标签填写提示内容
        okBut = new JButton("确定");//创建确定按钮

        okBut.addActionListener(this);
        
        d.add(lab);//将标签添加到弹出的对话框内
        d.add(okBut);//将确定按钮添加到弹出的对话框内。
        
        
        //设置布局  
        setLayout(new GridLayout(4, 2));  
        add(userLable);  
        add(userText);  
        add(passLable);  
        add(password);  
        add(macLable);
        add(macText);
        add(submitButton);
          
        //设置窗体  
        setTitle("登录界面");  
        int jwidth = 400;
        int jheight = 300;
        setSize(jwidth, jheight);  
        int width = Toolkit.getDefaultToolkit().getScreenSize().width - jwidth;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height - jheight;
        this.setLocation(width / 2 , height / 2);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
        setVisible(true);  
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("确定")){
			d.setVisible(false);
	        d.dispose();
	        if(!isKey)
	        	d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	        else
	        	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        return;
		}
		String userText = this.userText.getText().trim();
		String macText = this.macText.getText().trim();
		String password = new String(this.password.getPassword()).trim();
		String userMD = EncryptUtil.encrypt(userText);
		String passMD = EncryptUtil.encrypt(password);
		List<String> allMac = MACAddressUtils.getAllMac();
		for (String string : allMac) {
			String macMD = EncryptUtil.encrypt(MACAddressUtils.macPaser(string));
			String key = EncryptUtil.encrypt(userMD.substring(0, 8) + macMD.substring(1, 9) + passMD.substring(2, 10)).substring(0, 24);
			System.out.println(macText + "\n" + key);
			if(macText.equals(key))
				isKey = true;
		}
		String info = "";
		if(!isKey)
			info = "您输入得 Key 码 有误，请核对后重新输入！";
		else{
			info = "恭喜您激活成功！";
			Properties properties = new Properties();
			try {
				FileOutputStream fileOutputStream = new FileOutputStream("key.lsj", false);
				properties.setProperty("user", userMD);
				properties.setProperty("pass", passMD);
				properties.setProperty("key", macText);
				properties.store(fileOutputStream, "lsj");
				fileOutputStream.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			InitPro.key = true;
			
		}
		lab.setText(info);//显示文本错误提示信息
        d.setVisible(true);//设置对话框可见。
	}
    }  
