package keywordcover;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.awt.BorderLayout;
public class ViewSearchResult extends JFrame{
	JScrollPane jsp;
	DefaultTableModel dtm;
	JTable table;
	JPanel p1;
	JButton b1;
	HashMap<String,ArrayList<DB>> heap;
public ViewSearchResult(HashMap<String,ArrayList<DB>> heap){
	super("View Search Result");
	this.heap = heap;
	dtm = new DefaultTableModel(){
		public boolean isCellEditable(){
			return false;
		}
	};
	table = new JTable(dtm);
	table.setRowHeight(30);
	jsp = new JScrollPane(table);
	dtm.addColumn("Business ID");
	dtm.addColumn("Distance");
	getContentPane().add(jsp,BorderLayout.CENTER);
	Font f1 = new Font("Courier New",Font.BOLD,13);
	table.setFont(f1);
	p1 = new JPanel();
	b1 = new JButton("View Record");
	b1.setFont(f1);
	p1.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			viewRecord();
		}
	});
	getContentPane().add(p1,BorderLayout.SOUTH);
}
public void viewRecord(){
	ViewDetails vd = new ViewDetails();
	vd.setSize(600,500);
	int row = table.getSelectedRow();
	String id = dtm.getValueAt(row,0).toString().trim();
	boolean flag = false;
	for(Map.Entry<String,ArrayList<DB>> entry : heap.entrySet()){
		if(!flag){
		ArrayList<DB> list = entry.getValue();
		for(int i=0;i<list.size();i++){
			DB db = list.get(i);
			if(db.getID().equals(id)){
				vd.area.append(i+" "+"Business ID : "+db.getID()+"\n");
				vd.area.append("Address : "+db.getAddress()+"\n");
				vd.area.append("Categories : "+db.getCategories()+"\n");
				vd.area.append("Rating : "+db.getRating()+"\n");
				vd.area.append("Latitude : "+db.getLatitude()+"\n");
				vd.area.append("Longitude : "+db.getLongitude()+"\n");
				flag = true;
				break;
			}
		}
	}}
	vd.setVisible(true);
}
}