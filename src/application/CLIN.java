package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import java.io.IOException;

public class CLIN
{
    @FXML
    private HBox hBox;
    @FXML
    private Label label1;
    @FXML
    private Label label2;

    public CLIN()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CLIN.fxml"));
        fxmlLoader.setController(this);
        try
        {
            fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(String string)
    {
        
    }

    public HBox getBox()
    {
        return hBox;
    }
}