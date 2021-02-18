//Haseeb Hasan and Fawaz Tahir
package songlib.view;

import java.util.Collections;
import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
//import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class songlibController {
	@FXML Button addSongButton;
	@FXML Button editSongButton;
	@FXML Button deleteSongButton;
	@FXML Text songDetails;

	@FXML TextField songNameField;
	@FXML TextField songArtistField;
	@FXML TextField songYearField;
	@FXML TextField songAlbumField;
	
	@FXML TextField editsongNameField;
	@FXML TextField editsongArtistField;
	@FXML TextField editsongYearField;
	@FXML TextField editsongAlbumField;
	
	@FXML ListView<Song> songList;
	@FXML ObservableList<Song> list = FXCollections.observableArrayList();
	
//	public void start() {
//		list.add(new Song("Dynamite","Cruz"));
//		list.add(new Song("Lazy","Mars"));
//		songList.setItems(list);
//	}
	
	public void start(Stage mainStage)
	{
		//Read in data from file
//		list.add(new Song("Dynamite","Cruz",null,"Views"));
//		list.add(new Song("Lazy","Mars","2015",null));
//		list.add(new Song("Day","Pluto",null,null));
//		list.add(new Song("Dream","Saturn","2005",null));
//		list.add(new Song("Party","Juptier","2010","Planets"));
		
		addSorted(new Song("Dynamite","Cruz",null,"Views"));
		addSorted(new Song("Lazy","Mars","2015",null));
		addSorted(new Song("Day","Pluto",null,null));
		addSorted(new Song("Dream","Saturn","2005",null));
		addSorted(new Song("Party","Juptier","2010","Planets"));
		
		//alphabetize(list);
		songList.setItems(list);

		//select the first item
		songList.getSelectionModel().select(0);
		
		//set listener for the items
		songList
			.getSelectionModel()
			.selectedIndexProperty()
			.addListener(
				(obs, oldval, newval) ->
					showDetails());//mainStage));
	}
	
	public void showDetails()//Stage mainStage) 
	{
//		Alert alert = new Alert(AlertType.INFORMATION);
//		alert.initOwner(mainStage);
//		alert.setTitle("Song Info");
//		alert.setHeaderText("Song Details");
		String content = "Name: "+ songList.getSelectionModel().getSelectedItem().getName()
				+"\nArtist: " + songList.getSelectionModel().getSelectedItem().getArtist() +
				"\nYear: " + songList.getSelectionModel().getSelectedItem().getYear() +
				"\nAlbum: "+ songList.getSelectionModel().getSelectedItem().getAlbum() +"\n";
		
//		alert.setContentText(content);
//		alert.showAndWait();
		
		songDetails.setText(content);
	}

	public void addSong(Event e) 
	{
		//Input Check
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Song Error Message");
		alert.setHeaderText("Error 401");
		
		if (songNameField.getText().isEmpty() && songArtistField.getText().isEmpty()) {
			alert.setContentText("Please enter a song name and artist");
			alert.showAndWait();
		} else if (songNameField.getText().isEmpty()) {
			alert.setContentText("Please enter a song name");
			alert.showAndWait();
		} else if (songArtistField.getText().isEmpty()) {
			alert.setContentText("Please enter the song artist");
			alert.showAndWait();
		} else {
			Song entry = new Song(songNameField.getText(),songArtistField.getText(),songYearField.getText(), songAlbumField.getText());
			addSorted(entry);
			songList.setItems(list);
			songList.getSelectionModel().select(entry);
			// ^ Doing this already calls showDetails??
			//showDetails();
		}
	}
	
	public void editSong(Event e) 
	{
		if (!editsongNameField.getText().isEmpty()) {
			songList.getSelectionModel().getSelectedItem().setName(editsongNameField.getText());
		}
		if (!editsongArtistField.getText().isEmpty()) {
			songList.getSelectionModel().getSelectedItem().setArtist(editsongArtistField.getText());
		}
		if (!editsongYearField.getText().isEmpty()) {
			songList.getSelectionModel().getSelectedItem().setYear(editsongYearField.getText());
		}
		if (!editsongAlbumField.getText().isEmpty()) {
			songList.getSelectionModel().getSelectedItem().setName(editsongAlbumField.getText());
		}
		songList.setItems(list);
		songList.getSelectionModel();
		showDetails();
	}
	
	public void deleteSong (Event e) 
	{
		int index = songList.getSelectionModel().getSelectedIndex();
		list.remove(index);
		songList.setItems(list);
		//Select the next item
		songList.getSelectionModel().select(index);
		showDetails();
	}
	
	public void alphabetize(ObservableList<Song> list)
	{
		Comparator<Song> comparator = Comparator.comparing(Song::getNameLower); 
		FXCollections.sort(list, comparator);		
	}
	
	public void addSorted(Song item)
	{
	    int i = 0;
	    while (i < list.size() && list.get(i).getNameLower().compareTo(item.getNameLower()) < 0)
	        i++;
	    list.add(i, item);
	}

}
