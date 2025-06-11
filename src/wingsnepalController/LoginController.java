package wingsnepalController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import wingsnepal.dao.LoginDao;
import wingsnepal.model.Login;
import wingsnepal.view.LoginPage;
import wingsnepal.view.UserPortal;
import wingsnepal.view.EmployeeDashboard;
import wingsnepal.view.AdminDashboard;

public class LoginController {
    private final LoginPage view;

    public LoginController(LoginPage view) {
        this.view = view;
        this.view.getLoginButton().addActionListener(new LoginUser());
        this.view.getForgotPasswordLabel().addMouseListener(new ForgotPassword());
    }

    public void open() {
        this.view.setVisible(true);
    }

    public void close() {
        this.view.dispose();
    }

    class LoginUser implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = view.getEmailTextField().getText();
            String password = new String(view.getPasswordField().getPassword());
            String selectedRole = (String) view.getRoleComboBox().getSelectedItem();

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Fill in all the fields");
                return;
            }

            LoginDao loginDao = new LoginDao();
            Login user = loginDao.login(email, password);

            if (user == null || !user.getRole().equalsIgnoreCase(selectedRole)) {
                JOptionPane.showMessageDialog(view, "Invalid email, password, or role.");
                return;
            }

            JOptionPane.showMessageDialog(view, "Login successful! Welcome, " + user.getFullName());

            switch (user.getRole()) {
                case "User":
                    new UserPortal(user).setVisible(true);
                    break;
                case "Employee":
                    new EmployeeDashboard(user).setVisible(true);
                    break;
                case "Admin":
                    new AdminDashboard(user).setVisible(true);
                    break;
            }
            close();
        }
    }

    class ForgotPassword implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            JOptionPane.showMessageDialog(view, "Forgot password feature not implemented yet.");
        }

        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
    }
}