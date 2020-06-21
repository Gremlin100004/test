package com.senla.carservice.ui.menu;

import com.senla.carservice.ui.action.Action;

public class MenuItem {
    private final String title;
    private final Action action;
    private Menu nextMenu;

    public MenuItem(String title, Action action, Menu nextMenu) {
        this.title = title;
        this.action = action;
        this.nextMenu = nextMenu;
    }

    public Menu getNextMenu() {
        return nextMenu;
    }

    public void doAction() {
        this.action.execute();
    }

    @Override
    public String toString() {
        return title;
    }

}