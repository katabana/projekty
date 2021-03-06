package konstytucja;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {
	
	private Konstytucja kon;
	private String filePath;
	private String message;
	
	
	public Parser(String[] args) {
		this.kon = new Konstytucja();
		this.filePath = args[0];
		this.message = "";
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public void setMessage(String msg){
		this.message = msg;
	}
	
	public Konstytucja getKon(){
		return this.kon;
	}
	
	//reads file, save as Strings Array and delete every occurence of two unnecessary lines
	public ArrayList<String> cleanFile() {
		
		BufferedReader reader = null;
		ArrayList<String> file = new ArrayList<String>();
		
		try{
			reader = new BufferedReader(new FileReader(this.filePath + "\\konstytucja.txt"));
			
			String line = null;
			while((line = reader.readLine()) != null ){
				if(line.startsWith("Rozdzia")) {
		       		file.add(line);
		       		break;
		        }
			}
			while((line = reader.readLine()) != null) {
					
				if (line.startsWith("©")) {
					reader.readLine();
					continue;
				}
				else
					file.add(line);
			}
			
		} catch (FileNotFoundException e) {
			this.setMessage(e.getMessage());
			System.out.println(this.getMessage());
			return null;
		} 
	    finally {
	    	 try{
				  if (reader != null) 
					  reader.close();
			  } catch (IOException e) {
				  System.out.println(e.getMessage());
			  }
	    	 
	    	 return file;
	    }
		
	    
	}
	
	public boolean parse() {
		
		ArrayList<String> file = new ArrayList<String>();
		file = this.cleanFile();
		String line = "";
		int artnumber = 0;
		boolean parsed = false;
		
		/*System.out.println("Enter file path: ");
		Scanner scanner = new Scanner(System.in);
		filePath = scanner.nextLine();
		range = scanner.nextLine();
		scanner.close(); */
			        
		if(file != null && !file.isEmpty()){
			int i = 0;
			parsed = true;
			while(i < file.size() && file.get(i)!= null) {
					line = file.get(i);
			    	Chapter chapter = null;
			    	Article article = null;
			    	
			    	if(line.startsWith("Rozdzia")) {
			    		i++;
			    		if(file.get(i) != null)
			    			line = file.get(i);
			    		
			    		chapter = new Chapter(this.kon.getChapters().size()+1,line);
			    		
				    	this.kon.getChapters().add(chapter);
				    	i++;
				    	continue;
			    	}
				    if(line.startsWith("Art")) {
				    	chapter = this.kon.getChapters().get(this.kon.getChapters().size()-1);
				    	if(chapter.getArticles() != null) {
					    	artnumber++;
					    	article = new Article(artnumber);
					    	i++;
					    	article = article.readArticle(file, i);
					    	chapter.getArticles().add(article);
				    	} 	
				    	continue;
				    }
				    i++;
			    
			    }
			    
		}
		
		return parsed; 
	} 
} 
