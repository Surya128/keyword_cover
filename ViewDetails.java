package keywordcover;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import java.awt.Font;
public class ViewDetails extends JFrame{
	JScrollPane jsp;
	JTextArea area;
public ViewDetails(){
	super("View Search Result");
	area = new JTextArea();
	jsp = new JScrollPane(area);
	getContentPane().add(jsp);
	Font f1 = new Font("Courier New",Font.BOLD,13);
	area.setFont(f1);
	area.setEditable(false);
}
}