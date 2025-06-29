package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import wingsnepal.dao.UserDao;
import wingsnepal.model.UserDataModel;
import wingsnepal.view.LoginPage;
import wingsnepal.view.RegisterPage;
import wingsnepal.view.ForgotPassword;
import wingsnepal.view.UserPortal;
// import wingsnepal.view.AdminDashboard;
// import wingsnepal.view.EmployeeDashboard;

public class LoginController {
    private final LoginPage view;
    private final UserDao userDao;

    public LoginController(LoginPage view) {
        this.view = view;
        this.userDao = new UserDao();
        initController();
    }

    private void initController() {
        view.getLoginButton().addActionListener(new LoginHandler());

        view.getSignUpLabel().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RegisterPage registerPage = new RegisterPage();
                new RegisterController(registerPage);
                registerPage.setVisible(true);
                view.dispose();
            }
        });

        view.getForgotPasswordLabel().addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                wingsnepal.view.ForgotPassword forgotView = new wingsnepal.view.ForgotPassword();
                new ForgotPasswordController(forgotView).open();
                view.dispose();
            }
        });
    }

    private class LoginHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = view.getEmailTextField().getText().trim();
            String password = new String(view.getPasswordField().getPassword());
            String role = view.getRoleComboBox().getSelectedItem().toString();

            System.out.println("Login attempt - Email: " + email + ", Role: " + role);

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Please enter both email and password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                return;
            }

            UserDataModel user = userDao.loginUser(email, password, role);
            System.out.println("Login result - User: " + (user != null ? user.getFullName() : "null"));
            
            if (user != null) {
                System.out.println("User status: '" + user.getStatus() + "'");
                System.out.println("Status check: null=" + (user.getStatus() == null) + ", equalsActive=" + "Active".equalsIgnoreCase(user.getStatus()));
                if (user.getStatus() == null || !"Active".equalsIgnoreCase(user.getStatus())) {
                    System.out.println("BLOCKING LOGIN - User status is not Active");
                    JOptionPane.showMessageDialog(view, "Your account is inactive. Please contact the administrator.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                System.out.println("ALLOWING LOGIN - User status is Active");
                JOptionPane.showMessageDialog(view, "Login Successful - " + user.getFullName(), "Welcome", JOptionPane.INFORMATION_MESSAGE);
                switch (user.getRole()) {
                    case "User":
                    case "Customer":
                        System.out.println("Opening UserPortal for role: " + user.getRole());
                        wingsnepal.view.UserPortal userView = new wingsnepal.view.UserPortal(user);
                        new controller.UserPortalController(userView, user).open();
                        break;
                    case "Admin":
                        wingsnepal.view.AdminDashboard adminView = new wingsnepal.view.AdminDashboard(user);
                        new controller.AdminDashboardController(adminView, user).open();
                        break;
                    case "Employee":
                        wingsnepal.view.EmployeeDashboard employeeView = new wingsnepal.view.EmployeeDashboard(user);
                        employeeView.setVisible(true);
                        break;
                    default:
                        JOptionPane.showMessageDialog(view, "Unknown role: " + user.getRole(), "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                }
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view, "Invalid email, password, or role.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void open() {
        view.setVisible(true);
    }
}
