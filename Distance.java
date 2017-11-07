package keywordcover;
import java.util.Comparator;
public class Distance implements Comparator<Distance>
{
	String id;
	double distance;
	long rating;
public void setID(String id){
	this.id = id;
}
public String getID(){
	return id;
}
public void setDistance(double distance){
	this.distance=distance;
}
public double getDistance(){
	return distance;
}
public void setRating(long rating){
	this.rating = rating;
}
public long getRating(){
	return rating;
}
public int compare(Distance p1,Distance p2){
	double s1 = p1.getDistance();
    double s2 = p2.getDistance();
	if (s1 == s2)
		return 0;
    else if (s1 > s2)
    	return 1;
    else
		return -1;
}
}