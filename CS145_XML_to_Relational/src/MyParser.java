
/* CS145 Fall 2012 */
/* Parser skeleton for processing item-???.xml files. Must be compiled in
   JDK 1.4 or above. */

/* Instructions:

   This program processes all files passed on the command line (to parse
   an entire diectory, type "java MyParser myFiles/*.xml" at the shell).

   At the point noted below, an individual XML file has been parsed into a
   DOM Document node. You should fill in code to process the node. Java's
   interface for the Document Object Model (DOM) is in package
   org.w3c.dom. The documentation is available online at

http://www.w3.org/2003/01/dom2-javadoc/org/w3c/dom/package-summary.html

A tutorial of DOM can be found at:

http://www.w3schools.com/dom/default.asp

Some auxiliary methods have been written for you. You may find them
useful.

 */


import java.io.*;
import java.text.*;
import java.util.*;

import javax.management.Descriptor;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ErrorHandler;


class MyParser {

    // Any separator will do, but we recommend you do not use '|'
    static final String columnSeparator = "<>";

    static DocumentBuilder builder;

    static final String[] typeName = {
        "none",
        "Element",
        "Attr",
        "Text",
        "CDATA",
        "EntityRef",
        "Entity",
        "ProcInstr",
        "Comment",
        "Document",
        "DocType",
        "DocFragment",
        "Notation",
    };

    static class MyErrorHandler implements ErrorHandler {

        public void warning(SAXParseException exception)
            throws SAXException {
                fatalError(exception);
            }

        public void error(SAXParseException exception)
            throws SAXException {
                fatalError(exception);
            }

        public void fatalError(SAXParseException exception)
            throws SAXException {
                exception.printStackTrace();
                System.out.println("There should be no errors " +
                        "in the supplied XML files.");
                System.exit(3);
            }

    }

