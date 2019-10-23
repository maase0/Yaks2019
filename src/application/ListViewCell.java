package application;

import javafx.scene.control.ListCell;

public class ListViewCell extends ListCell<String>
{
    @Override
    public void updateItem(String string, boolean empty)
    {
        super.updateItem(string,empty);
        if(string != null)
        {
            CLIN data = new CLIN();
            data.setInfo(string);
            setGraphic(data.getBox());
        }
    }
}
