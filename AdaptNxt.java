package com.jsp.Demo.Mod1;

/**
 * Hello world!
 *
 */
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;

public class App {

    public static void main(String[] args) {
        String url = "https://www.quill.com/hanging-file-folders/cbk/122567.html";

        try {
            Document doc = Jsoup.connect(url).get();

            FileWriter csvWriter = new FileWriter("product_details.csv");
            csvWriter.append("Product Name,Product Price,Item Number/SKU/Product Code,Model Number,Product Category,Product Description\n");

//            Elements products = doc.select(".flex flex-wrap w-100 flex-grow-0 flex-shrink-0 ph2 pr0-xl pl4-xl mt0-xl");
            Elements products = doc.getElementsByClass("px-3 px-sm-5 container-lg");
            
            int count = 0;
            for (Element product : products) {
            	if(count==10) {
            		break;
            	}
            	String productCategory = product.select(".d-inline.h2").text();
            	//System.out.println(product.select(".blue-hover-link.scTrack.pfm.fg-jet.search-product-name.font-weight-lightbold"));
                String itemId = product.select(".body-xs.d-block.search-product-name-wrap").text();
                String productName = product.select(".body-xs.d-block.search-product-name-wrap").text();
                String productPrice = product.select(".d-inline-flex.align-items-center.price-break.price-sm").text();
                int spaceIndex = productName.indexOf(' ');
                String brand = spaceIndex != -1 ? productName.substring(0, spaceIndex) : productName;
                csvWriter.append(String.format("\"%s\",\"%s\",\"%s\",\"s\"\n",
                        productName, productPrice, itemId, productCategory));
                count++;
                }

            csvWriter.flush();
            csvWriter.close();
            System.out.println("Product details exported to product_details.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

