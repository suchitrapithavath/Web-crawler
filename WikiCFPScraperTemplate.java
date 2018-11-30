import java.net.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class WikiCFPScraperTemplate {
	public static int DELAY = 7;
	public static void main(String[] args) {
	
		try {
			
			
            String[] domain = {"data mining", "databases", "machine learning", "artificial intelligence"};

            int numOfPages = 20;
            for(int domains=0; domains<4; domains++) {
			String category = "data mining";
			String category = "databases";
			String category = "machine learning";
			String category = "artificial intelligence";
			
	    
	        
	        //create the output file
	        File file = new File(domain[domains]+".txt");
	        file.createNewFile();
	        FileWriter writer = new FileWriter(file); 
	       
	    
	        
	        //now start crawling the all 'numOfPages' pages
	        for(int i = 1;i<=numOfPages;i++) {
	        	//Create the initial request to read the first page 
				//and get the number of total results
	        	String linkToScrape = "http://www.wikicfp.com/cfp/call?conference="+
	        				      URLEncoder.encode(domain[domains], "UTF-8") +"&page=" + i;
	        	String content = getPageFromUrl(linkToScrape);
                        Document document = Jsoup.parse(content);
                        Elements elements = document.select("table[cellpadding=3]");
                        Elements rows = elements.get(0).select("tr");
                        String value ="";
                        String loc_arr[] =new String[25];
                        String name_arr[] =new String[25];
                        String acronym_arr[] = new String[25];
                        int loc =0;
                        int name=0;
                        int acro=0;
                        for (Element row : rows) {
                            if(row.select("td[align=left]").size()==3) {
                              value=(row.select("td[align=left]")).get(1).text();
                            loc_arr[loc] = value;
                            loc=loc+1;
                             
                            }              
                       }
                       Elements columns = document.select("td[colspan=3]"); 
                       for (Element column:columns)
                           {
                               name_arr[name]=column.text();
                               name=name+1;
                       }
                       Elements columns1 = document.select("td[rowspan=2]").select("td[align=left]");
                       for (Element column2:columns1)
                       {
                                 acronym_arr[acro]=column2.text();
                                 acro=acro+1;
                       }                       
                             for ( int j=0;j<20;j++)
                             {
                                 writer.write(acronym_arr[j] +"\t"+name_arr[j] +"\t"+loc_arr[j]+"\n");
                                  //System.out.println(acronym_arr[j] +"   "+name_arr[j] +"    "+loc_arr[j]+"\n");

                             }
	        		        	
	        	//IMPORTANT! Do not change the following:
	        	Thread.sleep(DELAY*1000); //rate-limit the queries
                          loc_arr=null;acronym_arr=null;name_arr=null;
	        }

        writer.close();
		} 
                } catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Given a string URL returns a string with the page contents
	 * Adapted from example in 
	 * http://docs.oracle.com/javase/tutorial/networking/urls/readingWriting.html
	 * @param link
	 * @return
	 * @throws IOException
	 */
	public static String getPageFromUrl(String link) throws IOException {
		URL thePage = new URL(link);
        URLConnection yc = thePage.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                                    yc.getInputStream()));
        String inputLine;
        String output = "";
        while ((inputLine = in.readLine()) != null) {
        	output += inputLine + "\n";
        }
        in.close();
		return output;
	}
	
	
	
	}


