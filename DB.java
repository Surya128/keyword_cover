package keywordcover;
public class DB{
	String id,address,categories;
	long rating;
	double latitude,longitude;
public void setID(String id){
	this.id = id;
}
public String getID(){
	return id;
}
public void setCategories(String categories){
	this.categories = categories;
}
public String getCategories(){
	return categories;
}
public void setAddress(String address){
	this.address = address;
}
public String getAddress(){
	return address;
}
public void setLatitude(double latitude){
	this.latitude = latitude;
}
public double getLatitude(){
	return latitude;
}
public void setLongitude(double longitude){
	this.longitude = longitude;
}
public double getLongitude(){
	return longitude;
}
public void setRating(long rating){
	this.rating = rating;
}
public long getRating(){
	return rating;
}
}