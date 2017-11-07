package keywordcover;
import java.util.Comparator;
import com.google.common.collect.ComparisonChain;
public class MinDistance implements Comparator<MinDistance>{
	String bid;
	double score,dis;
	long rating;
public void setID(String bid){
	this.bid = bid;
}
public String getID(){
	return bid;
}
public void setScore(double score){
	this.score = score;
}
public double getScore(){
	return score;
}
public void setDistance(double dis){
	this.dis = dis;
}
public double getDistance(){
	return dis;
}
public void setRating(long rating){
	this.rating = rating;
}
public long getRating(){
	return rating;
}
/*public int compare(MinDistance o1,MinDistance o2){
	int value = 0;
	if(new Double(o1.getScore()).compareTo(new Double(o2.getScore())) == 0){
		if(new Long(o1.getRating()).compareTo(new Long(o2.getRating())) == 0){
			value =  new Double(o1.getDistance()).compareTo(new Double(o2.getDistance()));
		}else{
			value = new Long(o1.getRating()).compareTo(new Long(o2.getRating()));
        }
		value = new Double(o1.getScore()).compareTo(new Double(o2.getScore()));
    }
	return value;
}*/
public int compare(MinDistance r1, MinDistance r2){
	return ComparisonChain.start()
        .compare(r1.getScore(), r2.getScore())
        .compare(r1.getDistance(), r2.getDistance())
        .compare(r1.getRating(), r2.getRating())
        .result();
  }
}