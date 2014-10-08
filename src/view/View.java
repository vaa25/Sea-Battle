package view;

import controller.Controller;

/**
 * @author Alexander Vlasov
 */
public interface View {
    public void show(String[] args);

    public void setController(Controller controller);
}
