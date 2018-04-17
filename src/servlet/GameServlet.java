package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.AbstractDocument.Content;

import controller.GameController;
import fakeDB.FakeAreaDB;
import gamePersist.DatabaseProvider;
import gamePersist.DerbyDatabase;
import gamePersist.IDatabase;
import model.Game;


public class GameServlet extends HttpServlet {
	public int level = 1;
	private static final long serialVersionUID = 1L;
	public boolean post = false;
	public ArrayList<String> content = new ArrayList<String>();
	public ArrayList<String> select = new ArrayList<String>();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		post = false;
		System.out.println("Game Servlet: doGet");
		DatabaseProvider.setInstance(new DerbyDatabase()); // some of this code taken from lab 06 and library example ---- CITING
		IDatabase db = DatabaseProvider.getInstance();
		//FakeAreaDB db2 = new FakeAreaDB();
		
		try {
			content = db.getArea(Integer.toString(level));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String para = content.get(2);
		req.setAttribute("para",para);
		
		//Sets the choices
		for(int i = 3; i < 9; i++){
			
			req.setAttribute("q" + (i - 2), (i - 2) + ": " + content.get(i));
		}

		
		req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		DatabaseProvider.setInstance(new DerbyDatabase()); // some of this code taken from lab 06 and library example ---- CITING
		IDatabase db = DatabaseProvider.getInstance();
		
		Game model = new Game();
		GameController controller = new GameController();
		
		post = true;
		String choice = null;
		System.out.println("Game Servlet: doPost");
		choice = req.getParameter("choice");
		System.out.println("choice= " + choice);
		System.out.println("db loaction = " + db.getPlayerLocation());
		
		
		try {
			String nextAreaNumber = select.get(Integer.parseInt(choice));
			level = Integer.parseInt(nextAreaNumber);
			select = db.getNextArea(Integer.toString(level)); //gets second line of csv
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String para = content.get(2); //gets second section (paragraph section) of the line in csv
		req.setAttribute("para",para);
		
		//Sets the choices
		for(int i = 3; i < 9; i++){
			
			req.setAttribute("q" + (i - 2), (i - 2) + ": " + content.get(i));
			
		}
		db.insertPlayerLocation("dresser1"); // change the area
			
		
		/*
		if (db.getPlayerLocation().equals("bedroom")) {
			System.out.println(db.getPlayerLocation());
			System.out.println(" if equals bedroom " + db.getPlayerLocation());  // check the first dresser
			if (choice.equals("1")) { //parameter from post form 
				try {
					content = db.getArea(Integer.toString(2)); //gets second line of csv
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2]; //gets second section (paragraph section) of the line in csv
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
					
				}
				db.insertPlayerLocation("dresser1"); // change the area
				
			}
			else if (choice.equals("2")) { // check the second dresser
				try {
					content = db.getArea(Integer.toString(3));///NOTE: does not correlate direclty to choice value
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2];
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
				}
				db.insertPlayerLocation("dresser2"); // change the area
				System.out.println("choice was 1" + db.getPlayerLocation());
			}
			else if (choice.equals("3")) {  //check the bed
				try {
					content = db.getArea(Integer.toString(4));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2];
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
				}
				db.insertPlayerLocation("checkBed"); // change the area
				System.out.println("choice was 3 " + db.getPlayerLocation());
			}
			else if (choice.equals("4")) { // make the bed
				try {
					content = db.getArea(Integer.toString(5));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2];
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
				}
				db.insertPlayerLocation("makeBed"); // change the area
				System.out.println("choice was 4 " + db.getPlayerLocation());
			}
			else if (choice.equals("5")) {  // turn the tv off
				try {
					content = db.getArea(Integer.toString(6));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2];
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
				}
				db.insertPlayerLocation("tv"); // change the area
				System.out.println("choice was 5 " + db.getPlayerLocation());
			}
			else if (choice.equals("6")) { //leave the room 
				try {
					content = db.getArea(Integer.toString(7));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2];
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
				}
				db.insertPlayerLocation("bathroom"); // change the area
				System.out.println("choice was 6 " + db.getPlayerLocation());
			}
			else {// nothing or wrong choice
				try {
					content = db.getArea(Integer.toString(1));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2];
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
				}
				
			}	
		}
////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////BEDROOM CHOICES///////
////////////////////////////////////////////////////////////////////////////////////////		
		
		else if(db.getPlayerLocation().equals("dresser1")) {
			System.out.println(" in dressser" + db.getPlayerLocation());
			if (choice.equals("1")) { //parameter from post form 
				try {
					content = db.getArea(Integer.toString(8)); 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2]; //gets second section (paragraph section) of the line in csv
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
					
				}
				db.insertPlayerLocation("bedroom");// change the area
				System.out.println(" end of dressser" + db.getPlayerLocation());
			}
			else {// no choice or wrong choice
				try {
					content = db.getArea(Integer.toString(2)); 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2]; //gets second section (paragraph section) of the line in csv
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
					
				}
			}
		}
		else if(db.getPlayerLocation().equals("dresser2")) {
			System.out.println(" in dressser2" + db.getPlayerLocation());
			if (choice.equals("1")) { //parameter from post form 
				try {
					content = db.getArea(Integer.toString(3)); 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2]; //gets second section (paragraph section) of the line in csv
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
					
				}
				controller.addItem(model.getLighter());
				db.insertPlayerLocation("dresser2");// change the area
				System.out.println(" end of dressser2" + db.getPlayerLocation());
			}
			else if (choice.equals("2")) { //parameter from post form 
				try {
					content = db.getArea(Integer.toString(8)); // redirects to next page
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2]; //gets second section (paragraph section) of the line in csv
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
					
				}
				db.insertPlayerLocation("bedroom");// change the area
				System.out.println(" end of dressser2" + db.getPlayerLocation());
			}
			else {// no choice or wrong choice
				try {
					content = db.getArea(Integer.toString(3)); 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2]; //gets second section (paragraph section) of the line in csv
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
					
				}
			}
		}	
		else if(db.getPlayerLocation().equals("checkBed")) {
			System.out.println(" check bed" + db.getPlayerLocation());
			if (choice.equals("1")) { //parameter from post form 
				try {
					content = db.getArea(Integer.toString(8)); 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				String para = content[2]; //gets second section (paragraph section) of the line in csv
				req.setAttribute("para",para);
					
				//Sets the choices
				for(int i = 3; i < 9; i++){
						
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
						
				}
				db.insertPlayerLocation("bedroom");// change the area
				System.out.println(" end of check bed " + db.getPlayerLocation());
			}
			else {// no choice or wrong choice
				try {
					content = db.getArea(Integer.toString(4)); 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				String para = content[2]; //gets second section (paragraph section) of the line in csv
				req.setAttribute("para",para);
					
				//Sets the choices
				for(int i = 3; i < 9; i++){
						
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
						
				}
			}
		}
		else if(db.getPlayerLocation().equals("makeBed")) {
			System.out.println(" make bed" + db.getPlayerLocation());
			if (choice.equals("1")) { //parameter from post form 
				try {
					content = db.getArea(Integer.toString(8)); 
				} catch (SQLException e) {
					// TODO Auto-generated catch block		
					e.printStackTrace();
					}
		
				String para = content[2]; //gets second section (paragraph section) of the line in csv
				req.setAttribute("para",para);
					
				//Sets the choices
				for(int i = 3; i < 9; i++){
						
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
						
				}
				db.insertPlayerLocation("bedroom");// change the area
				System.out.println(" end of make bed " + db.getPlayerLocation());
			}
			else {// no choice or wrong choice
				try {
					content = db.getArea(Integer.toString(5)); 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				String para = content[2]; //gets second section (paragraph section) of the line in csv
				req.setAttribute("para",para);
					
				//Sets the choices
				for(int i = 3; i < 9; i++){
						
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
						
				}
			}
		}
		else if(db.getPlayerLocation().equals("tv")) {
			System.out.println("tv " + db.getPlayerLocation());
			if (choice.equals("1")) { //parameter from post form 
				try {
					content = db.getArea(Integer.toString(8)); 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				String para = content[2]; //gets second section (paragraph section) of the line in csv
				req.setAttribute("para",para);
					
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
						
				}
				db.insertPlayerLocation("bedroom");// change the area
				System.out.println(" end of tv " + db.getPlayerLocation());
			}		
		}
//////////////////////////////BATHROOM////////////////////////////////////////////////
		
		else if(db.getPlayerLocation().equals("bathroom")) {
			System.out.println("bathroom " + db.getPlayerLocation());
			if (choice.equals("1")) { //goes to medicine cabinet 
				try {
					content = db.getArea(Integer.toString(9)); 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				String para = content[2]; //gets second section (paragraph section) of the line in csv
				req.setAttribute("para",para);
					
				//Sets the choices
				for(int i = 3; i < 9; i++){
						
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
						
				}
				db.insertPlayerLocation("medicine");// change the area
				System.out.println(" end of medicine " + db.getPlayerLocation());
			}
			else if (choice.equals("2")) { //goes to sink 
				try {
					content = db.getArea(Integer.toString(10)); // redirects to next page
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2]; //gets second section (paragraph section) of the line in csv
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
					
				}
				db.insertPlayerLocation("sink");// change the area
				System.out.println(" end of sink" + db.getPlayerLocation());
			}
			else if (choice.equals("3")) { //goes to shower
				try {
					content = db.getArea(Integer.toString(11)); // redirects to next page
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2]; //gets second section (paragraph section) of the line in csv
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
					
				}
				db.insertPlayerLocation("shower");// change the area
				System.out.println(" end of shower" + db.getPlayerLocation());
			}
			else if (choice.equals("4")) { //goes to light cover 
				try {
					content = db.getArea(Integer.toString(12)); // redirects to next page
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2]; //gets second section (paragraph section) of the line in csv
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
					
				}
				db.insertPlayerLocation("lightcover");// change the area
				System.out.println(" end of light cover " + db.getPlayerLocation());
			}
			else if (choice.equals("5")) { //goes to guest room
				try {
					content = db.getArea(Integer.toString(13)); // redirects to next page
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2]; //gets second section (paragraph section) of the line in csv
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
					
				}
				db.insertPlayerLocation("guestroom");// change the area
				System.out.println(" guest room  " + db.getPlayerLocation());
			}
			else if (choice.equals("6")) { //goes to bedroom2 
				try {
					content = db.getArea(Integer.toString(8)); // redirects to next page
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2]; //gets second section (paragraph section) of the line in csv
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
					
				}
				db.insertPlayerLocation("bedroom");// change the area
				System.out.println(" end of bathroom " + db.getPlayerLocation());
			}
			else {// nothing or wrong choice 
				try {
					content = db.getArea(Integer.toString(7)); // stays in bathroom
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2];
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
				}
				
			}	
		}
/////////////////BATHROOM CHOICES////////////////////////////////////////////////
		
		else if(db.getPlayerLocation().equals("medicine")) {
			System.out.println("medicine choice " + db.getPlayerLocation());
			if (choice.equals("1")) { //stays at bathroom and adds the band aids 
				try {
					content = db.getArea(Integer.toString(9)); 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				String para = content[2]; //gets second section (paragraph section) of the line in csv
				req.setAttribute("para",para);
					
				//Sets the choices
				for(int i = 3; i < 9; i++){
						
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
						
				}
				controller.addItem(model.getBandAids());
				
				db.insertPlayerLocation("medicine");// change the area
				System.out.println(" end of medicine " + db.getPlayerLocation());
			}
			else if (choice.equals("2")) { //goes backto bathroom
				try {
					content = db.getArea(Integer.toString(7)); 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				String para = content[2]; //gets second section (paragraph section) of the line in csv
				req.setAttribute("para",para);
					
				//Sets the choices
				for(int i = 3; i < 9; i++){
						
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
						
				}
				controller.addItem(model.getBandAids());
				
				db.insertPlayerLocation("bathroom");// change the area
				System.out.println(" end of bathroom " + db.getPlayerLocation());
			}
			else {// nothing or wrong choice
				try {
					content = db.getArea(Integer.toString(9)); // stays in medicine
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2];
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
				}
			}	
		}
		else if(db.getPlayerLocation().equals("sink")) {
			System.out.println("sink choice " + db.getPlayerLocation());
			if (choice.equals("1")) { //
				try {
					content = db.getArea(Integer.toString(10)); 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				String para = content[2]; //gets second section (paragraph section) of the line in csv
				req.setAttribute("para",para);
					
				//Sets the choices
				for(int i = 3; i < 9; i++){
						
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
						
				}
				controller.addItem(model.getToiletPaper());
				
				db.insertPlayerLocation("sink");// change the area
				System.out.println(" end of sink " + db.getPlayerLocation());
			}
			else if (choice.equals("2")) { //goes backto bathroom
				try {
					content = db.getArea(Integer.toString(7)); 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				String para = content[2]; //gets second section (paragraph section) of the line in csv
				req.setAttribute("para",para);
					
				//Sets the choices
				for(int i = 3; i < 9; i++){
						
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
						
				}
				controller.addItem(model.getBandAids());
				
				db.insertPlayerLocation("bathroom");// change the area
				System.out.println(" end of bathroom " + db.getPlayerLocation());
			}
			else {// nothing or wrong choice
				try {
					content = db.getArea(Integer.toString(10)); // stays in sink
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2];
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
				}
			}	
		}
		else if(db.getPlayerLocation().equals("shower")) {
			System.out.println("shower choice " + db.getPlayerLocation());
			if (choice.equals("1")) { //
				try {
					content = db.getArea(Integer.toString(11)); 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				String para = content[2]; //gets second section (paragraph section) of the line in csv
				req.setAttribute("para",para);
					
				//Sets the choices
				for(int i = 3; i < 9; i++){
						
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
						
				}
				controller.addItem(model.getShampoo());
				
				db.insertPlayerLocation("shower");// change the area
				System.out.println(" end of shower " + db.getPlayerLocation());
			}
			else if (choice.equals("2")) { //goes backto bathroom
				try {
					content = db.getArea(Integer.toString(7)); 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				String para = content[2]; //gets second section (paragraph section) of the line in csv
				req.setAttribute("para",para);
					
				//Sets the choices
				for(int i = 3; i < 9; i++){
						
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
						
				}
				controller.addItem(model.getBandAids());
				
				db.insertPlayerLocation("bathroom");// change the area
				System.out.println(" end of bathroom " + db.getPlayerLocation());
			}
			else {// nothing or wrong choice
				try {
					content = db.getArea(Integer.toString(11)); // stays in shower
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2];
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
				}
			}	
			
		}
		else if(db.getPlayerLocation().equals("lightcover")) {
			System.out.println("light  choice " + db.getPlayerLocation());
			if (choice.equals("1")) { //
				try {
					content = db.getArea(Integer.toString(7)); 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				String para = content[2]; //gets second section (paragraph section) of the line in csv
				req.setAttribute("para",para);
					
				//Sets the choices
				for(int i = 3; i < 9; i++){
						
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
						
				}
				
				
				db.insertPlayerLocation("bathroom");// change the area
				System.out.println(" end of light " + db.getPlayerLocation());
			}
			else {// nothing or wrong choice
				try {
					content = db.getArea(Integer.toString(12)); // stays in shower
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2];
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
				}
			}	
		}
		
//////////////////////////////////////////////////////////////////////////////////
///////////////////////////Guest ROOM/////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////
		
		
		if (db.getPlayerLocation().equals("guestroom")) {
			System.out.println(db.getPlayerLocation());
			System.out.println(" if equals guestroom " + db.getPlayerLocation());  // check the first dresser
			if (choice.equals("1")) { //check the bed2 
				try {
					content = db.getArea(Integer.toString(14)); //gets second line of csv
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2]; //gets second section (paragraph section) of the line in csv
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
					
				}
				db.insertPlayerLocation("bed2"); // change the area
				
			}
			else if (choice.equals("2")) { // check the nightstand
				try {
					content = db.getArea(Integer.toString(15));///NOTE: does not correlate direclty to choice value
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2];
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
				}
				db.insertPlayerLocation("nightstand"); // change the area
				System.out.println("choice was 1" + db.getPlayerLocation());
			}
			else if (choice.equals("3")) {  //look out the window
				try {
					content = db.getArea(Integer.toString(16));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2];
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
				}
				db.insertPlayerLocation("window"); // change the area
				System.out.println("choice was 3 " + db.getPlayerLocation());
			}
			else if (choice.equals("4")) { // go downstairs
				try {
					content = db.getArea(Integer.toString(17));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2];
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
				}
				db.insertPlayerLocation("livingroom"); // change the area
				System.out.println("choice was 4 " + db.getPlayerLocation());
			}
			else if (choice.equals("5")) {  // back to the bathroom
				try {
					content = db.getArea(Integer.toString(13));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2];
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
				}
				db.insertPlayerLocation("bathroom"); // change the area
				System.out.println("choice was 5 " + db.getPlayerLocation());
			}
			else if (choice.equals("6")) { //back to bedroom
				try {
					content = db.getArea(Integer.toString(8));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2];
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
				}
				db.insertPlayerLocation("bedroom"); // change the area
				System.out.println("choice was 6 " + db.getPlayerLocation());
			}
			else {// nothing or wrong choice
				try {
					content = db.getArea(Integer.toString(1));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2];
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
				}	
			}
		}	
	
////////////////////////GUEST ROOM CHOICES/////////////////////////////////////////
		
		else if(db.getPlayerLocation().equals("bed2")) {
			System.out.println("bed choice " + db.getPlayerLocation());
			if (choice.equals("1")) { //back to guest room 
				try {
					content = db.getArea(Integer.toString(13)); 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				String para = content[2]; //gets second section (paragraph section) of the line in csv
				req.setAttribute("para",para);
					
				//Sets the choices
				for(int i = 3; i < 9; i++){
						
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
						
				}
				
				
				db.insertPlayerLocation("guestroom");// change the area
				System.out.println(" end of medicine " + db.getPlayerLocation());
			}
			
			else {// nothing or wrong choice
				try {
					content = db.getArea(Integer.toString(14)); // stays in bed2
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2];
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
				}
			}	
		}
		else if(db.getPlayerLocation().equals("nightstand")) {
			System.out.println("nightstand choice " + db.getPlayerLocation());
			if (choice.equals("1")) { //
				try {
					content = db.getArea(Integer.toString(13)); //go back to guestroom 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				String para = content[2]; //gets second section (paragraph section) of the line in csv
				req.setAttribute("para",para);
					
				//Sets the choices
				for(int i = 3; i < 9; i++){
						
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
						
				}
				
				
				db.insertPlayerLocation("guestroom");// change the area
				System.out.println(" end of guestroom " + db.getPlayerLocation());
			}
			else {// nothing or wrong choice
				try {
					content = db.getArea(Integer.toString(15)); // stays in nightstand
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2];
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
				}
			}	
		}
		else if(db.getPlayerLocation().equals("window")) {
			System.out.println("window choice " + db.getPlayerLocation());
			if (choice.equals("1")) { //
				try {
					content = db.getArea(Integer.toString(13)); //back to guestroom
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
				String para = content[2]; //gets second section (paragraph section) of the line in csv
				req.setAttribute("para",para);
					
				//Sets the choices
				for(int i = 3; i < 9; i++){
						
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
						
				}
				
				
				db.insertPlayerLocation("guestroom");// change the area
				System.out.println(" end of guestroom " + db.getPlayerLocation());
			}
			else {// nothing or wrong choice
				try {
					content = db.getArea(Integer.toString(16)); // stays in shower
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				String para = content[2];
				req.setAttribute("para",para);
				
				//Sets the choices
				for(int i = 3; i < 9; i++){
					
					req.setAttribute("q" + (i - 2), (i - 2) + ": " + content[i]);
				}
			}	
			
		}
////////////////////////////////////////////////////////////////////////////////		
////////////////////////////////LIVING ROOM///////////////////////////////////////		
//////////////////////////////////////////////////////////////////////////////		
		
		if(db.getPlayerLocation().equals("livingroom")) { //take change in couch (two sizes) take *batteries* and remote
			
		
		
		
		
		
		
		
		
		}*/
		
		
		
		
		
			
		
		req.getRequestDispatcher("/_view/game.jsp").forward(req, resp);
	}
}