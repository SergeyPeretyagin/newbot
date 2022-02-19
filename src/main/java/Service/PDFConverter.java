package Service;

import Entity.User;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PDFConverter {
    static Map<String,List<String>> usersMap=new HashMap<>();

    public static File createPDF() {
        File file = new File("/opt/tomcat/apache-tomcat-9.0.58/webapps/botexample/WEB-INF/source/newPDF.pdf");
//        File file = new File("newPDF.pdf");
        try {
            List<User> users = JSConvertor.parse();
            PdfWriter pdfWriter = new PdfWriter("/opt/tomcat/apache-tomcat-9.0.58/webapps/botexample/WEB-INF/source/newPDF.pdf");
            float[] columnWidth = {200F, 100F, 200F};
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            pdfDocument.addNewPage();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Document document = new Document(pdfDocument);
            Paragraph paragraph = new Paragraph("RED TEAM").setFontSize(50F)
                    .setFontColor(Color.RED);
            Paragraph date = new Paragraph(String.valueOf(formatter.format(new Date(System.currentTimeMillis()))))
                    .setFontSize(15F);
            document.add(paragraph).add(date);
            Table table = new Table(columnWidth);
            table.addCell(new Cell().add("User name"));
            table.addCell(new Cell().add("Spend time"));
            table.addCell(new Cell().add("Activities"));

            for (Map.Entry<String,List<String>> el : usersMap.entrySet()
            ) {
                table.addCell(new Cell(el.getValue().size(),1).add(el.getKey()));
                for (String action:el.getValue()
                ) {
                    table.addCell(new Cell().add(action.split("&%@")[0]));
                    table.addCell(new Cell().add(action.split("&%@")[1]));
                }
            }
            document.add(table);
            document.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static void groupUsers(List<User> users){
        for (User el:
                users) {
            usersMap.computeIfAbsent(el.getUser_name(),k->new ArrayList<String>()).add(el.getSpend_time()+"&%@"+el.getActivities());
        }
    }
}
