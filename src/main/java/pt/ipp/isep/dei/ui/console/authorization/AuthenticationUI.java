package pt.ipp.isep.dei.ui.console.authorization;

import javafx.application.Platform;
import pt.ipp.isep.dei.controller.AuthenticationController;
import pt.ipp.isep.dei.domain.Employee;
import pt.ipp.isep.dei.domain.Organization;
import pt.ipp.isep.dei.ui.console.menu.AdminUI;
import pt.ipp.isep.dei.ui.console.menu.EditorUI;
import pt.ipp.isep.dei.ui.console.menu.MenuItem;
import pt.ipp.isep.dei.ui.console.menu.PlayerUI;
import pt.ipp.isep.dei.ui.console.utils.Utils;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class AuthenticationUI implements Runnable {
    private final AuthenticationController ctrl;
    private final Organization organization;

    public AuthenticationUI(Organization organization) {
        this.organization = organization;
        ctrl = new AuthenticationController();
    }

    public void run() {
        boolean success = doLogin();

        if (success) {
            List<UserRoleDTO> roles = this.ctrl.getUserRoles();
            if ((roles == null) || (roles.isEmpty())) {
                System.out.println("No role assigned to user.");
            } else {
                UserRoleDTO role = selectsRole(roles);
                if (!Objects.isNull(role)) {
                    List<MenuItem> rolesUI = getMenuItemForRoles();
                    this.redirectToRoleUI(rolesUI, role);
                } else {
                    System.out.println("No role selected.");
                }
            }
        }
        this.logout();
    }

    private List<MenuItem> getMenuItemForRoles() {
        List<MenuItem> rolesUI = new ArrayList<>();
        rolesUI.add(new MenuItem(AuthenticationController.ROLE_ADMIN, new AdminUI()));
        rolesUI.add(new MenuItem(AuthenticationController.ROLE_EDITOR, new EditorUI(organization))); // Passando o loggedInEmployee
        rolesUI.add(new MenuItem(AuthenticationController.ROLE_PLAYER, new PlayerUI()));
        return rolesUI;
    }

    public boolean doLogin() {
        System.out.println("\n\n--- LOGIN UI ---------------------------");

        int maxAttempts = 3;
        boolean success = false;
        do {
            maxAttempts--;
            String id = Utils.readLineFromConsole("Enter UserId/Email: ");
            String pwd = Utils.readLineFromConsole("Enter Password: ");

            success = ctrl.doLogin(id, pwd);
            if (!success) {
                System.out.println("Invalid UserId and/or Password. \n You have  " + maxAttempts + " more attempt(s).");
            }

        } while (!success && maxAttempts > 0);
        return success;
    }

    private void logout() {
        ctrl.doLogout();
    }

    private void redirectToRoleUI(List<MenuItem> rolesUI, UserRoleDTO role) {
        boolean found = false;
        for (MenuItem item : rolesUI) {
            if (item.hasDescription(role.getDescription())) {
                if (role.getDescription().equals(AuthenticationController.ROLE_PLAYER)) {
                    // Launch JavaFX for players
                    new Thread(() -> {
                        try {
                            pt.ipp.isep.dei.Main.launch(pt.ipp.isep.dei.Main.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }).start();
                } else {
                    // Run console UI for other roles
                    item.run();
                }
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("No UI for users with role '" + role.getDescription() + "'");
        }
    }

    private UserRoleDTO selectsRole(List<UserRoleDTO> roles) {
        if (roles.size() == 1) {
            return roles.get(0);
        } else {
            return (UserRoleDTO) Utils.showAndSelectOne(roles, "Select the role you want to adopt in this session:");
        }
    }
}
