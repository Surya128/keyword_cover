package keywordcover;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UIManager;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JOptionPane;
import com.jd.swing.util.Theme;
import com.jd.swing.util.PanelType;
import com.jd.swing.custom.component.panel.StandardPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import java.io.File;
import javax.swing.JFileChooser;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.Cursor;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import org.jfree.ui.RefineryUtilities;
public class MainProgram extends JFrame{
	StandardPanel p1;
	JPanel p2,p3;
	JLabel title;
	JButton b1,b2,b3,b4,b5;
	Font f1;
	JTable table;
	JScrollPane jsp;
	DefaultTableModel dtm;
	JFileChooser chooser;
	File file;
	ArrayList<MinDistance> knn = new ArrayList<MinDistance>();
	ArrayList<String> attributes = new ArrayList<String>();
	HashMap<String,ArrayList<DB>> heap = new HashMap<String,ArrayList<DB>>();
public MainProgram(){
	super("Best Keyword Cover");
	JPanel panel = new JPanel();
	panel.setLayout(new BorderLayout());
	p1 = new StandardPanel(Theme.STANDARD_GREEN_THEME,PanelType.PANEL_ROUNDED);
	p1.setPreferredSize(new Dimension(200,50));
	f1 = new Font("Courier New",Font.BOLD,14);
	p2 = new TitlePanel(600,60);
	p2.setBackground(new Color(204, 110, 155));
	title = new JLabel("<HTML><BODY><CENTER>BEST KEYWORD COVER SEARCH</BODY></HTML>");
	title.setFont(new Font("Courier New",Font.BOLD,16));
	p2.add(title);
	panel.add(p1,BorderLayout.CENTER);
	panel.add(p2,BorderLayout.NORTH);
	
	chooser = new JFileChooser(new File("."));

	b1 = new JButton("Upload Dataset");
	b1.setFont(f1);
	p1.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			int option = chooser.showOpenDialog(MainProgram.this);
			if(option == chooser.APPROVE_OPTION){
				file = chooser.getSelectedFile();
				JOptionPane.showMessageDialog(MainProgram.this,"Dataset selected");
			}
		}
	});

	b2 = new JButton("Build Index");
	b2.setFont(f1);
	p1.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			attributes.clear();
			heap.clear();
			buildIndex();
			System.out.println(heap.size()+" "+attributes.size());
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		}
	});

	b3 = new JButton("Search Keyword(KNN)");
	b3.setFont(f1);
	p1.add(b3);
	b3.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			knn.clear();
			KNN();
		}
	});

	b4 = new JButton("Index Size Chart");
	b4.setFont(f1);
	p1.add(b4);
	b4.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Chart chart1 = new Chart("KNN & Baseline Index Size Chart",heap.size(),attributes.size());
			chart1.pack();
			RefineryUtilities.centerFrameOnScreen(chart1);
			chart1.setVisible(true);
		}
	});

	b5 = new JButton("Exit");
	b5.setFont(f1);
	p1.add(b5);
	b5.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			
		}
	});

	p3 = new JPanel();
	p3.setLayout(new BorderLayout());
	dtm = new DefaultTableModel(){
		public boolean isCellEditable(int r,int c){
			return false;
		}
	};
	table = new JTable(dtm);
	table.setFont(f1);
	table.getTableHeader().setFont(new Font("Courier New",Font.BOLD,15));
	table.setRowHeight(30);
	jsp = new JScrollPane(table);
	jsp.getViewport().setBackground(Color.white);
	dtm.addColumn("Business ID");
	dtm.addColumn("Categories");
	dtm.addColumn("Latitude");
	dtm.addColumn("Longitude");
	p3.add(jsp,BorderLayout.CENTER);
	getContentPane().add(panel,BorderLayout.NORTH);
	getContentPane().add(p3,BorderLayout.CENTER);
}
public static void main(String a[])throws Exception{
	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	MainProgram mp = new MainProgram();
	mp.setVisible(true);
	mp.setExtendedState(JFrame.MAXIMIZED_BOTH);
	mp.setLocationRelativeTo(null);
}
public void buildIndex(){
	try{
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line  = null;
		JSONParser jsonParser = new JSONParser();
		while((line = br.readLine())!=null){
			line = line.trim();
			if(line.length() > 0){
				JSONObject jsonObject = (JSONObject) jsonParser.parse(line);
				String bid = (String) jsonObject.get("business_id");
				String address = (String) jsonObject.get("full_address");
				JSONArray cat= (JSONArray) jsonObject.get("categories");
				StringBuilder sb = new StringBuilder();
				for(int i=0;i<cat.size();i++){
					sb.append(cat.get(i)+",");
				}
				if(sb.length() > 0)
					sb.deleteCharAt(sb.length()-1);
				long rating = ((Long)jsonObject.get("review_count")).longValue();
				double lon = ((Double)jsonObject.get("longitude")).doubleValue();
				double lat = ((Double)jsonObject.get("latitude")).doubleValue();
				JSONObject att= (JSONObject) jsonObject.get("attributes");
				Set set= att.keySet();
				Iterator itr = set.iterator();
				StringBuilder sb1 = new StringBuilder();
				while(itr.hasNext()){
					String key = (String) itr.next();
					String value = att.get(key).toString();
					sb1.append(key+" = "+value+"\n");
				}
				Object row[] = {bid,sb.toString(),lat,lon};
				dtm.addRow(row);
				String arr[] = sb.toString().split(",");
				for(int i=0;i<arr.length;i++){
					String candidate = arr[i].trim();
					if(candidate.length() > 0){
						attributes.add(candidate);
						if(heap.containsKey(candidate)){
							DB db = new DB();
							db.setID(bid);
							db.setAddress(address);
							db.setCategories(sb.toString());
							db.setRating(rating);
							db.setLatitude(lat);
							db.setLongitude(lon);
							heap.get(candidate).add(db);
						}else{
							DB db = new DB();
							db.setID(bid);
							db.setAddress(address);
							db.setCategories(sb.toString());
							db.setRating(rating);
							db.setLatitude(lat);
							db.setLongitude(lon);
							ArrayList<DB> list = new ArrayList<DB>();
							list.add(db);
							heap.put(candidate,list);
						}
					}
				}
			}
		}
		br.close();
	}catch(Exception e){
		e.printStackTrace();
	}
}
public void KNN(){
	String query = JOptionPane.showInputDialog(MainProgram.this,"Enter query");
	if(query != null){
		query = query.trim().toLowerCase();
		String qry[] = query.split(",");
		ArrayList list[] = new ArrayList[qry.length];
		for(int i=0;i<qry.length;i++){
			for(Map.Entry<String,ArrayList<DB>> entry : heap.entrySet()){
				String key = entry.getKey().toLowerCase().trim();
				double score = Score.getScore(key,qry[i]);
				if(score > 0.80){
					list[i] = entry.getValue();
				}
			}
		}
		for(int i=0;i<list.length;i++){
			ArrayList<DB> temp1 = list[i];
			int j = i + 1;
			if(j < list.length){
				System.out.println(i+" "+j);
				ArrayList<DB> temp2 = list[j];
				if(temp1 != null && temp2 != null){
					ArrayList<Distance> dist = new ArrayList<Distance>();
					for(int k=0;k<temp1.size();k++){
						if(k < temp2.size()){
							DB db1 = temp1.get(k);
							DB db2 = temp2.get(k);
							double dis = distance(db1.getLatitude(),db1.getLongitude(),db2.getLatitude(),db2.getLongitude(),'M');
							Distance ob = new Distance();
							ob.setID(db1.getID()+","+db2.getID());
							ob.setDistance(dis);
							ob.setRating(db1.getRating()+db2.getRating());
							dist.add(ob);
						}
					}
					java.util.Collections.sort(dist,new Distance());
					if(dist.size() > 0){
						Distance ob = dist.get(0);
						MinDistance md = new MinDistance();
						md.setID(ob.getID());
						md.setRating(ob.getRating());
						md.setScore(100);
						md.setDistance(ob.getDistance());
						knn.add(md);
					}
				}
			}
		}
	}
	java.util.Collections.sort(knn,new MinDistance());
	ArrayList<String> temp = new ArrayList<String>();
	ViewSearchResult vsr = new ViewSearchResult(heap);
	vsr.setSize(400,300);
	vsr.setVisible(true);
	for(int i=0;i<knn.size();i++){
		MinDistance md = knn.get(i);
		String arr[] = md.getID().split(",");
		for(int j=0;j<arr.length;j++){
			if(!temp.contains(arr[j])){
				temp.add(arr[j]);
				Object row[] = {arr[j],md.getDistance()};
				vsr.dtm.addRow(row);
			}
		}
	}
}
public double distance(double lat1, double lon1, double lat2, double lon2, char unit){
	double theta = lon1 - lon2;
	double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
	dist = Math.acos(dist);
	dist = rad2deg(dist);
	dist = dist * 60 * 1.1515;
	if (unit == 'K') {
		dist = dist * 1.609344;
	} else if (unit == 'N') {
		dist = dist * 0.8684;
	}
	return (dist);
}
public double deg2rad(double deg) {
	return (deg * Math.PI / 180.0);
}
public double rad2deg(double rad) {
	return (rad * 180.0 / Math.PI);
}
}