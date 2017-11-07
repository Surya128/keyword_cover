package keywordcover;
import java.util.ArrayList;
public class JsonParseTest {
public static void main(String[] args)throws Exception {
	ArrayList<MinDistance> min = new ArrayList<MinDistance>();
	MinDistance m = new MinDistance();
	m.setDistance(40);
	m.setScore(30);
	m.setRating(8);
	min.add(m);

	m = new MinDistance();
	m.setDistance(50);
	m.setScore(25);
	m.setRating(6);
	min.add(m);

	m = new MinDistance();
	m.setDistance(10);
	m.setScore(60);
	m.setRating(2);
	min.add(m);

	java.util.Collections.sort(min,new MinDistance());
	for(int i=min.size()-1;i>=0;i--){
		MinDistance md = min.get(i);
		System.out.println(md.getScore()+" "+md.getRating()+" "+md.getDistance());
	}

}
  
}
