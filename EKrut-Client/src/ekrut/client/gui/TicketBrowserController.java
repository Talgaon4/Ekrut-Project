package ekrut.client.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import ekrut.client.EKrutClient;
import ekrut.client.EKrutClientUI;
import ekrut.client.managers.ClientTicketManager;
import ekrut.entity.Ticket;
import ekrut.entity.UserType;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class TicketBrowserController implements Initializable {
	
	private EKrutClient client;
	private ClientTicketManager clientTicketManager;

    @FXML
    private Button createTicketBtn;

    @FXML
    private VBox ticketsContainerVbox;

    @Override
	public void initialize(URL location, ResourceBundle resources) {
    	client = EKrutClientUI.getEkrutClient();
    	clientTicketManager = client.getClientTicketManager();
    	try {
    		String userArea = client.getClientSessionManager().getUser().getArea();
			ArrayList<Ticket> ticketsToShow = clientTicketManager.fetchTicketsByArea(userArea); // TBD OFEK: BUG WHEN WRONG AREA
			ObservableList<Node> ticketsContainerVboxChildren = ticketsContainerVbox.getChildren();
			for (Ticket ticket : ticketsToShow) {
				ticketsContainerVboxChildren.add(new TicketViewController(ticket));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	UserType usertype = client.getClientSessionManager().getUser().getUserType();
    	if (usertype != UserType.AREA_MANAGER)
    		createTicketBtn.setVisible(false);
	}
    
    
    
    @FXML
    void createTicket(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/ekrut/client/gui/TicketSubmission.fxml"));
		try {
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		Parent root = loader.getRoot();
		//TicketSubmissionController ticketSubmissionController = loader.getController();
		BaseTemplateController.getBaseTemplateController().setRightWindow(root);
	    }

}