import controller.Controller;
import service.Service;
import view.UI;

public class Main {
    public static void main(String[] args) {
        Service service = new Service();
        Controller controller = new Controller(service);
        UI ui = new UI(controller);
        ui.run();
    }
}
