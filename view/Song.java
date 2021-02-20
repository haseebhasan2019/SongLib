//Haseeb Hasan and Fawaz Tahir
package songlib.view;

public class Song {
	private String name;
	private String artist;
	private String year;
	private String album;
	
	public Song(String name, String artist, String year, String album) {
		super();
		this.setName(name);
		this.setArtist(artist);
		this.setYear(year);
		this.setAlbum(album);
	}
	
	public String getName() {
		return name;
	}
	
	public String getNameLower() {
		return name.toLowerCase();
	}
	
	public void setName(String name) {
		this.name = name.trim();
	}
	
	public String getArtist() {
		return artist;
	}
	
	public String getArtistLower() {
		return artist.toLowerCase();
	}
	
	public void setArtist(String artist) {
		this.artist = artist.trim();
	}
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		if (year == null) {
			this.year = "";
		} else {
			this.year = year;
		}
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		if (album == null) {
			this.album = "";
		}
		else
			this.album = album.trim();
	}
	
	@Override
	public String toString() {
		return this.getName() + " -" + this.getArtist();
	}
}
