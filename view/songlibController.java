//Haseeb Hasan and Fawaz Tahir
package songlib.view;

import java.awt.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
		
	public void start(Stage mainStage)
	{		
		//Reading
		try 
		{
	      File myObj = new File("data.txt");
	      Scanner myReader = new Scanner(myObj);
	      while (myReader.hasNextLine()) 
	      {
	        String data = myReader.nextLine();
	        //Re-Populate the ObservableList
	        String[] fourFields = new String[4];
	        
	        String[] fields = data.split("\\|");
	        fourFields[0] = fields[0];
	        fourFields[1] = fields[1];
	        if (fields.length == 2)
	        {
	        	fourFields[2] = "";
	        	fourFields[3] = "";
	        }
	        else if (fields.length == 3)
	        {
	        	fourFields[2] = fields[2];
	        	fourFields[3] = "";
	        }
	        else
	        	fourFields = fields;
			addSorted(new Song(fourFields[0],fourFields[1],fourFields[2],fourFields[3]));
	      }
	      myReader.close();
	    }
		catch (FileNotFoundException e) 
		{
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
		
		songList.setItems(list);

		//select the first item
		if (songList != null)
			songList.getSelectionModel().select(0);
		showDetails();
		//set listener for the items
		songList
			.getSelectionModel()
			.selectedIndexProperty()
			.addListener(
				(obs, oldval, newval) ->
					showDetails());//mainStage));
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		    public void run() {
		    	//Clearing Data on File
		    	FileWriter fwOb;
				try {
					fwOb = new FileWriter("data.txt", false);
					PrintWriter pwOb = new PrintWriter(fwOb, false);
			        pwOb.flush();
			        pwOb.close();
			        fwOb.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
		        
		    	//Writing
			    try(FileWriter fw = new FileWriter("data.txt", true);
		    	    BufferedWriter bw = new BufferedWriter(fw);
		    	    PrintWriter out = new PrintWriter(bw))
		    	{
			    	int index = 0;
			    	while (index < list.size())
			    	{
			    		//for each object, print all it's attributes followed by a "|"
			    		Song obj = list.get(index);
			    		out.println(obj.getName() + "|" + obj.getArtist() + "|" +obj.getYear() + "|"
			    				+obj.getAlbum() + "|");
			    		index++;
			    	}
		    	} 
			    catch (IOException e) {
		    	    //exception handling left as an exercise for the reader
			    	System.out.println("An error occurred.");
				      e.printStackTrace();
		    	}
		    }
		}));
	}
	
	public void showDetails()//Stage mainStage) 
	{
		String content;
		if (list.isEmpty())
		{
			content = "";
		}
		else
		{
			content = "Name: "+ songList.getSelectionModel().getSelectedItem().getName()
					+"\nArtist: " + songList.getSelectionModel().getSelectedItem().getArtist() +
					"\nYear: " + songList.getSelectionModel().getSelectedItem().getYear() +
					"\nAlbum: "+ songList.getSelectionModel().getSelectedItem().getAlbum() +"\n";
		}
		
		songDetails.setText(content);
	}

	public void addSong(Event e) 
	{
		Alert confirm = new Alert(AlertType.CONFIRMATION);
		confirm.setTitle("Confirmation Message");
		confirm.setHeaderText("Click OK to complete your changes");
		//Input Check
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Add Song Error Message");
		alert.setHeaderText("Error");
		if (errorFound(list, songNameField.getText().trim(), songArtistField.getText().trim())) {
			alert.setContentText("Song already exists");
			alert.showAndWait();
			return;
		}
		else if (songNameField.getText().trim().isEmpty() && songArtistField.getText().trim().isEmpty()) 
		{
			alert.setContentText("Please enter a song name and artist");
			alert.showAndWait();
		} 
		else if (songNameField.getText().trim().isEmpty()) 
		{
			alert.setContentText("Please enter a song name");
			alert.showAndWait();
		} 
		else if (songArtistField.getText().trim().isEmpty()) 
		{
			alert.setContentText("Please enter the song artist");
			alert.showAndWait();
		} 
		else if ((!songYearField.getText().isEmpty()) && (!isNumeric(songYearField.getText()) || !isWholeNumber(songYearField.getText()) || Integer.parseInt(songYearField.getText()) < 0))
		{
			alert.setContentText("Please enter a positive integer for year");
			alert.showAndWait();
		} else {
		
			Optional<ButtonType> result = confirm.showAndWait();
			if (result.get() == ButtonType.OK){
				
				Song entry = new Song(songNameField.getText(),songArtistField.getText(),songYearField.getText(), songAlbumField.getText());
				addSorted(entry);
				songList.setItems(list);
				songList.getSelectionModel().select(entry);
				// ^ Doing this already calls showDetails??
				//showDetails();
			}
		}
		songNameField.clear();
		songArtistField.clear();
		songYearField.clear();
		songAlbumField.clear();
	}
	
	public static boolean isWholeNumber(String str) 
	{
		double value = Double.parseDouble(str);
		if (value % 1 == 0)
		    return true;
		return false;
    }
	
	public static boolean isNumeric(String str) 
	{
        return str != null && str.matches("[-+]?\\d*\\.?\\d+");
    }
	
	public boolean errorFound(ObservableList<Song> list, String songName, String songArtist) {
		ArrayList<String> namesList = new ArrayList<String>();
		ArrayList<String> artistList = new ArrayList<String>();
		
		for (Song song : list) {
			namesList.add(song.getName().toLowerCase());
			artistList.add(song.getArtist().toLowerCase());
		}
		if (namesList.contains(songName.toLowerCase()) && artistList.contains(songArtist.toLowerCase())) {
			return true;
		}
		return false;
	}
	
	public void editSong(Event e) 
	{
		Alert confirm = new Alert(AlertType.CONFIRMATION);
		confirm.setTitle("Confirmation Message");
		confirm.setHeaderText("Click OK to complete your changes");
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Edit Song Error Message");
		alert.setHeaderText("Error");
		
		
		if (list.isEmpty())
		{
			alert.setContentText("Enter a song before editing");
			alert.showAndWait();
		}
		else if (errorFound(list, editsongNameField.getText().trim(), editsongArtistField.getText().trim())) 
		{
			alert.setContentText("Song already exists");
			alert.showAndWait();
		} 
		else if (editsongNameField.getText().trim().isEmpty() && editsongArtistField.getText().trim().isEmpty() && editsongYearField.getText().trim().isEmpty() && editsongAlbumField.getText().trim().isEmpty()) {
			System.out.print("");
		}
		else if ((!editsongYearField.getText().isEmpty()) && (!isNumeric(editsongYearField.getText()) || !isWholeNumber(editsongYearField.getText()) || Integer.parseInt(editsongYearField.getText()) < 0))
		{
			alert.setContentText("Please enter a positive integer for year");
			alert.showAndWait();
		}
		else {
			Optional<ButtonType> result = confirm.showAndWait();
			if (result.get() == ButtonType.OK)
			{
				if (!editsongNameField.getText().trim().isEmpty()) {
					songList.getSelectionModel().getSelectedItem().setName(editsongNameField.getText());
				}
				if (!editsongArtistField.getText().trim().isEmpty()) {
					songList.getSelectionModel().getSelectedItem().setArtist(editsongArtistField.getText());
				}
				if (!editsongYearField.getText().trim().isEmpty()) {
					songList.getSelectionModel().getSelectedItem().setYear(editsongYearField.getText());
				}
				if (!editsongAlbumField.getText().trim().isEmpty()) {
					songList.getSelectionModel().getSelectedItem().setAlbum(editsongAlbumField.getText());
				}
				Song entry = songList.getSelectionModel().getSelectedItem();
				int index = songList.getSelectionModel().getSelectedIndex();
				list.remove(index);
				addSorted(entry);
				songList.setItems(list);
				songList.getSelectionModel().select(entry);
			}
		}
		
		
			
		editsongNameField.clear();
		editsongArtistField.clear();
		editsongYearField.clear();
		editsongAlbumField.clear();
			
		
	}
	
	public void deleteSong (Event e) 
	{
		Alert confirm = new Alert(AlertType.CONFIRMATION);
		confirm.setTitle("Confirmation Message");
		confirm.setHeaderText("Click OK to complete your changes");
		
		Alert emptyListAlert = new Alert(AlertType.ERROR);
		emptyListAlert.setTitle("Delete Song Error Message");
		emptyListAlert.setHeaderText("500 Error");
		
		if (list.isEmpty())
		{
			emptyListAlert.setContentText("Enter a song before deleting");
			emptyListAlert.showAndWait();
		}
		else
		{
			Optional<ButtonType> result = confirm.showAndWait();
			if (result.get() == ButtonType.OK)
			{
				int index = songList.getSelectionModel().getSelectedIndex();
				list.remove(index);
				songList.setItems(list);
				//Select the next item
				if (!list.isEmpty())
				{
					songList.getSelectionModel().select(index);
					//showDetails();
				}
				else //empty list
				{
					showDetails();
				}
			}
		}
		
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
	    {
	    	i++;
	    }
	    if (i!=list.size()) 
	    {
	    	while (i < list.size() && list.get(i).getNameLower().compareTo(item.getNameLower()) == 0 && list.get(i).getArtistLower().compareTo(item.getArtistLower()) < 0) //compare artist names
		    {
		    	i++;
		    }
	    	
    	}
	    list.add(i, item);
	}

}