    /* Non-recursive (NR) version of Node.getElementsByTagName(...) */
    static Element[] getElementsByTagNameNR(Element e, String tagName) {
        Vector< Element > elements = new Vector< Element >();
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
            {
                elements.add( (Element)child );
            }
            child = child.getNextSibling();
        }
        Element[] result = new Element[elements.size()];
        elements.copyInto(result);
        return result;
    }

    /* Returns the first subelement of e matching the given tagName, or
     * null if one does not exist. */
    static Element getElementByTagNameNR(Element e, String tagName) {
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
                return (Element) child;
            child = child.getNextSibling();
        }
        return null;
    }

    /* Returns the text associated with the given element (which must have
     * type #PCDATA) as child, or "" if it contains no text. */
    static String getElementText(Element e) {
        if (e.getChildNodes().getLength() == 1) {
            Text elementText = (Text) e.getFirstChild();
            return elementText.getNodeValue();
        }
        else
            return "";
    }

    /* Returns the text (#PCDATA) associated with the first subelement X
     * of e with the given tagName. If no such X exists or X contains no
     * text, "" is returned. */
    static String getElementTextByTagNameNR(Element e, String tagName) {
        Element elem = getElementByTagNameNR(e, tagName);
        if (elem != null)
            return getElementText(elem);
        else
            return "";
    }

    /* Returns the amount (in XXXXX.xx format) denoted by a dollar-value
     * string like $3,453.23. Returns the input if the input is an empty
     * string. */
    static String formatDollar(String money) {
        if (money.equals(""))
            return money;
        else {
            double am = 0.0;
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
            try { am = nf.parse(money).doubleValue(); }
            catch (ParseException e) {
                System.out.println("This method should work for all " +
                        "money values you find in our data.");
                System.exit(20);
            }
            nf.setGroupingUsed(false);
            return nf.format(am).substring(1);
        }
    }

    /* Returns the time (in YYYY-MM-DD HH:MM:SS format) denoted by a
     * time string like Dec-31-01 23:59:59. */
    static String formatTime(String time) {
        DateFormat outputDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat inputDf = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
        String result = "";
        try { result = outputDf.format(inputDf.parse(time)); }
        catch (ParseException e) {
            System.out.println("This method should work for all date/" +
                    "time strings you find in our data.");
            System.exit(20);
        }
        return result;
    }

    /* Process one items-???.xml file. */
    static void processFile(File xmlFile) throws IOException {

        Document doc = null;
        try {
            doc = builder.parse(xmlFile);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        }
        catch (SAXException e) {
            System.out.println("Parsing error on file " + xmlFile);
            System.out.println("  (not supposed to happen with supplied XML files)");
            e.printStackTrace();
            System.exit(3);
        }

        /* At this point 'doc' contains a DOM representation of an 'Items' XML
         * file. Use doc.getDocumentElement() to get the root Element. */
        System.out.println("Successfully parsed - " + xmlFile);

        // Get the root of the tree
        Element root = doc.getDocumentElement();

        /* 
         * ##########
         * TODO: Here you will process xmlFile
         * ##########
         */
        BufferedWriter wrItemList = new BufferedWriter (new FileWriter("ItemList.dat", true));
        BufferedWriter wrItemDescription = new BufferedWriter (new FileWriter("ItemDescription.dat", true));
        BufferedWriter wrItemBuyPrice = new BufferedWriter (new FileWriter("ItemBuyPrice.dat", true)); 
        BufferedWriter wrItemSeller = new BufferedWriter (new FileWriter("ItemSeller.dat", true));
        BufferedWriter wrBids = new BufferedWriter (new FileWriter("Bids.dat", true));
        BufferedWriter wrUserRating = new BufferedWriter (new FileWriter("UserRating.dat", true));
        BufferedWriter wrUserLocation = new BufferedWriter (new FileWriter("UserLocation.dat", true));
        BufferedWriter wrUserCountry = new BufferedWriter (new FileWriter("UserCountry.dat", true));
        BufferedWriter wrItemCategory = new BufferedWriter (new FileWriter("ItemCategory.dat", true));
        
        Element[] elements = getElementsByTagNameNR(root, "Item");
        
        for (int i=0; i < elements.length; i++){
        	Element cur = elements[i];
        	String itemID = cur.getAttribute("ItemID");
        	String name = getElementTextByTagNameNR(cur, "Name");
        	String current = formatDollar(getElementTextByTagNameNR(cur, "Currently"));
        	String firstBid = formatDollar(getElementTextByTagNameNR(cur, "First_Bid"));
        	String NoOfBids = getElementTextByTagNameNR(cur, "Number_of_Bids");
           	String started = formatTime(getElementTextByTagNameNR(cur, "Started"));        	
        	String ends = formatTime(getElementTextByTagNameNR(cur, "Ends"));
        	String Description = getElementTextByTagNameNR(cur, "Description");
//          	String BuyPrice = formatDollar(getElementTextByTagNameNR(cur, "Buy_Price"));	
          	        	
        	
//        	ItemList (ItemID, Name, Currently, First_Bid, Number_of_bids, Started, Ends, Seller_UserID, location, country)
        	wrItemList.write(itemID + columnSeparator + name + columnSeparator + current + columnSeparator + firstBid + columnSeparator + NoOfBids + columnSeparator + started + columnSeparator + ends);
        	wrItemList.newLine();
 
//        	ItemDescription (ItemID, Description)
        	wrItemDescription.write(itemID + columnSeparator + Description + "\n");
        	
        	
//        	ItemBuyPrice (ItemID, BuyPrice)
           	String BuyPrice = formatDollar(getElementTextByTagNameNR(cur, "Buy_Price"));
           	if (!BuyPrice.equals("") && BuyPrice != null ){
           		wrItemBuyPrice.write(itemID + columnSeparator + BuyPrice + "\n");
//               	System.out.println(itemID + columnSeparator + BuyPrice + "\n");
           	}

//        	wrItemBuyPriceList.write(itemID + columnSeparator + BuyPrice + "\n");

            
//        	ItemSeller (ItemID, Seller_UserID, location, country)
            Element seller = getElementByTagNameNR(cur, "Seller");
            
        	String SellerID = seller.getAttribute("UserID");
        	String SellerRating = seller.getAttribute("Rating"); 
        	String Location = getElementTextByTagNameNR(cur, "Location");
        	String Country = getElementTextByTagNameNR(cur, "Country");
        	 
        	wrItemSeller.write(itemID + columnSeparator + SellerID + "\n");
        	
//        	ItemCategory (ItemID, Category)
        	Element[] categories = getElementsByTagNameNR(cur, "Category");

        	for (int j=0; j<categories.length; j++){
        		String category = getElementText(categories[j]);
//               	System.out.println(itemID + columnSeparator + category + "\n");
        		wrItemCategory.write(itemID + columnSeparator + category);
        		wrItemCategory.newLine();
        	}

//        	Bids (ItemID, UserID, Time, Amount)
//        	UserRating (UserID, Rating)
//        	UserLocation (UserID, location)
//        	UserCountry (UserID, Country)

        	wrUserRating.write(SellerID + columnSeparator + SellerRating);
        	wrUserRating.newLine();
        	wrUserLocation.write(SellerID + columnSeparator + Location);
        	wrUserLocation.newLine();
        	wrUserCountry.write(SellerID + columnSeparator + Country);
        	wrUserCountry.newLine();
        	
        	Element bidselement = getElementByTagNameNR(cur, "Bids");
        	Element[] bidlist = getElementsByTagNameNR(bidselement, "Bid");
        	
        	for (int j=0; j<bidlist.length; j++){
        		Element bidder = getElementByTagNameNR(bidlist[j], "Bidder");
        		String UserID = bidder.getAttribute("UserID");
        		String Rating = bidder.getAttribute("Rating");
        
        		wrUserRating.write(UserID + columnSeparator + Rating);
        		wrUserRating.newLine();
        		
        		String bidTime = formatTime(getElementTextByTagNameNR(bidlist[j], "Time"));
        		String bidAmount = formatDollar(getElementTextByTagNameNR(bidlist[j], "Amount"));
        		
        		wrBids.write(itemID + columnSeparator + UserID + columnSeparator + bidTime + columnSeparator + bidAmount);
        		wrBids.newLine();
        		        		
        		String BidderLocation = getElementTextByTagNameNR(bidder, "Location");
        		if(!BidderLocation.equals("")){
        			wrUserLocation.write(UserID + columnSeparator + BidderLocation);
        			wrUserLocation.newLine();
        		}
        		
        		String BidderCountry = getElementTextByTagNameNR(bidder, "Country");
        		if(!BidderCountry.equals("")){
        			wrUserCountry.write(UserID + columnSeparator + BidderCountry);
        			wrUserCountry.newLine();
        		}
        		
        	}
        
      	
        }

        wrItemList.close();
        wrItemDescription.close();
        wrItemSeller.close();
        wrItemCategory.close();
        wrItemBuyPrice.close();
        wrBids.close();
        wrUserRating.close();
        wrUserLocation.close();
        wrUserCountry.close();
       
        
     }

    public static void main (String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java MyParser [file] [file] ...");
            System.exit(1);
        }

        /* Initialize parser. */
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            factory.setIgnoringElementContentWhitespace(true);      
            builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new MyErrorHandler());
        }
        catch (FactoryConfigurationError e) {
            System.out.println("unable to get a document builder factory");
            System.exit(2);
        } 
        catch (ParserConfigurationException e) {
            System.out.println("parser was unable to be configured");
            System.exit(2);
        }

        /* Process each of the files */
        try {

            /* Process all files listed on command line. */
            for (int i = 0; i < args.length; i++) {
                File currentFile = new File(args[i]);
                processFile(currentFile);
            }

            // Success!
            System.out.println("Success creating the SQL input files.");

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
